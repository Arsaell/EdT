import java.util.List;

public class Main	{

	public static void main(String[] args)	{
	
		List<Classroom> classrooms= new List<Classroom>();
		List<Teacher> teachers = new List<Teacher>();
		List<Group> groups = new List<Group>();
		
		
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
		
		teachers.add(new Teacher(0, "Twilight Sparkle", F));
		teachers.add(new Teacher(1, "Rarity", G));
		teachers.add(new Teacher(2, "Apple Jack", H));
		teachers.add(new Teacher(3, "Rainbow Dash", I));
		teachers.add(new Teacher(4, "Flutter Shy", J));
		teachers.add(new Teacher(5, "Pinkie Pie", K));
		
		groups.add(new Group(0, 100));
		groups.add(new Group(1, 25));
		groups.add(new Group(2, 25));
		groups.add(new Group(3, 25));
		groups.add(new Group(4, 25));
		groups.add(new Group(5, 16));
		groups.add(new Group(6, 16));
		groups.add(new Group(7, 16));
		groups.add(new Group(8, 16));
		groups.add(new Group(9, 16));
		groups.add(new Group(10, 16));
	}
}
