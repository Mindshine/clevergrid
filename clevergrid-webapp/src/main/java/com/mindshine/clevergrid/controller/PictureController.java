package com.mindshine.clevergrid.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mindshine.clevergrid.io.PictureLoader;

@Controller
public class PictureController {

	@Autowired
	private PictureLoader pictureLoader;
	
	@RequestMapping(value = "/picture")
	public ResponseEntity<byte[]> getPicture(@RequestParam String name) throws IOException  {
		InputStream is = pictureLoader.getPicture(name);

	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);

	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	    int nRead;
	    byte[] data = new byte[16384];

	    while ((nRead = is.read(data, 0, data.length)) != -1) {
	      buffer.write(data, 0, nRead);
	    }

	    buffer.flush();

	    return new ResponseEntity<byte[]>(buffer.toByteArray(), headers, HttpStatus.OK);
	}
}
