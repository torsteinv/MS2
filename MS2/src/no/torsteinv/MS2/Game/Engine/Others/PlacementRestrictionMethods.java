package no.torsteinv.MS2.Game.Engine.Others;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Selectable;
import no.torsteinv.MS2.Entities.Sheets.Concrete;
import no.torsteinv.MS2.Entities.Sheets.Sheet;
import no.torsteinv.MS2.Game.Engine.GameLoading.GameLoadingException;
import no.torsteinv.MS2.Game.MS2.Pipe.BasicPipe;
import no.torsteinv.MS2.Main.Main;

public class PlacementRestrictionMethods {
	public static boolean FUNC1(Integer x, Integer y, Selectable e) {
		return BasicPipe.CanPlace(x, y);
	}

	public static boolean FUNC2(Integer x, Integer y, Selectable e) {
		assert e == null: new GameLoadingException("Selectable \"e\" is equals null!");
		for (Entity e1 : Main.Entities)
			if (!(e1 instanceof Sheet)
					&& e1 instanceof Selectable
					&& ((Selectable) e1).getSelectionBox().intersects(
							e.getSelectionBox()))
				return false;
		return true;
	}

	public static boolean FUNC3(Integer x, Integer y, Selectable e) {
		return BasicPipe.CanPlace(x, y) && (x - 50) / 50 == 9;
	}

	public static boolean FUNC4(Integer x, Integer y, Selectable e) {
		return BasicPipe.CanPlace(x, y) && (x - 50) / 50 == 0;
	}

	public static boolean FUNC5(Integer x, Integer y, Selectable e) {
		return BasicPipe.CanPlace(x, y) && (y - 50) / 50 == 9;
	}

	public static boolean FUNC6(Integer x, Integer y, Selectable e) {
		return BasicPipe.CanPlace(x, y) && (y - 50) / 50 == 0;
	}

	public static boolean FUNC7(Integer x, Integer y, Selectable e) {
		for (Entity e1 : Main.Entities)
			if (!(e1 instanceof Sheet)
					&& e1 instanceof Selectable
					&& ((Selectable) e1).getSelectionBox().intersects(
							e.getSelectionBox()))
				return false;
		for (Entity e2 : Main.Entities)
			if (e2 instanceof Concrete
					&& ((Sheet) e2).getSelectionBox().contains(
							e.getSelectionBox().getLocation()))
				return true;
		return false;
	}
}
