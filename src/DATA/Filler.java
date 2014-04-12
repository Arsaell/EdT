package DATA;

import java.util.ArrayList;
import java.util.LinkedList;
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
	
		this.computeConstraints();
		this.attributeTeachers();
		
	}
	
	public void attributeTeachers()	{
	
		
		/*
		Method :
			For each group
				for each field in this group
					for each teacher
						if (teacher.canTeach())
							group.setTeacher(field, teacher);
		
		*/
		
		//System.out.println("Filler.attributeTeachers()");
		
		boolean fieldDone = false;
		Field f;
		Teacher teach = null;
		
		for (Group g : this.groups)	{
			
			//System.out.println("#1 " + g);
			f = g.getNextUnattributedClass();
			
			while (f != null)	{
			
				fieldDone = false;
				//System.out.println(" #2 " + f);
				
				while (!fieldDone)	{
				
					teach = null;
					
					for (int j = 0 ; j < this.teachers.size() ; j++)	{
					
						Teacher t = this.teachers.get(j);
						//System.out.println("  #3 " + t);
						//System.out.println("Filler.attributeTeachers() #FindSuitableTeacher : " + g + " # " + f + " # " + t);
						
						if (t.canTeach(f, g))	{
							teach = t;
							//System.out.println("   #5 " + teach.getCWWH());
							break;
						}
					}
					fieldDone = g.setTeacher(teach, f);
					//System.out.println("    #6 " + teach.getCWWH());
				}
				f = g.getNextUnattributedClass();
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
		
		for (Field f : teachTimeAvailable.keySet())	{
			
			//System.out.println("Filler.computeConstraints() #teachersTime : #0 " + f);
			
			while (teachTimeAvailable.get(f).isLessThan(teachTimeNeeded.get(f)))	{
			
				for (Teacher teach : this.teachers)	{
					
					//System.out.println(" #1 " + teach);
					
					if (teach.knows(f))	{
						
						Time available = teach.getMWWH().substract(teach.getCWWH()); 				//Le temps "libre" du prof
						Time needed = teachTimeNeeded.get(f).substract(teachTimeAvailable.get(f));	//La quantité restante à attribuer.

						//System.out.println(" #2 " + teach.getCWWH() + " " + teach.getMWWH() + " " + available + " #3 " + needed);
						
						//Il faut pour chaque prof attribuer toutes les heures d'une matière d'un groupe, et non pas juste le temps d'un TD
						Time t;
						
						for (Group group : this.groups)	{
							
							//System.out.println(" #4 " + group);
							
							if (group.classes.containsKey(f))	{
								
								t = group.classes.get(f);
								
								//System.out.println(" #5 " + t);
								
								if (t.isLessThan(available) && t.isLessThan(needed))	{
								
									//System.out.println(" #6");
									
									teachTimeAvailable.put (f, teachTimeAvailable.get(f).add(t));
									
									teach.addToCWWH(t);
									
									//System.out.println("#7 Filler.computeConstraints() : " + f + " #8 " + teachTimeAvailable.get(f) + " / " + teachTimeNeeded.get(f) + " #9 " + teach + " #10 " + teach.getCWWH() + " #11 " + available + " #12 " + needed + " #13 " + t);
									
									available = teach.getMWWH().substract(teach.getCWWH());
								}
							}
						}
					}
				}
			}
		}
		
		boolean okay = true;
		//System.out.println("Filler.computeConstraints() #Teachers : ");
		
		for (Field f : teachTimeNeeded.keySet())	{
		
			//System.out.println("\t" + f + " --> " + teachTimeNeeded.get(f) + " / " + teachTimeAvailable.get(f));
			
			if (teachTimeNeeded.get(f).isMoreThan(teachTimeAvailable.get(f)))	{
				System.out.println("## /!\\ ## : Filler.computeConstraints() says : Not enough teachers ! ");
				System.out.println(f + " --> time needed : " + teachTimeNeeded.get(f) + " ; time available : " + teachTimeAvailable.get(f));
				okay = false;
			}
		}
		
		if (okay)
			System.out.println("Filler.ComputeConstraints() says : There are enough Teachers.");
		
	// END : ratio hours / teachers ADVANCED
		
//Eh bah non ! En fait, pour vraiment déterminer la contrainte, il faut après avoir fait tout ça déterminer le nombre d'heures disponibles restantes dans chaque matière, et le diviser par le nombre d'heures nécessaires dans cette matière.
		
		HashMap<Field, Time> teachTimeSpare = new HashMap<Field, Time>();
		
		for (Teacher teach : this.teachers)	{
			for (Field f : teach.getFields())	{
				teachTimeSpare.put(f,  teach.getMWWH().substract(teach.getCWWH()).divideBy(teach.getFields().length));
			}
		}
		
		/*
		État des lieux en ce point du code :
			On a des HashMaps roomTimeNeeded/Available, teachTimeNeeded/Available
			On affiche des message d'erreur dans la console en cas de problème --> Possibilité de popup des fenêtres plus tard.
			
			Il nous faut définir des contraintes pour chaque champ, en l'occurence timeNeeded.divideBy(timeAvailable)	//Division entre Times divise le nombre de minutes de chaque, puis renvoie un double.
			Puis retourner un tableau de champs ordonnés par contrainte.
		*/
		
		HashMap<Constrainable, Double> roomConstraints = new HashMap<Constrainable, Double>();
		HashMap<Constrainable, Double> teacherConstraints = new HashMap<Constrainable, Double>();
		
		for (ClassType type : roomTimeNeeded.keySet())
			roomConstraints.put(type, roomTimeNeeded.get(type).divideBy(roomTimeAvailable.get(type)));
		
		for (Field field : teachTimeNeeded.keySet())
			teacherConstraints.put(field, teachTimeNeeded.get(field).divideBy(teachTimeAvailable.get(field).add(teachTimeSpare.get(field))));
		//			constraint	=				Needed					/		(Available					+		spare)
		
		Constrainable[] res = this.orderByValues(roomConstraints, teacherConstraints);
		
		//On réinitialise les horaires des profs.
		for (Teacher teach : cwwhs.keySet())
			teach.setCWWH(cwwhs.get(teach));
		
		
		return res;
	}
	
	private Constrainable[] orderByValues(HashMap<Constrainable, Double> constraints1, HashMap<Constrainable, Double> constraints2)	{
	
		LinkedList<Constrainable> temp = new LinkedList<Constrainable>();
		System.out.println("Filler.orderByValues() #0 : \n" + constraints1 + "\n" + constraints2);
		
		//On insère un premier élément dans la liste.
		temp.add((Constrainable) constraints1.keySet().toArray()[0]);
		
		for (Constrainable c : constraints1.keySet())	{
			
			for (int i = 0 ; i < temp.size() ; i++)	{
			
				if ((double) constraints1.get(c) >= constraints1.get(temp.get(i)))
					temp.add(i, c);
				
				else if (i++ >= temp.size())
					temp.add(i, c);
			}
		}
		
		for (Constrainable c : constraints2.keySet())	{
			
			for (int i = 0 ; i < temp.size() ; i++)	{
			
				
				if (i == temp.size())	{
					temp.add(i, c);
					break;
				}
				
				else if ((double) constraints2.get(c) >= constraints2.get(temp.get(i)))
					temp.add(i, c);
				
				System.out.println("#" + c + " " + constraints2.get(c) + " " + temp.get(i) + " " + constraints2.get(temp.get(i)));
				
			}
		}

		Constrainable[] res = new Constrainable[temp.size()];
		
		for (int i = 0 ; i < temp.size() ; i++)	{
		
			res[i] = temp.get(i);
		}
		
		for (Constrainable c : res)
			System.out.println("Filler.orderByValues : " + c);
		
		return res;
	}
}






/*
 * La méthode computeContraints() renvoie des hashmap<> de constrainables avec leurs contraintes
 * Implémenter une méthode private Constrainable[] sortKeysByValues(HashMap<Constrainable, double>)
 * qui renvoie un tableau où les constrainables sont classés pas ordre de contrainte
 * Par la suite, il suffit de faire un méthode qui fill chaque constrainable en suivant l'ordre du tableau !
 */




