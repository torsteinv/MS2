package no.torsteinv.MS2.Entities.EntityAttachments;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Movables.Movable;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Managers.Visualization.TransperentManager;

public class Gun extends EntityAttachment {

	public Gun(int x, int y, int player, Entity parent, GunType Type) {
		super(x, y, player, parent, "Airgun");
		this.Properties.put("AngleR", 0f);
		this.Properties.put("Type", Type);
		setUpdateRequired(true);
	}
	
	public Gun(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties,Entities);
		setUpdateRequired(true);
	}
	
	@Override
	public void onUpdate(){
		pointAt(Main.findClosestWithPropertyIsNot(Movable.class, (int)getX(), (int)getY(),"Player",Main.player));
	}

	private void pointAt(Entity e) {
		pointAt(e.getX() - Main.HorisontalAlignment,e.getY() - Main.VerticalAlignment);
	}

	public void pointAt(Point p) {
		pointAt(p.x, p.y);
	}

	public void pointAt(float x, float y) {
		this.Properties.put("AngleR",(float) Math.atan2((y - (getFloat("Y") - Main.VerticalAlignment)),
				(x - (getFloat("X") - Main.HorisontalAlignment))));

	}

	@Override
	public Image getTexture() {
		int w = ((Image)this.Properties.get("Texture")).getWidth(null);
		int h = ((Image)this.Properties.get("Texture")).getHeight(null);
		Image img = new BufferedImage(w > h ? w : h, w > h ? w : h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(new Color(0xFA691A));
		g.fillRect(0, 0, w > h ? w : h, w > h ? w : h);
		g.translate(25, 25);
		g.rotate((float) this.Properties.get("AngleR") + Math.toRadians(180));
		g.drawImage((Image)this.Properties.get("Texture"), -25, -5, null);
		return TransperentManager
				.makeColorTransparent(img, new Color(0xFA691A));
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}
}
