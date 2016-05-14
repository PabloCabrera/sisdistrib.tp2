package ejercicio1y2;

import java.io.IOException;
import java.net.*;

public class ServidorEchoUDP {
	
	protected InetAddress miIP;
	protected Integer mipuerto=6666;
	
	protected InetAddress ipdestino;
	protected Integer puertodestino;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ServidorEchoUDP s=new ServidorEchoUDP();
		
	}
	
	public ServidorEchoUDP(){
		System.out.println("soy el server de Echo en UDP");
		DatagramSocket s=null;
		DatagramPacket p=null;
		boolean bandera=true;
		byte[] receiveData = new byte[2048];
		while (bandera){
			try {
				s= new DatagramSocket(mipuerto);
				p=new DatagramPacket(receiveData, receiveData.length);
				s.receive(p);
				System.out.println("recibi algo");
				puertodestino=p.getPort();
				System.out.println("puertodestino: "+puertodestino);
				ipdestino = p.getAddress();
				System.out.println("ipdestino: "+ipdestino);
				String info=new String(p.getData());
				System.out.println("info: "+info);
				info = "ECHO: "+info;
				System.out.println(info.getBytes().length);
				p = new DatagramPacket(info.getBytes(), info.getBytes().length, ipdestino, puertodestino);
				s.send(p);
				s.close();
			} catch (SocketException e) {
				e.printStackTrace();
				bandera=false;
			} catch (IOException e) {
				e.printStackTrace();
				bandera=false;
			}
		}
		
		
	}
	
	
}
