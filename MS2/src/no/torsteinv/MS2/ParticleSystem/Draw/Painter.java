package no.torsteinv.MS2.ParticleSystem.Draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Managers.Visualization.TransperentManager;
import no.torsteinv.MS2.ParticleSystem.Particle;
import no.torsteinv.MS2.ParticleSystem.ParticleSystemList;

public class Painter {
	public static final int transperencyKey = 0xFB569A;
	
	public static Image generateOverlay(){
		Image img = new BufferedImage(Main.Width,Main.Height,BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		
		g.setColor(new Color(transperencyKey));
		g.fillRect(0, 0, img.getWidth(null), img.getHeight(null));
		paintParticles(g);
		
		img = TransperentManager.makeColorTransparent(img, new Color(transperencyKey));
		
		return img;
	}

	private static void paintParticles(Graphics g) {
		for(Particle p : ParticleSystemList.Particles)
			drawPoint((int)p.x - (int)Main.HorisontalAlignment,(int)p.y - (int)Main.VerticalAlignment,p.c,g);
	}
	
	private static void drawPoint(int x, int y, Color c,Graphics g){
		g.setColor(c);
		g.fillRect(x, y, 2, 2);
	}
}