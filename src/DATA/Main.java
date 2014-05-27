package DATA;

public class Main	{

	public static void main(String[] args)	{
	
		//System.out.println("\n\n\n\t##########\n\n\n");

		DataStore ds = new DataStore();
		ds.addFixtures();
		Filler filler = new Filler(ds);
		//System.out.println("Main.main() : Filler built.");
		
		HashMap<Constrainable, Double> constraints = filler.computeConstraints(true);
		
		//System.out.println("Main.main() : Constraints computed.");
		
		//System.out.println("Main.main() : Teachers attributed to groups.");
		
		int errors = filler.fill(constraints, Filler.RETRY);
		
		//classrooms.get(0).getWeekTable().print();
		
		System.out.println("Main.main() : Groups filled : " + errors + " errors.");
		
		
		//new WelcomeFrame();
		/*
		for (Group g : ds.getGroups())	{
			
			new EdTViewerWindow(g.getWeekTable());
		}
		//*/
	}
}
