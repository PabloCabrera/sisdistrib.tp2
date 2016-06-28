package balanceCargaPosta;

import java.io.IOException;
import java.net.Socket;

import ej2_3.ThreadDeposito;
import otros.Servidor;

public class ServidorBalanceCarga extends Servidor {
	
	protected Integer nrohilo=0;
	
	public static void main(String[] args) {
		try {
			ServidorBalanceCarga server = new ServidorBalanceCarga(6000);
			new Thread(server).start();
		} catch (IOException e) {
			System.out.println("no pude iniciar el servidor de balance de carga");
			e.printStackTrace();
		}
	}
	
	public ServidorBalanceCarga() throws IOException {
		this(6000);
	}

	public ServidorBalanceCarga(int i) throws IOException {
		super(i);
		this.nombre="servidor balance de carga";
	}
	
	@Override
	public void run(){
		//metodo que crea un hilo que espera mensajes UDP de parte de los servidores
		//los agrega a la lista de servidores, y crea un hilo que cheque que los servidores estan vivos
		if(this.levantarBuscardorServidores()){
			//una vez tengo este servicio levantado, puedo funcionar como balanceador de carga y levanto el servicio
			this.levantarServicio();
		}else{
			System.out.println("Error al levantar el servicio buscador de servidores");
		}
		
	}

	protected boolean levantarBuscardorServidores() {
		return true;
	}
	
	@Override
	protected void recibiConexion(Socket so){
		System.out.println("+ "+this.nombre+": recibi una conexion desde "+so.getInetAddress());
		Thread t= new Thread(new ThreadBalanceCarga(so,this.nrohilo));
		t.start();
		this.threads.add(t);
	}
	
	
}
