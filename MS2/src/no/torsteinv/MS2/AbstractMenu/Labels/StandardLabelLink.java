package no.torsteinv.MS2.AbstractMenu.Labels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;

import no.torsteinv.MS2.AbstractMenu.ActionHandler;

public class StandardLabelLink extends StandardLabel {

	private String href = "http://google.com/";
	public StandardLabelLink(Point Pos, String name, Dimension size) {
		super(Pos, name, size);
		Handler = new ActionHandler(){

			@Override
			public void Handle() {
				Enter();
			}
		};
	}
	public StandardLabelLink(Point Pos, String name) {
		super(Pos, name);
		Handler = new ActionHandler(){

			@Override
			public void Handle() {
				Enter();
			}
		};
	}
	
	public void Enter() {
		try {
			Runtime.getRuntime().exec("cmd /c start " + href );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public StandardLabelLink setLink(String nl){
		href = nl;
		return this;
	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(HOVERED ? Color.BLUE : Color.BLACK);
		g.drawString(getText(),getPos().x,getPos().y + 20);
		g.drawLine(getPos().x,getPos().y + 21,getSize().width + getPos().x - 5,getPos().y + 21);
	}
	
}
