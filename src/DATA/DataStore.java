package DATA;

import java.util.ArrayList;
import java.util.HashMap;

import DATA.Teacher;
import DATA.Time;

public class DataStore {
	
	private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
	private ArrayList<Classroom> classrooms= new ArrayList<Classroom>();
	private ArrayList<Group> groups = new ArrayList<Group>();
	private ArrayList<ClassType> types = new ArrayList<ClassType>();

	public DataStore() {
		this.addFixtures();
	}
	
	public void addFixtures() {

		ArrayList<Slot> temp = new ArrayList<Slot>();

		temp.add(new Slot(new Time(800), new Time(1200)));
		temp.add(new Slot(new Time(1400), new Time(1800)));
		temp.add(new Slot(new Time(10800), new Time(11200)));
		temp.add(new Slot(new Time(11400), new Time(11800)));
		temp.add(new Slot(new Time(20800), new Time(21200)));
		temp.add(new Slot(new Time(21400), new Time(21800)));
		temp.add(new Slot(new Time(30800), new Time(31200)));
		temp.add(new Slot(new Time(40800), new Time(41200)));
		temp.add(new Slot(new Time(41400), new Time(41800)));
		
		WeekTable.setDefault(new WeekTable(temp, null));
		
		this.types.add(0, new ClassType("Cours", "Amphithéâtre", new Slot(new Time(100), new Time(130))));
		this.types.add(1, new ClassType("TD", "TD", new Slot(new Time(130), new Time(200))));
		this.types.add(2, new ClassType("TP Physique", "TP", new Slot(new Time(40), new Time(400))));
		this.types.add(3, new ClassType("TP Chimie", "Labo chimie", new Slot(new Time(300), new Time(400))));
		this.types.add(4, new ClassType("TP Construction", "TP", new Slot(new Time(400), new Time(400))));
		this.types.add(5, new ClassType("TD Conception", "TD", new Slot(new Time(130), new Time(200))));
		this.types.add(6, new ClassType("TD Info", "TD", new Slot(new Time(200), new Time(200))));
		this.types.add(7, new ClassType("Sport", "Gymnase", new Slot(new Time(200), new Time(200))));
		
		this.classrooms.add(new Classroom(0, types.get(1), "C9", 25));
		this.classrooms.add(new Classroom(0, types.get(1), "2.10", 25));
		this.classrooms.add(new Classroom(1, types.get(0), "Vannier", 100));
		this.classrooms.add(new Classroom(2, types.get(2), "Optique", 16));
		this.classrooms.add(new Classroom(3, types.get(3), "1", 16));
		this.classrooms.add(new Classroom(4, types.get(4), "Usinage", 32));
		this.classrooms.add(new Classroom(5, types.get(5), "Est", 25));
		this.classrooms.add(new Classroom(6, types.get(6), "Archie", 25));
		this.classrooms.add(new Classroom(7, types.get(7), "Piscine", 150));
		
		Field mathsa = (new Field(0, types.get(0), "Maths"));
		Field physiquea = (new Field(1, types.get(0), "Physique"));
		Field mathst = (new Field(2, types.get(1), "Maths"));
		Field physiquet = (new Field(3, types.get(1), "Physique"));
		Field concept = (new Field(4, types.get(5), "Conception"));
		Field contrucp = (new Field(5, types.get(4), "Usinage"));
		
		Field[] MaPtMt = {mathsa, physiquet, mathst};
		Field[] PaMtPt = {physiquea, mathst, physiquet};
		Field[] MtPtCt = {mathst, physiquet, concept};
		Field[] PtCtCp = {physiquet, concept, contrucp};
		Field[] CtCpMa = {concept, contrucp, mathsa};
		Field[] CpMaPa = {contrucp, mathsa, physiquea};
		
		Time MWWH = new Time (2500);
		
		this.teachers.add(new Teacher(0, "Twilight", "Sparkle", MaPtMt, MWWH));
		this.teachers.add(new Teacher(1, "Rarity", "", PaMtPt, MWWH));
		this.teachers.add(new Teacher(2, "Apple", "Jack", MtPtCt, MWWH));
		this.teachers.add(new Teacher(3, "Rainbow", "Dash", PtCtCp, MWWH));
		//this.teachers.add(new Teacher(4, "Flutter", "Shy", CtCpMa, MWWH));
		//this.teachers.add(new Teacher(5, "Pinkie", "Pie", CpMaPa, MWWH));
		//this.teachers.add(new Teacher(6, "Spike", "", MaPtMt, MWWH));
		//this.teachers.add(new Teacher(7, "Celestia", "", MaPtMt, MWWH));
		//this.teachers.add(new Teacher(8, "Princess", "Luna", MaPtMt, MWWH));
		//this.teachers.add(new Teacher(9, "Discord", "", MaPtMt, MWWH));
		
		
		HashMap<Field, Time> classes = new HashMap<Field, Time>();
		
		classes.put(mathsa, new Time(300));
		classes.put(physiquea, new Time(300));
		classes.put(mathst, new Time(330));
		classes.put(physiquet, new Time(330));
		classes.put(concept, new Time(200));
		classes.put(contrucp, new Time(400));
		
		//System.out.println("Main.main() : classes " + classes);
		
		this.groups.add(new Group(0, "Lanip", 100).setClasses(null).setParent(null));
		this.groups.add(new Group(1, "g46", 25).setClasses(classes).setParent(groups.get(0)).setChildren(null));
		this.groups.add(new Group(2, "g42", 25).setClasses(classes).setParent(groups.get(0)).setChildren(null));
		this.groups.add(new Group(3, "g2116", 25).setClasses(classes).setParent(groups.get(0)).setChildren(null));
		//this.groups.add(new Group(4, "gPi", 25).setClasses(classes).setParent(groups.get(1)).setChildren(null));
		
		Group[] gtab = {groups.get(1), groups.get(2)};
		this.groups.get(0).setChildren(gtab);
		
		//this.groups.add(new Group(3, 25).setClasses(classes));
		//this.groups.add(new Group(4, 25).setClasses(classes));
		/*
		this.groups.add(new Group(5, 16).setClasses(classes));
		this.groups.add(new Group(6, 16).setClasses(classes));
		this.groups.add(new Group(7, 16).setClasses(classes));
		this.groups.add(new Group(8, 16).setClasses(classes));
		this.groups.add(new Group(9, 16).setClasses(classes));
		this.groups.add(new Group(10, 16).setClasses(classes));
		*/
		
	}
	
	public ArrayList<Teacher> getTeachers() {
		return this.teachers;
	}

	public ArrayList<Classroom> getClassrooms() {
		return classrooms;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public ArrayList<ClassType> getTypes() {
		return types;
	}

	public void setTeachers(ArrayList<Teacher> teachers) {
		this.teachers = teachers;
	}

	public void setClassrooms(ArrayList<Classroom> classrooms) {
		this.classrooms = classrooms;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	public void setTypes(ArrayList<ClassType> types) {
		this.types = types;
	}
}
