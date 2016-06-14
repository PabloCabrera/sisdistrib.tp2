package ejercicio2y3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadDeposito implements Runnable {
	
	protected Socket s;
	protected ObjectOutputStream outStrm;
	protected ObjectInputStream inStrm;
	
	protected boolean conectado;
	
	public ThreadDeposito(Socket so) {
		this.s=so;
		try {
			this.inStrm=new ObjectInputStream( this.s.getInputStream() );
			this.outStrm=new ObjectOutputStream( this.s.getOutputStream() );
		} catch (IOException e) {
			System.out.println("Error: ");
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		
	}

}
