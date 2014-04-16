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
		
	/*
	 * Dans l'ordre spécifié par la paramètre,
	 * cette méthode devra attribuer les constrainables
	 * jusqu'à ce que l'emploi du temps soit complet.
	 * (Prepare for trouble ! And make it Double ! 'Cause here comes the longest, most complex part of the code.)
	 * */

	public void fill(Constrainable[] order)	{
	
		for (Constrainable c : order)	{
		
			if (c instanceof Field)	{
			
				this.takeCareOf((Field) c);
			}
			
			else if (c instanceof ClassType)	{
			
				this.takeCareOf((ClassType) c);
			}
			
			else if (c instanceof Teacher)	{
				
				this.takeCareOf((Teacher) c);
			}
			
			else if (c instanceof Group)	{
				
				this.takeCareOf((Group) c);
			}
		}
	}
	
	
	//Attribue les profs aux groupes dans l'ordre indiqué par le paramètre order (par ordre de contrainte dans l'idée)
	public boolean attributeTeachers(ArrayList<Field> order)	{
	
		/*
			Method :
			
			for each field in order
				for each group
					if this group has the field
						find a teacher for this {group,field}
			
			Deux boucles très similaires se suivent : au cas où la première échouerait,
			la deuxième parcourt le tableau différemment et pourrait donner un autre résultat.
			
		*/
		boolean failed = false;
		
		int i = 0, j = 0;
		
		for (Field f : order)	{
		
			for (Group g : this.groups)	{
			
				if (g.getClasses().containsKey(f))	{
				
					j = i;
					for (i = i ; i < this.teachers.size() ; i++)	{
					
						Teacher teach = this.teachers.get(i);
						
						if (teach.canTeach(f, g))	{
						
							g.setTeacher(teach, f);
							//System.out.println("Filler.attributeTeachers(Field[] order) : " + g + " " + f + " " + teach);
							break;
						}
						
						/*
						else if (teach == this.teachers.get(this.teachers.size() - 1))
							System.out.println("Filler.attributeTeachers(Field[] order) says : Couldn't attribute a teacher for field " +  f + " to group " + g);
						*/
						
						if(i == this.teachers.size() - 1)
							i = -1;
						
						if (i == j - 1)	{
							failed = true;
							System.out.println("Filler.attributeTeachers(Field[] order) says : Couldn't attribute a teacher for field " +  f + " to group " + g);
							break;
						}
					}
				}
			}
		}
		
		if (failed)	{
		
			failed = false;
			
			for (Field f : order)	{
				
				for (Group g : this.groups)	{
				
					if (g.getClasses().containsKey(f))	{
					
						for (Teacher teach : this.teachers)	{
						
							if (teach.canTeach(f, g))	{
							
								g.setTeacher(teach, f);
								//System.out.println("Filler.attributeTeachers(Field[] order) : " + g + " " + f + " " + teach);
								break;
							}
							
							
							else if (teach == this.teachers.get(this.teachers.size() - 1))	{
								failed = true;
								System.out.println("Filler.attributeTeachers(Field[] order) says : Couldn't attribute a teacher for field " +  f + " to group " + g);
							}
						}
					}
				}
			}
		}
		
		return (!failed);
	}
	
	public Constrainable[] computeConstraints(boolean attributeTeachers)	{
		
		/*
			Retourne un tableau des objets les plus contraints, dans l'ordre
			
			Contraintes à considérer :
				#1	nombre d'heures de cours / nombre de salles (ex TP au PC)
				#2	nombre d'heures de cours / nombre d'heures de prof dispo
				#3	Profs : CWWH / MWWH
				#4	Élèves : sum(Classes.hours) / MWWH
				#5	
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
			On a des HashMaps roomTimeNeeded/Available, teachTimeNeeded/Available contenant les temps considérés
			On affiche des message d'erreur dans la console en cas de problème (<=> contrainte supérieure à 1) --> Possibilité de popup des avertissements plus tard.
			
			Il nous faut définir des contraintes pour chaque champ, en l'occurence timeNeeded.divideBy(timeAvailable)	//Division entre Times divise l'équivalent en minutes de chaque, puis renvoie un double.
			Puis retourner un tableau de champs ordonnés par contrainte.
		*/
		
		HashMap<ClassType, Double> roomConstraints = new HashMap<ClassType, Double>();
		HashMap<Field, Double> teachingConstraints = new HashMap<Field, Double>();
		
		for (ClassType type : roomTimeNeeded.keySet())
			roomConstraints.put(type, roomTimeNeeded.get(type).divideBy(roomTimeAvailable.get(type)));
		//			constraint =			Needed				  /			Available
		
		for (Field field : teachTimeNeeded.keySet())
			teachingConstraints.put(field, teachTimeNeeded.get(field).divideBy(teachTimeAvailable.get(field).add(teachTimeSpare.get(field))));
		//			constraint	=				Needed					/		(Available					+		spare)
		
		//System.out.println("Filler.computeConstraints() #orderByValues : " + roomConstraints.size() + " " + teacherConstraints.size());
		
		


	/*
	 * En ce point du code, on dispose des contraintes sur les Field, on peut donc attribuer les Teachers aux Groups
	 * Ce qui permet par la suite de calculer réellement les contraintes des profs, et non pas sur la base des horaires
	 * estimés lors du calcul de contraintes.
	 * Ce choix est déterminé par la boolean attributeTeachers en paramètre.
	 */
		
		if (attributeTeachers)	{
			
			//On réinitialise les horaires des profs (simulés pour le calcul de contrainte).
			for (Teacher teach : cwwhs.keySet())
				teach.setCWWH(cwwhs.get(teach));
			
			ArrayList<Field> temp = new ArrayList<Field>();
			temp.add((Field) teachingConstraints.keySet().toArray()[teachingConstraints.size() - 1]);
			
			for (Field f1 : teachingConstraints.keySet()){
				for (int i = 0 ; i < temp.size() ; i++)	{
					Field f2 = temp.get(i);
					
					if (teachingConstraints.get(f1) > teachingConstraints.get(f2))	{
						temp.add(i, f1);
						break;
					}
					
					else if (i == temp.size() - 1)	{
						temp.add(f1);
						break;
					}
				}
				if (f1 == teachingConstraints.keySet().toArray()[0])	{
					temp.remove(teachingConstraints.keySet().toArray()[teachingConstraints.size() - 1]);
				}
			}
			
			if (!this.attributeTeachers(temp))
				System.out.println("\n\n\t\t##########\n\n\tFiller.attributeTeachers() failed !\n\n\t\t##########\n\n");
		}
		
		
		
		
	//BEGIN : # 3 each teacher's constraints
		
		HashMap<Teacher, Double> teachersConstraints = new HashMap<Teacher, Double>();
		
		for (Teacher teach : this.teachers)	{
			teachersConstraints.put(teach, teach.getCWWH().divideBy(teach.getMWWH()));
		}
		
	//END : #3
		
	//BEGIN : #4 Overloaded groups
		
		HashMap<Group, Double> groupConstraints = new HashMap<Group, Double>();
		
		for (Group group : this.groups)	{
			Time t = new Time();
			for (Field f : group.classes.keySet())
				t = t.add(group.classes.get(f));
			groupConstraints.put(group, t.divideBy(this.MWWH));
		}
		
	//END : #4
		
		//On réinitialise les horaires des profs.
		if (!attributeTeachers)
			for (Teacher teach : cwwhs.keySet())
				teach.setCWWH(cwwhs.get(teach));
		
		ArrayList<HashMap> data = new ArrayList<HashMap>();
		data.add(teachersConstraints);
		data.add(teachingConstraints);
		data.add(groupConstraints);
		data.add(roomConstraints);
		
		//Constrainable[] res1 = this.orderByValues(roomConstraints, teachingConstraints, teachersConstraints, groupConstraints);
		Constrainable[] res2 = this.orderByValues(data);
		return res2;
	}
	
	
	private Constrainable[] orderByValues(ArrayList<HashMap> data){
		
		System.out.print("Filler.orderByValues(ArrayList<HashMap<Constrainable, Double>>) : ");
		
		ArrayList<Constrainable> temp = new ArrayList<Constrainable>();
		HashMap<Constrainable, Double> constraints = new HashMap<Constrainable, Double>();
		
	//On insère un premier élément pour lancer la boucle.
		temp.add((Constrainable) data.get(data.size() - 1).keySet().toArray()[data.get(0).size() - 1]);
		
		//System.out.println(temp);
		
		for (HashMap<Constrainable, Double> hm : data){
			
			//System.out.println("#0 (hashmap) : " + hm.size());
			
			for (Constrainable c1 : hm.keySet()){
				
				constraints.put(c1,  hm.get(c1));
				//System.out.println(" #1 : " + c1 + " --> " + constraints.get(c1));
				
				for (int i = 0 ; i < temp.size(); i++)	{
					
					//System.out.print("  #2 : " + i);
					
					Constrainable c2 = temp.get(i);
					double con = -1;
					
					//System.out.println(" c2 : " + c2 + " # " + con);
					
					for (HashMap<Constrainable, Double> hmap : data)	{
						
						//System.out.print("   #3 (hashmap) : " + hmap.size() + " # " + c2 + "\t");
						
						con = hmap.get(c2) == null ? - 1 : hmap.get(c2);
						
						//System.out.println(" --> " + con);
						
						if (con != -1)
							break;
					}
					
					if (hm.get(c1) > con)	{
						//System.out.println("Fits here : " + i);
						temp.add(i , c1);
						break;
					}
					
					else if (i == temp.size() - 1)	{
						//System.out.println("addLast() : " + (temp.size() + 1));
						temp.add(c1);
						break;
					}
				}
			}
			
			//On enlève l'élément inséré à l'initialisation.
			if (hm == data.get(1))	{
				temp.remove(data.get(data.size() - 1).keySet().toArray()[data.get(0).size() - 1]);
			}
		}
		
		Constrainable[] res = new Constrainable[temp.size()];
		
		for (int i = 0 ; i < temp.size(); i++)
			res[i] = temp.get(i);
		
		/*
		for (int i = 0 ; i < res.length ; i++)	
			System.out.println("Filler.orderByValues(ArrayList<HashMap<Constrainable, Double>>) : " + res[i] + "\t\t--> " + constraints.get(res[i]));
		//*/
		
		return res;
	}

	private boolean takeCareOf(Field f)	{
		
		return true;
	}

	private boolean takeCareOf(ClassType ct)	{
		
		return true;
	}
	
	private boolean takeCareOf(Teacher teach)	{
		
		return true;
	}
	
	private boolean takeCareOf(Group g)	{
		
		return true;
	}
	





/*
 * La méthode computeContraints() renvoie des hashmap<> de constrainables avec leurs contraintes
 * Implémenter une méthode private Constrainable[] sortKeysByValues(HashMap<Constrainable, double>)
 * qui renvoie un tableau où les constrainables sont classés pas ordre de contrainte
 * Par la suite, il suffit de faire un méthode qui fill chaque constrainable en suivant l'ordre du tableau !
 */


//Ci dessous, la première méthode orderByValues, si besoin plus tard.
	//Ça serait quand même bien de pouvoir la virer de là, ça fait un grand bloc de texte qu'Eclipse refuse de replier, du coup c't'assez chiant.
/*
 * 
 * private Constrainable[] orderByValues(HashMap<ClassType, Double> roomConstraints, HashMap<Field, Double> teachingConstraints, HashMap<Teacher, Double> teachersConstraints, HashMap<Group, Double> groupConstraints)	{

	LinkedList<Constrainable> temp = new LinkedList<Constrainable>();
	//System.out.println("\nFiller.orderByValues() #0 : \n" + constraints1.keySet() + "\n\n\n" + constraints2.keySet());
	
	//On insère un premier élément dans la liste.
	temp.add((Constrainable) roomConstraints.keySet().toArray()[roomConstraints.size() - 1]);
	
	for (ClassType ct : roomConstraints.keySet())	{
		
		//System.out.println("#0 : " + c);
		
		for (int i = 0 ; i < temp.size() ; i++)	{
			
			double con = roomConstraints.get(ct);
			Constrainable c = temp.get(i);
			
			//System.out.println(" #1 : "+ i + " " + current + " : " + con + " / " + constraints1.get(current));
			
			if (con > roomConstraints.get(c))	{
				//System.out.println("  Fits here " + temp.size());
				temp.add(i, ct);
				break;
			}
			
			else if (i == temp.size() - 1)	{
				//System.out.println("  End of List : " + temp.size());
				temp.add(i++, ct);
			}
			
			//On retire l'élément inséré en trop lors de l'initialisation.
			if (ct == roomConstraints.keySet().toArray()[0])
				temp.remove((Constrainable) roomConstraints.keySet().toArray()[roomConstraints.size() - 1]);
		}
	}
	
	//System.out.println("res1 : "+ temp.size() + " " + constraints1.size() + " " + temp);
	
	
	for (Field f : teachingConstraints.keySet())	{
		
		//System.out.println("0 : " + c);
		
		for (int i = 0 ; i < temp.size() ; i++)	{
			
			Constrainable c = temp.get(i);
			double cons = teachingConstraints.get(c) != null ? teachingConstraints.get(c) : roomConstraints.get(c);
			
			//System.out.println(" 1 : [" + i + "] " + co + " # " + cons + " " + constraints2.get(c));
			
			if (teachingConstraints.get(f) > cons)	{
				//System.out.println("  2 : " + temp.indexOf(co));
				temp.add(temp.indexOf(c), f);
				break;
			}
			
			else if (i == temp.size() - 1)	{
				temp.addLast(f);
				break;
			}
		}
	}
	
	for (Teacher t : teachersConstraints.keySet())	{
		
		for (int i = 0 ; i < temp.size(); i++)	{
			Constrainable c = temp.get(i);
			double con = (roomConstraints.get(c) != null ? roomConstraints.get(c) : (teachingConstraints.get(c) != null ? teachingConstraints.get(c) : teachersConstraints.get(c)));
			
			if (teachersConstraints.get(t) > con)	{
				temp.add(i, t);
				break;
			}
			
			else if (i == temp.size() - 1)	{
				temp.addLast(t);
				break;
			}
		}
	}
	
	for (Group g : groupConstraints.keySet())	{
		
		for (int i = 0 ; i < temp.size(); i++){
			
			Constrainable c = temp.get(i);
			double con = (roomConstraints.get(c) != null ? roomConstraints.get(c) : (teachingConstraints.get(c) != null ? teachingConstraints.get(c) : (teachersConstraints.get(c) != null ? teachersConstraints.get(c) : groupConstraints.get(c))));
			
			if (groupConstraints.get(g) > con)	{
				temp.add(i, g);
				break;
			}
			
			else if (i == temp.size() - 1)	{
				temp.addLast(g);
				break;
			}
		}
	}
	
	
	Constrainable[] res = new Constrainable[temp.size()];
	
	for (int i = 0 ; i < temp.size() ; i++)	{
	
		res[i] = temp.get(i);
	}
	
	/*
	for (Constrainable c : res)
		System.out.println("Filler.orderByValues : " + c + "\t" + (roomConstraints.get(c) != null ? roomConstraints.get(c) : (teachingConstraints.get(c) != null ? teachingConstraints.get(c) : (teachersConstraints.get(c) != null ? teachersConstraints.get(c) : groupConstraints.get(c)))));
	*
	
	return res;
}
 */

	
}