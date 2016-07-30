package twitchviewer.assist;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

public class Thumbnailer {
	private final static String THUMBNAIL_DIR = "./data/thumbnails";
	private final static String IMAGE_DIR = "./data/images";
	
	public static File getFileFromUrl(final String imageUrl) throws IOException {
		File output = null;
		
		if(!("").equals(imageUrl)) {
			final URL url = new URL(imageUrl);
			String newFileName = imageUrl.replaceAll("\\.", "_");
			newFileName = newFileName.substring(newFileName.lastIndexOf("/") + 1, newFileName.length());
			
			final InputStream inputStream = url.openStream();
			final ImageInputStream input = ImageIO.createImageInputStream(inputStream);
			final Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
			
			if(readers.hasNext()) {
				ImageReader reader = readers.next();
				
				reader.setInput(input);
				
				BufferedImage image = reader.read(0);
				
				String format = reader.getFormatName();
				output = new File(IMAGE_DIR + "/" + newFileName + "." + format.toLowerCase());
				ImageIO.write(image, format, output);
			}
			
			input.close();
			
			return output;
		}
		
		return null;
	}
	
	public static String resizeImage(final File input, final int width, final int height, final int type) throws IOException {
		if(input != null) {
			final String format = input.getName().substring(input.getName().lastIndexOf(".") + 1);
		    final String newFileName = input.getName().substring(0,input.getName().lastIndexOf(".")) + "_" + width + "_" + height;
			final File output = new File(THUMBNAIL_DIR + "/" + newFileName + "." + format.toLowerCase());
		    
			final BufferedImage originalImage = ImageIO.read(input);
			final BufferedImage resizedImage = new BufferedImage(width, height, type);
		    final Graphics2D g = resizedImage.createGraphics();
		    g.drawImage(originalImage, 0, 0, width, height, null);
		    g.dispose();
		    
		    ImageIO.write(resizedImage, format, output);
	
		    return output.getAbsolutePath();
		}
		
		return null;
	}
}
