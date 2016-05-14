package ejercisio8;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ClienteRMITareas {

	protected Registry registro;
	protected String direccionServidor;
	protected Integer puertoServidor;
	protected InterfazTarea tarea;
	protected InterfazRMIej8 servidor;
	
	
	public ClienteRMITareas(String ip, int puerto) {
		direccionServidor = ip;
		puertoServidor= puerto;
		this.conectarse();	
	}
	
	protected void conectarse(){
		try {
			registro = LocateRegistry.getRegistry(direccionServidor, puertoServidor);
			servidor = (InterfazRMIej8)(registro.lookup("rmiServidorTareas"));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}	
	}

	public static void main(String[] args) {
		ClienteRMITareas ct= new ClienteRMITareas("localhost",5555);
		ct.enviarTarea();
	}
	
	public void enviarTarea(){
		tarea = new Tarea();
		try {
			tarea = servidor.hacerTarea(tarea);
			System.out.println("el estado de la tarea es: "+tarea.getEstado());
			System.out.println("el resultado de la tarea es: "+tarea.getResultado().toString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
