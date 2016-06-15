package ejercicio2y3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManejadorArchivo {
	
	//metodo que chequea si existe un archivo
	public static boolean chequearExistencia(String path){
		File archivo = new File(path);
		if(archivo.exists()) {
		      return true;
		} else {
		     return false;
		}
	}
	
	public static boolean crearArchivo(String path){
		File archivo = new File(path);
		try {
			archivo.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean inicializarArchivo(String path){
		File archivo = new File(path);
	    BufferedWriter bw;
	    try {
	    	bw = new BufferedWriter(new FileWriter(archivo));
	    	for(int i=0;i<=10;i++){
	    		bw.write(i);
	    		bw.write(i*100);
	    	}
	    	bw.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
		} 
		return true;
	}
	
	public static MensajeBanco buscarPorId(Integer id,String path){
		MensajeBanco mensaje=null;
		File archivo = new File(path);
	    boolean encontrado=false;
	    int index=0;
	    try {
	    	FileReader fr= new FileReader(archivo);
	    	while(!encontrado && index<10){
	    		int id_leido=fr.read();
	    		int monto=fr.read();
	    		if(id==id_leido){
	    			encontrado=true;
	    			mensaje=new MensajeBanco();
	    			mensaje.setId(id_leido);
	    			mensaje.setMonto(monto);
	    			mensaje.setOperacion(Operacion.Consulta);
	    		}
	    		index++;
	    	}
	    	fr.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
		} 
		return mensaje;
	}

	public static boolean eliminarArchivo(String path) {
		File archivo = new File(path);
		if(archivo.exists()){
			if( archivo.delete() ){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	
	
}
