package no.torsteinv.MS2.Entities.Items;


public enum RecipeAction {
	None('n', -1), Burn('b', 0), Compress('c', 1), Refine('r', 2), Mix('m', 3), Craft(
			'a', 4), Crush('u',5);
	char ID = 'n';
	public int IDN = -1;

	RecipeAction(char ID, int IDN) {
		this.ID = ID;
		this.IDN = IDN;
	}
}
