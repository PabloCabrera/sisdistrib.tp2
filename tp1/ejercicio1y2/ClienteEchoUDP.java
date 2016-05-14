package ejercicio1y2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClienteEchoUDP {

	protected InetAddress miIP;
	protected Integer mipuerto;
	
	protected InetAddress ipdestino;
	protected Integer puertodestino=6666;
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ClienteEchoUDP c = new ClienteEchoUDP();
	}

	public ClienteEchoUDP(){
		System.out.println("soy el cliente de Echo en UDP");
		DatagramSocket s=null;
		DatagramPacket p=null;
		try {
			s= new DatagramSocket();
			String info = "primera segunda tercera cuarta quinta";
			this.ipdestino= InetAddress.getLocalHost();
			System.out.println("bytes: "+info.getBytes().length);
			String info2= new String(info.getBytes());
			System.out.println("info2: "+info2);
			byte [] test = info.getBytes();
			p = new DatagramPacket(test, test.length, ipdestino, puertodestino);
			s.send(p);
			System.out.println("mande el msj");
			byte [] receiveData = new byte [1024];
			DatagramPacket receiveDatagramPacket = new DatagramPacket(receiveData, receiveData.length);
			
			s.receive(receiveDatagramPacket);
			info = new String(receiveDatagramPacket.getData());
			System.out.println(info);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
