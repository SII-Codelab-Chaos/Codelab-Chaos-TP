package fr.sii.atlantique.fusiion.fusiion_gestion_competences.services;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.imageio.ImageIO;

/**
 * 
 * GestionImage est la classe qui permet de gérer la transformation de et vers la base 64
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public final class GestionImage {
	/**
	 * 
	 * @param src l'image a redimmentionner
	 * @param w la largeur demandée
	 * @param h la hauteur demandée
	 * @return L'image apres redimentionnement
	 */
	public static BufferedImage scale(BufferedImage src, int w, int h, int type)
	{
		BufferedImage img = new BufferedImage(w, h, type);
		int x, y;
		int ww = src.getWidth();
		int hh = src.getHeight();
		int[] ys = new int[h];
		for (y = 0; y < h; y++)
			ys[y] = y * hh / h;
		for (x = 0; x < w; x++) {
			int newX = x * ww / w;
			for (y = 0; y < h; y++) {
				int col = src.getRGB(newX, ys[y]);
				img.setRGB(x, y, col);
			}
		}
		return img;
	}

	/**
	 * Permet de transfomrée une BufferedImage en String Base64
	 * 
	 * @param img
	 * @param formatName
	 * @return L'image encodée
	 */
	public static String imgToBase64String(final RenderedImage img, final String formatName) {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
			return os.toString(StandardCharsets.ISO_8859_1.name());
		} catch (final IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	/**
	 * Permet de transformer une image encodée en BufferedImage
	 * 
	 * @param base64String
	 * @return L'image décodée
	 * @throws Exception 
	 */
	public static BufferedImage base64StringToImg(final String base64String) throws Exception {
		try {
			byte[] b = Base64.getDecoder().decode(base64String);
			return ImageIO.read(new ByteArrayInputStream(b));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
