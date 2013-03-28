package no.torsteinv.MS2.Game.MS2.Factory.Buildings;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Buildings.Building;
import no.torsteinv.MS2.Entities.Factory.Buildings.Machine;
import no.torsteinv.MS2.Entities.Items.RecipeAction;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public class Compressor extends Machine {

	public Compressor(int CanvasX, int CanvasY, Building Home, int Player) {
		super(CanvasX, CanvasY, Home, Player);
		this.Properties.put("Texture",
				MainResourceLoading.images.get("Compressor"));
	}

	@Override
	public Image getTexture() {
		return (Image) this.Properties.get("Texture");
	}

	@Override
	public Color decodeColor() {
		return Color.DARK_GRAY;
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}

	@Override
	public RecipeAction getAssosiatedRecipeAction() {
		return RecipeAction.Compress;
	}

	public Compressor(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	@Override
	public PaintState getAssosiatedInterface() {
		return null;
	}
}
