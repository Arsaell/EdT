public abstract class People	{

	protected char ID;
	protected String email;

	public abstract String getMail();
	
	public char getID()	{
		return this.ID;
	}

    public String getEmail() {
        return this.email;
    }

    public People setEmail(String email) {
        this.email = email;
        return this;
    }
}
