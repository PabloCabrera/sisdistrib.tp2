package ej6_7_8;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import ej2_3.ThreadDeposito;
import otros.Servidor;

public class ServidorHora extends Servidor {
	
	protected boolean horaLocal;
	protected Date fechayhora;
	protected boolean soyMaster;
	
	public ServidorHora(boolean horalocal) throws IOException {
		super();
		this.horaLocal=horalocal;
	}
	
	public ServidorHora(Integer puerto,boolean horalocal) throws IOException {
		this(puerto);
		this.horaLocal=horalocal;
	}
	
	public ServidorHora(Integer puerto) throws IOException {
		super(puerto);
		this.horaLocal=true;
	}
	
	public static void main(String[] args) {
		try {
			ServidorHora sd= new ServidorHora(6000,true);
			Thread t= new Thread(sd);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		if(this.horaLocal){		//si trabajo con hora local o la busco en la pagina
			//al usar hora local no me sincronizo con nadie
			this.fechayhora = new Date();
			this.soyMaster=true;
		}else{
			//broadcast pregunto si hay algun master que tenga la fechayhora
			//si uno me responde le pido la fecha a ese
			//sino me pongo a mi mismo de master y saco la hora de internet
			//broadcast de que estoy disponible
		}
		this.levantarServicio();
	}
	
	@Override
	protected void recibiConexion(Socket so){
		System.out.println("+ "+this.nombre+": recibi una conexion desde "+so.getInetAddress());
		Thread t= new Thread(new ThreadHora(so,this));
		t.start();
		this.threads.add(t);
	}

	public Date pedirHora() {
		return this.fechayhora;
	}
	
}
