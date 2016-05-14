package ejercicio1y2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteEchoTCP {
	
	protected Integer puerto_destino;
	protected String IP_destino;
	protected Socket socket;
	protected PrintWriter printer;
	protected BufferedReader reader;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		boolean bandera=true;
		while(bandera){
			try {
				ClienteEchoTCP ce= new ClienteEchoTCP(bandera);
				bandera=false;
			} catch (UnknownHostException e) {
				System.out.println("no pude encontrar el servidor");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("otro error");
				e.printStackTrace();
			}	
		}
		
	}
	
	@SuppressWarnings("resource")
	protected void pedirDatos() {
		Scanner s= new Scanner(System.in);
		boolean bandera=true;
		String ip="localhost";
		Integer p=5555;
		while(bandera){
			try{
			System.out.println("ingrese la IP del servidor:");
			ip=s.nextLine();
			System.out.println("ingrese el puerto del servidor:");
			p=s.nextInt();
			bandera=false;
			}catch(Exception e){
				System.out.println("escribime bien las cosas GIL!");
				s= new Scanner(System.in);
			}
		}
		this.IP_destino=ip;
		this.puerto_destino=p;
	}

	public ClienteEchoTCP() throws UnknownHostException,IOException {		
	}
	
	public ClienteEchoTCP(boolean b) throws UnknownHostException,IOException {
		pedirDatos();
		socket = new Socket(this.IP_destino, this.puerto_destino);
		this.printer = new PrintWriter(this.socket.getOutputStream(), true);
		this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		System.out.println("me conecte bien con el servidor! :D");
		this.hacerecho();
		
	}
	
	@SuppressWarnings("resource")
	protected void hacerecho(){
		Scanner s= new Scanner(System.in);
		String msj;
		boolean bandera=true;
		while(bandera){
			try {
				System.out.println("ingrese el texto a mandar al servidor");
				msj=s.nextLine();
				printer.println(msj);
				printer.flush();
				String e=reader.readLine();
				System.out.println(e);
				if(msj.equals("quit")){
					bandera=false;
				}
			} catch (IOException e) {
				System.out.println("algo exploto en el cliente");
				bandera=false;
				e.printStackTrace();
			}
		}//fin while
		
		try {
			printer.close();
			reader.close();
		} catch (IOException e) {
			System.out.println("algo exploto en el cliente al cerrar los recursos");
			e.printStackTrace();
		}
	}
	
	
}
