package ejercisio3_4y7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import ejercicio1y2.ClienteEchoTCP;

public class ClienteTCPMensajes extends ClienteEchoTCP {
	
	private ArrayList<Mensaje> mensajes;
	private Integer id_emisor;
	protected ObjectOutputStream outStrm;
	protected ObjectInputStream inStrm;
	
	public static void main(String[] args){
		ClienteTCPMensajes c;
		boolean bandera=true;
		while(bandera){
			try {
				c = new ClienteTCPMensajes();
				c.menu();
				bandera=false;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ClienteTCPMensajes() throws UnknownHostException, IOException{
		super(); //llamo al constructor superior que se encarga de pedir datos y hacer conexion con el servidor
		pedirDatos();
		socket = new Socket(this.IP_destino, this.puerto_destino);
		outStrm=new ObjectOutputStream(this.socket.getOutputStream());
		inStrm=new ObjectInputStream(this.socket.getInputStream());
		this.mensajes= new ArrayList<Mensaje>();
	}
	
	@SuppressWarnings("resource")
	public void menu(){
		Scanner sm= new Scanner(System.in);
		boolean bandera=true;
		//int entrada;
		this.id_emisor=-1;
		
		while(this.id_emisor<=0){
			try{
				System.out.println("ingrese su ID");
				this.id_emisor= sm.nextInt();
			}catch(Exception e){
				sm=new Scanner(System.in);
			}
		}
		
		while(bandera){
			try {				
				System.out.println("Menu principal");
				System.out.println("1 - mensaje nuevo");
				System.out.println("2 - tengo mensajes?");
				System.out.println("3 - descargar mensajes");
				System.out.println("4 - visualizar mensajes descargados");
				System.out.println("5 - salir");
				Integer entrada=sm.nextInt();
				sm.nextLine();
				
				switch(entrada){
				case 1:
					mensaje_nuevo();
					break;
				case 2:
					tengoMensajes();
					break;
				case 3: 
					descargarMensajes();
					break;
				case 4:
					visualizarMensajes();
					break;
				case 5:
					bandera=false;
					System.out.println("chau!");
					break;
				default: 
					System.out.println("numeros del 1 al 5!");
					break;
				}
			} catch (Exception e) {
				System.out.println("ingrese bien los datos del menu!");
				e.getMessage();
				e.getLocalizedMessage();
				e.getCause();
				sm = new Scanner(System.in);
			}
		}//fin while
		
	}
	
	private boolean visualizarMensajes() {
		if(this.mensajes.isEmpty()){
			System.out.println("NO TIENE MENSAJES DESCARGADOS!");
			return false;
		}else{
			System.out.println("Usted tiene: "+mensajes.size()+" mensajes");
			System.out.println("----------------------------------------");
			for(Mensaje m: mensajes){
				System.out.println("emisor: "+m.getId_emisor());
				System.out.println("receptor: "+m.getId_receptor());
				System.out.println("mensaje"+m.getMsj());
				System.out.println("----------------------------------------");
			}
		}
		return true;
	}

	private boolean descargarMensajes() {
		//mensaje con receptor -1 significa q es para el sistema, que el mensaje sea -2 significa q pido mis mensajes
		Mensaje mensaje= new Mensaje(-1,this.id_emisor,-2);
		try {
			outStrm.writeObject(mensaje);
			outStrm.flush();
			String respuesta=((Mensaje)(inStrm.readObject())).getMsj();
			Integer e= Integer.parseInt(respuesta);
			if(e==0){
				System.out.println("Usted no tiene mensajes en el servidor!");
				return false;
			}else{
				System.out.println("Usted tiene "+e+" mensajes en el servidor!");
			}
			for (int i=0;i<e;i++){
				mensajes.add( (Mensaje) inStrm.readObject() );	
				//le respondo al servidor un ok
				outStrm.writeObject(new Mensaje(-1,this.id_emisor,"ok")); 
				outStrm.flush();
			}
			System.out.println("todos los mensajes han sido descargados, estos han sido borrados del servidor!");
		} catch (IOException e) {
			System.out.println("algo fallo :(");
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			System.out.println("clase no encontrada! :(");
			e1.printStackTrace();
		}
		return true;
	}

	private void tengoMensajes() {
		//mensaje con receptor -1 significa q es para el sistema, que el mensaje sea -1 significa q pregunta si tengo msjs
		Mensaje mensaje= new Mensaje(-1,this.id_emisor,-1);
		try {
			this.outStrm.writeObject(mensaje);
			outStrm.flush();
			String e=((Mensaje) inStrm.readObject()).getMsj();	
			if(e.equals("si")){
				System.out.println("Tiene mensajes en el servidor! :D");
			}else{
				if(e.equals("no")){
					System.out.println("NO Tiene mensajes en el servidor! :(");
				}else{
					System.out.println("no se pudo interpretar la respuesta del servidor! :0");
				}
			}
			} catch (IOException e) {
				System.out.println("algo fallo :(");
				e.printStackTrace();
			} catch (ClassNotFoundException e1) {
				System.out.println("fallo la lectura de lo que mando el servidor");
				e1.printStackTrace();
			}
		
	}

	private void mensaje_nuevo(){
		Mensaje mensaje;
		Scanner s2= new Scanner(System.in);
		String msj="";
		Integer id_receptor=0;
		boolean bandera=true;
		while(bandera){
			try {
				System.out.println("ingrese el ID numerico del receptor del mensaje(mayor que cero)");
				id_receptor=s2.nextInt();
				if(id_receptor<=0){
					throw new Exception("id_receptor <= 0");
				}
				System.out.println("ingrese el texto del mensaje");
				s2.nextLine();
				msj= s2.nextLine();
				bandera=false;
			} catch (Exception e) {
				System.out.println("ingrese bien los datos de receptor o el mensaje!");
				s2= new Scanner(System.in);
			}
		}//fin while
		
		System.out.println("enviando mensaje");
		mensaje = new Mensaje(id_receptor, id_emisor, msj);
		try {
		outStrm.writeObject(mensaje);
		outStrm.flush();
		String e=((Mensaje) inStrm.readObject()).getMsj();
		if(e.equals("ok")){
			System.out.println("Mensaje enviado correctamente");
		}
		} catch (IOException e) {
			System.out.println("algo fallo :(");
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			System.out.println("algo fallo :(");
			e1.printStackTrace();
		}
		s2.close();
	}
}//fin de la clase