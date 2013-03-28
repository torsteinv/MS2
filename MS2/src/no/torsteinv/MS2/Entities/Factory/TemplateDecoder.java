package no.torsteinv.MS2.Entities.Factory;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import no.torsteinv.MS2.Entities.PlaceableBuyer;
import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Game.Engine.Lists.EntityList;
import no.torsteinv.MS2.Game.MS2.Buildings.FactoryBuilding;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Burner;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Compressor;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Crafter;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.InputMachine;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Mixer;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.OutputMachine;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Refiner;
import no.torsteinv.MS2.Game.MS2.Items.MainItemList;
import no.torsteinv.MS2.Game.MS2.Pipe.BasicPipe;
import no.torsteinv.MS2.Main.Main;

public class TemplateDecoder {
	public static FactoryElement[] decode(Template t,FactoryBuilding Parent){
		BufferedImage d = (BufferedImage)t.getImage();
		Scanner scan = null;
		try {
			scan = new Scanner(new FileInputStream(t.getData()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CopyOnWriteArrayList<FactoryElement> fel = new CopyOnWriteArrayList<FactoryElement>();
		for(int x = 0;x < 10;x++)
			for(int y = 0;y < 10;y++)
				if(d.getRGB(x, y) != 0xFFFFFF)
				{
					Color c = new Color(d.getRGB(x,y));
					MachineType mt = getMachineTypeByColorCode(c);
					FactoryElement fe = null;
					if(mt.toString().equals("Pipe"))
						fe = new BasicPipe(x,y,Main.player,Parent);
					else if(mt.toString().equals("Burner"))
						fe = new Burner(x,y,Parent,Main.player);
					else if(mt.toString().equals("Compresser"))
						fe = new Compressor(x,y,Parent,Main.player);
					else if(mt.toString().equals("Mixer"))
						fe = new Mixer(x,y,Parent,Main.player);
					else if(mt.toString().equals("Refiner"))
						fe = new Refiner(x,y,Parent,Main.player);
					else if(mt.toString().equals("Output"))
						fe = new OutputMachine(x,y,Parent,Main.player);
					else if(mt.toString().equals("Input"))
						fe = new InputMachine(x,y,Parent, MainItemList.none, 1);
					else if(mt.toString().equals("Crafter"))
						fe = new Crafter(x,y,Parent,Main.player);
					if(fe != null)
						fel.add(fe);
				}
		String str = "";
		while(!(str = scan.nextLine()).equals("//c"))
		{
			String[] s = str.split(" ");
			int x = 0;
			int y = 0;
			BaseItem in = MainItemList.none;
			int quantity = 0;
			
			for(String st : s){
				String[] sa = st.split("=");
				if(sa[0].equals("x"))
					x = Integer.parseInt(sa[1]);
				else if(sa[0].equals("y"))
					y = Integer.parseInt(sa[1]);
				else if(sa[0].equals("in"))
					in = BaseItem.ItemByID(Integer.parseInt(sa[1]));
				else if(sa[0].equals("q"))
					quantity = Integer.parseInt(sa[1]);
			}
			
			InputMachine writeTo = (InputMachine)at(x,y,fel);
			writeTo.setQuantity(quantity).setIn(in);
		}
		
		return fel.toArray(new FactoryElement[fel.size()]);
	}
	public static void buy(Template t) throws Exception{
		Scanner scan = null;
		try {
			scan = new Scanner(new FileInputStream(t.getData()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(!scan.nextLine().equals("//c"));
		while(scan.hasNext())
		{
			String[] s = scan.nextLine().split("=");
			
			@SuppressWarnings("unchecked")
			Class<? extends FactoryElement> c = (Class<? extends FactoryElement>) EntityList.MasterEntityTypeList.get(s[0]);
			if(c == null)
				continue;
			
			for(int i = 0;i < Integer.parseInt(s[1]);i++)
				PlaceableBuyer.buy(c);
			
		}
	}
	
	private static MachineType getMachineTypeByColorCode(Color code){
		for(MachineType t : MachineType.TypeList)
			if(code.equals(t.decodeColor()))
				return t;
		return MachineType.None;
	}
	
	private static FactoryElement at(int x,int y,CopyOnWriteArrayList<FactoryElement> fel){
		for(FactoryElement fe : fel)
			if(fe.getCanvasX() == x && fe.getCanvasY() == y)
				return fe;
		return null;
	}
}
