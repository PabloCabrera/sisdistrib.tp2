package ej2_3;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServidorExtraccion extends ServidorDeposito {
	
	public ServidorExtraccion() throws IOException {
		super();
		this.nombre="Servidor Extracciones";
	}

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
	
	protected void recibiConexion(Socket so){
		System.out.println("+ "+this.nombre+": recibi una conexion desde "+so.getInetAddress());
		Thread t= new Thread(new ThreadExtraccion(so));
		t.start();
		this.ThreadsDepositos.add(t);
	}
}
