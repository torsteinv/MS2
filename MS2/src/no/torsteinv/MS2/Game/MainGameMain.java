package no.torsteinv.MS2.Game;

import java.awt.Rectangle;

import no.torsteinv.MS2.Game.Engine.GameLoading.Game;
import no.torsteinv.MS2.Game.Engine.Lists.Lists;
import no.torsteinv.MS2.Game.MS2.Buildings.MainBuildingList;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.MainFactoryBuildingList;
import no.torsteinv.MS2.Game.MS2.FormGeneration.GameFormGeneration;
import no.torsteinv.MS2.Game.MS2.FormGeneration.MenuFormGeneration;
import no.torsteinv.MS2.Game.MS2.Items.MainItemList;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Game.MS2.PlaceStates.MainPlaceStateList;

public class MainGameMain extends Game {

	public MainGameMain() {
		super("Core Game");
	}

	@Override
	public void loadResources() {
		addSound("ButtonClick.wav", "ButtonClick");

		addImage("TerrainSand.png", true, new Rectangle(0, 0, 50, 50),
				"MarsSand");
		addImage("naturalResources.png", true, new Rectangle(51, 0, 50, 50),
				"IceLake");
		addImage("naturalResources.png", true, new Rectangle(0, 0, 50, 50),
				"Mountain");
		addImage("naturalResources.png", true, new Rectangle(102, 0, 50, 50),
				"MountainIron");
		addImage("naturalResources.png", true, new Rectangle(153, 0, 50, 50),
				"MountainUran");

		addImage("Vehicles.png", false, new Rectangle(0, 0, 25, 10), "Explorer");
		addImage("Vehicles.png", false, new Rectangle(25, 0, 25, 10),
				"Transporter");

		addImage("Machines.png", true, new Rectangle(0, 0, 50, 50), "Burner");
		addImage("Machines.png", true, new Rectangle(50, 0, 50, 50),
				"Compressor");
		addImage("Machines.png", true, new Rectangle(100, 0, 50, 50), "Refiner");
		addImage("Machines.png", true, new Rectangle(150, 0, 50, 50), "Mixer");
		addImage("Machines.png", true, new Rectangle(200, 0, 50, 50), "Input");
		addImage("Machines.png", true, new Rectangle(250, 0, 50, 50), "Output");
		addImage("Machines.png", true, new Rectangle(300, 0, 50, 50), "Crafter");
		addImage("Machines.png", true, new Rectangle(350, 0, 50, 50),
				"Macerator");

		addImage("Airgun.png", true, "Airgun");

		addImage("Tier1Buildings.png", true, new Rectangle(0, 0, 50, 50),
				"MainBuilding");
		addImage("Tier1Buildings.png", true, new Rectangle(52, 0, 50, 50),
				"ContainerBuilding");
		addImage("Tier1Buildings.png", true, new Rectangle(104, 0, 50, 50),
				"FactoryBuilding");
		addImage("Tier1Buildings.png", true, new Rectangle(156, 0, 50, 50),
				"GunBuilding");
		addImage("Tier1Buildings.png", true, new Rectangle(208, 0, 50, 50),
				"Assembler");
		addImage("Tier1Buildings.png", true, new Rectangle(260, 0, 50, 50),
				"SandQuarry");
		addImage("Tier1Buildings.png", true, new Rectangle(312, 0, 50, 50),
				"MineStation");

		addImage("stg.png", false, "TemplateStoneImage");
		addImage("brg.png", false, "TemplateBrickImage");
		addImage("flg.png", false, "TemplateFuelImage");

		addFile("stg.dat", "TemplateStoneData");
		addFile("brg.dat", "TemplateBrickData");
		addFile("flg.dat", "TemplateFuelData");

		Lists.loadPlaceStateList(new MainPlaceStateList());
		Lists.loadPaintStateList(new MainPaintStateList());
		Lists.loadEntityList(new MainFactoryBuildingList());
		Lists.loadEntityList(new MainBuildingList());
		Lists.loadItemList(new MainItemList());
		Lists.loadFormGenerator(new MenuFormGeneration());
		Lists.loadFormGenerator(new GameFormGeneration());
	}

}
