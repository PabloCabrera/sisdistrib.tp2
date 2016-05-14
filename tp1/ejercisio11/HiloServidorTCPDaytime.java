package ejercisio11;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.Jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ejercicio1y2.HiloServidorTCP;

public class HiloServidorTCPDaytime extends HiloServidorTCP {
	
	protected ObjectOutputStream outStrm;
	protected ObjectInputStream inStrm;
	protected boolean conectado;
	protected String URL="es.thetimenow.com";
	
	public HiloServidorTCPDaytime(){
		
	}
	
	public HiloServidorTCPDaytime(Socket so) {
		this.socket=so;
		try {
			outStrm=new ObjectOutputStream(this.socket.getOutputStream());
			inStrm=new ObjectInputStream(this.socket.getInputStream());
			this.conectado=true;
		} catch (IOException e) {
			cerrarConexion();
		}
	}

	public void run(){
		boolean bandera=true;
		try {
			while(bandera){
				//leo la peticion que me llega, en base al codigo del mensaje veo si me piden info de una ciudad, o si me cierran la conexion
				DayTimeCity p=(DayTimeCity)this.inStrm.readObject();
				Integer c=p.getCodigo();
				switch(c){
				case 1:
					this.cerrarConexion();
					bandera=false;
					break;
				case 2:
					peticion_de_ciudad(p);
					break;
				default:
					responder_error();
				}
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void responder_error() {
		DayTimeCity respuesta= new DayTimeCity();
		respuesta.setCodigo(3);		//codigo de peticion mal formada
		try {
			this.outStrm.writeObject(respuesta);
		} catch (IOException e) {
			this.cerrarConexion();
		}
	}

	private void peticion_de_ciudad(DayTimeCity p) {
		/*
		try {
			Socket so = new Socket (InetAddress.getByName(this.URL),80);
			BufferedReader br = new BufferedReader(new InputStreamReader(so.getInputStream()));
			PrintWriter pw = new PrintWriter(so.getOutputStream());
			pw.println("GET "+peticion+" HTTP/1.1");
			pw.println();
			pw.flush();
			String f=br.readLine();
				//para mostrar la respuesta recibida
			while(f!=null){
				f=br.readLine();
				System.out.println("respuesta del server: "+f);
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//HttpURLConnection con;
		//add reuqest header
		/*String url = "http://es.thetimenow.com/search.php";
		URL obj;
		try {
			obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();		
			con.setRequestMethod("POST");
					
			String urlParameters =peticion+ "&cx=partner-pub-3846400427615131%3A7321420857&cof=FORID%3A11&ie=UTF-8&sa" ;
				
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//print result
			System.out.println(response.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		/* 		//GET
		try {
			HttpURLConnection con;
			String nueva_url= "http://es.thetimenow.com/"+ peticion;
			URL obj = new URL(nueva_url);
			con = (HttpURLConnection) obj.openConnection();
			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + this.URL);
			System.out.println("Response Code : " + responseCode);
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String result = "";
		    String line;
		    while ((line = rd.readLine()) != null) {
		    	result.concat(line);
		    	System.out.println(line);
		    }
		    result.indexOf("timeBoxLength fancy-box dark-blue");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		*/
		try {
			//String nueva_url= "http://es.thetimenow.com/"+ peticion;
			String nueva_url= "http://time.is/"+ p.getCiudad();
			//hago la url de la pagina q voy a pedir con el nombre de la ciudad
			URL obj = new URL(nueva_url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			Document document = Jsoup.connect(nueva_url).get();
			//con esto obtengo el HTML 
			//puedo buscar datos en el HTML como si fuera JQUERY, busco mediante los id que tenia la pagina
	        String hora = document.select("#twd").text();
	        String fecha = document.select("#dd").text();
	        System.out.println("como me llega la hs en el html " + hora);
	        System.out.println("como me llega la fecha en el html: " + fecha);
	        
	        //reacodomo la fecha, por que supongo que la pagina reacomoda la fecha con javascript, pero como en esta peticion
	        //no se llega a ejecutar, la fecha viene mal e incompleta y le agrego a mano el nro de mes que tambien se pierde
	        String dia= fecha.substring(15, 16);
	        System.out.println("dia: " + dia);
	        Date fecha_para_sacar_mes= new Date();
	        String mes= String.valueOf( fecha_para_sacar_mes.getMonth() );
	        System.out.println("mes: " + mes);
	        String anio = fecha.substring(18, 22);
	        System.out.println("anio: " + anio);
	        String fecha_bien=dia +","+mes+","+anio;
	        System.out.println("fecha bien: " + fecha_bien);
	        
	        
	        DayTimeCity respuesta = new DayTimeCity();
	        respuesta.setCiudad(p.getCiudad());
	        respuesta.setHora(hora);
	        respuesta.setDia(" - ");
	        respuesta.setCodigo(5);		// codigo de respuesta a una peticion
	        respuesta.setFecha(fecha_bien);
	        this.outStrm.writeObject(respuesta);
	        
		} catch(org.jsoup.HttpStatusException e){
			DayTimeCity respuesta = new DayTimeCity();		//error que tira cuando no puede recuperar el HTML
			respuesta.setCodigo(4);		//codigo de que ciudad no existe
			try {
				this.outStrm.writeObject(respuesta);
			} catch (IOException e1) {
	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void cerrarConexion(){
		this.conectado=false;
		try {
			this.inStrm.close();
			this.outStrm.close();
			this.socket.close();
		} catch (IOException e) {
			
		}
		System.out.println("conexion cerrada, ignore el stacktrace");
	}
}
