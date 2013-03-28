package no.torsteinv.MS2.Game.Engine.Lists;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Items.ItemAccessModifierHelper;
import no.torsteinv.MS2.Game.MS2.Items.Item;
import no.torsteinv.MS2.Main.Identification.Identification;
import no.torsteinv.MS2.Main.Identification.IdentificationException;
import no.torsteinv.MS2.Main.Identification.IdentificationTypes;

public abstract class ItemList extends List {

	public static HashMap<BaseItem, String> Items = new HashMap<BaseItem, String>();

	public abstract void loadItems();

	public static void load(BaseItem item, String name) {
		Items.put(item, name);
		try {
			Identification.add(name, IdentificationTypes.Item);
			if (item.ID == 1025)
				return;
			if (BaseItem.ItemList == null) {
				BaseItem.ItemList = new Item[1024];
			}
			if (BaseItem.ItemList[item.ID] != null) {
				throw new IllegalArgumentException("Conflict at ID " + item.ID
						+ " caused by " + BaseItem.ItemList[item.ID].toString()
						+ " trying to override " + item.toString() + ".");
			} else {
				ItemAccessModifierHelper.addItem(item, item.ID);
			}
		} catch (IdentificationException e) {
			e.printStackTrace();
		}
	}

	public static void addList(Object classObject) {
		try {
			Class<? extends Object> c = classObject.getClass();
			Method method = c.getMethod("loadItems");
			method.invoke(classObject);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static String getName(BaseItem baseItem) {
		return Items.get(baseItem);
	}
}
