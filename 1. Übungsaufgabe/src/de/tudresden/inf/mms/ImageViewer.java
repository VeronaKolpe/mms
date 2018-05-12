package de.tudresden.inf.mms;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * JFrame-Anwendung (Fenster mit Bildansicht und Funktionsauswahl) <br>
 * <strong>Nicht bearbeiten!</strong>
 */
public class ImageViewer extends JFrame {

	private static final long serialVersionUID = 1L;
	ImageIcon myImage;
	File file = new File("bild.jpg");
	ImageEditor myEditor;

	JPanel imagePanel = new JPanel();

	public ImageViewer() {
		try {
			myImage = new ImageIcon(file.getAbsolutePath());
			myEditor = new ImageEditor(ImageIO.read(file));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		imagePanel.add(new JLabel(myImage));
		imagePanel.setBackground(Color.WHITE);

		add(imagePanel, BorderLayout.CENTER);

		final JComboBox<String> operator = new JComboBox<String>();
		operator.addItem("Originalbild");
		operator.addItem("Graustufen");
		operator.addItem("RGB->BGR");
		operator.addItem("Gammakorrektur");
		operator.addItem("Spiegelung");
		operator.addItem("Farbkanäle RGB");
		operator.addItem("Farbkanäle YCbCr");
		operator.addItem("Pixelwiederholung");
		operator.addItem("Interpolation");
		operator.addItem("Ordered Dithering");
		operator.addItem("Floyd-Steinberg-Dithering");
		operator.addItem("Zusatzaufgabe");
		
		operator.setMaximumRowCount(operator.getModel().getSize());
		
		JPanel menu = new JPanel();
		menu.setBackground(Color.WHITE);
		add(menu, BorderLayout.NORTH);

		menu.add(operator);

		operator.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				Image img = myImage.getImage();
				switch (operator.getSelectedItem().toString()) {
				case "Originalbild":
					img = myEditor.original();
					break;
				case "Graustufen":
					img = myEditor.grayscale();
					break;
				case "RGB->BGR":
					img = myEditor.swap();
					break;
				case "Gammakorrektur":
					img = myEditor.gamma();
					break;
				case "Spiegelung":
					img = myEditor.mirror();
					break;
				case "Farbkanäle RGB":
					img = myEditor.rgb_tiles();
					break;
				case "Farbkanäle YCbCr":
					img = myEditor.ycbcr_tiles();
					break;
				case "Pixelwiederholung":
					img = myEditor.resize();
					break;
				case "Interpolation":
					img = myEditor.resample();
					break;
				case "Ordered Dithering":
					img = myEditor.orderedDithering();
					break;
				case "Floyd-Steinberg-Dithering":
					img = myEditor.floydSteinbergDithering();
					break;
				case "Zusatzaufgabe":
					img = myEditor.zusatzaufgabe();
					break;
				}
				myImage.setImage(img);
				imagePanel.validate();
				imagePanel.repaint();
			}
		});


	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ImageViewer frame = new ImageViewer();

		frame.setSize(frame.myImage.getIconWidth() + 50,
				frame.myImage.getIconHeight() + 100);
		frame.setTitle("Aufgabe 1");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}

}
