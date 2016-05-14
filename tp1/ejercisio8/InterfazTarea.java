package ejercisio8;

import java.io.Serializable;

public interface InterfazTarea extends Serializable{
	
	public void realizarTarea();
	public String getEstado();
	public Object getResultado();
	
}
