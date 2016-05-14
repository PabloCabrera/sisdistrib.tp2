package ejercisio3_4y7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import ejercicio1y2.HiloServidorTCP;

public class HiloServidorMensajes extends HiloServidorTCP{
	
	protected ControladorMensajes cm;
	protected ObjectOutputStream outStrm;
	protected ObjectInputStream inStrm;
	protected Mensaje mensaje;
	protected boolean conectado;
	
	public HiloServidorMensajes(Socket so, ControladorMensajes cm) {
		super();
		this.socket=so;
		try {
			outStrm=new ObjectOutputStream(this.socket.getOutputStream());
			inStrm=new ObjectInputStream(this.socket.getInputStream());
			this.conectado=true;
		} catch (IOException e) {
			cerrarConexion();
		}
		this.cm=cm;
	}
	
	@Override
	public void run(){
		while(this.conectado){
			try {
				this.mensaje=(Mensaje) inStrm.readObject();
				if(mensaje.getId_receptor()==-1){
					procesar_mensaje();
				}else{
					if(cm.guardarMensaje(this.mensaje)){
						this.outStrm.writeObject(new String("ok"));
					}else{
						this.outStrm.writeObject(new String("no pude guardar el mensaje"));
					}
				}
			} catch (ClassNotFoundException e) {
				cerrarConexion();
			} catch (IOException e) {
				cerrarConexion();
			}
		}
		
	}

	protected void cerrarConexion(){
		this.conectado=false;
		try {
			this.inStrm.close();
			this.outStrm.close();
		} catch (IOException e) {
			
		}
		System.out.println("conexion cerrada, ignore el stacktrace");
	}
	
	protected void procesar_mensaje() {
		Integer nro_operacion= Integer.parseInt(this.mensaje.getMsj());
		switch(nro_operacion){
			case -1:
				tengoMensajes();
				break;
			case -2:
				descargarMensajes();
				break;
			default:
				enviarError();
		}
	
	
	
	}

	protected void enviarError() {
		Mensaje respuesta= new Mensaje(this.mensaje.getId_emisor(),-1,"error al parsear el tipo de mensaje enviado al servidor");
		try {
			this.outStrm.writeObject(respuesta);
		} catch (IOException e) {
			cerrarConexion();
			System.out.println("error al enviar respuesta de error");
		}
	}

	protected void descargarMensajes() {
		System.out.println("por enviar los mensajes al cliente");
		ArrayList<Mensaje> mensajes=this.cm.getMensajesPorEmisor(this.mensaje.getId_emisor());
		Integer cantidadMensajes= mensajes.size();
		System.out.println("le envio que tiene "+cantidadMensajes+" nro de mensajes");
		Mensaje enviado= new Mensaje(mensaje.getId_emisor(),-1,cantidadMensajes);
		
		try {
			this.outStrm.writeObject(enviado);
			System.out.println("ya le envie la cantidad de mensajes");
			Integer i=0;
			boolean error=false;
			while(!error && i<cantidadMensajes){
				this.outStrm.writeObject(mensajes.get(i));
				System.out.println("ya le envie el mensajes nro: "+i);
				Mensaje respuesta=(Mensaje)this.inStrm.readObject();
				System.out.println("me respondio el mensaje enviado");
				if(respuesta.getMsj().equals("ok")){
					cm.borrarMensaje(mensajes.get(i));
					i++;
				}else{
					this.outStrm.writeObject(mensajes.get(i));
					respuesta=(Mensaje)this.inStrm.readObject();
					if(!respuesta.getMsj().equals("ok")){
						this.outStrm.writeObject(new Mensaje(-1,-1,"cerrar"));
						error=true;
						this.conectado=false;
						throw new IOException("error en la conexion!");
					}
				}
			}
		} catch (IOException e) {
			cerrarConexion();
		} catch (ClassNotFoundException e) {
			cerrarConexion();
		}
	}

	protected void tengoMensajes() {
		boolean tienemensajes = this.cm.hayMensajesPorEmisor(this.mensaje.getId_emisor());
		Mensaje enviado;
		if(tienemensajes){
			System.out.println("el ID: "+this.mensaje.getId_emisor()+" tiene mensajes");
			enviado= new Mensaje(mensaje.getId_emisor(),-1,"si");
		}else{
			System.out.println("el ID: "+this.mensaje.getId_emisor()+" NO tiene mensajes");
			enviado= new Mensaje(mensaje.getId_emisor(),-1,"no");
		}
		try {
			this.outStrm.writeObject(enviado);
		} catch (IOException e) {
			cerrarConexion();
		}
	}

}
