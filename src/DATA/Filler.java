package DATA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Filler	{

	private ArrayList<Classroom> classrooms;
	private ArrayList<ClassType> types;
	private ArrayList<Group> groups;
	private ArrayList<Teacher> teachers;
	private Time MWWH; //Max Worked Week Hours, durée en heures de la semaine
	//private List<Constraint> constraints;
	
	public Filler(ArrayList<Classroom> aClassrooms, ArrayList<Group> aGroups, ArrayList<Teacher> aTeachers, ArrayList<ClassType> aTypes, Time aMWWH/*, ArrayList<Constraint> aConstraints*/)	{
	
		this.classrooms = aClassrooms;
		this.groups = aGroups;
		this.teachers = aTeachers;
		this.types = aTypes;
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
				#1	nombre d'heures de cours / nombre de salles (ex TP au PC)
				#2	nombre d'heures de cours / nombre d'heures de prof dispo
				#3	
		*/
		
	// BEGIN : ratio hours / Classroom
		
		/* Method :
		 * count the hours for each classtype
		 * count the available hours in classrooms
		 * ratio must be under 1.00
		 */
		
		HashMap<ClassType, Time> roomTimeNeeded = new HashMap<ClassType, Time>();
		HashMap<ClassType, Time> roomTimeAvailable = new HashMap<ClassType, Time>();
		
		for (int i = 0 ; i < this.types.size(); i++)	{
			roomTimeNeeded.put(this.types.get(i), new Time());
			roomTimeAvailable.put(this.types.get(i), new Time());
		}
		
		Group g;
		
		for (int i = 0 ; i < this.groups.size() ; i++)	{
			
			g = this.groups.get(i);
			
			for (Field f : g.classes.keySet())	{
				
				//System.out.println("Filler.computeConstraints() #classrooms Time Needed 1 : # " + f + " # " + f.getType() + " # " + g + " # " + g.classes.get(f) + " # " + roomTimeNeeded.get(f.getType()));
				
				Time t = roomTimeNeeded.get(f.getType()).add(g.classes.get(f));
				roomTimeNeeded.put(f.getType(), t);

				//System.out.println("Filler.computeConstraints() #classrooms Time Needed 2 : " + g + " # " + f + " # " + f.getType() + t + " # " + roomTimeNeeded.get(f.getType()));
			}
		}
		
		Classroom c;
		
		for (int i = 0 ; i < this.classrooms.size() ; i++)	{
			c = this.classrooms.get(i);
			Time t = roomTimeAvailable.get(c.getType()).add(this.MWWH);
			roomTimeAvailable.put(c.getType(), t);
			
			//System.out.println("Filler.computeConstraints() #classrooms Time Available : " + c + " " + t + roomTimeAvailable.get(c.getType()));
		}
		
		boolean failed = false;
		
		for (int i = 0 ; i < this.types.size() ; i++)	{
			
			//System.out.println("Filler.computeConstraints() #classHours : " + i + " " + (this.classrooms.size() > i ? this.classrooms.get(i) : "null") + " " + durationsNeeded[i] + " / " + durationsAvailable[i]);
			
			if (roomTimeAvailable.get(this.types.get(i)) != null  && roomTimeNeeded.get(this.types.get(i)) != null && roomTimeNeeded.get(this.types.get(i)).isMoreThan(roomTimeAvailable.get(this.types.get(i))) )	{
				System.out.println("## /!\\ ## : Filler.computeConstraints() says : Not enough classrooms ! " + this.types.get(i));
				failed = true;
			}
		}
		
		if (!failed)
			System.out.println("Filler.computeConstraints() says : There are enough classrooms.");
		
	// END : ration hours / classrooms
		
	// BEGIN : ration hours / teachers

		HashMap<Field, Time> teachTimeNeeded = new HashMap<Field, Time>();
		HashMap<Field, Time> teachTimeAvailable = new HashMap<Field, Time>();
		
		//Hours needed by students
		for (int i = 0 ; i < this.groups.size() ; i++)	{
			g = this.groups.get(i);
			for (Field f : g.classes.keySet())	{
				
				if (!teachTimeNeeded.containsKey(f))	{
					teachTimeNeeded.put(f, new Time());
					teachTimeAvailable.put(f,  new Time());
				}
				
				Time t = teachTimeNeeded.get(f).add(g.classes.get(f));
				teachTimeNeeded.put(f, t);
			}
		}
		
	// END : ratio hours / teachers

	// BEGIN : ratio hours / teachers ADVANCED (teachers' side of the problem)
		
		//On stocke les horaires des profs, pour réinitialiser plus tard.
		HashMap<Teacher, Time> cwwhs = new HashMap<Teacher, Time>();
		for (int i = 0 ; i < this.teachers.size() ; i++)
			cwwhs.put(this.teachers.get(i), this.teachers.get(i).getCWWH().clone());
		
		/*
		 * Méthode :
		 * On parcourt les Field
		 * pour chacun, on cherche les profs qui peuvent l'enseigner
		 * pour chacun de ces profs, on ajoute la durée maximale d'un créneau
		 * jusqu'à couvrir le besoin des étudiants.
		 * */
		
		for (Field f : teachTimeAvailable.keySet()){
			while (teachTimeAvailable.get(f).isLessThan(teachTimeNeeded.get(f)))	{
				for (Teacher teach : this.teachers)	{
					if (teach.knows(f))	{
						
						Time t = f.getDuration().getEnd();
						Time left = teachTimeNeeded.get(f).substract(teachTimeAvailable.get(f));
						
//Ici, il faut réfléchir à un moyen d'être sûrs de bien retomber sur le bon Time en ajoutant quelques Times...
//J'ai la flemme de faire ça ce soir, Léo si tu vois ça, Tu te tais ou JE te tais !
						
						if (t.multiplyBy(3.0).isMoreThan(left))	{
							
							t = f.getDuration().getBegin();
						}
					}
				}
				break;
			}
		}
		
	// END : ratio hours / teachers ADVANCED
		
		return null;
	}
}






/*
 * La méthode computeContraints() renvoie des hashmap<> de constrainables avec leurs contraintes
 * Implémenter une méthode private Constrainable[] sortKeysByValues(HashMap<Constrainable, double>)
 * qui renvoie un tableau où les constrainables sont classés pas ordre de contrainte
 * Par la suite, il suffit de faire un méthode qui fill chaque constrainable en suivant l'ordre du tableau !
 */




