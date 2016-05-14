package ejercisio3_4y7;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfazRMIEj3_4_7 extends Remote {
	
	public boolean tengoMensajes(Integer id_receptor) throws RemoteException;
	public boolean enviarMensaje(Mensaje mensaje)throws RemoteException;
	public ArrayList<Mensaje> devolverMensajes(Integer id_propio)throws RemoteException;
	
}
