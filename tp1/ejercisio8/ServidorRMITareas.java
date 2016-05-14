package ejercisio8;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServidorRMITareas extends UnicastRemoteObject implements InterfazRMIej8 {

	protected Integer puerto=5555;
	protected Registry registro;
	protected String ip;
	private static final long serialVersionUID = 1L;
	
	protected ServidorRMITareas() throws RemoteException {
		super();
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		registro = LocateRegistry.createRegistry(puerto);
		System.out.println("mi ip es:");
		System.out.println(ip);
		System.out.println("mi puerto es:");
		System.out.println(this.puerto);
		registro.rebind("rmiServidorTareas", this);
	}

	public static void main(String[] args) {
		try {
			ServidorRMITareas server= new ServidorRMITareas();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	@Override
	public InterfazTarea hacerTarea(InterfazTarea tarea) throws RemoteException {
		tarea.realizarTarea();
		return tarea;
	}

}
