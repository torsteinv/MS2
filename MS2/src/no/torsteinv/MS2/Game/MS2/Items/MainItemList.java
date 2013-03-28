package no.torsteinv.MS2.Game.MS2.Items;

import no.torsteinv.MS2.Entities.Items.Recipe;
import no.torsteinv.MS2.Entities.Items.RecipeAction;
import no.torsteinv.MS2.Game.Engine.Lists.ItemList;

public class MainItemList extends ItemList {

	public static final Item none = new Item(1025, Recipe.NULL);
	public static final Item Ice = new Item(7, Recipe.NULL);
	public static final Item Iron = new Item(8, Recipe.NULL);
	public static final Item Mars_Sand = new Item(12, Recipe.NULL);
	public static final Item Oil = new Item(14, Recipe.NULL);
	public static final Item Uran = new Item(2, Recipe.NULL);
	public static final Item Mars_Stone = new Item(4, Recipe.NULL);
	public static final Item Water = new Item(10, new Recipe(10,
			RecipeAction.Burn, Ice).setQuantities(2));
	public static final Item Red_Goo = new Item(9, new Recipe(9,
			RecipeAction.Mix, Mars_Sand, Water).setQuantities(2, 1));
	public static final Item Sand_Stone = new Item(5, new Recipe(5,
			RecipeAction.Compress, Mars_Stone));
	public static final Item Wet_Sand_Stone = new Item(6, new Recipe(6,
			RecipeAction.Mix, Sand_Stone, Water).setQuantities(2, 1));

	public static final Item Brick = new Item(0, new Recipe(0,
			RecipeAction.Burn, Red_Goo).setResult(10));
	public static final Item Steel = new Item(1, new Recipe(1,
			RecipeAction.Refine, Iron).setQuantities(2));
	public static final Item Stone = new Item(3, new Recipe(3,
			RecipeAction.Burn, Wet_Sand_Stone));
	public static final Item Processed_Uran = new Item(11, new Recipe(11,
			RecipeAction.Refine, Uran).setQuantities(2));
	public static final Item Fuel = new Item(13, new Recipe(13,
			RecipeAction.Refine, Oil));
	public static final Item Gunpowder = new Item(16, new Recipe(16,
			RecipeAction.Mix, Mars_Sand, Fuel).setQuantities(2, 1).setResult(3));
	public static final Item Bullet = new Item(15, new Recipe(15,
			RecipeAction.Craft, Stone, Gunpowder).setQuantities(7, 5)
			.setResult(20));
	public static final Item Iron_Tube = new Item(17, new Recipe(17,
			RecipeAction.Craft, Steel).setQuantities(7).setResult(20));
	public static final Item Igniter_Mechanism = new Item(18, new Recipe(18,
			RecipeAction.Craft, Steel, Fuel).setQuantities(2, 10).setResult(1));
	public static final Item Iron_Machine_Base = new Item(19, new Recipe(19,
			RecipeAction.Craft, Steel, Brick).setQuantities(20, 5).setResult(1));
	public static final Item Sand = new Item(20, new Recipe(20,
			RecipeAction.Crush, Stone));
	public static final Item Silicon = new Item(21, new Recipe(21,
			RecipeAction.Burn, Sand));
	public static final Item Iron_Dust = new Item(22, new Recipe(22,
			RecipeAction.Crush, Steel));
	public static final Item Silicon_Alloy = new Item(23, new Recipe(23,
			RecipeAction.Compress, Silicon, Iron_Dust, Water).setQuantities(10,
			1, 1).setResult(12));
	public static final Item Silicon_Alloy_Type2 = new Item(24, new Recipe(24,
			RecipeAction.Compress, Silicon, Gunpowder, Water).setQuantities(10,
			1, 1).setResult(12));

	@Override
	public void loadItems() {
		load(none, "none");
		load(Iron, "Iron");
		load(Ice, "Ice");
		load(Mars_Sand, "Mars_Sand");
		load(Water, "Water");
		load(Oil, "Oil");
		load(Uran, "Uran");
		load(Mars_Stone, "Mars_Stone");
		load(Red_Goo, "Red_Goo");
		load(Sand_Stone, "Sand_Stone");
		load(Wet_Sand_Stone, "Wet_Sand_Stone");
		load(Brick, "Brick");
		load(Steel, "Steel");
		load(Stone, "Stone");
		load(Processed_Uran, "Processed_Uran");
		load(Fuel, "Fuel");
		load(Gunpowder, "Gunpowder");
		load(Bullet, "Bullet");
		load(Iron_Tube, "Iron_Tube");
		load(Igniter_Mechanism, "Igniter_Mechanism");
		load(Iron_Machine_Base, "Iron_Machine_Base");
		load(Sand, "Sand");
		load(Silicon, "Silicon");
		load(Iron_Dust,"Iron_Dust");
		load(Silicon_Alloy, "Silicon_Alloy");
		load(Silicon_Alloy_Type2, "Silicon_Alloy_Type2");
	}

}
