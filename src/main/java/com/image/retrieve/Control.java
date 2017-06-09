package com.image.retrieve;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Control {
	
	@RequestMapping("/city")
	public String method1() {
		File file = new File("C:/chennai");
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		return Arrays.toString(directories);
	}

	@RequestMapping("/city/{cname}/")
	public List<Display> method2(@PathVariable("cname") String cityname, HttpServletRequest request) {
		File dir = new File("C:/chennai/" + cityname);
		List<Display> list = new ArrayList<>();
		for (String a : dir.list()) {
			Display d = new Display();
			String url = request.getRequestURL().toString();
			
			d.setUrl(url + a);
			list.add(d);
			
		}
		
		return list;
	}
   
	@RequestMapping(value = "/city/{cname}/{name}", method = RequestMethod.GET)
	public byte[] method3(@PathVariable("cname") String cityname, @PathVariable("name") String imagename)
			throws IOException {
		BufferedImage buffIm = ImageIO.read(new File("c:/chennai/" + cityname + "/" + imagename + ".jpg"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(buffIm, "jpg", baos);
		byte[] imageData = baos.toByteArray();
		return imageData;
	}

}
