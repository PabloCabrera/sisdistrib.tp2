package ejercicio2y3;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

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
	
	public static boolean escribirEnID(Integer id, Integer monto,String path){
		try {
			RandomAccessFile archivo = new RandomAccessFile(new File(path),"rw");
			archivo.seek(id*4*2);
			archivo.skipBytes(4);	//me salteo los 4 bytes del id
			archivo.writeInt(monto);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static MensajeBanco buscarDirectoPorID(Integer id,String path){
		MensajeBanco mb=null;
		File archivo = new File(path);
		if(!archivo.exists()){
			return null;
		}
		try {
			RandomAccessFile directo = new RandomAccessFile(archivo,"r");
			directo.seek(id*4*2);
			Integer id_leido= directo.readInt();
			System.out.println("lei el dato: id: "+id_leido);
			Integer monto = directo.readInt();
			System.out.println("lei el dato: monto: "+monto);
			mb= new MensajeBanco();
			mb.setId(id_leido);
			mb.setMonto(monto);
			directo.close();
		}catch(java.io.EOFException e){
			System.out.println("me pase de rango en el archivo");
		}catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return mb;
	}
	
	public static boolean iniciarArchivoDirecto(String path){
		MensajeBanco mb=null;
		File archivo = new File(path);
		if(!archivo.exists()){
			return false;
		}
		RandomAccessFile directo;
		try {
			directo = new RandomAccessFile(archivo,"rw");
			directo.seek(0);
			for(Integer i=0;i<=10;i++){
	    		directo.writeInt(i);
	    		directo.writeInt(i*10);
	    	}
			directo.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean lockFile() {
		File sharedLockFile = new File("lockfile.lock");
		FileChannel channel;
		FileLock lock;
		try{
		   // Check if the lock exist
		   if (sharedLockFile.exists()){
			   	// if exist try to delete it
			   	sharedLockFile.delete();
		   }
		   // Try to get the lock
		   channel = new RandomAccessFile(sharedLockFile, "rw").getChannel();
		   lock = channel.tryLock();
		   if (lock == null) {
			   // File is locked by other application
			   channel.close();
			   return false;
		   	}
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
		   return true;
	}

	public static boolean unlockFile(String path){
		File sharedLockFile = new File(path);
		try{	
			FileChannel channel = channel = new RandomAccessFile(sharedLockFile, "rw").getChannel();;
			FileLock lock=channel.lock();
			// release and delete file lock
			try {
				if (lock != null) {
					lock.release();
					channel.close();
				}
			} finally {
				if (sharedLockFile != null){
					sharedLockFile.delete();
				}
			}
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * METODO PARA CREAR UNA CARPETA
	 */
	
	/*
	 * The mkdir( ) method creates a directory, returning true on success and false on failure.
	 *  Failure indicates that the path specified in the File object already exists,
	 *   or that the directory cannot be created because the entire path does not exist yet.
	 * 
	  public static void main(String args[]) {
	      String dirname = "/tmp/user/java/bin";
	      File d = new File(dirname);
	      // Create directory now.
	      d.mkdirs();
  		}
  		Java automatically takes care of path separators on UNIX and Windows as per conventions.
  		If you use a forward slash (/) on a Windows version of Java, the path will still resolve correctly.
	 */
	
	/**
	 * METODO PARA LISTAR CARPETAS Y ARCHIVOS EN UN PATH
	 */
	/*
	 * You can use list( ) method provided by File object to list down all the files and 
	 * directories available in a directory as follows:
	 * 
	 * 
	 * public static void main(String[] args) {
      
	      File file = null;
	      String[] paths;   
		      try{      
		         // create new file object
		         file = new File("/tmp");
		                                 
		         // array of files and directory
		         paths = file.list();
		            
		         // for each name in the path array
		         for(String path:paths)
		         {
		            // prints filename and directory name
		            System.out.println(path);
		         }
		      }catch(Exception e){
		         // if any error occurs
		         e.printStackTrace();
		      }
   		}
	 */
}
