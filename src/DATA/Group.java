import java.util.HashMap;

public class Group {

	private String name;

	protected int effectif;
	private HashMap<Field, Character/*, Time*/> classes;

	private Group parent;
	private Group[] children;
	
	public Group(char aID, int aEff)	{
		
		this.ID = aID;
		this.effectif = aEff;
		this.classes = null;	//Implement
	}
	
	public String getMail()	{
		return "pcg" + Integer.toString((int)this.ID + 1) + "@insa-lyon.fr";
	}
	
	public int getEffectif()	{
		return this.effectif;
	}
}
