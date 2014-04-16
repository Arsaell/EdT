package DATA;

public class Link {

		private Teacher teach;
		private Group group;
		private Field field;
		
		public Link(Teacher aTeach, Group aGroup, Field aField)	{
			this.teach = aTeach;
			this.group = aGroup;
			this.field = aField;
		}

		public Teacher getTeacher() {
			return teach;
		}

		public void setTeacher(Teacher teach) {
			this.teach = teach;
		}

		public Group getGroup() {
			return group;
		}

		public void setGroup(Group group) {
			this.group = group;
		}

		public Field getField() {
			return field;
		}

		public void setField(Field field) {
			this.field = field;
		}

}
