package ejercisio11;

import java.io.Serializable;

public class DayTimeCity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected Integer codigo;		//1= bye, 2=dame info de ciudad, 3=peticion mal formada, 4=ciudad no encontrada, 5=respuesta
	protected String ciudad;
	protected String dia;
	protected String fecha;
	protected String hora;
	
	@Override
	public String toString(){
		return "Ciudad: "+this.ciudad+ " ,dia: "+this.dia+" ,fecha: "+this.fecha +" ,hora: " +this.hora;
	}
	
	public DayTimeCity(){
		
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
}
