package DATA;

import java.util.ArrayList;
import java.util.Iterator;

public class Filler	{

	private ArrayList<Classroom> classrooms;
	private ArrayList<Group> groups;
	private ArrayList<Teacher> teachers;
	private Time MWWH; //Max Worked Week Hours, durée en heures de la semaine
	//private List<Constraint> constraints;
	
	public Filler(ArrayList<Classroom> aClassrooms, ArrayList<Group> aGroups, ArrayList<Teacher> aTeachers, Time aMWWH/*, ArrayList<Constraint> aConstraints*/)	{
	
		this.classrooms = aClassrooms;
		this.groups = aGroups;
		this.teachers = aTeachers;
		this.MWWH = aMWWH;
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
		
	// BEGIN : ratio hours / Classroom
		
		/* Method :
		 * count the hours for each classtype
		 * count the available hours in classrooms
		 * ratio must be under 1.0
		 */

		Time[] durationsNeeded = new Time[Classroom.types.size()];
		Time[] durationsAvailable = new Time[Classroom.types.size()];
		
		for (int i = 0 ; i < durationsNeeded.length ; i++)	{
			durationsNeeded[i] = new Time();
			durationsAvailable[i] = new Time();
		}
		
		Group g;
		
		for (int i = 0 ; i < this.groups.size() ; i++)	{
			
			g = this.groups.get(i);
			
			for (Field f : g.classes.keySet())	{
				
				//System.out.println("Filler.computeConstraints() # classHours : " + durationsNeeded + " # " + durationsNeeded.length + " # " + f + " # " + (int)f.getType() + " # " + durationsNeeded[(int)f.getType()] + " # " + g + " # " + g.classes.size());
				/*
				if (durationsNeeded[(int)f.getType()] == null)
					durationsNeeded[(int)f.getType()] = new Time();
				*/
				durationsNeeded[(int)f.getType()] = durationsNeeded[(int)f.getType()].add(g.classes.get(f));
			}
		}
		
		Classroom c;
		
		for (int i = 0 ; i < this.classrooms.size() ; i++)	{
			c = this.classrooms.get(i);
			if (durationsAvailable[c.getType()] == null)
				durationsAvailable[c.getType()] = new Time();
			durationsAvailable[c.getType()] = durationsAvailable[c.getType()].add(this.MWWH);
		}
		
		boolean failed = false;
		
		for (int i = 0 ; i < durationsAvailable.length ; i++)	{
			
			//System.out.println("Filler.computeConstraints() #classHours : " + i + " " + (this.classrooms.size() > i ? this.classrooms.get(i) : "null") + " " + durationsNeeded[i] + " / " + durationsAvailable[i]);
			
			if (durationsAvailable[i] == null && durationsNeeded[i] != null || durationsAvailable[i] != null && durationsNeeded[i] != null && durationsNeeded[i].isMoreThan(durationsAvailable[i]) )	{
				System.out.println("## /!\\ ## : Filler.computeConstraints() says : Not enough classrooms ! " + Classroom.types.get(i));
				failed = true;
			}
		}
		
		if (!failed)
			System.out.println("Filler.computeConstraints() says : There are enough classrooms.");
		
	// END : ration hours / classrooms
		
	// BEGIN : ration hours / teachers

		Time[] hoursNeeded = new Time[Field.names.size()];
		Time[] hoursAvailable= new Time[Field.names.size()];

		for (int i = 0 ; i < hoursNeeded.length ; i++)	{
			hoursNeeded[i] = new Time();
			hoursAvailable[i] = new Time();
		}
		
		//Hours needed by students
		for (int i = 0 ; i < this.groups.size() ; i++)	{
			g = this.groups.get(i);
			for (Field f : g.classes.keySet())	{
				hoursNeeded[f.getType()] = hoursNeeded[f.getType()].add(g.classes.get(f));
			}
		}
		
		Teacher t;
		Field f;
		for (int i = 0 ; i < this.teachers.size() ; i++)	{
			t = this.teachers.get(i);
			
			for (int j = 0 ; j < t.getFields().length ; j++)	{
				f = t.getFields()[j];
				//System.out.println(i + " " + j + " Filler.computeConstraints() #teachersHours : " + t + " " + t.getCWWH() + " / " + t.getMWWH() + " " + f);
				hoursAvailable[f.getType()] = hoursAvailable[f.getType()].add(t.getMWWH());
			}
		}
		
		failed = false;
		for (int i = 0 ; i < hoursAvailable.length ; i++)	{
			System.out.println(i + " Filler.computeConstraints() #TeachersHours : " + Field.names.get((char)i) + " " + hoursNeeded[i] + " / " + hoursAvailable[i]);
			if (hoursNeeded[i].isMoreThan(hoursAvailable[i]))	{
				failed = true;
				System.out.println("## /!\\ ## : Filler.computeConstraints() says : Not enough teachers ! " + Field.names.get(i));
			}
		}
		
	// END : ratio hours / teachers
		
		Constrainable[] res = new Constrainable[this.groups.size() + this.teachers.size() + this.classrooms.size()];
		ArrayList<Double> constraints = new ArrayList<Double>();
		
		return null;
	}
}











