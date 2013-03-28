package no.torsteinv.MS2.Game.Engine.Lists;

public class Lists {

	public static void loadEntityList(EntityList list) {
		EntityList.addList(list);
	}

	public static void loadItemList(ItemList list) {
		ItemList.addList(list);
	}

	public static void loadFormGenerator(FormList list) {
		FormList.addList(list);
	}

	public static void loadPlaceStateList(PlaceStateList list) {
		PlaceStateList.addList(list);
	}

	public static void loadPaintStateList(PaintStateList list) {
		PaintStateList.addList(list);
	}

}
