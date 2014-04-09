package DATA;

import java.util.ArrayList;
import java.util.Iterator;

public class Filler	{

	private ArrayList<Classroom> classrooms;
	private ArrayList<Group> groups;
	private ArrayList<Teacher> teachers;
	//private List<Constraint> constraints;
	
	public Filler(ArrayList<Classroom> aClassrooms, ArrayList<Group> aGroups, ArrayList<Teacher> aTeachers/*, ArrayList<Constraint> aConstraints*/)	{
	
		this.classrooms = aClassrooms;
		this.groups = aGroups;
		this.teachers = aTeachers;
		//this.constraints = aConstraints;
	}
	
	public void fill()	{
	
		this.attributeTeachers();
		this.computeConstraints();
		
	}
	
	public void attributeTeachers()	{
	
		//System.out.println("Filler.attributeTeachers()");
		
		boolean end = false;
		
		while (!end)	{
		
			end = true;
			
			for (int i = 0 ; i < this.groups.size() ; i ++)	{
			
				Group g = this.groups.get(i);
				Field f = g.getNextUnattributedClass();
				
				if (f != null)	{
				
					end = false;
					boolean fieldDone = false;
					
					while (!fieldDone)	{
					
						Teacher teach = null;
						for (int j = 0 ; j < this.teachers.size() ; j++)	{
						
							Teacher t = this.teachers.get(j);
							//System.out.println("Filler.attributeTeachers()#FindSuitableTeacher : " + t);
							if (t.canTeach(f, g))	{
								teach = t;
								j = this.teachers.size();
							}
						}
					
						fieldDone = g.setTeacher(teach, f);
					}
				}
			}
		}
	}
	
	public Constrainable[] computeConstraints()	{
		
		/*
			Retourne un tableau des objets les plus contraints, dans l'ordre
			
			Contraintes à considérer :
				#1	nombre d'heures de cours / nombre de salles (ex TP)
				#2	nombre d'heures de cours / nombre d'heures de prof dispo
				#3	
		*/
		
		Constrainable[] res = new Constrainable[this.groups.size() + this.teachers.size() + this.classrooms.size()];
		ArrayList<Double> constraints = new ArrayList<Double>();
		
		for (Group g : groups)
			//constraints.addAll(g.getConstraint().Set());
			break;
		return null;
	}
}











