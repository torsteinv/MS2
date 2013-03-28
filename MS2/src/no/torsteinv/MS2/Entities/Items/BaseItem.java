package no.torsteinv.MS2.Entities.Items;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import no.torsteinv.MS2.Game.MS2.Items.MainItemList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Managers.Visualization.TransperentManager;

public abstract class BaseItem {
	/*
	 * none(1025, Recipe.NULL), Ice(7, Recipe.NULL), Iron(8, Recipe.NULL),
	 * Mars_Sand(12, Recipe.NULL), Oil(14, Recipe.NULL), Uran(2, Recipe.NULL),
	 * Mars_Stone(4, Recipe.NULL), Water(10, new Recipe(10, RecipeAction.Burn,
	 * Ice).setQuantities(2)), Red_Goo(9, new Recipe(9,
	 * RecipeAction.Mix,Mars_Sand, Water).setQuantities(2, 1)), Sand_Stone(5,
	 * new Recipe(5,RecipeAction.Compress, Mars_Stone)), Wet_Sand_Stone(6, new
	 * Recipe(6, RecipeAction.Mix, Sand_Stone, Water).setQuantities(2, 1)),
	 * Brick(0, new Recipe(0, RecipeAction.Burn, Red_Goo).setResult(10)),
	 * Steel(1, new Recipe(1, RecipeAction.Refine, Iron).setQuantities(2)),
	 * Stone(3, new Recipe(3, RecipeAction.Burn, Wet_Sand_Stone)),
	 * Processed_Uran(11, new Recipe(11, RecipeAction.Refine,
	 * Uran).setQuantities(2)), Fuel(13, new Recipe(13, RecipeAction.Refine,
	 * Oil)), Gunpowder(16,new Recipe(16, RecipeAction.Mix, Mars_Sand,
	 * Fuel).setQuantities(2,1).setResult(3)), Bullet(15, new
	 * Recipe(15,RecipeAction.Craft, Stone, Gunpowder).setQuantities(7,
	 * 5).setResult(20)), Iron_Tube(17, new Recipe(17,
	 * RecipeAction.Craft,Steel).setQuantities(7).setResult(20)),
	 * Igniter_Mechanism(18,new Recipe(18, RecipeAction.Craft, Steel,
	 * Fuel).setQuantities(2, 10).setResult(1)), Iron_Machine_Base(19,new
	 * Recipe(19, RecipeAction.Craft, Steel,
	 * Brick).setQuantities(20,5).setResult(1));
	 */
	public int ID = 0;
	public Recipe recipe = Recipe.NULL;
	public static BaseItem[] ItemList;

	public BaseItem(int ID, Recipe recipie) {
		this.ID = ID;
		this.recipe = recipie;
		
	}

	// Uses old loading system
	public static Image Texture(int ID, int Scale) throws IOException {
		if (!Main.applet)
			switch (Scale) {
			case 1:
				return TransperentManager.makeColorTransparent(
						((BufferedImage) ImageIO.read(new File("images/Items"
								+ ((int) (ID / 400) + 1) + ".png")))
								.getSubimage((ID % 20) * 50, (int) (ID / 20) * 50,
										50, 50), new Color(255, 0, 255));
			case 2:
				return TransperentManager.makeColorTransparent(
						((BufferedImage) ImageIO.read(new File("images/SItems"
								+ ((int) (ID / 400) + 1) + ".png")))
								.getSubimage(ID * 20, (int) (ID / 100) * 20,
										20, 20), new Color(255, 0, 255));
			default:
				return null;
			}
		return null;
	}

	public static final int Scale_Default = 1;
	public static final int Scale_Tube = 2;

	public static BaseItem ItemByID(int ID) {
		switch (ID) {
		case 1025:
			return MainItemList.none;
		default:
			return ItemList[ID];
		}
	}

	public String toString() {
		return MainItemList.getName(this);
	}

	public abstract void onUpdateInPipe();
	
	public boolean equals(Object other){
		return other instanceof BaseItem && ((BaseItem)other).ID == ID;
	}
}
