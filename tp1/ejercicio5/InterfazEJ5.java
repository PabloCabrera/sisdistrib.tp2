package ejercicio5;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazEJ5 extends Remote {
	
	public String GetClima()throws RemoteException;
	public Integer GetTemperatura() throws RemoteException;
	public Integer GetHumedad() throws RemoteException;
	
	
}
