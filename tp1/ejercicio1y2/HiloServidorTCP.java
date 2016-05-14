package ejercicio1y2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloServidorTCP extends Thread {
	
	protected Socket socket;
	protected PrintWriter printer;
	protected BufferedReader reader;
	
	public HiloServidorTCP(Socket so) {
		this.socket=so;
		try {
			this.printer = new PrintWriter(this.socket.getOutputStream(), true);
			this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("explote al crear el buffer y el reader");
			e.printStackTrace();
		}
	}
	
	public HiloServidorTCP() {
		//contructor vacio para cuando heredo
	}

	@Override
	public void run(){
		 boolean bandera=true;
		while(bandera){
			try {
				String s= reader.readLine();				//leo lo que me mando el cliente
				
				printer.println("Echo:"+s);					//le devuelvo lo q mando mas el ECHO
				printer.flush();							//fuerzo al sistema a vaciar el buffer aunque no este lleno
				if(s.equals("quit")){
					printer.println("Cerrando servicio ECHO");
					bandera=false;
				}
			} catch (IOException e) {
				bandera=false;
				printer.println("exploto la comunicacion con el cliente!");
			}
		}//fin del while

		try {
			printer.close();								//cierro los recursos
			reader.close();
		} catch (IOException e) {
			System.out.println("ya estaban cerrado los recursos");
		}
	}//fin del run
	
	
}//fin de la clase