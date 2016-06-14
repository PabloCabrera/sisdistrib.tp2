package ej1_9;

import java.net.Socket;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

class HiloCliente extends Thread {
	ServidorArchivos servidor;
	Socket socket = null;
	BufferedReader input = null;
	PrintStream output = null;

	public HiloCliente (Socket socket, ServidorArchivos servidor) {
		this.servidor = servidor;
		this.socket = socket;
		
		try {
			this.input  = new BufferedReader (new InputStreamReader (socket.getInputStream()));
			this.output = new PrintStream (socket.getOutputStream());
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
			this.mostrarPrompt();
			try {
				linea = this.input.readLine();

				if(linea != null) {

					/* Cambiar directorio de trabajo */
					if (linea.matches ("^put .*$")) {
						dir = null;
					} else if (linea.matches ("^del .*$")) {
						// Eliminar archivo
					} else if (linea.matches ("^get .*$")) {
						// Recuperar archivo
					} else if (linea.matches ("^dir .*$")) {
						// Mostrar listado de directorio
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

		this.servidor.log("CONEXION CERRADA: " + this.socket.getRemoteSocketAddress());
	}	

	private void mostrarPrompt() {
		this.output.print("> ");
	}
	
}
