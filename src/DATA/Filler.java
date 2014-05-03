package DATA;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

public class Filler	{

	private ArrayList<Classroom> classrooms;
	private ArrayList<ClassType> types;
	private ArrayList<Group> groups;
	private ArrayList<Teacher> teachers;
	
	private HashMap<Group, HashMap<Field, Boolean>> fieldsDone;
	
	private Time MWWH; //Max Worked Week Hours, durée en heures de la semaine
	//private List<Constraint> constraints;
	
	public Filler(ArrayList<Classroom> aClassrooms, ArrayList<Group> aGroups, ArrayList<Teacher> aTeachers, ArrayList<ClassType> aTypes, Time aMWWH/*, ArrayList<Constraint> aConstraints*/)	{
	
		this.classrooms = aClassrooms;
		this.groups = aGroups;
		this.teachers = aTeachers;
		this.types = aTypes;
		this.MWWH = aMWWH;
		
		this.fieldsDone = new HashMap<Group, HashMap<Field, Boolean>>();
		
		for (Group g : this.groups)	{
			this.fieldsDone.put(g, new HashMap<Field, Boolean>());
			for (Field f : g.classes.keySet())
				this.fieldsDone.get(g).put(f, false);
		}
	}
		
	/*
	 * Dans l'ordre spécifié par la paramètre,
	 * cette méthode devra attribuer les constrainables
	 * jusqu'à ce que l'emploi du temps soit complet.
	 * */
	public void fill(Constrainable[] order)	{

		ArrayList<Group> groupsOrder = new ArrayList<Group>();
		ArrayList<Field> fieldsOrder = new ArrayList<Field>();
		
		for (Constrainable c : order)	{
			if (c instanceof Group)
				groupsOrder.add((Group)c);
			
			else if (c instanceof Field)
				fieldsOrder.add((Field)c);
		}
		
		for (Constrainable c : order)	{
			
			if (c instanceof Field)	{				//Order : Group
			
				this.takeCareOf((Field) c, groupsOrder);
			}
			
			else if (c instanceof ClassType)	{	//Order : Group
			
				this.takeCareOf((ClassType) c, groupsOrder);
			}
			
			else if (c instanceof Teacher)	{		//Order : Field
				
				this.takeCareOf((Teacher) c, fieldsOrder, groupsOrder);
			}
			
			else if (c instanceof Group)	{		//Order : Field
				//System.out.println("Filler.fill() #Group");
				this.takeCareOf((Group) c, fieldsOrder);
				//((Group) c).printWeekTable();
				//System.out.println();
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
				
				boucle 1 : Pour chaque matière dans un groupe, reprend la liste de profs là où elle en était la fois précédente
					==> permet de ne pas surcharger les premiers profs de la liste, de répartir plus équitablement les heures de cours.
				
				boucle 2 : plus traditionnelle, parcourt pour chaque matière la liste d'enseignants depuis le début.
			
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
						
							boolean res = g.setTeacher(teach, f);
							//System.out.println("Filler.attributeTeachers(Field[] order) : " + g + " " + f + " " + teach + " --> " + res);
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
							//System.out.println("Filler.attributeTeachers(Field[] order) says : Couldn't attribute a teacher for field " +  f + " to group " + g);
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
							
								boolean res = g.setTeacher(teach, f);
								System.out.println("Filler.attributeTeachers(Field[] order) : " + g + " " + f + " " + teach + " --> " + res);
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
				#1	nombre d'heures de cours / nombre de salles (ex TP au PC)	--> ClassType
				#2	nombre d'heures de cours / nombre d'heures de prof dispo	--> Field
				#3	Profs : CWWH / MWWH											--> Teacher
				#4	Élèves : sum(Classes.hours) / MWWH							--> Group
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
		HashMap<Field, Time> teachTimeAttributed = new HashMap<Field, Time>();
		
		//Hours needed by students
		for (int i = 0 ; i < this.groups.size() ; i++)	{
			g = this.groups.get(i);
			for (Field f : g.classes.keySet())	{
				
				if (!teachTimeNeeded.containsKey(f))	{
					teachTimeNeeded.put(f, new Time());
					teachTimeAttributed.put(f,  new Time());
				}
				
				Time t = teachTimeNeeded.get(f).add(g.classes.get(f));
				teachTimeNeeded.put(f, t);
			}
		}
		/*
		for (Field f : teachTimeNeeded.keySet())
			System.out.println("Filler.computeConstraints() #Fields : " + f + " --> " + teachTimeNeeded.get(f));
		//*/
		
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
		
		for (Field f : teachTimeAttributed.keySet())	{
			
			//System.out.println("Filler.computeConstraints() #teachersTime : #0 " + f);
			
			while (teachTimeAttributed.get(f).isLessThan(teachTimeNeeded.get(f)))	{
			
				for (Teacher teach : this.teachers)	{
					
					//System.out.println("  #1 " + teach);
					
					if (teach.knows(f))	{
						
						Time available = teach.getMWWH().substract(teach.getCWWH()); 				//Le temps "libre" du prof
						Time needed = teachTimeNeeded.get(f).substract(teachTimeAttributed.get(f));	//La quantité restante à attribuer.

						//System.out.println("   #2 " + teach.getCWWH() + " " + teach.getMWWH() + " " + available + " #3 " + needed);
						
						Time t;
						
						for (Group group : this.groups)	{
							
							//System.out.println("    #4 " + group);
							
							if (group.classes.containsKey(f))	{
								
								t = group.classes.get(f);
								
								//System.out.println("     #5 " + t);
								
								if (t.isLessThan(available) && t.isLessThan(needed))	{
								
									//System.out.println("      #6");
									
									teachTimeAttributed.put (f, teachTimeAttributed.get(f).add(t));
									
									teach.addToCWWH(t);
									
									available = teach.getMWWH().substract(teach.getCWWH());
								
									//System.out.println("       #7 : " + f + " " + teachTimeAvailable.get(f) + " / " + teachTimeNeeded.get(f) + " #9 " + teach + " #10 " + teach.getCWWH() + " #11 " + available + " #12 " + needed + " #13 " + t);
								}
							}
						}
					}
				}
			}
		}
		
		/*
		for (Field f : teachTimeAttributed.keySet())
			System.out.println("Filler.computeConstraints() #Fields : " + f + " --> " + teachTimeNeeded.get(f) + teachTimeAttributed.get(f));
		//*/
		
		boolean okay = true;
		//System.out.println("Filler.computeConstraints() #Teachers : ");
		
		for (Field f : teachTimeNeeded.keySet())	{
		
			//System.out.println("\t" + f + " --> " + teachTimeNeeded.get(f) + " / " + teachTimeAvailable.get(f));
			
			if (teachTimeNeeded.get(f).isMoreThan(teachTimeAttributed.get(f)))	{
				System.out.println("## /!\\ ## : Filler.computeConstraints() says : Not enough teachers ! ");
				System.out.println(f + " --> time needed : " + teachTimeNeeded.get(f) + " ; time available : " + teachTimeAttributed.get(f));
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
			On a des HashMaps roomTimeNeeded/Available, teachTimeNeeded/Spare contenant les temps considérés
			On affiche des message d'erreur dans la console en cas de problème (<=> contrainte supérieure à 1) --> Possibilité de popup des avertissements plus tard.
			
			Il nous faut définir des contraintes pour chaque champ, en l'occurence timeNeeded.divideBy(timeAvailable)	//Division entre Times divise l'équivalent en minutes de chaque, puis renvoie un double.
			Puis retourner un tableau de champs ordonnés par contrainte.
		*/
		
		HashMap<Constrainable, Double> roomConstraints = new HashMap<Constrainable, Double>();
		HashMap<Constrainable, Double> teachingConstraints = new HashMap<Constrainable, Double>();
		
		for (ClassType type : roomTimeNeeded.keySet())	{
			
			//System.out.println("Filler.computeConstraints() #Rooms : " + type + " --> " + roomTimeNeeded.get(type) + " / " + roomTimeAvailable.get(type) + " = " + roomTimeNeeded.get(type).divideBy(roomTimeAvailable.get(type)));
			
			roomConstraints.put(type, roomTimeNeeded.get(type).divideBy(roomTimeAvailable.get(type)));
		//			constraint =			Needed				  /			Available
		}
		
		for (Field field : teachTimeNeeded.keySet())	{
			
			//System.out.println("Filler.computeConstraints() #Fields : " + field + " --> " + teachTimeNeeded.get(field) + " / " + teachTimeSpare.get(field).add(teachTimeAttributed.get(field)) + " = " + teachTimeNeeded.get(field).divideBy(teachTimeAttributed.get(field).add(teachTimeSpare.get(field))));
			teachingConstraints.put(field, teachTimeNeeded.get(field).divideBy(teachTimeAttributed.get(field).add(teachTimeSpare.get(field))));
		//			constraint	=				Needed					/		(Available					+		spare)
		}
		//System.out.println("Filler.computeConstraints() #orderByValues : " + roomConstraints.size() + " " + teacherConstraints.size());
		
		

		HashMap<Constrainable, Double> groupConstraints = new HashMap<Constrainable, Double>();
		
		for (Group group : this.groups)	{
			Time t = new Time();
			for (Field f : group.classes.keySet())
				t = t.add(group.classes.get(f));
			groupConstraints.put(group, t.divideBy(this.MWWH));
			//System.out.println("Filler.computeConstraints() #Groups : " + group + " --> " + t + " / " + this.MWWH + " = " + t.divideBy(this.MWWH));
		}
		
		HashMap<Constrainable, Double> teachersConstraints = new HashMap<Constrainable, Double>();
		
		for (Teacher teach : this.teachers)	{
			//System.out.println("Filler.computeConstraints() #Teachers : " + teach + " --> " + teach.getCWWH() + " / " + teach.getMWWH() + " = " + teach.getCWWH().divideBy(teach.getMWWH()));
			teachersConstraints.put(teach, teach.getCWWH().divideBy(teach.getMWWH()));
		}

	/*
	 * En ce point du code, on dispose des contraintes sur les Field (ainsi que ClassType, Teacher et Group), on peut donc attribuer les Teachers aux Groups
	 * Ce qui permet par la suite de calculer réellement les contraintes des profs, et non pas sur la base des horaires
	 * estimés lors du calcul de contraintes.
	 * Ce choix est déterminé par la boolean attributeTeachers en paramètre.
	 */

		if (attributeTeachers)	{
			
			//On réinitialise les horaires des profs (simulés pour le calcul de contrainte).
			for (Teacher teach : cwwhs.keySet())
				teach.setCWWH(cwwhs.get(teach));
			
			//La liste temp contiendra les field triés par ordre de contrainte.
			ArrayList<Field> temp = new ArrayList<Field>();
			
			//On insère un élément pour lancer la boucle.
			temp.add((Field) teachingConstraints.keySet().toArray()[teachingConstraints.size() - 1]);
			
			for (Constrainable cons : teachingConstraints.keySet()){
				Field f1 = (Field) cons;
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
				//On enlève l'élément inséré pour l'initialisation
				if (f1 == teachingConstraints.keySet().toArray()[0])	{
					temp.remove(teachingConstraints.keySet().toArray()[teachingConstraints.size() - 1]);
				}
			}
			
			if (!this.attributeTeachers(temp))
				System.out.println("\n\n\t\t##########\n\n\tFiller.attributeTeachers() failed !\n\n\t\t##########\n\n");
			else
				System.out.println("Filler.computeConstraints() says : Teachers attributed.");
			
			//Maintenant que les profs sont attribués, on peut calculer leur contrainte réelle !
			for (Teacher teach : this.teachers)	{
				teachersConstraints.put(teach, teach.getCWWH().divideBy(teach.getMWWH()));
				//System.out.println("Filler.computeConstraints() #Teachers2 : " + teach + " --> " + teach.getCWWH() + " / " + teach.getMWWH() + " = " + teach.getCWWH().divideBy(teach.getMWWH()));
			}
			
		}
		
		//On réinitialise les horaires des profs.
		if (!attributeTeachers)
			for (Teacher teach : cwwhs.keySet())
				teach.setCWWH(cwwhs.get(teach));
		
		
		ArrayList<HashMap<Constrainable, Double>> data = new ArrayList<HashMap<Constrainable, Double>>();
		
		data.add(roomConstraints);
		data.add(teachersConstraints);
		data.add(teachingConstraints);
		data.add(groupConstraints);
		
		Constrainable[] res = this.orderByValues(data);
		
		return res;
	}
	
	private Constrainable[] orderByValues(ArrayList<HashMap<Constrainable, Double>> data){
		
		//System.out.print("Filler.orderByValues(ArrayList<HashMap<Constrainable, Double>>) : ");
		
		ArrayList<Constrainable> temp = new ArrayList<Constrainable>();
		HashMap<Constrainable, Double> constraints = new HashMap<Constrainable, Double>();
		
	//On insère un premier élément pour lancer la boucle.
		temp.add((Constrainable) data.get(data.size() - 1).keySet().toArray()[data.get(data.size() - 1).size() - 1]);
		
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
				temp.remove(data.get(data.size() - 1).keySet().toArray()[data.get(data.size() - 1).size() - 1]);
			}
		}
		
		Constrainable[] res = new Constrainable[temp.size()];
		
		for (int i = 0 ; i < temp.size(); i++)
			res[i] = temp.get(i);
		
		/*
		for (int i = 0 ; i < res.length ; i++)	
			if (res[i] instanceof ClassType)
				System.out.println("Filler.orderByValues(ArrayList<HashMap<Constrainable, Double>>) : " + res[i] + "\t\t--> " + constraints.get(res[i]));
		//*/
		
		//*
		//Boucle d'affichage uniquement, peut être passée sous commentaire
		for (Constrainable c : res)	{
			
			double con = -1;
			
			for (HashMap<Constrainable, Double> hm : data)
				if (hm.get(c) != null)	{
					con = hm.get(c);
					break;
				}
			
			//System.out.println("Filler.orderByValues() #res : " + c + " \t " + con);
		}
		//*/
		return res;
	}

	private boolean takeCareOf(Field f, ArrayList<Group> order)	{
		
		return true;
	}

	private boolean takeCareOf(ClassType ct, ArrayList<Group> order)	{
		
		return true;
	}
	
	private boolean takeCareOf(Teacher teach, ArrayList<Field> fieldsOrder, ArrayList<Group> groupsOrder)	{
		
		System.out.println("\n\n\tFiller.takeCareOf (  " + teach + "  )\n\n");
		
		ArrayList<Field> fields = new ArrayList<Field>();
		
		for (Field f : teach.getFields())
			fields.add(f);
		
		System.out.println("#0 : " + fields.size());
		
		for (Field f : fieldsOrder)	{
			if (fields.contains(f))	{
				
				System.out.println(" #1 : " + f);
				
				for (Group g : groupsOrder)	{
					
					System.out.println(" #1.5 : " + g + " " + teach.getLinks().getLinks(g).size() + " " + this.fieldsDone.get(g).get(f));
					
					if (teach.getLinks().getLinks(g).size() != 0 && !(this.fieldsDone.get(g).get(f)))	{

						System.out.println("  #2 : " + g);
						/*
						 * On a ici :
						 * Teacher teach
						 * Field f
						 * Group g
						 * tous liés et sélectionnés par contrainte.
						 * */
						Time left = g.getClasses().get(f);
						
						for (Lesson l : g.getWeekTable().getSlotsConcerning(f)) 
							left = left.substract(l.getDuration());

						System.out.println("  #2.5 : " + left);
						
						while(left.isMoreThan(new Time()))	{
							
							Time duration = new Time();
							
							if (left.isntLessThan(f.getDuration().getBegin().add(f.getDuration().getEnd())))
								duration = f.getDuration().getEnd();
							
							else if (left.isntLessThan(f.getDuration().getBegin()) && left.isntMoreThan(f.getDuration().getEnd()))
								duration = left;
							
							else	{
								System.out.println("\n\n\t/!\\ ### Filler.takeCareOf(Group) failed !\n\n");
							}
							
							System.out.println("   #3 : " + duration);
							
							ArrayList<Slot> teachersSlots = teach.getAllFreeSlots(duration);
							ArrayList<Slot> groupsSlots= g.getAllFreeSlots(duration);
							ArrayList<Slot> disp = new ArrayList<Slot>();
							
							System.out.println("   #3.5 : " + teachersSlots.size() + " " + groupsSlots.size());
							
							for (Slot s1 : teachersSlots)
								for (Slot s2 : groupsSlots)
									if (s1.intersects(s2) && s1.intersection(s2).getDuration().isntLessThan(duration))
										disp.add(s1.intersection(s2));
							
							System.out.println("   #3.75 : " + disp.size());
							
							//On cherche tous les slots de la durée requise inclus dans les slots disponibles à la fois pour le group et le teacher
							for (int i = 0 ; i < disp.size() ; i++)	{
								
								Slot s = disp.get(i);
								disp.remove(s);
								
								Time begin = s.getBegin();
								Time end = s.getBegin().add(duration);
								
								disp.add(i, new Slot(begin, end));
								i++;
								
								while(end.isntMoreThan(s.getEnd()))	{
									begin = begin.add(WeekTable.getMinDelta());
									end = end.add(WeekTable.getMinDelta());
									if (end.isntMoreThan(s.getEnd()))	{
										disp.add(i, new Slot(begin, end));
										i++;
									}
								}
							}
							
							System.out.println("    #4 : " + disp.size());
							
							/*
							 * En ce point, on a :
							 * Teacher, Group, Field
							 * disp : ArrayList de Slots disponibles de la durée requise
							 * Il manque encore :
							 * La salle.
							 * La Lesson.
							 * */
							
	
							Classroom place = null;
							Slot slot = null;
							
							for (Slot s : disp)	{
								
								place = this.findClassRoom(f.getType(), s);
								if (place != null)	{
									slot = s;
									System.out.println("     #5 : " + slot + " (slot retained) " + place + " (Classroom)");
									break;
								}
							}
							
							if (slot == null || place == null)	{
								System.out.println("\n\n\t/!\\ ### Filler.takeCareOf(Teacher) could not find a room for : " + teach + " " + g + " " + f + " " + place + "\n\n");
								break;
							}
							
							Lesson res = new Lesson(teach, g, f, place, slot);
							
							boolean failed = false;
	
							failed = teach.addLesson(res) ? failed : true;
							failed = place.addLesson(res) ? failed : true;
							failed = g.addLesson(res) ? failed : true;
							
							if (failed)
								System.out.println("\n\n\n/!\\ ### Filler.takeCareOf(Teacher) could set Lesson for : " + res);
							
							else	{
								left = left.substract(duration);
								System.out.println("      #6 : res = " + res + "\n\n\tleft = " + left);
							}
						}
					}
					
					this.fieldsDone.get(g).put(f, true);
				}
			}
		}
		return true;
	}
	
	private boolean takeCareOf(Group g, ArrayList<Field> order)	{
		
		//System.out.println("\n\n\t\t########\n\n");
		
		//System.out.println("Filler.takeCareOf(Group) #0 : " + g);
		
		HashMap<Field, Time> timesLeft = new HashMap<Field, Time>();
		
		for (Field f : g.classes.keySet())
			timesLeft.put(f, g.classes.get(f));
		
		//System.out.println("Filler.takeCareOf(Group) #0.5 : Fields to process --> " + timesLeft.size());
		
		boolean lessonSet = true;
		
		for (Field f : order)	{

			//System.out.println("Filler.takeCareOf(Group)  #1 : " + f + " ( " + f.getType().getDuration() + " )");
			
			if (g.classes.containsKey(f) && !this.fieldsDone.get(g).get(f))	{

				//System.out.println("Filler.takeCareOf(Group)  #1.5 : Field was unattributed");
				
				while (timesLeft.get(f).isMoreThan(new Time()))	{

					//System.out.println("Filler.takeCareOf(Group)   #2 : " + timesLeft.get(f) + " (time left)");
					
					Time duration = new Time();
					
					if (timesLeft.get(f).isntLessThan(f.getDuration().getBegin().add(f.getDuration().getEnd())))
						duration = f.getDuration().getEnd();
					
					else if (timesLeft.get(f).isntLessThan(f.getDuration().getBegin()) && timesLeft.get(f).isntMoreThan(f.getDuration().getEnd()))
						duration = timesLeft.get(f);
					
					else	{
						System.out.println("\n\n\t/!\\ ### Filler.takeCareOf(Group) failed !\n\n");
					}
					
					//System.out.println("Filler.takeCareOf(Group)   #3 : " + duration + " (time attributed for this block)");
					
					ArrayList<Slot> sg = g.getAllFreeSlots(duration);									//Les dispos du Group

					//System.out.println("Filler.takeCareOf(Group)   #4 : " + g.getTeacher(f) + " (Teacher)");
					
					ArrayList<Slot> st = g.getTeacher(f).getAllFreeSlots(duration);					//Les dispos du Teacher
					ArrayList<Slot> disp = new ArrayList<Slot>();								//L'intersection des deux;
					
					//System.out.println("Filler.takeCareOf(Group)    #5 : " + sg.size() + " " + st.size());
					
					for (Slot s1 : sg)	{
						for (Slot s2 : st)	{
							//System.out.println("Filler.takeCareOf(Group)     #6 : " + s1 + " " + s2);
							if (s1.intersects(s2) && s1.intersection(s2).getDuration().isntLessThan(duration))	{
								disp.add(s1.intersection(s2));
								//System.out.println("Filler.takeCareOf(Group)      #7 : " + s1 + " is available for both " + g + " and " + g.getTeacher(f));
								break;
							}
						}
					}
					
					//System.out.println("Filler.takeCareOf(Group)      #7.5 : " + disp.size());
					
					//On cherche tous les slots de la durée requise inclus dans les slots disponibles à la fois pour le group et le teacher
					for (int i = 0 ; i < disp.size() ; i++)	{
						
						Slot s = disp.get(i);
						disp.remove(s);
						
						Time begin = s.getBegin();
						Time end = s.getBegin().add(duration);
						
						disp.add(i, new Slot(begin, end));
						i++;
						
						while(end.isntMoreThan(s.getEnd()))	{
							begin = begin.add(WeekTable.getMinDelta());
							end = end.add(WeekTable.getMinDelta());
							if (end.isntMoreThan(s.getEnd()))	{
								disp.add(i, new Slot(begin, end));
								i++;
							}
						}
					}
					
					//System.out.println("Filler.takeCareOf(Group)    #8 : " + disp.size()/* + "\n" + disp*/);
					
					/*
					 * En ce point, on a :
					 * Le Group (en paramètre)
					 * le Field (par la première boucle)
					 * le Teacher (par le Group)
					 * les Slot disponibles (s)
					 * 
					 * Il faut :
					 * trouver une Classroom
					 * créer la Lesson
					 * l'insérer dans les WeekTable des concernés.
					 * */
					
					Classroom place = null;
					Slot slot = null;
					
					for (Slot s : disp)	{
						//On vérifie que le group n'a pas déjà eu le Field le jour même (ni la veille ou le lendemain)
						if (!(g.getWeekTable().fieldHappensInDay(f, s.getBegin().getDay())) && !(g.getWeekTable().fieldHappensInDay(f, (byte)(s.getBegin().getDay() + 1)))  && !(g.getWeekTable().fieldHappensInDay(f, (byte)(s.getBegin().getDay() - 1))) )	{
							place = this.findClassRoom(f.getType(), s);
							if (place != null)	{
								slot = s;
								//System.out.println("Filler.takeCareOf(Group)       #8 : " + slot + " (slot retained) " + place + " (Classroom)");
								break;
							}
						}
					}
					
					//System.out.println("Filler.takeCareOf(Group)     #9 : Slot choosen = " + slot);
					
					if (slot == null)
						break;
					
					Lesson res = new Lesson(g.getLinks().getLinks(f).get(0).getTeacher(), g, f, place, new Slot(slot.getBegin(), slot.getBegin().add(duration)));
					
					//System.out.println("Filler.takeCareOf(Group)        #9 : res =\n" + res);
					
	// /!\ : Vérifier ici que le lessonSet finisse bien à true.
					lessonSet = g.addLesson(res) ? true : false;
					lessonSet = g.getTeacher(f).addLesson(res) ? lessonSet : false;
					lessonSet = place.addLesson(res) ? lessonSet : false;
					
					if (lessonSet)
						timesLeft.put(f, (timesLeft.get(f).substract(res.getDuration())));
					else
						System.out.println("/!\\ Filler.takeCareOf(Group) could not aggree on a slot for " + res);
					
					//System.out.println("\n\tPONEY ! " + lessonSet + " " + timesLeft.get(f) + " " + res.getDuration() + " " + timesLeft.get(f).substract(res.getDuration()));
				}
				
				this.fieldsDone.get(g).put(f, true);
			}
		}
		
		return true;
	}

	private Classroom findClassRoom(ClassType type, Slot s) {
		
		//System.out.print("Filler.findClassRoom (" + type.getShortName() + ", " + s + ") --> ");
		
		for (Classroom c : this.classrooms)
			if (c.getType().equals(type))
				if (s.canFitIn(c.getAllFreeSlots(s.getDuration())))	{		//On vérifie ici que la salle est libre pendant le slot s
					//System.out.println(c);
					return c;
				}
		//System.out.println("NULL");
		return null;
	}
	
}