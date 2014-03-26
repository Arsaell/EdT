import java.util.List;

public class Filler	{

	private List<Classroom> classrooms;
	private List<Group> groups;
	private List<Teacher> teachers;
	//private List<Constraint> constraints;
	
	public Filler(List<Classroom> aClassrooms, List<Group> aGroups, List<Teacher> aTeachers/*, List<Constraint> aConstraints*/)	{
	
		this.classrooms = aClassrooms;
		this.groups = aGroups;
		this.teachers = aTeachers;
		//this.constraints = aConstraints;
	}
	
	public void fill()	{
	
		//attributeTeachers();
		
	}
	
	private void attributeTeachers()	{
	
		boolean end = false;
		
		while (!end)	{
		
			end = true;
			for (Group g : groups)	{
			
				Field f = g.getNextUnattributedClass();
				
				if (f != null)	{
				
					end = false;
					boolean fieldDone = false;
					
					while (!fieldDone)	{
					
						Teacher teach = null;
						for (Teacher t : this.teachers)	{
							if (t.canTeach(f, g))	{
								teach = t;
								break;
							}
						}
					
						fieldDone = g.setTeacher(teach, f);
					}
				}
			}
		}
	}
	
	private Constrainable[] computeConstraints()	{
		
		List<Double> constraints = new List<Double>();
		
		for (Group g : groups)
			constraints.addAll(g.getConstraint().set());
		
		return null;
	}
}











