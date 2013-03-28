package no.torsteinv.MS2.Wrappers;

import java.util.ArrayList;
import java.util.HashMap;

import no.torsteinv.MS2.Entities.Container;
import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Factory.FactoryElement;
import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Items.ItemMethodException;
import no.torsteinv.MS2.Entities.Items.ItemStack;

public abstract class ContainerFactoryElementWrapper extends FactoryElement implements
		Container {

	public ContainerFactoryElementWrapper(float x, float y, int player) {
		super(x, y, player);
	}

	public ContainerFactoryElementWrapper(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	ArrayList<ItemStack> content = new ArrayList<ItemStack>();

	@Override
	public ArrayList<ItemStack> getContent() {
		return this.content;
	}

	@Override
	public void clearContent() {
		this.content.clear();
	}

	@Override
	public void addContent(ArrayList<ItemStack> content) {
		content.addAll(content);
	}

	@Override
	public void removeContent(ArrayList<ItemStack> content) {
		for (ItemStack is : content) {
			int RCQ = is.getQuantity();
			if (findItemStackByType(is, this.content) == null) {
				new ItemMethodException("Requested removal does not exist!")
						.printStackTrace();
				continue;
			}
			int CCQ = findItemStackByType(is, content).getQuantity();

			if (CCQ < RCQ)
				new ItemMethodException(
						"Requested removal quantity is greater than the content quantity! Bad Maths?")
						.printStackTrace();
			else if (CCQ > RCQ)
				is.setQuantity(CCQ - RCQ);
			else
				this.content.remove(is);
		}
	}

	@Override
	public void removeContent(ItemStack is) {
		removeContent(is.getType(), is.getQuantity());
	}

	@Override
	public void addContent(ItemStack is) {
		this.content.add(is);
	}

	@Override
	public void removeContent(BaseItem i, int q) {
		int RCQ = q;
		if (findItemStackByType(i, this.content) == null)
			new ItemMethodException("Requested removal does not exist!")
					.printStackTrace();
		int CCQ = findItemStackByType(i, this.content).getQuantity();
		if (CCQ < RCQ)
			new ItemMethodException(
					"Requested removal quantity is greater than the content quantity! Bad Maths?")
					.printStackTrace();
		else if (CCQ > RCQ)
			findItemStackByType(i, this.content).setQuantity(CCQ - RCQ);
		else
			this.content.remove(new ItemStack(i, this, q, getPrefferedX(),
					getPrefferedY(),false));
	}

	@Override
	public void addContent(BaseItem i, int q) {
		addContent(new ItemStack(i, this, q, getPrefferedX(), getPrefferedY(),false));
	}

	private int getPrefferedY() {
		return ItemStack.getPrefferdPosition(this).y;
	}

	private int getPrefferedX() {
		return ItemStack.getPrefferdPosition(this).x;
	}

	private ItemStack findItemStackByType(ItemStack is,
			ArrayList<ItemStack> content) {
		for (ItemStack isl : content)
			if (isl.getType().equals(is.getType()))
				return isl;
		return null;
	}

	private ItemStack findItemStackByType(BaseItem i,
			ArrayList<ItemStack> content) {
		for (ItemStack isl : content)
			if (isl.getType().equals(i))
				return isl;
		return null;
	}

	@Override
	public ItemStack getItemAtPos(int i, int j) {
		for (ItemStack e : this.content)
			if (e.getX() == i && e.getY() == j)
				return e;
		return null;
	}

	@Override
	public boolean contains(BaseItem i, int q) {
		for (ItemStack is : this.content)
			if (is.getQuantity() > q)
				return true;
		return false;
	}
	
	@Override
	public boolean pureContianer(){
		return false;
	}

}
