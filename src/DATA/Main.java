
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
		classrooms.add(new Classroom(3, (char)3, "1", 16));
		classrooms.add(new Classroom(4, (char)4, "Usinage", 32));
		classrooms.add(new Classroom(5, (char)5, "Est", 25));
		classrooms.add(new Classroom(6, (char)6, "Archie", 25));
		classrooms.add(new Classroom(7, (char)7, "Piscine", 150));
		classrooms.add(new Classroom(8, (char)8, "PC", 900));
		
		Field a = (new Field(0, (char)0)).setClassroomType((char)1).setName("Amphi Maths").setTimes(new Slot(new Time(100), new Time(130)));
		Field b = (new Field(1, (char)1)).setClassroomType((char)1).setName("Amphi Physique").setTimes(new Slot(new Time(100), new Time(130)));
		Field c = (new Field(2, (char)2)).setClassroomType((char)0).setName("TD Maths").setTimes(new Slot(new Time(130), new Time(200)));
		Field d = (new Field(3, (char)3)).setClassroomType((char)0).setName("TD Physique").setTimes(new Slot(new Time(130), new Time(200)));
		Field e = (new Field(4, (char)4)).setClassroomType((char)5).setName("Conception").setTimes(new Slot(new Time(200), new Time(200)));
		Field f = (new Field(5, (char)5)).setClassroomType((char)4).setName("TP Construction").setTimes(new Slot(new Time(400), new Time(400)));
		
		System.out.println("Main.main() : fields + classrooms " + Field.classrooms);
		
		Field[] F = {a, b, c};
		Field[] G = {b, c, d};
		Field[] H = {c, d, e};
		Field[] I = {d, e, f};
		Field[] J = {e, f, a};
		Field[] K = {f, a, b};
		
		Time MWWH = new Time (2000);
		
		teachers.add(new Teacher(0, "Twilight Sparkle", F, MWWH));
		teachers.add(new Teacher(1, "Rarity", G, MWWH));
		teachers.add(new Teacher(2, "Apple Jack", H, MWWH));
		teachers.add(new Teacher(3, "Rainbow Dash", I, MWWH));
		teachers.add(new Teacher(4, "Flutter Shy", J, MWWH));
		teachers.add(new Teacher(5, "Pinkie Pie", K, MWWH));
		teachers.add(new Teacher(6, "Spike", F, MWWH));
		teachers.add(new Teacher(7, "Celestia", G, MWWH));
		teachers.add(new Teacher(8, "Princess Luna", H, MWWH));
		//teachers.add(new Teacher(9, "Discord", F, MWWH));
		
		
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
		
		Group[] gtab = {groups.get(0), groups.get(0)};
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
		System.out.println("Main.main() : Examples set.");
		
		Filler filler = new Filler(classrooms, groups, teachers, MWWH);
		
		System.out.println("Main.main() : Filler built.");
		
		filler.attributeTeachers();
		
		System.out.println("Main.main() : Teachers attributed to groups.");
		
		/*
		for (Group g : groups)
			System.out.println("Main.main() --> Group.teachers : " + g + " " + g.getTeachers());
		*/
		
		filler.computeConstraints();
		
		System.out.println("Main.main() : Constraints computed");
	}
}
