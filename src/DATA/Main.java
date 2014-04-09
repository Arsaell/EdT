package DATA;

import java.util.ArrayList;
import java.util.HashMap;

public class Main	{

	public static void main(String[] args)	{
	
		ArrayList<Classroom> classrooms= new ArrayList<Classroom>();
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		ArrayList<Group> groups = new ArrayList<Group>();
		ArrayList<ClassType> types = new ArrayList<ClassType>();

		types.add(0, new ClassType("Cours", "Amphithéâtre", new Slot(new Time(100), new Time(130))));
		types.add(1, new ClassType("TD", "TD", new Slot(new Time(130), new Time(200))));
		types.add(2, new ClassType("TP Physique", "TP", new Slot(new Time(40), new Time(400))));
		types.add(3, new ClassType("TP Chimie", "Labo chimie", new Slot(new Time(300), new Time(400))));
		types.add(4, new ClassType("TP Construction", "TP", new Slot(new Time(400), new Time(400))));
		types.add(5, new ClassType("TD Conception", "TD", new Slot(new Time(130), new Time(200))));
		types.add(6, new ClassType("TD Info", "TD", new Slot(new Time(200), new Time(200))));
		types.add(7, new ClassType("Sport", "Gymnase", new Slot(new Time(200), new Time(200))));
		
		classrooms.add(new Classroom(0, types.get(1), "C9", 25));
		classrooms.add(new Classroom(1, types.get(0), "Vannier", 100));
		classrooms.add(new Classroom(2, types.get(2), "Optique", 16));
		classrooms.add(new Classroom(3, types.get(3), "1", 16));
		classrooms.add(new Classroom(4, types.get(4), "Usinage", 32));
		classrooms.add(new Classroom(5, types.get(5), "Est", 25));
		classrooms.add(new Classroom(6, types.get(6), "Archie", 25));
		classrooms.add(new Classroom(7, types.get(7), "Piscine", 150));
		
		Field a = (new Field(0, types.get(0), "Maths"));
		Field b = (new Field(1, types.get(0), "Physique"));
		Field c = (new Field(2, types.get(1), "Maths"));
		Field d = (new Field(3, types.get(1), "Physique"));
		Field e = (new Field(4, types.get(5), "Conception"));
		Field f = (new Field(5, types.get(4), "Usinage"));
		
		Field[] F = {a, b, c};
		Field[] G = {b, c, d};
		Field[] H = {c, d, e};
		Field[] I = {d, e, f};
		Field[] J = {e, f, a};
		Field[] K = {f, a, b};
		
		Time MWWH = new Time (2500);
		
		teachers.add(new Teacher(0, "Twilight", "Sparkle", F, MWWH));
		teachers.add(new Teacher(1, "Rarity", "", G, MWWH));
		teachers.add(new Teacher(2, "Apple", "Jack", H, MWWH));
		teachers.add(new Teacher(3, "Rainbow", "Dash", I, MWWH));
		teachers.add(new Teacher(4, "Flutter", "Shy", J, MWWH));
		teachers.add(new Teacher(5, "Pinkie", "Pie", K, MWWH));
		teachers.add(new Teacher(6, "Spike", "", F, MWWH));
		//teachers.add(new Teacher(7, "Celestia", "", G, MWWH));
		//teachers.add(new Teacher(8, "Princess", "Luna", H, MWWH));
		//teachers.add(new Teacher(9, "Discord", "", F, MWWH));
		
		
		HashMap<Field, Time> classes = new HashMap<Field, Time>();
		
		classes.put(a, new Time(300));
		classes.put(b, new Time(300));
		classes.put(c, new Time(330));
		classes.put(d, new Time(330));
		classes.put(e, new Time(200));
		classes.put(f, new Time(400));
		
		//System.out.println("Main.main() : classes " + classes);
		
		groups.add(new Group(0, "Lanip", 100).setClasses(null).setParent(null));
		groups.add(new Group(1, "g46", 25).setClasses(classes).setParent(groups.get(0)).setChildren(null));
		groups.add(new Group(2, "g42", 25).setClasses(classes).setParent(groups.get(0)).setChildren(null));
		//groups.add(new Group(3, "g2116", 25).setClasses(classes).setParent(groups.get(0)).setChildren(null));
		groups.add(new Group(4, "gPi", 25).setClasses(classes).setParent(groups.get(1)).setChildren(null));
		
		Group[] gtab = {groups.get(1), groups.get(2), groups.get(3)};
		groups.get(0).setChildren(gtab);
		
		//groups.add(new Group(3, 25).setClasses(classes));
		//groups.add(new Group(4, 25).setClasses(classes));
		/*
		groups.add(new Group(5, 16).setClasses(classes));
		groups.add(new Group(6, 16).setClasses(classes));
		groups.add(new Group(7, 16).setClasses(classes));
		groups.add(new Group(8, 16).setClasses(classes));
		groups.add(new Group(9, 16).setClasses(classes));
		groups.add(new Group(10, 16).setClasses(classes));
		*/
		System.out.println("Main.main() : Examples set :");
		System.out.println("# ClassType : " + types.get(0) + "\n# Classroom : " + classrooms.get(0) + "\n# Field : " + a + "\n# Teacher : " + teachers.get(0) + "\n# Group : " + groups.get(3));
		
		Filler filler = new Filler(classrooms, groups, teachers, types, new Time(3500));
		
		System.out.println("Main.main() : Filler built.");
		
		filler.computeConstraints();
		
		System.out.println("Main.main() : Constraints computed");
		
		filler.attributeTeachers();
		
		System.out.println("Main.main() : Teachers attributed to groups.");
		
		/*
		for (Group g : groups)
			System.out.println("Main.main() --> Group.teachers : " + g + " " + g.getTeachers());
		*/
	}
}
