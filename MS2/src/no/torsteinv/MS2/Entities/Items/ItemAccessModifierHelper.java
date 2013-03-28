package no.torsteinv.MS2.Entities.Items;

import no.torsteinv.MS2.Game.MS2.Items.Item;

public class ItemAccessModifierHelper {
	public static void addItem(BaseItem baseItem, int ID){
		Item.ItemList[ID] = baseItem;
	}
}
