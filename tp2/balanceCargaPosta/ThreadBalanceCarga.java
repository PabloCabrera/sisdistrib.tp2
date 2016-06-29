package balanceCargaPosta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import otros.Hilo;

public class ThreadBalanceCarga extends Hilo {
	
	protected Integer id;
	
	protected Socket socketServidor;
	protected String ipServidor;
	protected Integer puertoServidor;
	protected ObjectOutputStream enviarAServidor;
	protected ObjectInputStream leerDeServidor;
	protected boolean conectadoServidor;
	
	
	public ThreadBalanceCarga(Socket so, Integer nrohilo,String ipServidor, Integer puerto) {
		super(so);
		this.id=nrohilo;
		this.ipServidor=ipServidor;
		this.puertoServidor=puerto;
		this.conectadoServidor=false;
	}
	
	public boolean ConectarseAServidor(){
		try {
			this.socketServidor=new Socket(ipServidor,puertoServidor);
			this.leerDeServidor=new ObjectInputStream( this.s.getInputStream() );
			this.enviarAServidor=new ObjectOutputStream( this.s.getOutputStream() );
			this.conectadoServidor=true;
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public void run(){
		if(this.conectadoServidor){
			try {
				//este hilo se encarga de leer lo que manda el cliente y mandarselo al servidor
				Thread lectura = new Thread(new ThreadLecturaEnvio(this.inStrm,this.enviarAServidor));	
				lectura.start();
				//este hilo hace al reves, lo que le envia el servidor se lo envia al cliente
				Thread escritura = new Thread(new ThreadLecturaEnvio(this.leerDeServidor,this.outStrm));
				//espero que lo hilos terminen, terminan cuando se corta la comunicacion
				lectura.start();
				lectura.join();
				escritura.join();
			} catch (Exception e) {
				//TODO borra excepcion una vez q ande todo bien
				e.printStackTrace();
			}
		}
	}
	
}