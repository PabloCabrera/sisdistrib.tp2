package balanceCargaPosta;

import java.net.Socket;

import otros.Hilo;

public class ThreadBalanceCarga extends Hilo {
	
	protected Integer id;
	
	public ThreadBalanceCarga(Socket so, Integer nrohilo) {
		super(so);
		this.id=nrohilo;
	}
	
	@Override
	public void run(){
		
	}
}
