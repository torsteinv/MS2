package no.torsteinv.MS2.Game.MS2.Factory.Buildings;

import no.torsteinv.MS2.Game.Engine.Lists.EntityList;

public class MainFactoryBuildingList extends EntityList{

	@Override
	public void loadEntities() {
		load(Burner.class);
		load(Compressor.class);
		load(Crafter.class);
		load(InputMachine.class);
		load(Macerator.class);
		load(Mixer.class);
		load(OutputMachine.class);
		load(Refiner.class);
	}

}
