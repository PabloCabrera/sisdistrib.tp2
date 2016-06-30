package ej_4;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/* Responsabilidades de la clase:
	cargar una imagen
	dividirla en N partes 
	enviar las partes N WorkerSobel para que las procesen
	recibir los resultados y guardarlos temporalmente
	unir los resultados
	mostrar el resultado final en pantalla y/o permitir guardarlo en un archivo
	mostrar datos de performance
*/
public class AdminSobel {
	BufferedImage imagen_fuente = null;
	BufferedImage imagen_procesada = null;
	
	protected RemoteSobel interfaz[];
	protected ArrayList<Runnable> hilos;
	
	public static void main(String args[]){
		//TODO pedir imagen
	}
	
	public boolean cargarImagen (File archivo) {
		try {
			this.imagen_fuente = ImageIO.read(archivo);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/* Dada una imagen, devuelve una lista de pedazos de esa imagen */
	private List<PedazoImagen> dividir (int partes, BufferedImage imagen) {
		List<PedazoImagen> pedazos = new ArrayList<PedazoImagen>();
		List<Rectangle> rectangulos = this.recortes (partes, imagen);
		for (Rectangle rect: rectangulos) {
			BufferedImage buf_pedazo = imagen.getSubimage((int)rect.getX(),(int) rect.getY(),(int) rect.getWidth(),(int) rect.getHeight());
			PedazoImagen pedazo = new PedazoImagen(imagen.getWidth(), imagen.getHeight(),rect.getX(), rect.getY(),
					rect.getWidth(), rect.getHeight(),imagen.getType());
		}
		return pedazos;
	}

	private List<Rectangle> recortes (int partes, BufferedImage imagen) {
		Rectangle rect = new Rectangle(imagen.getWidth(), imagen.getHeight());
		return this.recortes (partes, rect,0);	//TODO ni idea para q sirve nivel, como vi q no lo usas le mande cero
	}

	private List<Rectangle> recortes (int partes, Rectangle rectangle, int nivel) {
		List<Rectangle> rects = new ArrayList<Rectangle>();
			// Hacer cuadraditos
			if (partes == 1) {
				rects.add (rectangle);
				return rects;
			} else if (partes == 4) {
				int midX =(int) (rectangle.getWidth() / 2);
				int midY =(int) rectangle.getHeight() / 2;
				boolean xPar = (rectangle.getWidth()%2 == 0);
				boolean yPar = (rectangle.getHeight()%2 == 0);
				Rectangle r1, r2, r3, r4;
				r1 = new Rectangle (midX+1, midY+1);
				r2 = new Rectangle (
					xPar? midX+1: midY+2, //offset X
					0, //offset Y
					xPar? midX: midX+1, //width
					midY //height
				);
				r3 = new Rectangle (
					0, //offset X
					yPar? midY+1: midY+2, //offset Y
					midX, //width
					yPar? midY: midY+1 //height
				);
				r4 = new Rectangle (
					xPar? midX+1: midY+2, //offset X
					yPar? midY+1: midY+2, //offset Y
					xPar? midX: midX+1, //width
					yPar? midY: midY+1 //height
				);
			} else {
				throw new RuntimeException ("Solo esta soportado dividir la imagen en 4 partes");
			}
		return rects;
	}

	
}
