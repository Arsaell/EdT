package DATA;

import java.util.ArrayList;

public class LinksList extends ArrayList<Link> {
	
	public LinksList()	{
		super();
	}

	public LinksList getLinks(Group g)	{
		
		LinksList ll = new LinksList();
		
		for (Link l : this)
			if (l.getGroup() == g)
				ll.add(l);
		
		return ll;
	}

	public LinksList getLinks(Field f)	{
		
		LinksList ll = new LinksList();
		
		for (Link l : this)
			if (l.getField() == f)
				ll.add(l);
		
		return ll;
	}

	public LinksList getLinks(Teacher t)	{
		
		LinksList ll = new LinksList();
		
		for (Link l : this)
			if (l.getTeacher() == t)
				ll.add(l);
		
		return ll;
	}
}
