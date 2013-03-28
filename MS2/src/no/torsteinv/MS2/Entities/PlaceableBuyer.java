package no.torsteinv.MS2.Entities;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import no.torsteinv.MS2.Annotations.AnnotationsToolkit;
import no.torsteinv.MS2.Annotations.CostMethod;
import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Main.Main;

public class PlaceableBuyer {
	public static void buy(Class<? extends Placeable> e) throws Exception {
		for (Entry<BaseItem, Integer> is : getCostForClass(e).entrySet())
			Main.removeItemStack(is.getKey(), is.getValue());
	}

	@SuppressWarnings("unchecked")
	public static HashMap<BaseItem, Integer> getCostForClass(
			Class<? extends Placeable> e) {
		try {
			return (HashMap<BaseItem, Integer>) AnnotationsToolkit
					.findStaticMethodWithAnnotaion(e, CostMethod.class).invoke(
							null);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		return new HashMap<BaseItem, Integer>();
	}
}
