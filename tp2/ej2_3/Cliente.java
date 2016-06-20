package ej2_3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	
	protected Socket socket;
	protected Scanner scaner;
	protected ObjectOutputStream outStrm;
	protected ObjectInputStream inStrm;
	
	private int depositar=1;
	private int extraer=2;
	
	public static void main(String[] args) {
		Cliente c = new Cliente();
		c.menu();
	}
	
	public Cliente(){
		scaner = new Scanner(System.in);
	}
	
	public boolean menu(){
		Integer opcion=-1;
		while(opcion!=4){
			System.out.println("Menu: ");
			System.out.println("1- Servicio Deposito");
			System.out.println("2- Servicio Extraccion");
			System.out.println("3- Servicio Consulta ");
			System.out.println("4- Salir");
			Integer puerto=4500;
			opcion=Integer.parseInt( scaner.nextLine() );
			if(opcion==4){
				return true;
			}
			System.out.println("Ingrese la IP del servidor ");
			String ip= scaner.nextLine();
			if(this.socket!=null){
				cerrarConexion();
			}
			switch(opcion){
				case 1: puerto=4500;
					break;
				case 2: puerto=5000;
					break;
				case 3: puerto=5500;
					break;
				default:
					System.out.println("ingrese un dato correcto");
					break;
			}
			try {
				socket= new Socket(ip,puerto);
				this.outStrm=new ObjectOutputStream( this.socket.getOutputStream() );
				this.inStrm=new ObjectInputStream( this.socket.getInputStream() );
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			System.out.println("Conectado!");
			switch(opcion){
				case 1: depositarExtraer(depositar);
					break;
				case 2: depositarExtraer(extraer);
					break;
				case 3: consulta();
					break;
				default:
					System.out.println("ingrese un dato correcto");
					break;
			}
		}
		return true;
	}
	
	protected boolean depositarExtraer(int accion){
		int id=0;
		while(id>=0){
			System.out.println();
			if(accion==depositar){
				System.out.println("----- MENU DE DEPOSITO  --------");
			}else{
				System.out.println("----- MENU DE EXTRACCION  --------");
			}
			//pido los datos de nro de cuenta y monto a depositar/extraer
			System.out.println();
			System.out.println("ingrese un id negativo o mayor a 9 para salir");
			System.out.println("ingrese el id de la cuenta ( nro entre 0 y 9 incluidos) ");
			id=Integer.parseInt( this.scaner.nextLine() );
			if(id<0 || id>9){
				return true;
			}
			System.out.println("ingrese el monto ");
			int monto = Integer.parseInt( this.scaner.nextLine() );
			Operacion op;
			if(accion==depositar){
				op=Operacion.Deposito;
			}else{
				op=Operacion.Extraccion;
			}
			//creo el mensaje con los datos
			MensajeBanco mensaje = new MensajeBanco(id,monto,op);
			MensajeBanco respuesta=null;
			try {
				//envio el mensaje y espero respuesta
				this.outStrm.writeObject(mensaje);
				respuesta =(MensajeBanco) this.inStrm.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				cerrarConexion();
				return false;
			}
			//el servidor me responde un error, o me envia la informacion de la cuenta haces de hacer la operacion
			if(respuesta.getOperacion()==Operacion.Error){
				System.out.println("El servidor informa el error:");
				System.out.println(respuesta.getError());
			}else if(respuesta.getOperacion()!=Operacion.Consulta){
				System.out.println("ERROR: el servidor respondio cualquier cosa");
			}else{
				System.out.println("Informacion de la cuenta antes de la modificacion:");
				System.out.println("+ id: "+respuesta.getId());
				System.out.println("+ monto: "+respuesta.getMonto());
				System.out.println("----------------------------------------------------------");
				System.out.println("");
				System.out.println("EL SERVIDOR ESTA PROCESANDO LA PETICION, POR FAVOR AGUARDE");
				System.out.println("");
				try {
					//tras recibir la primer respuesta, espero una segunda q informar el nuevo saldo de la cuenta
					respuesta =(MensajeBanco) this.inStrm.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					cerrarConexion();
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					cerrarConexion();
					return false;
				}
				if(respuesta.getOperacion()==Operacion.Error){
					System.out.println("El servidor informa el error:");
					System.out.println(respuesta.getError());
				}else if(respuesta.getOperacion()!=Operacion.Consulta){
					System.out.println("ERROR: el servidor respondio cualquier cosa");
				}else{
					System.out.println("Informacion de la cuenta DESPUES de la modificacion:");
					System.out.println("+ id: "+respuesta.getId());
					System.out.println("+ monto: "+respuesta.getMonto());
					System.out.println("----------------------------------------------------------");
				}
			}
		}
		if(accion==depositar){
			System.out.println("----- SALIENDO DE DEPOSITO  --------");
		}else{
			System.out.println("----- SALIENDO DE EXTRACCION  --------");
		}
		System.out.println("");
		enviarFin();
		return true;
	}
	
	protected boolean consulta(){
		while(true){
			System.out.println("");
			System.out.println("----- MENU DE CONSULTA  --------");
			System.out.println("");
			System.out.println("Ingrese el nro de ID a consultar (rango 0-10)");
			System.out.println("si el ID es >10 se mostraran todos los registrios, para salir ingrese id<0");
			int id= Integer.parseInt( scaner.nextLine() );
			if(id<0){
				return true;
			}
			MensajeBanco pedido = new MensajeBanco();
			pedido.setOperacion(Operacion.Consulta);
			pedido.setId(id);
			try {
				this.outStrm.writeObject(pedido);
			} catch (IOException e) {
				e.printStackTrace();
				cerrarConexion();
				return false;
			}
			MensajeBanco respuesta=null;
			if(id>10 ){
				System.out.println("");
				System.out.println("RECUPERANDO TODO LOS REGISTROS:");
				System.out.println("");
				System.out.println("-------------------------");
				for(int i=0;i<10;i++){
					
					try {
						respuesta =(MensajeBanco)this.inStrm.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						cerrarConexion();
						return false;
					} catch (IOException e) {
						e.printStackTrace();
						cerrarConexion();
						return false;
					}
					System.out.println("REGISTRO NRO: "+i);
					if(respuesta.getOperacion()==Operacion.Error){
						System.out.println("ERROR DEL SERVIDOR: "+respuesta.getError() );
					}else{
						System.out.println("+ id: "+respuesta.getId());
						System.out.println("+ monto: "+respuesta.getMonto());
					}
					System.out.println("-------------------------");
				}
			}else{
				System.out.println("");
				System.out.println("RECUPERANDO EL REGISTRO: "+id);
				System.out.println("");
				try {
					respuesta =(MensajeBanco)this.inStrm.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					cerrarConexion();
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					cerrarConexion();
					return false;
				}
				if(respuesta.getOperacion()==Operacion.Error){
					System.out.println("ERROR DEL SERVIDOR: "+respuesta.getError() );
				}else{
					System.out.println("+ id: "+respuesta.getId());
					System.out.println("+ monto: "+respuesta.getMonto());
				}
				System.out.println("-------------------------");
			}
		}
	}
	
	protected void cerrarConexion(){
		try {
			System.out.println("cerrando conexiones...");
			if(this.inStrm!=null){
				this.inStrm.close();
			}
			if(this.outStrm!=null){
				this.outStrm.close();
			}
			if(socket!=null){
				socket.close();
			}
			System.out.println("　　　　CONEXIONES CERRADAS!!!!!!!!");
		} catch (IOException e) {
		}//fin try
	}//fin cerrar conexion
	
	protected boolean enviarFin(){
		MensajeBanco mb= new MensajeBanco();
		mb.setOperacion(Operacion.Fin);
		try {
			this.outStrm.writeObject(mb);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}//fin clase