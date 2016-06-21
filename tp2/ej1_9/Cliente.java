package ej1_9;

import java.util.Scanner;
import java.net.Socket;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.io.IOException;

public class Cliente {
	Socket socket = null;
	OutputStream out;
	PrintStream printer;


	public Cliente (String servidor, int puerto) throws UnknownHostException, IOException {
		this.socket = new Socket(servidor, puerto);
		out = socket.getOutputStream();
		printer = new PrintStream (out);
	}

	public void interactivo() {
		Scanner s= new Scanner(System.in);
		String msj;
		boolean continuar=true;

		if (this.socket == null) {
			System.out.println("No se ha conectado a un servidor");
			continuar = false;
		} else {
			System.out.println("Comandos:");
			System.out.println("\tget nombre_archivo : Descargar archivo del servidor");
			System.out.println("\tput nombre_archivo : Subir archivo al servidor");
			System.out.println("\tdir: Mostrar archivos en servidor");
			System.out.println("\tdel nombre_archivo: Eliminar archivo del servidor");
			System.out.println("\tquit: Salir");
			System.out.println("");
		}

		while(continuar){
			try {
				msj=s.nextLine();
				this.printer.println(msj);
				this.printer.flush();
				System.out.print("> ");
				if(msj.equals("quit")){
					continuar=false;
				}
			} catch (Exception e) {
				System.out.println("ERROR");
				continuar=false;
				e.printStackTrace();
			}
		}
	}


	public static void main(String args[]) {
		String servidor=null;
		int puerto=-1;
		Scanner sc;
		Cliente cl;

		if (args.length == 2) {
		} else {
			sc = new Scanner(System.in);
			System.out.print("Conectarse al servidor: ");
			servidor = sc.nextLine();
			System.out.print("Puerto: ");
			try {
				puerto = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
				System.out.println("Error: Numero de puerto no valido");
			}

			if (puerto != -1) {
				try {
					cl = new Cliente(servidor, puerto);
					cl.interactivo();
				} catch (Exception e) {
					System.out.println("Error: No se puede conectar al servidor");
				}
			}
		}
	}

}
