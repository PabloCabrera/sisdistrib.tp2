package ej4;

public class PedazoImagen {
	private int originalWidth, originalHeight;
	private int offsetX, offsetY;
	private int width, height;
	BufferedImage buffer;

	public PedazoImagen (
		originalWidth, originalHeight, 
		offsetX, offsetY,
		width, height,
		imageType
	) {
		this.originalWidth = originalWidth;
		this.originalHeight = originalHeight;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.width = width;
		this.height = height;
		buffer = new BufferedImage(width, height, imageType);
	}

	public getOriginalWidth () {
		return this.originalWidth;
	}

	public getOriginalHeight () {
		return this.originalHeight;
	}

	public getOffsetX() {
		return this.offsetX;
	}

	public getOffsetY() {
		return this.offsetY;
	}

	public getWidth() {
		return this.width;
	}

	public getHeight() {
		return this.height;
	}

	public getBufferedImage() {
		return this.buffer;
	}
}
