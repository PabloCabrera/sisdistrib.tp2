package ejercisio10;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ejercisio3_4y7.Mensaje;

public class CalculadorRTT {
	
	protected Terminal vista;
	protected Integer hits;
	protected ArrayList<String> url;
	protected boolean proxy;
	protected String proxy_ip;
	protected String proxy_puerto;	
	
	public static void main(String[] args) {
		CalculadorRTT crtt = new CalculadorRTT();
	}
	
	public CalculadorRTT(){
		vista= new Terminal(this);
		this.hits=5;
		this.proxy=false;
		vista.menu();
	}

	public void setHits(Integer hits) {
		this.hits=hits;		
	}

	public void leerArchivo() {		//leo los archivos con las ips
		FileReader f;
		this.url= new ArrayList<String>();
		try {
			String path="C:\\Users\\Lucas\\workspace\\SistemasDistribuidosYProgramacionParalela\\tp2\\ejercisio10\\ips_a_probar.txt";
			f = new FileReader(path);
			BufferedReader b = new BufferedReader(f);
			boolean bandera= true;
			while(bandera) {
			  	String l= b.readLine();
			   	if(l==null){
			   		bandera=false;
			   	}else{
			   		this.url.add(l);
			   	}
			}
		b.close();
		} catch (FileNotFoundException e) {
			vista.mostrarMSJ("archivo no encontrado!");
			e.printStackTrace();
		} catch (IOException e) {
			vista.mostrarMSJ("error de IO");
			e.printStackTrace();
		}
		vista.mostrarMSJ("");
	    vista.mostrarMSJ("URL leidas del archivo: ");
	    int i=1;
		for(String s: this.url){
			vista.mostrarMSJ(i+"-   "+s);
			i++;
		}
		vista.mostrarMSJ("");
		vista.mostrarMSJ("CALCULAR POR UDP Y TCP -(UDP deshabilitado)");
		for(String s: this.url){
			//calcularRTT_UDP(s);		//no habilitado
			calcularRTT_TCP(s);
		}
		vista.mostrarMSJ("--FIN--");
	}

	public boolean calcularRTT_UDP(String url) {		//no anda por que nadie responde mensajes UDP
		vista.mostrarMSJ("RTT de: "+url+" por UDP:");
		DatagramSocket clientSocket;
		try {
			clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(url); 
			//InetAddress IPAddress = InetAddress.getByName("localhost"); 
	        byte[] sendData = new byte[128]; 
	        byte[] receiveData = new byte[128];
	       
	        sendData = "holaa".getBytes();
	        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 6666); 
	        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);    
	        clientSocket.setSoTimeout(4000);
	        long inicio = System.currentTimeMillis();
	        clientSocket.send(sendPacket); 
	        System.out.println("Packet sent");
	        clientSocket.receive(receivePacket); 
	        //String s= receivePacket.getData().toString();
	        //vista.mostrarMSJ("paquete: "+s);
	        System.out.println("Packet received");
		}catch(java.net.SocketTimeoutException e){
			System.out.println("Packet lost");
		}
		catch (SocketException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return true;	
	}

	public boolean calcularRTT_TCP(String url){
		//vector con los RTT de cada hit
		long v[]= new long[this.hits];
		vista.mostrarMSJ("RTT de: "+url+" por TCP:");
		for(int i=0;i<this.hits;i++){
			try{
				//al java no poder hacer PING de verdad, uso un GET de http y al recibir CUALQUIER respuesta, considero hecho el ping
				Socket s = new Socket(InetAddress.getByName(url), 80);
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				pw.println("GET / HTTP/1.1");
				pw.println();
				//configuracion del proxy QUE NO ANDA!
				if(this.proxy){
					System.getProperties().put("http.proxyHost", this.proxy_ip);
					System.getProperties().put("http.proxyPort", this.proxy_puerto);
				}
				//inicio timer, envio msj, recibo respuesta, tomo timer otra ves
				long inicio = System.currentTimeMillis();
				pw.flush();
				String f=br.readLine();
				long fin = System.currentTimeMillis();
					
				/*	//para mostrar la respuesta recibida
				while(f!=null){
					f=br.readLine();
					vista.mostrarMSJ("respuesta del server: "+f);
				}
				*/
				//agrego al vector con los RTT
				v[i] = fin - inicio;
				vista.mostrarMSJ("PING- "+i+" 		RTT:"+v[i]);
				br.close();
				s.close();				
			}catch(IOException e){
				vista.mostrarMSJ("no se pudo hacer la peticion a: "+url);
				return false;
			}
		}
		//sumo los RTT de todos y saco el promedio
		long sum=0;
		for(int i=0; i<this.hits;i++){
			sum+=v[i];
		}
		int promedio=(int) (sum/this.hits);
		vista.mostrarMSJ("promedio:"+promedio);
		return true;
	}

	public boolean isProxy() {
		return proxy;
	}

	public void setProxy(boolean proxy) {
		this.proxy = proxy;
	}

	public String getProxy_ip() {
		return proxy_ip;
	}

	public void setProxy_ip(String proxy_ip) {
		this.proxy_ip = proxy_ip;
	}

	public String getProxy_puerto() {
		return proxy_puerto;
	}

	public void setProxy_puerto(String proxy_puerto) {
		this.proxy_puerto = proxy_puerto;
	}
	
}
