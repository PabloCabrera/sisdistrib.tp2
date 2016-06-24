package balanceCargaJuanMartin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

	public static void main(String args[]) {
		try {
			Socket s = new Socket("localhost", 5000);
			BufferedReader b = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String valor = b.readLine();
			while ( (!(valor.equals(null))) && (!(valor.equals("END"))) ) {
				System.out.println("Client: recibi " + valor);
				valor = b.readLine();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
