package ej6_7_8;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import otros.Servidor;

public class ServidorHora extends Servidor {
	
	protected boolean horaLocal;
	protected Date fechayhora;
	protected boolean soyMaster;
	protected String ipMaster;
	protected Integer PuertoMaster;
	
	public ServidorHora(boolean horalocal) throws IOException {
		super();
		this.horaLocal=horalocal;
		this.nombre="ServidorHora";
	}
	
	public ServidorHora(Integer puerto,boolean horalocal) throws IOException {
		this(puerto);
		this.horaLocal=horalocal;
	}
	
	public ServidorHora(Integer puerto) throws IOException {
		super(puerto);
		this.horaLocal=true;
		this.nombre="ServidorHora";
	}
	
	public static void main(String[] args) {
		try {
			//solo para probar
									//si es true uso Date, si es false uso la pagina
			ServidorHora sd= new ServidorHora(6000,true);
			Thread t= new Thread(sd);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		this.soyMaster= buscarMaster();		//busco a ver si soy yo master u otro
		
		this.levantarServicio();
	}
	
	@Override
	protected void recibiConexion(Socket so){
		System.out.println("+ "+this.nombre+": recibi una conexion desde "+so.getInetAddress());
		Thread t=null;
		if(this.soyMaster){		//si soy Master
			if(this.horaLocal){		//si trabajo con hora local o la busco en la pagina
				//uso hora local
				t= new Thread(new ThreadHoraLocal(so));
			}else{
				//busco en la pagina
				t= new Thread(new ThreadHoraWeb(so));
			}
		}else{
			t= new Thread(new ThreadHoraLocal(so));
		}
		
		t.start();
		this.threads.add(t);
	}

	public Date pedirHora() {
		return this.fechayhora;
	}
	
	protected boolean buscarMaster(){
		if(this.soyMaster){	//si me setearon que soy master digo q soy master
			return true;
		}
		//sino busco a un master, si no encuentro me pongo a mi mismo
		//mando broadcast preguntando por master
		return true;
	}
}
