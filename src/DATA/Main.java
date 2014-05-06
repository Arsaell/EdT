package DATA;

import java.util.ArrayList;
import java.util.HashMap;

public class Main	{

	public static void main(String[] args)	{
	
		//System.out.println("\n\n\n\t##########\n\n\n");

		DataStore ds = new DataStore();
		
		ArrayList<Classroom> classrooms= ds.getClassrooms();
		ArrayList<Teacher> teachers = ds.getTeachers();
		ArrayList<Group> groups = ds.getGroups();
		ArrayList<ClassType> types = ds.getTypes();
		
		WeekTable.setMinDelta(new Time(30));
		
		System.out.println("Main.main() : Examples loaded.");
		
		//System.out.println("# ClassType : " + types.get(0) + "\n# Classroom : " + classrooms.get(0) + "\n# Field : " + mathsa + "\n# Teacher : " + teachers.get(0) + "\n# Group : " + groups.get(3));
		
		Filler filler = new Filler(classrooms, groups, teachers, types, new Time(3600));
		
		System.out.println("Main.main() : Filler built.");
		
		Constrainable[] constraints = filler.computeConstraints(true);
		
		System.out.println("Main.main() : Constraints computed.");
		
		//System.out.println("Main.main() : Teachers attributed to groups.");
		
		int errors = filler.fill(constraints);
		
		//classrooms.get(0).getWeekTable().print();
		
		System.out.println("Main.main() : Groups filled : " + errors + " errors.");
		
		//*
		for (Group g : groups)	{
			g.getWeekTable().print();
			System.out.println("\n\n");
		}
		//*/
	}
}
