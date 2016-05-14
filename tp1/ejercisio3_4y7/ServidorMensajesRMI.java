package ejercisio3_4y7;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServidorMensajesRMI extends UnicastRemoteObject implements InterfazRMIEj3_4_7 {

	private static final long serialVersionUID = 1L;
	private ControladorMensajes cm;
	protected Integer puerto=5555;
	protected Registry registro;
	protected String ip;
	
	protected ServidorMensajesRMI() throws RemoteException {
		super();
		cm= new ControladorMensajes();
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
		registro.rebind("rmiServidorMensajes", this);
	}

	@Override
	public boolean tengoMensajes(Integer id_receptor) throws RemoteException {
		if(cm.hayMensajesPorEmisor(id_receptor)){
			return true;
		}else{
			return false;
		}		
	}

	@Override
	public boolean enviarMensaje(Mensaje mensaje) throws RemoteException {
		cm.guardarMensaje(mensaje);
		return true;
	}

	@Override
	public ArrayList<Mensaje> devolverMensajes(Integer id_propio) throws RemoteException {
		ArrayList<Mensaje> msj= cm.getMensajesPorEmisor(id_propio);
		for(Mensaje m: msj){
			cm.borrarMensaje(m);
		}		
		return msj;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			ServidorMensajesRMI smrmi= new ServidorMensajesRMI();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
