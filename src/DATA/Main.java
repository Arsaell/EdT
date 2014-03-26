import java.util.ArrayList;
import java.util.HashMap;

public class Main	{

	public static void main(String[] args)	{
	
		ArrayList<Classroom> classrooms= new ArrayList<Classroom>();
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		ArrayList<Group> groups = new ArrayList<Group>();
		
		
		classrooms.add(new Classroom(0, (char)0, "C9", 25));
		classrooms.add(new Classroom(1, (char)1, "Vannier", 100));
		classrooms.add(new Classroom(2, (char)2, "Optique", 16));
		classrooms.add(new Classroom(3, (char)3, "Labo Chimie 1", 16));
		classrooms.add(new Classroom(4, (char)4, "Usinage", 32));
		
		Field a = new Field(0, (char)0, "Amphi Maths");
		Field b = new Field(1, (char)1, "Amphi Physique");
		Field c = new Field(2, (char)2, "TD Maths");
		Field d = new Field(3, (char)3, "TD Physique");
		Field e = new Field(4, (char)4, "Conception");
		Field f = new Field(5, (char)5, "TP Construction");
		
		Field[] F = {a, b, c};
		Field[] G = {b, c, d};
		Field[] H = {c, d, e};
		Field[] I = {d, e, f};
		Field[] J = {e, f, a};
		Field[] K = {f, a, b};
		
		Time MWWH = new Time ((byte)1, (byte)6, (byte)0);
		
		teachers.add(new Teacher(0, "Twilight Sparkle", F, MWWH));
		teachers.add(new Teacher(1, "Rarity", G, MWWH));
		teachers.add(new Teacher(2, "Apple Jack", H, MWWH));
		teachers.add(new Teacher(3, "Rainbow Dash", I, MWWH));
		teachers.add(new Teacher(4, "Flutter Shy", J, MWWH));
		teachers.add(new Teacher(5, "Pinkie Pie", K, MWWH));
		
		HashMap<Field, Time> classes = new HashMap<Field, Time>();
		
		classes.put(a, new Time((byte)0, (byte)3, (byte)0));
		classes.put(b, new Time((byte)0, (byte)3, (byte)0));
		classes.put(c, new Time((byte)0, (byte)3, (byte)30));
		classes.put(d, new Time((byte)0, (byte)3, (byte)30));
		classes.put(e, new Time((byte)0, (byte)2, (byte)0));
		classes.put(f, new Time((byte)0, (byte)4, (byte)0));
		
		groups.add(new Group(0, 100).setClasses(classes));
		groups.add(new Group(1, 25).setClasses(classes));
		groups.add(new Group(2, 25).setClasses(classes));
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
		System.out.println("Main.main() : Examples set.");
		
		Filler filler = new Filler(classrooms, groups, teachers);
		
		System.out.println("Main.main() : Filler built.");
		
		filler.attributeTeachers();
		
		System.out.println("Main.main() : Teachers attributed to groups.");
		
		/*
		for (Group g : groups)
			System.out.println("Main.main() --> Group.teachers : " + g + " " + g.getTeachers());
		*/
		
		filler.computeConstraints();
	}
}