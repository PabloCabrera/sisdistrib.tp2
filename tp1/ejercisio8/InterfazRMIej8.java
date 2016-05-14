package ejercisio8;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazRMIej8  extends Remote {
	
	public InterfazTarea hacerTarea (InterfazTarea tarea) throws RemoteException;
	
}
