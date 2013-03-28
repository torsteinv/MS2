package no.torsteinv.MS2.Managers.Visualization;

import java.awt.*;
import java.awt.image.*;

public class TransperentManager {
	public static Image makeColorTransparent(Image im, final Color color) {
		ImageFilter filter = new RGBImageFilter() {

			public int markerRGB = color.getRGB() | 0xFF000000;

			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					return 0x00FFFFFF & rgb;
				} else {
					return rgb;
				}
			}
		};

		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}
}
