package no.torsteinv.MS2.AbstractMenu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import no.torsteinv.MS2.Main.Identification.Identification;
import no.torsteinv.MS2.Main.Identification.IdentificationException;
import no.torsteinv.MS2.Main.Identification.IdentificationTypes;


public abstract class AbstractElement{
	public static Font defaultFont = Font.decode("Serif");
	public static FontMetrics defaultMetrics = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB).getGraphics().getFontMetrics();

	
	public boolean FOCUS = false;
	public boolean HOVERED = false;
	public boolean HIDDEN = false;
	
	private String text = "Login";
	private String toolTip = "";
	private Dimension size = new Dimension(10,10);
	private Point pos = new Point();
	
	public ActionHandler Handler;
	
	public AbstractElement(Point Pos, String name, String toolTip){
		init(Pos,name,new Dimension((int)(defaultMetrics.stringWidth(name)) + 10,30), toolTip);
	}
	public AbstractElement(Point Pos, String name,Dimension size, String toolTip){
		init(Pos,name,size, toolTip);
	}
	public void init(Point Pos,String name,Dimension size, String toolTip){
		this.size = size;
		this.text = name;
		this.pos = Pos;
		this.toolTip = toolTip;
		try {
			Identification.add(name + "@" + size + "@" + Pos,IdentificationTypes.MenuElement);
		} catch (IdentificationException e) {
			e.printStackTrace();
		}
	}
	public abstract void paint(Graphics g);
	
	public boolean isFOCUS() {
		return FOCUS;
	}
	public AbstractElement setFOCUS(boolean fOCUS) {
		FOCUS = fOCUS;
		return this;
	}
	public String getText() {
		String censoredStr = "";
			for(int x = 0;x < text.length();x++)
				censoredStr += "*";
		return HIDDEN ? censoredStr : text;
	}
	public AbstractElement setText(String text) {
		this.text = text;
		return this;
	}
	public Dimension getSize() {
		return size;
	}
	public AbstractElement setSize(Dimension size) {
		this.size = size;
		return this;
	}
	public Point getPos() {
		return pos;
	}
	public AbstractElement setPos(Point pos) {
		this.pos = pos;
		return this;
	}
	public String getToolTip() {
		return toolTip;
	}
	public AbstractElement setToolTip(String toolTip) {
		this.toolTip = toolTip;
		return this;
	}
}
