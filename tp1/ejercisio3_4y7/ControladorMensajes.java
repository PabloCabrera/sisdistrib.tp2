package ejercisio3_4y7;

import java.util.ArrayList;
import java.util.Scanner;

public class ControladorMensajes {
	
	protected ArrayList<Mensaje> mensajes;
	
	public ControladorMensajes(){
		mensajes= new ArrayList<Mensaje>();
	}
	
	//recibe un mensaje y lo guarda
	synchronized public boolean guardarMensaje(Mensaje mensaje) {
		System.out.println();
		System.out.println("mensaje a guardar: "+mensaje.toString());
		mensajes.add(mensaje);
		return true;
	}

	//devuelve los mensajes por emidor
	synchronized public ArrayList<Mensaje> getMensajesPorEmisor(Integer id_emisor) {
		System.out.println("mensajes del id: "+id_emisor);
		ArrayList<Mensaje> respuesta= new ArrayList<Mensaje>();
		for(Mensaje m: this.mensajes){
			if(m.getId_receptor().intValue()==id_emisor.intValue()){
				respuesta.add(m);
				System.out.println(m.toString());
			}
		}
		return respuesta;
	}

	//con que haya un solo mensaje para ese id ya devuelve true
	synchronized public boolean hayMensajesPorEmisor(Integer id_emisor) {
		for(Mensaje m: this.mensajes){
			Integer id= (m.getId_receptor());
			if(id.intValue()==id_emisor.intValue()){
				return true;
			}
		}
		return false;
	}

	//borra el mensaje recibido
	synchronized public void borrarMensaje(Mensaje mensaje) {
		if(this.mensajes.contains(mensaje)){
			mensajes.remove(mensaje);
		}else{
			System.out.println("no existe el objeto a borrar");
		}
	}

}
