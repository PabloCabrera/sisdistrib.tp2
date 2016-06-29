package balanceCargaPosta;

public class InfoServidor {
	
	protected String ip;
	protected Integer puerto;
	protected Integer nroConexionesAsignadas;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPuerto() {
		return puerto;
	}
	public void setPuerto(Integer puerto) {
		this.puerto = puerto;
	}
	public Integer getNroConexionesAsignadas() {
		return nroConexionesAsignadas;
	}
	public void setNroConexionesAsignadas(Integer nroConexionesAsignadas) {
		this.nroConexionesAsignadas = nroConexionesAsignadas;
	}
	public void incrementarNroConexionesAsignadas(){
		this.nroConexionesAsignadas++;
	}
	public void decrementarNroConexionesAsignadas(){
		this.nroConexionesAsignadas--;
	}
}
