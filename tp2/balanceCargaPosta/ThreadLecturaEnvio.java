package balanceCargaPosta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ThreadLecturaEnvio implements Runnable {
	
	protected ObjectOutputStream enviar;
	protected ObjectInputStream leer;
	
	public ThreadLecturaEnvio(ObjectInputStream lectura, ObjectOutputStream escritura) {
		this.leer=lectura;
		this.enviar=escritura;
	}

	//metodo que lo unico q hace es leer de un stream y mandar al otro.
	//si hay error el hilo termina
	@Override
	public void run() {
		boolean error=false;
		while(!error){
			try {
				Object o=leer.readObject();
				enviar.writeObject(o);
			} catch (ClassNotFoundException | IOException e) {
				error=true;
			}
		}
	}

}
