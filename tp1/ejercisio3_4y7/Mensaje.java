package ejercisio3_4y7;

import java.io.Serializable;

public class Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Integer id_receptor;
	protected Integer id_emisor;
	protected String msj;	
	
	/*si el receptor es -1 entonces son mensajes del sistema
	 * para lo cual se parseara el msj a un integer, y en base a ese numero se tomara una decision.
	*/
	public Mensaje(Integer id_receptor, Integer id_emisor, String msj) {
		this.id_receptor=id_receptor;
		this.id_emisor=id_emisor;
		this.msj= msj;
	}

	public Mensaje(Integer id_receptor, Integer id_emisor, Integer nro_operacion) {
		this.id_receptor=id_receptor;
		this.id_emisor=id_emisor;
		this.msj= nro_operacion.toString();		
		// nro_operacion = -1 significa que pregunto si tengo mensajes, la respuesta es "si" o "no"
		
	}

	public Integer getId_receptor() {
		return id_receptor;
	}

	public void setId_receptor(Integer id_receptor) {
		this.id_receptor = id_receptor;
	}

	public Integer getId_emisor() {
		return id_emisor;
	}

	public void setId_emisor(Integer id_emisor) {
		this.id_emisor = id_emisor;
	}

	public String getMsj() {
		return msj;
	}

	public void setMsj(String msj) {
		this.msj = msj;
	}
	
	@Override
	public String toString(){
		return "emisor: "+this.id_emisor+" receptor: "+this.id_receptor+ " msj: "+this.msj;
	}
}
