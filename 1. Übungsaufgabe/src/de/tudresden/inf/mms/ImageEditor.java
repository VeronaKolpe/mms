package de.tudresden.inf.mms;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @author <Verona> <Kolpe>, <4609197>
 * 
 */
public class ImageEditor {

	/**
	 * Originalbild (Ausgangspunkt f�r Berechnungen)
	 */
	private BufferedImage image;

	/**
	 * Tempor�re Kopie bzw. Ergebnisbild
	 */
	private BufferedImage tmpImg;

	public ImageEditor(BufferedImage image) {
		super();
		this.image = image;
		tmpImg = new BufferedImage(image.getWidth(), image.getHeight(),
				image.getType());
	}
	
	/**
	 * Liefert das Ausgangsbild.
	 * 
	 * @return {@link java.awt.image.BufferedImage}
	 */
	public Image original() {
		
		return image;
	}

	/**
	 * Konvertiert das vorgegebene RGB-Bild ({@link ImageEditor#image}) in ein
	 * Graustufenbild.
	 * 
	 * @return {@link java.awt.image.BufferedImage}
	 */
	public Image grayscale() {
		int gray;

		int[] rgb;

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				rgb = ImageHelper.toRGBArray(image.getRGB(x, y));
				gray = (int) Math.floor((rgb[0] + rgb[1] + rgb[2]) / 3);
				rgb[0] = gray;
				rgb[1] = gray;
				rgb[2] = gray;
				tmpImg.setRGB(x, y, ImageHelper.toIntRGB(rgb));
			}
		}

		return tmpImg;
	}

	/**
	 * @see Aufgabe 1.1.1
	 * @return {@link java.awt.image.BufferedImage}
	 */
	public Image swap() {

		int[] rgb;
		
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				rgb = ImageHelper.toRGBArray(image.getRGB(x, y));
				
				int red = rgb[0]; //zwischenspeicherung des farbwertes rot
				int blue = rgb[2]; //zwischenspeicherung des farbwertes blau
				
				rgb[0] = blue; // dem vorherigen rot wird nun der blaue farbwert zugeordnet
				rgb[2] = red; // dem vorherigen blau wird nun der rote farbwert zugeordnet
				
				tmpImg.setRGB(x, y, ImageHelper.toIntRGB(rgb));
			}
		}
		return tmpImg;
	}
	
	/**
	 * @see Aufgabe 1.1.2
	 * @return {@link java.awt.image.BufferedImage}
	 */
	public Image gamma() {

		int[] rgb;

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				rgb = ImageHelper.toRGBArray(image.getRGB(x, y));
				//führt die gammakorrektur pro farbwert aus
				rgb[0] = (int) (rgb[0]*2.2); 
				rgb[1] = (int) (rgb[1]*2.2);
				rgb[2] = (int) (rgb[2]*2.2);
				tmpImg.setRGB(x, y, ImageHelper.toIntRGB(rgb));
			}
		}

		
		return tmpImg;
	}

	/**
	 * @see Aufgabe 1.1.3
	 * @return {@link java.awt.image.BufferedImage}
	 */
	
	public Image mirror() {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// mirroredX entspricht dem x-ten pixel, von der rechten seite des bildes aus gezählt
				//angenommen das bild wäre 10 pixel breit, dann:
				//x=0 und mirroredX=9
				//also wird der erste pixel der zeile mit dem letzten pixel der zeile getauscht
				int mirroredX = image.getWidth() - x - 1;
				tmpImg.setRGB(x, y, image.getRGB(mirroredX, y));
			}
		}
		
		/*for (int x = 0, mirroredX = image.getWidth() - 1; x < image.getWidth(); x++, mirroredX--) {
			for (int y = 0; y < image.getHeight(); y++) {
				tmpImg.setRGB(x, y, image.getRGB(mirroredX, y));
			}
		}
		*/
		return tmpImg;
	}

	/**
	 * @see Aufgabe 1.2.1
	 * @return {@link java.awt.image.BufferedImage}
	 */
	public Image rgb_tiles() {

		int[] rgb;
		int w = image.getWidth();
		int h = image.getHeight();
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				//alle pixel, die auf der rechten oberen hälfte des Bildes liegen
				if (x >= (w/2) && y< (h/2)) {
					//auswahl der pixelfarbe aus dem linken oberen bildbereich
					int clipX = x % (w/2);
					int clipY = y % (h/2);
					rgb = ImageHelper.toRGBArray(image.getRGB(clipX, clipY));
					//rot einfärben
					rgb[1] = 0;
					rgb[2] = 0;
					tmpImg.setRGB(x, y, ImageHelper.toIntRGB(rgb));
					
				}
				//alle pixel, die auf der linken unteren hälfte des Bildes liegen
				else if (x < (w/2) && y >= (h/2)) {
					//auswahl der pixelfarbe aus dem linken oberen bildbereich
					int clipX = x % (w/2);
					int clipY = y % (h/2);
					rgb = ImageHelper.toRGBArray(image.getRGB(clipX, clipY));
					//grün einfärben
					rgb[0] = 0;
					rgb[2] = 0;
					tmpImg.setRGB(x, y, ImageHelper.toIntRGB(rgb));
					
				}
				//alle pixel, die auf der rechten unteren hälfte des Bildes liegen
				else if (x >= (w/2) && y >= (h/2)) {
					//auswahl der pixelfarbe aus dem linken oberen bildbereich
					int clipX = x % (w/2);
					int clipY = y % (h/2);
					rgb = ImageHelper.toRGBArray(image.getRGB(clipX, clipY));
					//blau einfärben
					rgb[0] = 0;
					rgb[1] = 0;
					tmpImg.setRGB(x, y, ImageHelper.toIntRGB(rgb));	
				}
				else {
					rgb = ImageHelper.toRGBArray(image.getRGB(x, y));
					tmpImg.setRGB(x, y, ImageHelper.toIntRGB(rgb));
					
				}
			}
		}
		return tmpImg;
	}
	
	/**
	 * @see Aufgabe 1.2.2
	 * @return {@link java.awt.image.BufferedImage}
	 */
	public Image ycbcr_tiles() {
		// funktioniert analog zu rgb_tiles(), nur dass es hier andere einfärbungen gibt
		int[] rgb;
		int w = image.getWidth();
		int h = image.getHeight();
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (x >= (w/2) && y< (h/2)) {
					int clipX = x % (w/2);
					int clipY = y % (h/2);
					rgb = ImageHelper.toRGBArray(image.getRGB(clipX, clipY));
					//Y	 einfärben
					int yChannel = (int) (0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2]);
					rgb[0] = yChannel;
					rgb[1] = yChannel;
					rgb[2] = yChannel;
					tmpImg.setRGB(x, y, ImageHelper.toIntRGB(rgb));
					
				}
				else if (x < (w/2) && y >= (h/2)) {
					int clipX = x % (w/2);
					int clipY = y % (h/2);
					rgb = ImageHelper.toRGBArray(image.getRGB(clipX, clipY));
					//Cb einfärben
					int cbChannel =(int) (128 - 0.168736 * rgb[0] - 0.331264 * rgb[1] + 0.5 * rgb[2]);
					rgb[0] = cbChannel;
					rgb[1] = cbChannel;
					rgb[2] = cbChannel;
					tmpImg.setRGB(x, y,ImageHelper.toIntRGB(rgb));
					
				}
				else if (x >= (w/2) && y >= (h/2)) {
					int clipX = x % (w/2);
					int clipY = y % (h/2);
					rgb = ImageHelper.toRGBArray(image.getRGB(clipX, clipY));
					//Cr einfärben
					int crChannel =(int) (128 + 0.5 * rgb[0] - 0.418688 * rgb[1] - 0.081312 * rgb[2]);
					rgb[0] = crChannel;
					rgb[1] = crChannel;
					rgb[2] = crChannel;
					tmpImg.setRGB(x, y, ImageHelper.toIntRGB(rgb));	
				}
				else {
					rgb = ImageHelper.toRGBArray(image.getRGB(x, y));
					tmpImg.setRGB(x, y, ImageHelper.toIntRGB(rgb));
					
				}
			}
		}
				
		return tmpImg;
	}

	/**
	 * @see Aufgabe 1.3.1
	 * @return {@link java.awt.image.BufferedImage}
	 */
	public Image resize() {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				tmpImg.setRGB(x, y, image.getRGB(x - (x%2), y - (y%2)));
			}
		}
		return tmpImg;
	}
	
	/**
	 * @see Aufgabe 1.3.2
	 * @return {@link java.awt.image.BufferedImage}
	 */
	public Image resample() {

		/*
		 * ToDo
		 */
		
		return tmpImg;
	}

	

	/**
	 * @see Aufgabe 1.4.1
	 * @return {@link java.awt.image.BufferedImage}
	 */
	public Image orderedDithering() {
		//neue farben für die eingeschränkte farbpalette
		int[] black = {0,0,0};
		int[] blue = {0,0,255};
		int[] green = {0,255,0};
		int[] cyan = {0,255,255};
		int[] red = {255,0,0};
		int[] magenta = {255,0,255};
		int[] yellow = {255,255,0};
		int[] white = {255,255,255};
		int[][] palette = {black,blue,green,cyan,red,magenta,yellow,white};
		
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int[] rgb;
				rgb = ImageHelper.toRGBArray(image.getRGB(x, y));
				//anwendung der bayer-matrix auf die rgb-farben
				rgb[0] = rgb[0] + ImageHelper.BAYER8x8[x%8][y%8];
				rgb[1] = rgb[1] + ImageHelper.BAYER8x8[x%8][y%8];
				rgb[2] = rgb[2] + ImageHelper.BAYER8x8[x%8][y%8];
				//dem jeweiligen pixel wird nun eine farbe aus der palette zugeordent, die möglichst nah am vorherigen wert war
				tmpImg.setRGB(x, y, ImageHelper.toIntRGB((ImageHelper.getColorFromPalette(rgb, palette))));
			}
		}
		
		return tmpImg;
	}

	/**
	 * @see Aufgabe 1.4.2
	 * @return {@link java.awt.image.BufferedImage}
	 */
	public Image floydSteinbergDithering() {
		int[] intArray = { 7, 9, 5, 1, 3 };
		System.out.println(Arrays.toString(intArray));
		/*int[] rgb;
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				rgb = ImageHelper.toRGBArray(image.getRGB(x, y));
				tmpImg.setRGB(x, y, ImageHelper.toIntRGB(rgb));
			}
		}*/
		
		int[] black = {0,0,0};
		int[] blue = {0,0,255};
		int[] green = {0,255,0};
		int[] cyan = {0,255,255};
		int[] red = {255,0,0};
		int[] magenta = {255,0,255};
		int[] yellow = {255,255,0};
		int[] white = {255,255,255};
		int[][] palette = {black,blue,green,cyan,red,magenta,yellow,white};
		
		for (int y = 0; y < image.getHeight()-1; y++) {
			for (int x = 1; x < image.getWidth()-1; x++) {
				//markiert momentane farbwerte als "old_rgb"
				int [] old_rgb  = ImageHelper.toRGBArray(image.getRGB(x, y)); //wenn hier anstatt "image" "tmpImg" steht, dann wird es schwarz

				//wandelt alte rgb in farben aus der neuen palette um
				int [] new_rgb = ImageHelper.getColorFromPalette(old_rgb, palette);

				//setzt momentanen pixel auf die neue farbe 
				tmpImg.setRGB(x, y, ImageHelper.toIntRGB(new_rgb));
				//berechnet den farbfehler zwischen alten und neuen farbwert
				int [] color_error =   {old_rgb[0] - new_rgb[0],
										old_rgb[1] - new_rgb[1],
										old_rgb[2] - new_rgb[2]
									   };
					int [] current_color = ImageHelper.toRGBArray(tmpImg.getRGB(x+1, y));
					System.out.println(Arrays.toString(current_color));
					//System.out.println(current_color + " current color davor");
					int r_error = (int) ((color_error[0]*7/16.0) + 0.5d);
					int g_error = (int) ((color_error[1]*7/16.0) + 0.5d);
					int b_error = (int) ((color_error[2]*7/16.0) + 0.5d);
					
					current_color [0] = current_color [0] + r_error;
					current_color [1] = current_color [1] + g_error;
					current_color [2] = current_color [2] + b_error;
					System.out.println(Arrays.toString(current_color));
					//System.out.println(current_color + " current color danach");
					tmpImg.setRGB(x+1, y, ImageHelper.toIntRGB(current_color));
//__________________________________________________________________________________________________
					current_color = ImageHelper.toRGBArray(tmpImg.getRGB(x-1, y+1));
					
					r_error = (int) ((color_error[0]*3/16.0) + 0.5d);
					g_error = (int) ((color_error[1]*3/16.0) + 0.5d);
					b_error = (int) ((color_error[2]*3/16.0) + 0.5d);
					
					current_color [0] = current_color [0] + r_error;
					current_color [1] = current_color [1] + g_error;
					current_color [2] = current_color [2] + b_error;
					
					tmpImg.setRGB(x-1, y+1, ImageHelper.toIntRGB(current_color));
//__________________________________________________________________________________________________
					current_color = ImageHelper.toRGBArray(tmpImg.getRGB(x, y+1));
					
					r_error = (int) ((color_error[0]*5/16.0) + 0.5d);
					g_error = (int) ((color_error[1]*5/16.0) + 0.5d);
					b_error = (int) ((color_error[2]*5/16.0) + 0.5d);
					
					current_color [0] = current_color [0] + r_error;
					current_color [1] = current_color [1] + g_error;
					current_color [2] = current_color [2] + b_error;
					
					tmpImg.setRGB(x, y+1,  ImageHelper.toIntRGB(current_color));
//__________________________________________________________________________________________________
					current_color = ImageHelper.toRGBArray(tmpImg.getRGB(x+1, y+1));
					
					r_error = (int) ((color_error[0]*1/16.0) + 0.5d);
					g_error = (int) ((color_error[1]*1/16.0) + 0.5d);
					b_error = (int) ((color_error[2]*1/16.0) + 0.5d);
					
					current_color [0] = current_color [0] + r_error;
					current_color [1] = current_color [1] + g_error;
					current_color [2] = current_color [2] + b_error;
					
					tmpImg.setRGB(x+1, y+1, ImageHelper.toIntRGB(current_color));
				}			
			}
		
		return tmpImg;
	}
	
	/**
	 * @see Zusatzaufgabe (optional)
	 * @return {@link java.awt.image.BufferedImage}
	 */
	public Image zusatzaufgabe() {

		/*
		 * ToDo
		 */
		
		return tmpImg;
	}

}
