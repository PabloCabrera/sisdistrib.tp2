package ejercicio5;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServidorClimaRMI extends UnicastRemoteObject implements InterfazEJ5, Runnable {
	
	private static final long serialVersionUID = 1L;
	protected String ip;
	protected Integer puerto;
	protected Registry registro;
	
	protected String clima="despejado";
	protected Integer temperatura=25;
	protected Integer humedad=70;

	
	protected ServidorClimaRMI(Integer puerto) throws RemoteException {
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
		System.out.println(this.puerto=puerto);
		registro.rebind("rmiServidor", this);
	}
	
	@Override
	public String GetClima() throws RemoteException {
		return this.clima;
	}

	@Override
	public Integer GetTemperatura() throws RemoteException {
		return this.temperatura;
	}

	@Override
	public Integer GetHumedad() throws RemoteException {
		return this.humedad;
	}

	@Override
	public void run(){
		this.clima="nublado";
		/*
		try {
			System.out.println("esperando conexiones");
			registro.rebind("rmiServidor", this);
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		*/
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			ServidorClimaRMI sc= new ServidorClimaRMI(6666);
			Thread a =new Thread(sc);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
