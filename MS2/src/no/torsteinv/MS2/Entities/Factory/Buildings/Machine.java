package no.torsteinv.MS2.Entities.Factory.Buildings;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import no.torsteinv.MS2.Annotations.CostMethod;
import no.torsteinv.MS2.Entities.Container;
import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Placeable;
import no.torsteinv.MS2.Entities.Selectable;
import no.torsteinv.MS2.Entities.Buildings.Building;
import no.torsteinv.MS2.Entities.Factory.FactoryElement;
import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Items.ItemStack;
import no.torsteinv.MS2.Entities.Items.Recipe;
import no.torsteinv.MS2.Entities.Items.RecipeAction;
import no.torsteinv.MS2.Entities.Movables.Command;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Game.Engine.Others.PlacementRestriction;
import no.torsteinv.MS2.Game.MS2.Buildings.FactoryBuilding;
import no.torsteinv.MS2.Game.MS2.Items.Item;
import no.torsteinv.MS2.Game.MS2.Items.MainItemList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Main.PlaceStateTypes;
import no.torsteinv.MS2.Wrappers.ContainerFactoryElementWrapper;

public abstract class Machine extends ContainerFactoryElementWrapper implements
		Selectable, Placeable {

	public abstract RecipeAction getAssosiatedRecipeAction();

	public Machine(int CanvasX, int CanvasY, Building Home, int Player) {
		super(CanvasX, CanvasY, Player);
		this.Properties.put("CanvasX", CanvasX);
		this.Properties.put("CanvasY", CanvasY);
		this.Properties.put("Player", Player);
		this.Entities.put("Home", Home);
	}

	public Machine(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	public Machine() {
		super(0, 0, 0);
	}

	@Override
	public int getCanvasX() {
		return ((Float) this.Properties.get("X")).intValue();
	}

	public void setCanvasX(int canvasX) {
		this.Properties.put("X", canvasX);
	}

	@Override
	public int getCanvasY() {
		return ((Float) this.Properties.get("Y")).intValue();
	}

	public void setCanvasY(int canvasY) {
		this.Properties.put("Y", canvasY);
	}

	@Override
	public FactoryBuilding getHome() {
		return (FactoryBuilding) this.Entities.get("Home");
	}

	public void setHome(FactoryBuilding Home) {
		this.Entities.put("Home", Home);
	}

	@Override
	public void transitAll() {
		ArrayList<ItemStack> is = getContent();
		if (is.isEmpty())
			return;
		for (Entity p : Main.Entities)
			if (p instanceof FactoryElement
					&& ((FactoryElement) p).getCanvasY() == getCanvasY()
					&& ((FactoryElement) p).getCanvasX() == getCanvasX() + 1) {
				((FactoryElement) p).setFrom(2);
				ItemStack s = PerformRecipe(toItemArray(is.toArray()));
				if (s == null)
					return;
				ItemStack i = new ItemStack(s.getType(), (Container) p,
						s.getQuantity(), 0, 0,true);
				i.setInPipe(true);
				for (Entity s1 : is)
					try {
						Main.removeEntity(s1);
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
	}

	public ItemStack PerformRecipe(ItemStack[] is) {
		is = combineStackData(is);
		for (BaseItem i : Item.ItemList)
			if (i != null && i.recipe != Recipe.NULL) {
				ItemStack c;
				boolean[] hasThing = new boolean[i.recipe.items.length];
				for (int in = 0; in < i.recipe.items.length; in++) {
					c = new ItemStack(i.recipe.items[in], null,
							i.recipe.quantities[in], 0, 0,false);
					for (int in1 = 0; in1 < is.length; in1++)
						if (c.getType().equals(is[in1].getType())
								&& c.getQuantity() <= is[in1].getQuantity())
							addHasThing(hasThing);
				}
				if (allTrue(hasThing)
						&& i.recipe.Action.IDN == getAssosiatedRecipeAction().IDN) {
					int qm = 0;
					for (int ind = 0; ind < i.recipe.items.length; ind++)
						qm += is[ind].getQuantity() / i.recipe.quantities[ind];
					return new ItemStack(Item.ItemByID(i.recipe.Product),
							getHome(), i.recipe.Result * qm, 0, 0, false);
				}
			}
		return null;
	}

	private ItemStack[] combineStackData(ItemStack[] is) {
		// Hashmap init
		HashMap<BaseItem, Integer> Data = new HashMap<BaseItem, Integer>();

		// Hashmap putting
		for (ItemStack s : is)
			if (Data.containsKey(s.getType()))
				Data.put(s.getType(), s.getQuantity() + Data.get(s.getType()));
			else
				Data.put(s.getType(), s.getQuantity());

		// Hashmap converting
		ItemStack[] isn = new ItemStack[Data.size()];
		for (int i = 0; i < Data.size(); i++)
			isn[i] = new ItemStack((Item) Data.keySet().toArray()[i], null,
					Data.get(Data.keySet().toArray()[i]).intValue(), 0, 0,false);

		return isn;
	}

	private void addHasThing(boolean[] hasThing) {
		for (int i = 0; i < hasThing.length; i++)
			if (!hasThing[i]) {
				hasThing[i] = true;
				return;
			}
	}

	private boolean allTrue(boolean[] parts) {
		for (boolean b : parts)
			if (!b)
				return false;
		return true;
	}

	protected static ItemStack[] toItemArray(Object[] obj) {
		ItemStack[] is = new ItemStack[obj.length];

		for (int i = 0; i < obj.length; i++)
			if (obj[i] instanceof ItemStack)
				is[i] = (ItemStack) obj[i];

		return is;
	}

	@Override
	public void OnClick() {

	}

	@CostMethod
	public static HashMap<BaseItem, Integer> Cost() {
		HashMap<BaseItem, Integer> hm = new HashMap<BaseItem, Integer>();
		hm.put(MainItemList.Iron_Machine_Base, 1);
		hm.put(MainItemList.Steel, 5);
		hm.put(MainItemList.Silicon, 7);
		return hm;
	}

	@Override
	public Command getCommandCenter() {
		return null;
	}

	Rectangle r = new Rectangle();

	@Override
	public Rectangle getSelectionBox() {
		this.r.setBounds((int) getX(), (int) getY(), 1, 1);
		return this.r;
	}

	@Override
	public boolean isSelected() {
		return false;
	}

	@Override
	public void setSelected(boolean Selected) {

	}

	@Override
	public CommandType CommandAt(int i, int j, boolean leftClick) {
		return CommandType.None;
	}

	@Override
	public PlaceStateTypes getPlaceType() {
		return PlaceStateTypes.Factory;
	}

	@Override
	public PlacementRestriction getRestrictions() {
		return PlacementRestriction.NON_OVERLAP_FACTORY;
	}

	@Override
	public void OnPlaceBought(int x, int y) {

	}
}
