package ejercisio8;

import java.io.Serializable;

public class Tarea implements InterfazTarea, Serializable {

	private String estado;
	private Object resultado;	
	
	@Override
	public void realizarTarea() {
		try{
			this.resultado=new Integer( 10000+20000);
			this.estado="correcto";
		}catch(Exception e){
			this.estado="error la hacer la cuenta";
			this.resultado=null;
		}
	}

	@Override
	public String getEstado() {
		return estado;
	}

	@Override
	public Object getResultado() {
		return resultado;
	}

}
