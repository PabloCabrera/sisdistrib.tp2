package ej1_9;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class ServidorArchivos {
	/* Debe inicializarse y escuchar en un puerto por conexiones entrantes*/
	/* Debe aceptar los comandos:
	put subir archivo
	del eliminar archivo
	get recuperar archivo
	dir listar archivos */
	/* Debe sincronizarse con un servidor de backup */
	/* Debe registrar todo en un log */

	private ServerSocket servidor;
	private List<HiloCliente> clientes = new ArrayList<HiloCliente>();
	private PrintStream streamLog;
	private int puerto;
	private boolean terminar = false;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");

	public static void main (String[] args) {
		int arg_puerto = 9019;
		String arg_logfile = null;
		ServidorArchivos servidor_archivos;

		
		if (args.length > 1) {
			arg_logfile = args[1];
		}

		if (args.length > 0) {
			try {
				arg_puerto = Integer.parseInt (args[0]);
			} catch (NumberFormatException e) {
				System.err.println ("Warning: \"" + args[0] + "\" no es un numero de puerto valido. Se utilizara el puerto predeterminado: " + arg_puerto);
			}
		} else {
			Scanner scan = new Scanner (System.in);
			try {
				System.out.print ("Ingresar numero de puerto: ");
				String stringPuerto = scan.nextLine();
				arg_puerto = Integer.parseInt(stringPuerto);
			} catch (NumberFormatException e) {
				System.err.println ("Warning: Puerto no valido. Se utilizara el puerto predeterminado: " + arg_puerto);
			}
				System.out.print ("Ingresar nombre de archivo log: ");
				arg_logfile = scan.nextLine();
		}

		servidor_archivos = new ServidorArchivos (arg_puerto, arg_logfile);
		
	}
	
	public ServidorArchivos (int puerto, String archivoLog) {
		this.puerto = puerto;
		this.inicializarLog (archivoLog);
		try {
			this.inicializarSocket (puerto);
			this.escuchar();
		} catch (RuntimeException e) {
			System.err.println(e.getMessage());
		}

	}

	private void inicializarLog (String archivoLog) {
		try {
			this.streamLog = new PrintStream (new FileOutputStream (archivoLog, true));
			
		} catch (Exception e) {
			System.err.println ("Warning: No se puede abrir el archivo de log. Se utilizara la salida estandar en su lugar.");
			this.streamLog = System.out;
		
		}

	}

	private void inicializarSocket (int puerto) {
		try {
			this.servidor = new ServerSocket(this.puerto);
		} catch (IOException e) {
			throw new RuntimeException("No se puede abrir el puerto especificado", e);
		}
	}

	private void escuchar() {
		while (!this.terminar){
			Socket socket_cliente = null;
			HiloCliente hilo_cliente = null;
			try {
				socket_cliente = this.servidor.accept();
				hilo_cliente = new HiloCliente (socket_cliente, this);
				this.clientes.add(hilo_cliente);
				hilo_cliente.start();
			} catch (Exception e) {
					System.err.println("Error intentando aceptar conexion entrante");
					System.err.println(e.getMessage());
					this.terminar = true;
			}
		}
	}


	public synchronized void log(String mensaje) {
		this.streamLog.println (this.ahora() + mensaje);
	}

	private String ahora () {
		return ServidorArchivos.dateFormat.format(new Date());
	}
}

