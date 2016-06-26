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
	
	public static BufferedImage getBufferedImageFromUrl(final String imageUrl) throws IOException {
		if(!("").equals(imageUrl)) {
			final URL url = new URL(imageUrl);
			String newFileName = imageUrl.replaceAll("\\.", "_");
			newFileName = newFileName.substring(newFileName.lastIndexOf("/") + 1, newFileName.length());
			System.out.println(newFileName);
			
			final InputStream inputStream = url.openStream();
			final ImageInputStream input = ImageIO.createImageInputStream(inputStream);
			final Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
			
			if(readers.hasNext()) {
				ImageReader reader = readers.next();
				
				reader.setInput(input);
				
				BufferedImage image = reader.read(0);
				
				String format = reader.getFormatName();
				File file = new File(IMAGE_DIR + "/" + newFileName + "." + format.toLowerCase());
				ImageIO.write(image, format, file);
			}
			
			input.close();
		}
		
		return null;
	}
	
	public static ImageIcon resizeImageIcon(final BufferedImage originalImage, final int width, final int height, final int type) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(originalImage, 0, 0, width, height, null);
	    g.dispose();

	    return new ImageIcon(resizedImage);
	}
}
