package com.mahendra.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mahendra.services.ImageResizerService;

@RestController
@RequestMapping("/api")
public class ImageResizerController {

	private final ImageResizerService imageResizerService;

	public ImageResizerController(ImageResizerService imageResizerService) {
		this.imageResizerService = imageResizerService;
	}

	@PostMapping("/resize")
	public ResponseEntity<?> resize(@RequestParam("data") MultipartFile file, @RequestParam("scale") float scale) {
		long t1 = System.currentTimeMillis();
		try {
			System.out.println("processing file : " + file.getOriginalFilename());
			BufferedImage resizedImage = this.imageResizerService.resize(file.getBytes(),scale);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(resizedImage, "jpg", byteArrayOutputStream);
			byteArrayOutputStream.close();
			byte[] bytes = byteArrayOutputStream.toByteArray();
			System.out.println("Bytes written: " + bytes.length);
			long t2 = System.currentTimeMillis();
			long time = t2 - t1;
			System.out.println("Processing time: " + time + " ms");
			return ResponseEntity.ok().contentLength(bytes.length).contentType(MediaType.IMAGE_JPEG).body(bytes);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
