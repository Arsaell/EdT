package DATA;

import DATA.Teacher;
import DATA.Time;

public class DataStore {
	
	private Teacher[] teachers;
	
	public DataStore() {
		this.addFixtures();
	}
	
	public void addFixtures() {
		this.teachers = new Teacher[]{ new Teacher(1, "Un", "test", new Field[]{}, new Time((byte)1, (byte)1, (byte)1))};
	}
	
	public Teacher[] getTeachers() {
		return this.teachers;
	}
}
