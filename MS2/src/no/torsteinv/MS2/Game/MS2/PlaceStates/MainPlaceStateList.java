package no.torsteinv.MS2.Game.MS2.PlaceStates;

import java.awt.Color;

import no.torsteinv.MS2.Entities.Sheets.Concrete;
import no.torsteinv.MS2.Game.Engine.Lists.PlaceStateList;
import no.torsteinv.MS2.Game.Engine.Others.PlaceState;
import no.torsteinv.MS2.Game.MS2.Buildings.GunBuilding;
import no.torsteinv.MS2.Game.MS2.Buildings.QuarryBuilding;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Burner;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Compressor;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Crafter;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.InputMachine;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Macerator;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Mixer;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.OutputMachine;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Refiner;
import no.torsteinv.MS2.Game.MS2.Pipe.AdvancedPipe;
import no.torsteinv.MS2.Game.MS2.Pipe.BasicPipe;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Main.PlaceStateTypes;

public class MainPlaceStateList extends PlaceStateList {

	public static final PlaceState None = new PlaceState(0,
			PlaceStateTypes.None, null, Main.SolidColor(64, 64,
					Color.MAGENTA));
	public static final PlaceState Concrete = new PlaceState(1,
			PlaceStateTypes.Sheet, new Concrete(0, 0, 0, 0, 0),
			Main.SolidColor(50, 50, Color.GRAY));

	@Override
	public void loadEntities() {
		load(None, "None");
		load(Concrete, "Concrete");
		
		autoAssignPlaceState(new AdvancedPipe(0,0,0,null),"AdvancedPipe");
		autoAssignPlaceState(new BasicPipe(0,0,0,null),"BasicPipe");
		autoAssignPlaceState(new Burner(0,0,null,0),"Burner");
		autoAssignPlaceState(new Compressor(0,0,null,0),"Compressor");
		autoAssignPlaceState(new Crafter(0,0,null,0),"Crafter");
		autoAssignPlaceState(new InputMachine(0,0,null,null,0),"Input");
		autoAssignPlaceState(new Macerator(0,0,null,0),"Macerator");
		autoAssignPlaceState(new Mixer(0,0,null,0),"Mixer");
		autoAssignPlaceState(new OutputMachine(0,0,null,0),"Output");
		autoAssignPlaceState(new Refiner(0,0,null,0),"Refiner");
		autoAssignPlaceState(new GunBuilding(0,0,0),"GunBuilding");
		autoAssignPlaceState(new QuarryBuilding(0,0,0),"QuarryBuilding");
	}

}
