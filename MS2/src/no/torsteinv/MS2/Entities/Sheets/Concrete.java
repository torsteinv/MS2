package no.torsteinv.MS2.Entities.Sheets;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.Engine.Others.PlacementRestriction;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Main.Main;

public class Concrete extends Sheet {

	public static final float PrizePerPixel = 0.004f;

	public Concrete(int x, int y, int width, int height, int player) {
		super(x, y, width, height, player);
	}

	public Concrete(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	public static boolean canBePlaced(int x, int y, int width, int height) {
		for (Entity s : Main.Entities)
			if (s instanceof Concrete
					&& ((Concrete) s).getSelectionBox().intersects(x, y, width,
							height) && s.getPlayer() == Main.player)
				return true;
		return false;
	}

	public static int calcultePrize(int x, int y, int width, int height) {
		Rectangle r = new Rectangle(x, y, width, height);
		int i = 0;
		for (Entity s : Main.Entities)
			if (s instanceof Concrete
					&& ((Concrete) s).getSelectionBox().intersects(x, y, width,
							height)) {
				Rectangle in = (Rectangle) ((Concrete) s).getSelectionBox()
						.createIntersection(r);
				int w = in.width;
				int h = in.height;

				i += (w * h) * PrizePerPixel;
			}
		return (int) (((width * height) * PrizePerPixel) - i) < 0 ? 0
				: (int) (((width * height) * PrizePerPixel) - i);
	}

	@Override
	public Color getMapColor() {
		return Color.DARK_GRAY;
	}

	@Override
	public Color gameColor() {
		return Color.GRAY;
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}

	public static Sheet Parse(String str) {
		int x = Integer.parseInt(str.split(":")[1]);
		int y = Integer.parseInt(str.split(":")[2]);
		int w = Integer.parseInt(str.split(":")[3]);
		int h = Integer.parseInt(str.split(":")[4]);
		int p = Integer.parseInt(str.split(":")[5]);
		return new Concrete(x, y, w, h, p);
	}

	@Override
	public Image getTexture() {
		return Main.SolidColor(getSelectionBox().width,
				getSelectionBox().height, gameColor());
	}

	@Override
	public PaintState getAssosiatedInterface() {
		return MainPaintStateList.Game;
	}

	@Override
	public CommandType CommandAt(int i, int j) {
		return CommandType.None;
	}

	@Override
	public PlacementRestriction getRestrictions() {
		return PlacementRestriction.NON_OVERLAP_GAME;
	}

	@Override
	public void OnPlaceBought(int x, int y) {
	}
}
