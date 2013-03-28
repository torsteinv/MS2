package no.torsteinv.MS2.Game.MS2.Factory.Buildings;

import java.awt.Color;
import java.awt.Image;
import java.util.Arrays;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Buildings.Building;
import no.torsteinv.MS2.Entities.Factory.FactoryElement;
import no.torsteinv.MS2.Entities.Factory.Buildings.Machine;
import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Items.ItemStack;
import no.torsteinv.MS2.Entities.Items.RecipeAction;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.Engine.Others.PlacementRestriction;
import no.torsteinv.MS2.Game.MS2.FormGeneration.GameFormGeneration;
import no.torsteinv.MS2.Game.MS2.Items.Item;
import no.torsteinv.MS2.Game.MS2.Items.MainItemList;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public class InputMachine extends Machine {

	@Override
	public void OnClick() {
		if (this.Properties.get("In") == null)
			this.Properties.put("In", MainItemList.none);
		if (this.Properties.get("Quantity") == null)
			this.Properties.put("Quantity", 1);
		GameFormGeneration.ToggleItems.setText(this.Properties.get("In")
				.toString().replace("_", " "));
		GameFormGeneration.quantityText.setText("Quantity : "
				+ this.Properties.get("Quantity"));
		Main.setPaintState(MainPaintStateList.Input);
		Main.Interfaced = this;
	}

	public InputMachine(int CanvasX, int CanvasY, Building Home, BaseItem in,
			int quantity) {
		super(CanvasX, CanvasY, Home, Home == null ? 0 : Home.getPlayer());

		this.Properties.put("In", in);
		this.Properties.put("Quantity", quantity);
		this.Properties.put("Texture", MainResourceLoading.images.get("Input"));

	}

	public InputMachine(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	public BaseItem getInput() {
		return (BaseItem) this.Properties.get("In");
	}

	public InputMachine setIn(BaseItem in) {
		this.Properties.put("In", in);
		return this;
	}

	int i = 0;

	@Override
	public void transitAll() {
		if (this.i < 4) {
			this.i++;
			return;
		}
		if (this.Properties.get("In") == null
				|| this.Properties.get("In").equals((MainItemList.none)))
			return;
		ItemStack sd = ItemStack.findStackWithStuffInContainer(
				(BaseItem) this.Properties.get("In"),
				(int) this.Properties.get("Quantity"));
		FactoryElement p = FactoryElement.FindFactoryElementAt(
				getCanvasX() + 1, getCanvasY());
		if (sd == null || p == null)
			return;
		ItemStack s = sd.ExtractQuantity((int) this.Properties.get("Quantity"));
		if (s == null)
			return;
		s.setInPipe(true);
		p.addContent(s);
		p.setFrom(2);
		try {
			Main.addEntity(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public InputMachine setQuantity(int quantity) {
		this.Properties.put("Quantity", quantity);
		return this;
	}

	public int getQuantity() {
		return (int) this.Properties.get("Quantity");
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
		return new Color(255, 0, 255);
	}

	public static BaseItem Toggle(BaseItem in) {
		return Item.ItemList[in.ID + 1 >= Arrays.asList(Item.ItemList).size() ? 0
				: in.ID + 1];
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
		return PlacementRestriction.FACTORY_LEFTSIDE;
	}

}
