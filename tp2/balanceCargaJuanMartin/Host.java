package balanceCargaJuanMartin;

public class Host {
	private String ip;
	private int port;
	
	public String getIp() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
	
	public Host(String i, int p) {
		ip = i;
		port = p;
	}
}
