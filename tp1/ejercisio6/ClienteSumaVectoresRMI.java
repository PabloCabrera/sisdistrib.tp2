package ejercisio6;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import ejercisio6.InterfazRMIej6;

public class ClienteSumaVectoresRMI {
	
	protected Registry registro;
	protected String direccionServidor;
	protected Integer puertoServidor;
	protected InterfazRMIej6 server;
	
	protected Integer vector1[];
	protected Integer vector2[];
	
	public static void main(String[] args) {
		ClienteSumaVectoresRMI csv= new ClienteSumaVectoresRMI("localhost",5555);
		csv.menu();
	}
	
	public ClienteSumaVectoresRMI(String ip, int puerto) {
		direccionServidor = ip;
		puertoServidor= puerto;
		vector1 = new Integer[2];
		vector2 = new Integer[2];
		this.conectarse();		
		
	}
	
	protected void conectarse(){
		try {
			registro = LocateRegistry.getRegistry(direccionServidor, puertoServidor);
			server = (InterfazRMIej6)(registro.lookup("rmiServidorVectores"));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}	
	}
	
	public void menu(){
		Scanner s = new Scanner(System.in);
		Integer resultado=0;
		boolean bandera=true;
		while(bandera){
			try{
				System.out.println("Ingrese el primer numero para el vector 1:");
				Integer i=Integer.parseInt(s.nextLine());
				vector1[0]=i;
				System.out.println("Ingrese el segundo numero para el vector 1:");
				i=Integer.parseInt(s.nextLine());
				vector1[1]=i;
				
				System.out.println("Ingrese el primer numero para el vector 2:");
				i=Integer.parseInt(s.nextLine());
				vector2[0]=i;
				System.out.println("Ingrese el segundo numero para el vector 2:");
				i=Integer.parseInt(s.nextLine());
				vector2[1]=i;
				
				System.out.println("los vectores quedaron:");
				System.out.println("vector1: "+"["+vector1[0]+","+vector1[1]+"]");
				System.out.println("vector2: "+"["+vector2[0]+","+vector2[1]+"]");
				
				System.out.println("ingrese 1 para sumar,2 para restar");
				i=Integer.parseInt(s.nextLine());
				if (i==1){
					 resultado=this.server.sumar(vector1,vector2);
				}else{
					if(i==2){
						 resultado = this.server.restar(vector1,vector2);
					}else{
						throw new Exception("ingrese 1 o 2!");
					}
				}
				bandera=false;
			}catch(Exception e){
				System.out.println("ingrese bien los datos! todo devuelta!");
			}
		}
		
		System.out.println("el resultado de la operacion es: "+resultado);
		System.out.println("los vectores quedaron:");
		System.out.println("vector1: "+"["+vector1[0]+","+vector1[1]+"]");
		System.out.println("vector2: "+"["+vector2[0]+","+vector2[1]+"]");
		
		
		
	}
	
}
