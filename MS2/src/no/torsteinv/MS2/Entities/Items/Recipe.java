package no.torsteinv.MS2.Entities.Items;

import no.torsteinv.MS2.Game.MS2.Buildings.ContainerBuilding;
import no.torsteinv.MS2.Game.MS2.Items.Item;
import no.torsteinv.MS2.Main.Main;

public class Recipe {
	public static final Recipe NULL = new Recipe(-1, RecipeAction.None);
	public RecipeAction Action;
	public int Product;
	public Item[] items;
	public int[] quantities = { 1, 1, 1, 1 };
	public int Result = 1;
	boolean quantitiesSet = false;

	public Recipe(int Item, RecipeAction a, Item... i) {
		this.Action = a;
		this.items = i;
		this.Product = Item;
	}

	public Recipe setResult(int r) {
		this.Result = r;
		return this;
	}

	public Recipe setQuantities(int... q) {
		this.quantities = q;
		this.quantitiesSet = true;
		return this;
	}

	public void fulfil() throws Exception {
		for (int in = 0; in < this.items.length; in++)
			if (!ItemStack.has(new ItemStack(this.items[in], new ContainerBuilding(
					0, 0, Main.player), this.quantitiesSet ? this.quantities[in] : 1, 0,
					0,false)))
				throw new Exception("Does not have resorce to fulfil recipe");
		for (int i = 0; i < this.items.length; i++)
			Main.removeItemStack(this.items[i], this.quantitiesSet ? this.quantities[i] : 1);
		
		Main.addItemStack(BaseItem.ItemByID(this.Product), this.Result,true);
	}
	
}
