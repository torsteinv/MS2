package no.torsteinv.MS2.Game.MS2.Factory.Buildings;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Buildings.Building;
import no.torsteinv.MS2.Entities.Factory.Buildings.Machine;
import no.torsteinv.MS2.Entities.Items.ItemStack;
import no.torsteinv.MS2.Entities.Items.RecipeAction;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.Engine.Others.PlacementRestriction;
import no.torsteinv.MS2.Game.MS2.Buildings.ContainerBuilding;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public class OutputMachine extends Machine {

	public OutputMachine(int CanvasX, int CanvasY, Building Home, int Player) {
		super(CanvasX, CanvasY, Home, Player);
		this.Properties
				.put("Texture", MainResourceLoading.images.get("Output"));
	}

	public OutputMachine(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	@Override
	public void transitAll() {
		for (ItemStack s : getContent()){
				ItemStack cb = ItemStack
						.findStackOfTypeInContainer(s.getType());
				if (cb != null) {
					try {
						Main.setEntityProperty(
								cb,
								"Quantity",
								s.getQuantity()
										+ s.getQuantity());
						Main.setEntityPropertyEntity(cb, "Parent", Main
								.findClosest(ContainerBuilding.class, getHome()
										.getX(), getHome().getY()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						ContainerBuilding p = (ContainerBuilding) Main
								.findClosest(ContainerBuilding.class,
										(int) getHome().getX(), (int) getHome()
												.getY());

						Main.setEntityPropertyEntity(s, "Parent", p);
						Main.setEntityProperty(s, "InPipe", false);
						Main.setEntityProperty(s, "X",
								(float) ItemStack.getPrefferdPosition(p).x);
						Main.setEntityProperty(s, "X",
								(float) ItemStack.getPrefferdPosition(p).x);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
	}

	@Override
	public Image getTexture() {
		return (Image) this.Properties.get("Texture");
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}

	@Override
	public Color decodeColor() {
		return new Color(255, 106, 0);
	}

	@Override
	public RecipeAction getAssosiatedRecipeAction() {
		return null;
	}

	@Override
	public PaintState getAssosiatedInterface() {
		return null;
	}

	@Override
	public PlacementRestriction getRestrictions() {
		return PlacementRestriction.FACTROY_RIGHTSIDE;
	}

}
