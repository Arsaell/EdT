

public class Group extends People	{

	protected int effectif;
	
	public Group(char aID, int aEff)	{
		
		this.ID = aID;
		this.effectif = aEff;
	}
	
	public String getMail()	{
	
		return "pcg" + Integer.toString((int)this.ID + 1) + "@insa-lyon.fr";
	}
	
	public int getEffectif()	{
		return this.effectif;
	}
}
