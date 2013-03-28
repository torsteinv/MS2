package no.torsteinv.MS2.GUI;

import java.awt.Graphics;

import no.torsteinv.MS2.AbstractMenu.Form;

public interface IGUI {
	public void draw(Graphics g);
	
	public boolean isOverlay();
	
	public void tick();
	
	public void defineButtons(Form gui);
	
	
}
