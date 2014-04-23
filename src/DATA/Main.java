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
		
		System.out.println("Main.main() : Examples set.");
		
		//System.out.println("# ClassType : " + types.get(0) + "\n# Classroom : " + classrooms.get(0) + "\n# Field : " + mathsa + "\n# Teacher : " + teachers.get(0) + "\n# Group : " + groups.get(3));
		
		Filler filler = new Filler(classrooms, groups, teachers, types, new Time(3500));
		
		System.out.println("Main.main() : Filler built.");
		
		Constrainable[] constraints = filler.computeConstraints(true);
		
		System.out.println("Main.main() : Constraints computed.");
		
		/*
		for (Group g : groups)
			System.out.println("Main.main() --> Group.teachers : " + g + " " + g.getLinks().size());
		//*/
		
		//System.out.println("Main.main() : Teachers attributed to groups.");
		
		filler.fill(constraints);
		
		System.out.println("Main.main() : Groups filled.");
		System.out.println("\n\n");
		
		///*
		for (Group g : groups)	{
			g.printWeekTable();
			System.out.println("\n\n");
		}
		//*/
	}
}
