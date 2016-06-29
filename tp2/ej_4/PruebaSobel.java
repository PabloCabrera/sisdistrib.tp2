package ej_4;

import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class PruebaSobel extends JFrame {
	public static void main (String[] args) {
		PruebaSobel ventana = new PruebaSobel();
	}

	private Image imagen = null;

	public PruebaSobel() {
		super("Prueba Sobel");
		this.setSize(1000, 667);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.show();
		try {
			BufferedImage img_in = ImageIO.read (new File("ej_4/imagen_prueba.jpg"));
			this.setImg(img_in);
			WorkerSobel worker = new WorkerSobel();
			BufferedImage img_out = worker.sobel(img_in);
			this.setImg(img_out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint (Graphics g) {
		if (this.imagen != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage (this.imagen, 0, 0, null);
		}
	}

	public void setImg(Image img){
		this.imagen=img;
		this.repaint();
	}
}
