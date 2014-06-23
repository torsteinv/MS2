package no.torsteinv.MS2.Entities.Movables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Selectable;
import no.torsteinv.MS2.Entities.Updatable;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Managers.Visualization.TransperentManager;

public abstract class Movable extends Entity implements Updatable, Selectable {

	public Movable(float x, float y, int player, int discoverPower) {
		super(x, y, player);
		Main.Darkness.add(this, true, discoverPower);
		this.Properties.put("Done", false);
		this.Properties.put("CommandCenter", new Command());
		this.Properties.put("Hitbox", new Rectangle((int) x, (int) y, 10, 50));
		this.Properties.put("Selected", false);
	}

	public Movable(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	public abstract void updateOthers();

	@Override
	public abstract Color getMapColor();

	@Override
	public abstract PaintState getAssosiatedInterface();

	@Override
	public void update() {
		getCommandCenter().update(this);
		getSelectionBox().setBounds((int) getX(), (int) getY(),
				getAbsoluteTexture().getWidth(null),
				getAbsoluteTexture().getHeight(null));
		updateOthers();
	}

	@Override
	public Image getTexture() {
		Image img = new BufferedImage(getAbsoluteTexture().getWidth(null),
				getAbsoluteTexture().getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.drawImage(getAbsoluteTexture(), 0, 0, null);
		g.setColor(Color.BLUE);
		if (isSelected())
			g.drawRect(0, 0, getSelectionBox().width - 1,
					getSelectionBox().height - 1);
		return TransperentManager.makeColorTransparent(img, new Color(255, 0,
				255));
	}

	protected Image getAbsoluteTexture() {
		return (Image) this.Properties.get("Texture");
	}

	@Override
	public CommandType CommandAt(int i, int j,boolean leftClick) {
		return CommandType.None;
	}

	@Override
	public Command getCommandCenter() {
		return (Command) this.Properties.get("CommandCenter");
	}

	@Override
	public Rectangle getSelectionBox() {
		return (Rectangle) this.Properties.get("Hitbox");
	}

	@Override
	public boolean isSelected() {
		return (boolean) this.Properties.get("Selected");
	}

	@Override
	public void setSelected(boolean Selected) {
		this.Properties.put("Selected", Selected);
	}

	public void setDone(boolean b) {
		this.Properties.put("Done", b);
	}

	public boolean isDone() {
		return (boolean) this.Properties.get("Done");
	}
}
