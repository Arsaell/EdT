import java.util.List;

public class Filler	{

	private List<Classroom> classrooms;
	private List<Group> groups;
	private List<Teacher> teachers;
	private List<Constraint> constraints;
	
	public Filler(List<Classroom> aClassrooms, List<Group> aGroups, List<Teacher> aTeachers, List<Constraint> aConstraints)	{
	
		this.classrooms = aClassrooms;
		this.groups = aGroups;
		this.teachers = aTeachers;
		this.constraints = aConstraints;
	}
	
	public void fill()	{
	
		//attributeTeachers();
		
	}
	
}
