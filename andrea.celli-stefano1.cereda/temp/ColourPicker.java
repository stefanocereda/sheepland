package grafica;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ColourPicker extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 1L;

	private final BufferedImage image;
	private final JLabel red, green, blue;

	@Override
	public void mouseMoved(MouseEvent event) {
		int rgb = image.getRGB(event.getX(), event.getY());
		Color colour = new Color(rgb);
		setRGB(colour);
	}
	
	private void setRGB(Color colour) {
		red.setText("Red: " + colour.getRed());
		green.setText("Green: " + colour.getGreen());
		blue.setText("Blue: " + colour.getBlue());
	}

	ColourPicker(final BufferedImage image) {
		this.image = image;
		JLabel imageDisplay = new JLabel(new ImageIcon(image));
		red = new JLabel(); 
		green = new JLabel(); 
		blue = new JLabel();
		setRGB(Color.BLACK);
		
		JPanel rgbPanel = new JPanel(new GridLayout(4, 1));
		rgbPanel.add(red);
		rgbPanel.add(green);
		rgbPanel.add(blue);
		rgbPanel.add(Box.createHorizontalStrut(150)); // This is hacky. Don't do it. :)/>
		
		this.setLayout(new BorderLayout());
		this.add(imageDisplay, BorderLayout.CENTER);
		this.add(rgbPanel, BorderLayout.EAST);
		
		imageDisplay.addMouseMotionListener(this);
		
		}
	
	
	
	
	public static void main(String[] args) throws IOException {
		
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		BufferedImage image = ImageIO.read(chooser.getSelectedFile());
		ColourPicker picker = new ColourPicker(image);
		
		JFrame frame = new JFrame();
		frame.getContentPane().add(picker);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		mouseMoved(event);
	}

}

