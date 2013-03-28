package no.torsteinv.MS2.Game.MS2.Factory.Buildings;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Buildings.Building;
import no.torsteinv.MS2.Entities.Factory.Buildings.Machine;
import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Items.ItemStack;
import no.torsteinv.MS2.Entities.Items.RecipeAction;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.MS2.Items.Item;
import no.torsteinv.MS2.Game.MS2.Items.MainItemList;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Loading.MainResourceLoading;

public class Crafter extends Machine {

	public BaseItem getOutput() {
		return (BaseItem) this.Properties.get("Output");
	}

	public void setOutput(BaseItem output) {
		this.Properties.put("Output", output);
	}

	public void setTexture(Image texture) {
		this.Properties.put("Texture", texture);
	}

	public Crafter(int CanvasX, int CanvasY, Building Home, int Player) {
		super(CanvasX, CanvasY, Home, Player);
		this.Properties.put("Texture",
				MainResourceLoading.images.get("Crafter"));
		this.Properties.put("Output", MainItemList.Bullet);
	}

	public Crafter(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	@Override
	public void OnClick() {
		Main.Interfaced = this;
		Main.setPaintState(MainPaintStateList.Crafter);
	}

	@Override
	public Image getTexture() {
		return (Image) this.Properties.get("Texture");
	}

	@Override
	public Color decodeColor() {
		return Color.CYAN;
	}

	@Override
	public ItemStack PerformRecipe(ItemStack[] is) {
		is = combineStackData(is);
		ItemStack c;
		boolean[] hasThing = new boolean[getOutput().recipe.items.length];
		for (int in = 0; in < getOutput().recipe.items.length; in++) {
			c = new ItemStack(getOutput().recipe.items[in], null,
					getOutput().recipe.quantities[in], 0, 0, false);
			for (int in1 = 0; in1 < is.length; in1++)
				if (c.getType().equals(is[in1].getType())
						&& c.getQuantity() <= is[in1].getQuantity())
					addHasThing(hasThing);
		}
		if (allTrue(hasThing)
				&& getOutput().recipe.Action.IDN == RecipeAction.Craft.IDN) {
			int qm = 0;
			for (int ind = 0; ind < getOutput().recipe.items.length; ind++)
				qm += is[ind].getQuantity()
						/ getOutput().recipe.quantities[ind];
			return new ItemStack(Item.ItemByID(getOutput().recipe.Product),
					getHome(), getOutput().recipe.Result * qm, 0, 0, false);
		}
		return null;
	}

	private ItemStack[] combineStackData(ItemStack[] is) {
		// Hashmap init
		HashMap<BaseItem, Integer> Data = new HashMap<BaseItem, Integer>();

		// Hashmap putting
		for (ItemStack s : is)
			if (Data.containsKey(s.getType()))
				Data.put(s.getType(), s.getQuantity() + Data.get(s.getType()));
			else
				Data.put(s.getType(), s.getQuantity());

		// Hashmap converting
		ItemStack[] isn = new ItemStack[Data.size()];
		for (int i = 0; i < Data.size(); i++)
			isn[i] = new ItemStack((Item) Data.keySet().toArray()[i], null,
					Data.get(Data.keySet().toArray()[i]).intValue(), 0, 0,
					false);

		return isn;
	}

	private void addHasThing(boolean[] hasThing) {
		for (int i = 0; i < hasThing.length; i++)
			if (!hasThing[i]) {
				hasThing[i] = true;
				return;
			}
	}

	private boolean allTrue(boolean[] parts) {
		for (boolean b : parts)
			if (!b)
				return false;
		return true;
	}

	public void Toggle() {
		setOutput(ArrayCrafterUsingRecipes()[ListCrafterUsingRecipes().indexOf(
				getOutput()) >= ArrayCrafterUsingRecipes().length - 1 ? 0
				: ListCrafterUsingRecipes().indexOf(getOutput()) + 1]);
	}

	private BaseItem[] ArrayCrafterUsingRecipes() {
		return convert(ListCrafterUsingRecipes().toArray());
	}

	private ArrayList<BaseItem> ListCrafterUsingRecipes() {
		ArrayList<BaseItem> i = new ArrayList<BaseItem>();
		for (BaseItem it : Item.ItemList)
			if (it != null && it.recipe.Action == RecipeAction.Craft)
				i.add(it);
		return i;
	}

	private Item[] convert(Object[] i) {
		Item[] is = new Item[i.length];
		for (int in = 0; in < i.length; in++)
			is[in] = (Item) i[in];
		return is;
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}

	@Override
	public RecipeAction getAssosiatedRecipeAction() {
		return RecipeAction.Craft;
	}

	@Override
	public PaintState getAssosiatedInterface() {
		return null;
	}
}
