

public class Laniere extends Group	{

	private Group[] groups;
	
	public Laniere(char aID, Group[] aGroups)	{
		
		super(aID, 0);
		
		this.groups = aGroups;
		
		for (int i = 0 ; i < this.groups.length ; i++)
			this.effectif += this.groups[i].getEffectif();
	}
	
	public String getMail()	{
	
		return "pclan" + (char)(this.ID + 'A') + "@insa-lyon.fr";
	}
}
