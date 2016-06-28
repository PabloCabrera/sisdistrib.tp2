package ej_4;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSobel extends Remote {
	public Object sobel (Object image) throws RemoteException;
}
