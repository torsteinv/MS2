package no.torsteinv.MS2.Game.MS2.Buildings;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Buildings.Building;
import no.torsteinv.MS2.Entities.Buildings.ClickableBuilding;
import no.torsteinv.MS2.Entities.EntityAttachments.Gun;
import no.torsteinv.MS2.Entities.EntityAttachments.GunType;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.MS2.FormGeneration.GameFormGeneration;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public class GunBuilding extends Building implements ClickableBuilding {

	public GunBuilding(int x, int y, int player) {
		super(x, y, player);
		this.Properties.put("Texture",
				MainResourceLoading.images.get("GunBuilding"));
		this.Properties.put("BulletCount", 0);
		this.Properties.put("Leave100", true);
	}

	public GunBuilding(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	@Override
	public Color getMapColor() {
		return Color.GRAY;
	}

	@Override
	public Image getTexture() {
		return (Image) this.Properties.get("Texture");
	}

	@Override
	public void OnClick() {
		Main.setPaintState(MainPaintStateList.Gun);
		Main.Interfaced = this;
		GameFormGeneration.toggleleave100.setText((Boolean) this.Properties
				.get("Leave100") ? "Yes" : "No");
	}

	public GunBuilding() {
	}

	@Override
	public PaintState getAssosiatedInterface() {
		return MainPaintStateList.Gun;
	}

	@Override
	public CommandType CommandAt(int i, int j) {
		return CommandType.None;
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}

	@Override
	public void OnPlaceBought(int x, int y) {
		try {
			Main.addEntity(new Gun(x, y, getPlayer(), this,
					GunType.StandrardPressureGun));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
