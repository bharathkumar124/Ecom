package com.example.Dto;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
@Service
public class ImageDecoder {
	 public BufferedImage decodeImage(byte[] imageData) throws IOException {
	        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
	        return ImageIO.read(bis);
	    }
}
