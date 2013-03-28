package no.torsteinv.MS2.Game.MS2.Items;

import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Items.Recipe;

public class Item extends BaseItem{

	public Item(int ID, Recipe recipie) {
		super(ID, recipie);
	}

	@Override
	public void onUpdateInPipe() {
		
	}

}
