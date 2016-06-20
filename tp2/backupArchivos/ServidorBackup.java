package backupArchivos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorBackup implements Runnable {
	
	protected ServerSocket server;
	protected Socket so;
	protected Integer puerto;
	
	protected String carpeta;	//carpeta en donde se guardan los archivos que recibo
	protected boolean funcionando=false;
	protected Integer tiempoEspera=3000;
	
	public static void main(String[] args) {
		
	}
	
	public ServidorBackup(){
		
	}
	
	public ServidorBackup(Integer puerto){
		this.puerto=puerto;
	}
	
	@Override
	public void run() {
		funcionando=true;
		try {
			server=new ServerSocket(this.puerto);
		} catch (IOException e) {
			System.out.println("Error del tipo: ");
			e.printStackTrace();
			funcionando=false;
		}
		
		while(funcionando){
			try {
				this.so=server.accept();
				
			} catch (IOException e) {
				System.out.println("error dentro del funcionando: ");
				e.printStackTrace();
			}
			
			
		}
	}

	
	
	
}
