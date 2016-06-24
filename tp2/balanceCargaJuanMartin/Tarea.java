package balanceCargaJuanMartin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class Tarea implements Runnable {
	private double valor;
	Socket s;
	
	public Tarea(Socket ss) {
		s = ss;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("BS: cliente conectado");
		try {
			PrintWriter p = new PrintWriter (s.getOutputStream(), true);
			valor =2;
			for (int i= 1; i < 10; i++) {
				p.println(valor);
				Thread.sleep(5000);
				valor *= valor;
			}
			System.out.println("Valor calculado " + valor);
			p.println(valor);
			p.println("END");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
