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
		
		
		System.out.println("Main.main() : Examples set :");
		
		//System.out.println("# ClassType : " + types.get(0) + "\n# Classroom : " + classrooms.get(0) + "\n# Field : " + mathsa + "\n# Teacher : " + teachers.get(0) + "\n# Group : " + groups.get(3));
		
		Filler filler = new Filler(classrooms, groups, teachers, types, new Time(3500));
		
		System.out.println("Main.main() : Filler built.");
		
		Constrainable[] constraints = filler.computeConstraints();
		
		System.out.println("Main.main() : Constraints computed");
		
		ArrayList<Field> temp = new ArrayList<Field>();
		
		for (Constrainable c : constraints)
			if (c instanceof Field)
				temp.add((Field)c);
		
		Field[] f = new Field[temp.size()];
		
		for (int i = 0 ; i < temp.size() ; i++)
			f[i] = temp.get(i);
		
		filler.attributeTeachers(f);
		
		System.out.println("Main.main() : Teachers attributed to groups.");
		
		/*
		for (Group g : groups)
			System.out.println("Main.main() --> Group.teachers : " + g + " " + g.getLinks());
		*/
	}
}
