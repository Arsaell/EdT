package DATA;

public class FillerError{

	private Constrainable source;
	int start;
	
	public FillerError(Constrainable c, int index)	{
		this.source = c;
		this.start = index;
	}

	public int getStart() {
		return start;
	}

	public Constrainable getSource() {
		return source;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setSource(Constrainable source) {
		this.source = source;
	}
	
	public String toString()	{
		return "FillerError : [" + start + "] " + source;
	}
}
