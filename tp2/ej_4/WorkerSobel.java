package ej_4;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.RemoteException; 

/* Responsabilidades de la clase:
	cargar una porcion de imagen
	aplicar el filtro sobel sobmre la porcion de imagen
	devolver el resultado
*/
public class WorkerSobel extends UnicastRemoteObject implements RemoteSobel{
	public WorkerSobel() {
	}

	@Override
	public Object sobel (Object image) throws RemoteException {
		return null;
	}

}
