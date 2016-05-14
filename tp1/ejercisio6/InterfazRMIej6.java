package ejercisio6;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazRMIej6 extends Remote {
	
	public Integer sumar(Integer[] vector1, Integer[] vector2) throws RemoteException;
	public Integer restar(Integer[] vector1, Integer[] vector2) throws RemoteException;
}
