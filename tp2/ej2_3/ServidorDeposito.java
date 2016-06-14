package ejercicio2y3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Scanner;

public class ServidorDeposito implements Runnable {
	
	protected Integer puerto;
	protected ServerSocket serversocket;
	protected ArrayList<Runnable> ThreadsDepositos;
	protected String nombre="Servidor Deposito";
	
	protected boolean esperarConexiones;
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Ingrese en puerto en el cual escuchar");
		try {
			ServidorDeposito sd= new ServidorDeposito( s.nextInt() );
		} catch (IOException e) {
			System.out.println("ERROR: ");
			e.printStackTrace();
		}
	}
	
	public ServidorDeposito() throws IOException{
		this(4500);
	}
	
	public ServidorDeposito(Integer puerto) throws IOException{
		this.puerto=puerto;
		this.esperarConexiones=true;
		this.ThreadsDepositos = new ArrayList<Runnable>();
		this.serversocket = new ServerSocket(this.puerto);
		this.serversocket.setSoTimeout(1000);
		
	}
	
	protected boolean levantarServicio(){
		System.out.println(this.nombre+": LEVANTANDO SERVICIO");
		while(this.esperarConexiones){
			try {
				Socket so=this.serversocket.accept();
				this.recibiConexion(so);
			}catch(SocketTimeoutException e){
				//si sale por el timeout no pasa nada
			}
			catch (IOException e) {
				System.out.println("ERROR: ");
				e.printStackTrace();
				System.out.println("FIN DEL SERVIDOR! ");
				this.esperarConexiones=false;
			}
		}
		return true;
	}

	@Override
	public void run() {
		levantarServicio();
	}
	
	protected void recibiConexion(Socket so){
		System.out.println("+ "+this.nombre+": recibi una conexion desde "+so.getInetAddress());
		Thread t= new Thread(new ThreadDeposito(so));
		t.start();
		this.ThreadsDepositos.add(t);
	}
}
