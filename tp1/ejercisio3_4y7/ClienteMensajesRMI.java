package ejercisio3_4y7;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteMensajesRMI {

	protected InterfazRMIEj3_4_7 rmiservidor;
	protected Registry registro;
	protected String direccionServidor;
	protected Integer puertoServidor;
	
	protected Integer id_emisor;
	protected ArrayList<Mensaje> mensajes;
	
	public ClienteMensajesRMI(String ip, int puerto) {
		direccionServidor = ip;
		puertoServidor= puerto;
		mensajes = new ArrayList<Mensaje>();
		this.conectarse();		
		
	}

	
	
	protected void conectarse(){
		try {
			registro = LocateRegistry.getRegistry(direccionServidor, puertoServidor);
			rmiservidor = (InterfazRMIEj3_4_7)(registro.lookup("rmiServidorMensajes"));
			menu();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
			
	}
		
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
				int entrada=sm.nextInt();
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
				e.printStackTrace();
				sm = new Scanner(System.in);
			}
		}//fin while
		sm.close();
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
	
	try {
		if(this.rmiservidor.tengoMensajes(id_emisor)){
			ArrayList<Mensaje> nueva=this.rmiservidor.devolverMensajes(id_emisor);
			if(nueva==null){
				System.out.println("no se pudieron descargar los mensajes");
				return false;
			}
			this.mensajes.addAll(nueva);
			System.out.println("todos los mensajes han sido descargados, estos han sido borrados del servidor!");
		}else{
			System.out.println("no tiene mensajes para descargar!");
		}
	} catch (RemoteException e) {
		e.printStackTrace();
	}
	
	return true;
}

private void tengoMensajes() {
	
	try {
		if(this.rmiservidor.tengoMensajes(id_emisor)){
			System.out.println("Tiene mensajes en el servidor! :D");
		}else{
			System.out.println("NO tiene mensajes en el servidor");
		}
	} catch (RemoteException e) {
		e.printStackTrace();
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
	
	if(this.rmiservidor.enviarMensaje(mensaje)){
		System.out.println("Mensaje enviado correctamente");
	}
	} catch (IOException e) {
		System.out.println("algo fallo :(");
		e.printStackTrace();
	}
	//s2.close();
}


	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ClienteMensajesRMI cmrmi= new ClienteMensajesRMI("localhost",5555);
	}

}
