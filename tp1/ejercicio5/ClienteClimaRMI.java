package ejercicio5;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import practicaEnSalon.InterfazReceptorMensajes;


@SuppressWarnings("unused")
public class ClienteClimaRMI {
	
	private InterfazEJ5 rmiServidor;
	protected Registry registro;
	protected String direccionServidor;
	protected Integer puertoServidor;
	
	public ClienteClimaRMI(String ip, Integer puerto) throws RemoteException, NotBoundException{
		direccionServidor = ip;
		puertoServidor= puerto;
		this.conectarse();
	}
	
	protected void conectarse(){
		try {
			registro = LocateRegistry.getRegistry(direccionServidor, puertoServidor);
			rmiServidor = (InterfazEJ5)(registro.lookup("rmiServidor"));
			String c=this.rmiServidor.GetClima();
			System.out.println("clima: "+c);
			System.out.println("temp: "+ this.rmiServidor.GetTemperatura() );
			System.out.println("humedad: "+ this.rmiServidor.GetHumedad() );
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		try {
			ClienteClimaRMI cc = new ClienteClimaRMI("localhost",6666);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
