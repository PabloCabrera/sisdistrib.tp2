package ej1_9;

import java.net.Socket;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

class HiloCliente extends Thread {
	ServidorArchivos servidor;
	Socket socket = null;
	InputStream input = null;
	OutputStream output = null;
	PrintStream printer = null;

	public HiloCliente (Socket socket, ServidorArchivos servidor) {
		this.servidor = servidor;
		this.socket = socket;
		
		try {
			this.input  = socket.getInputStream();
			this.output = socket.getOutputStream();
			this.printer = new PrintStream (output);
		} catch (IOException e) {
			System.err.println("Error en conexion con cliente");
		}
	}

	@Override
	public void run() {
		boolean cerrar = false;
		String linea = null;
		String dir = null;
		File fdir = null;
		String resultado = null;

		this.servidor.log ("CONEXION ABIERTA: " + this.socket.getRemoteSocketAddress());

		while ( (!this.socket.isInputShutdown()) && (!cerrar)) {
			try {
				linea = this.getLinea();

				if(linea != null) {

					/* Cambiar directorio de trabajo */
					if (linea.matches ("^put .*$")) {
						// Subir archivo
						this.put(linea.replaceFirst("del ", ""));
					} else if (linea.matches ("^del .*$")) {
						// Eliminar archivo
						this.del(linea.replaceFirst("del ", ""));
					} else if (linea.matches ("^get .*$")) {
						// Recuperar archivo
						this.get(linea.replaceFirst("get ", ""));
					} else if (linea.matches ("^dir.*$")) {
						// Mostrar listado de directorio
						this.dir();
					} else {
						// Mostrar cartel de comando desconocido
					}
				} else {
					cerrar = true;
				}
			} catch (Exception e) {
				System.out.println("Excepcion: " + e.getMessage());
				cerrar = true;
			}
		}

		this.servidor.log("CLOSED " + this.socket.getRemoteSocketAddress());
	}

	public void put (String filename) {
		this.servidor.log("PUT "+filename+"\t"+ this.socket.getRemoteSocketAddress());
	}

	public void get (String filename) {
		this.servidor.log("GET "+filename+"\t"+ this.socket.getRemoteSocketAddress());
		File f = new File (filename);
	}

	public void dir () {
		this.servidor.log("DIR"+"\t"+ this.socket.getRemoteSocketAddress());
	}

	public void del (String filename) {
		this.servidor.log("DEL "+filename+"\t"+ this.socket.getRemoteSocketAddress());
		boolean borrado = false;
		try {
			File f = new File (filename);
			borrado = f.delete();
		} catch (SecurityException e) {
			this.servidor.log ("ERROR DEL " + filename+"\t"+ this.socket.getRemoteSocketAddress());
		}
		if (borrado) {
			this.printer.println("DELETED "+filename);
		} else {
			this.printer.println("ERROR");
		}
	}

	public String getLinea() {
		int leido=0;
		ByteArrayOutputStream barray = new ByteArrayOutputStream();
		boolean continuar = true;

		while (continuar) {
			try {
				leido = this.input.read();
				if (leido == '\r') {
				//hacer nada
				} else if (leido == '\n') {
					continuar = false;
				} else {
					barray.write(leido);
				}
			} catch (Exception e) {
				continuar = false;
			}
		}
		return barray.toString();
	}
}
