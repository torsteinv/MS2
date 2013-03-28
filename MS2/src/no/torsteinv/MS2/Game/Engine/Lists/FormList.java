package no.torsteinv.MS2.Game.Engine.Lists;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import no.torsteinv.MS2.AbstractMenu.ActionHandler;
import no.torsteinv.MS2.AbstractMenu.Form;
import no.torsteinv.MS2.AbstractMenu.Buttons.StandardButton;
import no.torsteinv.MS2.Entities.PlaceableBuyer;
import no.torsteinv.MS2.Entities.Factory.FactoryElement;
import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Items.ItemMethodException;
import no.torsteinv.MS2.Entities.Items.ItemStack;
import no.torsteinv.MS2.Entities.Items.RecipeAction;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.Engine.Others.PlaceState;
import no.torsteinv.MS2.Main.Main;

public abstract class FormList extends List {

	public abstract void initForms();

	public class GameFormGeneration {

	}

	public static ArrayList<Form> AddQueue = new ArrayList<Form>();
	public static boolean loaded = false;

	public static void addList(FormList List) {
		try {
			List.getClass().getMethod("initForms")
					.invoke(List.getClass().newInstance());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	public static void add(Form form) {
		AddQueue.add(form);
		loaded = false;
	}

	public static void autoCreateButtonForPaintState(String name, Point pos,
			String toolTip, final PaintState ps, Form addTo) {
		StandardButton b = getBasicButton(pos, name, new Dimension(50, 10),
				true, toolTip, Color.GRAY);

		b.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(ps);
			}

		};

		addTo.add(b);
	}

	public static void autoCreateButtonForPlaceState(String name, Point pos,
			String toolTip, final PlaceState ps, Form addTo) {
		StandardButton b = getBasicButton(pos, name, new Dimension(50, 10),
				true, toolTip, Color.GRAY);

		b.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = ps;
			}

		};

		addTo.add(b);
	}

	public static StandardButton getButtonForPaintState(String name, Point pos,
			String toolTip, final PaintState ps) {
		StandardButton b = getBasicButton(pos, name, new Dimension(50, 10),
				true, toolTip, Color.GRAY);

		b.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(ps);
			}

		};
		return b;
	}

	public static StandardButton getButtonForPlaceState(String name, Point pos,
			String toolTip, final PlaceState ps) {
		StandardButton b = getBasicButton(pos, name, new Dimension(50, 10),
				true, toolTip, Color.GRAY);

		b.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = ps;
			}

		};
		return b;
	}

	public static StandardButton getBasicButton(Point p, String name,
			Dimension size, boolean Active, String toolTip, Color c) {
		return new StandardButton(p, name, size, Active, toolTip, c);
	}

	public static void ensureAdded() {
		if (!loaded)
			addAll();
		loaded = true;
	}

	private static void addAll() {
		for (Form form : AddQueue)
			Main.Access.Forms.add(form);
		AddQueue.clear();
	}

	protected static int positionByIndex(int i) {
		return (i * 10) + (40 * (1 + i));
	}

	protected static int positionByIndexDefined(int index, int Spacing,
			int ButtonHeight, int Margin) {
		return index * (Spacing + ButtonHeight) + Margin;
	}

	protected static String FromItemStack(ItemStack is) {
		return is.getQuantity() + "x "
				+ is.getType().toString().toLowerCase().replace("_", " ");
	}

	protected static Point positionByIndexAction(int i, RecipeAction a) {
		Point p = new Point();
		p.x = (a == RecipeAction.Burn ? 0
				: a == RecipeAction.Compress ? 700 / 2
						: a == RecipeAction.Refine ? 700 / 2
								: a == RecipeAction.Mix ? 0 : 0);
		p.x += 55;
		p.y = (a == RecipeAction.Burn ? 0
				: a == RecipeAction.Compress ? 500 / 2
						: a == RecipeAction.Refine ? 0
								: a == RecipeAction.Mix ? 500 / 2 : 0);
		p.y += 70;

		p.y += (i * 35);
		return p;
	}

	protected static HashMap<BaseItem, Integer> allCost(FactoryElement[] fe)
			throws ItemMethodException {
		HashMap<BaseItem, Integer> al = new HashMap<BaseItem, Integer>();
		HashMap<BaseItem, Integer> hm = null;
		for (FactoryElement fe1 : fe) {
			hm = PlaceableBuyer.getCostForClass(fe1.getClass());
			for (BaseItem Type : hm.keySet()) {
				int quantity = hm.get(Type);
				al.put(Type, quantity);
			}
		}
		return al;
	}

	protected static String generateStringForFactoryButtons(
			HashMap<BaseItem, Integer> hm) {
		String s = "";
		for (BaseItem bi : hm.keySet())
			s += hm.get(bi)
					+ " "
					+ bi.toString().replace("_", " ")
					+ (hm.keySet().size() - 2 == Arrays.asList(
							hm.keySet().toArray()).indexOf(bi) ? " and "
							: " , ");
		String s1 = s.substring(0, s.length() - 3);
		s1 += ".";
		return s1;
	}
}
