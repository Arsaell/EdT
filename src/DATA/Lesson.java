package DATA;



public class Lesson	{
	
	private char type;
	private Teacher teacher;
	private Group students;
	private Field field;
	private Classroom place;
	private Slot slot;
	
	public Lesson (char aType, Teacher aTeacher, Group aGroup, Field aField, Classroom aPlace, Slot aSlot)	{
	
		this.type = aType;
		this.teacher = aTeacher;
		this.students = aGroup;
		this.field = aField;
		this.place = aPlace;
		this.slot = aSlot;
	}
	
	private boolean check()	{
	
		boolean res = true;
		
		if (this.place.getType() != this.type)	{
		
			if (res)
				System.out.println("Lesson.check() : incoherent data ");
			System.out.println("Place type doesn't match lesson type.");
			res = false;
		}
		
		if (!this.teacher.canTeach(this.field, this.students))	{
		
			if (res)
				System.out.println("Lesson.check() : incoherent data ");
			System.out.println("Teacher " + this.teacher + " can't teach " + this.field);
			res = false;
		}
		
		if (this.students.getEffectif() > this.place.getEffectif())	{
		
			if (res)
				System.out.println("Lesson.check() : incoherent data ");
			System.out.println("Classroom " + this.place + " is too small for group " + this.students);
			res = false;
		}
		
		/*
		Vérifier aussi :
			la disponibilité de la salle
			si les étudiants étudient bien cte matière.
		*/
		
		return res;
	}
	
	public Slot getSlot()	{
		return this.slot;
	}
}
