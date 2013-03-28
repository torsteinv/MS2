package no.torsteinv.MS2.Game.MS2.Buildings;

import no.torsteinv.MS2.Game.Engine.Lists.EntityList;

public class MainBuildingList extends EntityList{

	@Override
	public void loadEntities() {
		load(MainBuilding.class);
		load(ContainerBuilding.class);
		load(FactoryBuilding.class);
		load(GunBuilding.class);
		load(AssemblerBuilding.class);
	}

}
