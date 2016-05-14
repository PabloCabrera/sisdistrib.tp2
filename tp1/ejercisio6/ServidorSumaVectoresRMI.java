package ejercisio6;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServidorSumaVectoresRMI extends UnicastRemoteObject implements InterfazRMIej6 {

	protected Integer puerto=5555;
	protected Registry registro;
	protected String ip;
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		try {
			ServidorSumaVectoresRMI server= new ServidorSumaVectoresRMI();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public ServidorSumaVectoresRMI() throws RemoteException {
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
		registro.rebind("rmiServidorVectores", this);
	}
	
	@Override
	public Integer sumar(Integer[] vector1, Integer[] vector2) {
		vector1[0]=0;
		vector2[0]=0;
		Integer suma= vector1[0]+vector1[1]+vector2[0]+vector2[1];
		return suma;
	}
	
	@Override
	public Integer restar(Integer[] vector1, Integer[] vector2) {
		vector1[0]=0;
		vector2[0]=0;
		Integer resta= vector1[0]+vector1[1]-(vector2[0]+vector2[1]);
		return resta;
	}

}
