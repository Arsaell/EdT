import GUI.*;
import DATA.*;

public class Main {

	public static void main(String[] args)	{
		DataStore ds = new DataStore();
		ds.addFixtures();
		new WelcomeFrame();
		new GroupWindow();
	}
}
