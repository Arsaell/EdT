import java.util.HashMap;

public class Classroom	{

	private char ID;
	private char type;
	private String name;
	private int effectif;
	private static HashMap<Character, String> types;	//Les noms des types de salle
	
	
	static	{
	
		types = new HashMap<Character, String>();
		
		types.put((char)0, "TD");
		types.put((char)1, "Amphithéâtre");
		types.put((char)2, "TP Physique");
		types.put((char)3, "TP Chimie");
		types.put((char)4, "TP Construction");
		types.put((char)5, "Salle Informatique");
		types.put((char)6, "Gymnase");
	}
	
	
	public Classroom(char aID, char aType, String aName, int aEff)	{
	
		this.ID = aID;
		this.type = aType;
		this.name = aName;
		this.effectif = aEff;
	}
	
	public char getType()	{
		return this.type;
	}
	
	public int getEffectif()	{
		return this.effectif;
	}
}
