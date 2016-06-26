package twitchviewer.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

public class IconListRenderer extends DefaultListCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1740077435738933533L;
	private Map<Object, ImageIcon> icons = null;
	
	public IconListRenderer(final Map<Object, ImageIcon> icons) {
		this.icons = icons;
	}
	
	@Override
	public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, 
			final boolean isSelected, final boolean cellHasFocus) {
		final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, 
				isSelected, cellHasFocus);
		
		if(icons.get(value) != null) {
			final Image img = icons.get(value).getImage();
			
			final ImageIcon icon = new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
			
			label.setIcon(icon);
		}
		
		label.setBounds(label.getX(), label.getY(), 50, 50);
		
		return label;
	}
}
