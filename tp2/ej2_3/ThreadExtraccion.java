package ej2_3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadExtraccion extends ThreadDeposito {
	
	public ThreadExtraccion(Socket so) {
		super(so);
	}
	
	
}
