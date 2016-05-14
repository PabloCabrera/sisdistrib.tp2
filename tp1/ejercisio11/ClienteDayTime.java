package ejercisio11;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteDayTime {
	
	protected Socket socket;
	protected ObjectOutputStream outStrm;
	protected ObjectInputStream inStrm;
	private int puerto_destino;
	private String IP_destino;
	
	public static void main(String[] args) {
		boolean bandera=true;
		while(bandera){
			try {
				ClienteDayTime c= new ClienteDayTime();
				c.menu();
				bandera=false;
			} catch (UnknownHostException e) {
				System.out.println("no me pude conectar a ese servidor");
			} catch (IOException e) {
				System.out.println("error de InputOutput");
			}
		}
		
		
	}

	public ClienteDayTime() throws UnknownHostException, IOException{
		pedirDatos();
		socket = new Socket(this.IP_destino, this.puerto_destino);
		outStrm=new ObjectOutputStream(this.socket.getOutputStream());
		inStrm=new ObjectInputStream(this.socket.getInputStream());
	}
	
	public void menu(){
		Scanner sm= new Scanner(System.in);
		boolean bandera=true;
		try {Thread.sleep(500);} catch (InterruptedException e1) {}		//para que tarde un cachito en mostrar el menu
		while(bandera){
			try {				
				System.out.println("Menu principal");
				System.out.println("1 - pedir datos de una ciudad");
				System.out.println("2 - cerrar conexion");
				Integer entrada=sm.nextInt();
				sm.nextLine();
				
				switch(entrada){
				case 1:
					datos_ciudad();
					break;
				case 2:
					pedir_cierre();
					bandera=false;
					break;
				default: 
					System.out.println("numeros del 1 al 2!");
					break;
				}
			} catch (Exception e) {
				System.out.println("ingrese bien los datos del menu!");
				sm = new Scanner(System.in);
			}
		}//fin while
	}

	private boolean pedir_cierre() {	
		DayTimeCity mensaje = new DayTimeCity();
		mensaje.setCodigo(1); //mando un bye y cierro
		try {
			this.outStrm.writeObject(mensaje);	//envio mensaje para que le server tamb cierre sus recursos
		} catch (IOException e) {
			cerrar_conexion();
		}
		cerrar_conexion();
		
		return true;
		
	}

	private void cerrar_conexion() {	//cierro los recursos
		try {
			this.outStrm.close();
			this.inStrm.close();
			this.socket.close();
			System.out.println("----------------------  conexion finalizada! -----------------");
		} catch (IOException e) {
			
		}
	}

	private boolean datos_ciudad() {
		//leo la ciudad a buscar
		Scanner s= new Scanner(System.in);
		System.out.println("ingrese el nombre de la ciudad a buscar:");
		String ciudad=s.nextLine();
		
		//envio el mensaje con el nombre de la ciudad y codigo correspondiente
		DayTimeCity mensaje = new DayTimeCity();
		mensaje.setCiudad(ciudad);
		mensaje.setCodigo(2);		//codigo q indica q quiero info de un pais
		try {
			this.outStrm.writeObject(mensaje);
		} catch (IOException e) {
			System.out.println("no se pudo enviar el mensaje");
			return false;
		}
		try {
			//leo la respuesta que me da el servidor
			long inicio = System.currentTimeMillis();
			DayTimeCity respuesta=(DayTimeCity) this.inStrm.readObject();
			long fin = System.currentTimeMillis();
			System.out.println("timepo de respuesta: "+(fin-inicio) +" milisegundos");
			// veo cual fue la respuesta del servidor mediante el codigo
			if(respuesta.getCodigo()==3){
				System.out.println("la peticion fue mal formada! :(");
			}else{
				if(respuesta.getCodigo()==4){
					System.out.println("no hubo resultados para esa ciudad");
				}else{
					//respondio que encontro la ciudad
					System.out.println("Los datos son:");
					System.out.println(respuesta.toString());
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("clase no encontrada! :(");
			return false;
		} catch (IOException e) {
			System.out.println("error de comunicacion! :(");
			return false;
		}
		return true;
		
	}
	
	protected void pedirDatos() {		//pido los datos del servidor al user
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
}
