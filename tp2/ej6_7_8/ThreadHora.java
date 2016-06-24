package ej6_7_8;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class ThreadHora implements Runnable {
	
	protected Socket s;
	protected ServidorHora servidorHora;
	protected ObjectOutputStream outStrm;
	protected ObjectInputStream inStrm;
	protected Date fechayhora;
	
	public ThreadHora(Socket so, ServidorHora servidorHora) {
		this.s=so;
		this.servidorHora=servidorHora;
		try {
			this.inStrm=new ObjectInputStream( this.s.getInputStream() );
			this.outStrm=new ObjectOutputStream( this.s.getOutputStream() );
		} catch (Exception e) {
			System.out.println("Error: ");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		boolean enviando=true;
		while(enviando){
			try {
				this.inStrm.readObject();
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("ThreadHora: se desconecto el cliente");
				enviando=false;
			}
			if( this.servidorHora.pedirHora() !=null){
				try {
					this.outStrm.writeObject(this.fechayhora);
				} catch (IOException e) {
					System.out.println("ThreadHora: no pude enviar la hora");
					enviando=false;
				}
			}else{
				System.out.println("ThreadHora: no consegui hora del master");
				enviando=false;
			}
			
		}
	}

	protected void cerrarConexion() {
		try {
			if(this.inStrm!=null){
				this.inStrm.close();
			}
			if(this.outStrm!=null){
				this.outStrm.close();
			}
			if(this.s!=null){
				this.s.close();
			}
		} catch (IOException e) {
			//no me interesan los errores q haya aca
		}
	}
}