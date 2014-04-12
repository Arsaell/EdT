package DATA;

import java.util.ArrayList;

import DATA.Teacher;
import DATA.Time;

public class DataStore {
	
	private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
	
	public DataStore() {
		this.addFixtures();
	}
	
	public void addFixtures() {
		this.teachers.add(new Teacher(1, "Un", "test", new Field[]{}, new Time((byte)1, (byte)1, (byte)1)));
	}
	
	public ArrayList<Teacher> getTeachers() {
		return this.teachers;
	}
}
