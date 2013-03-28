package no.torsteinv.MS2.Entities;

import java.util.ArrayList;

import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Items.ItemStack;

public interface Container {
	public ArrayList<ItemStack> getContent();
	public void clearContent();
	public void removeContent(ArrayList<ItemStack> content);
	public void addContent(ArrayList<ItemStack> content);
	public void removeContent(ItemStack is);
	public void addContent(ItemStack is );
	public void removeContent(BaseItem i, int q);
	public void addContent(BaseItem i, int q);
	public boolean contains(BaseItem i, int q);
	public ItemStack getItemAtPos(int i, int j);
	public boolean pureContianer();
}
