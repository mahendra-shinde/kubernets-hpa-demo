package com.mahendra.services;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

@Component
public class ImageResizerService {

	public BufferedImage resize(byte[] image,float scale) throws IOException {
		
		InputStream in = new ByteArrayInputStream(image);
		BufferedImage imageBuf = ImageIO.read(in);

		int targetHeight = (int) (imageBuf.getHeight() * scale);
		int targetWidth = (int) (imageBuf.getWidth() * scale);

		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, imageBuf.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(imageBuf, 0, 0, targetWidth, targetHeight, null);
		g.dispose();
		return resizedImage;
	}

}
