package balanceCargaJuanMartin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class ThreadReader implements Runnable {
	BufferedReader bR;
	PrintWriter pW;
	
	public ThreadReader(BufferedReader b, PrintWriter p) {
		bR = b;
		pW = p;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String s;
		try {
			System.out.println("TR: leyendo");
			s = bR.readLine();
			System.out.println("TR: " + s);
			//while (!(s.equals(null)) && (!(s.equals("END"))) ) {
			while (!(s.equals("END"))) {
				pW.println(s);
				s = bR.readLine();
				System.out.println("TR: " + s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
