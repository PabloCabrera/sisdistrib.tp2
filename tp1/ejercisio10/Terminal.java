package ejercisio10;

import java.util.Scanner;

public class Terminal {
	
	protected CalculadorRTT calculadorRTT;
	
	//esta clase siemplemente tiene el menu para manejar el programa y muestra msj
	
	public Terminal(CalculadorRTT calculadorRTT) {
		this.calculadorRTT=calculadorRTT;
	}

	public void menu(){
		boolean bandera=true;
		Scanner sc= new Scanner(System.in);
		Integer entrada=0;
		System.out.println("Programa para medir RTT");
		System.out.println();
		System.out.println();
		while(bandera){
			try{
				System.out.println("menu:");
				System.out.println("	1-Probar contra una url mediante TCP");
				System.out.println("	2-Probar contra una url mediante UDP");
				System.out.println("	3-Usar archivo 'ips_a_probar.txt'");
				System.out.println("	4-Definir cantidad de hits (default=5)");
				System.out.println("	5-Configurar proxy(default= sin proxy)");
				System.out.println("	9-Salir");
				
				entrada=Integer.parseInt(sc.nextLine());
				switch(entrada){
					case 1:
						url();
						break;
					case 2:
						url_udp();
						break;
					case 3:
						archivo();
						break;
					case 4:
						hits();
						break;
					case 5:
						proxy();
						break;
					case 9:
						bandera=false;
						break;
					default:
						System.out.println("ingrese un valor valido!");
				}//fin switch
			}catch(Exception e){
				sc= new Scanner(System.in);
			}
			
		}//fin while
		
		
	}

	private void proxy() {
		Scanner s= new Scanner(System.in);
		System.out.println("CONFIGURACION DEL PROXY");
		System.out.println("Ingrese la ip del proxy");
		String s1=s.nextLine();
		calculadorRTT.setProxy_ip(s1);
		System.out.println("Ingrese le puerto del proxy");
		s1=s.nextLine();
		calculadorRTT.setProxy_puerto(s1);
		calculadorRTT.setProxy(true);
	}

	private void hits() {
		Scanner s= new Scanner(System.in);
		System.out.println("Ingrese la cantidad de hits");
		Integer hits=Integer.parseInt(s.nextLine());
		calculadorRTT.setHits(hits);
	}

	private void archivo() {
		calculadorRTT.leerArchivo();		
	}

	private void url() {
		Scanner s= new Scanner(System.in);
		System.out.println("Ingrese la URL para calcular el RTT");
		String url=s.nextLine();
		calculadorRTT.calcularRTT_TCP(url);				
	}
	
	private void url_udp() {
		Scanner s= new Scanner(System.in);
		System.out.println("Ingrese la URL para calcular el RTT");
		String url=s.nextLine();
		calculadorRTT.calcularRTT_UDP(url);				
	}

	public void mostrarMSJ(String string) {
		System.out.println(string);
		
	}
	
	
}
