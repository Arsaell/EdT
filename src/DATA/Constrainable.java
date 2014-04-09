
import java.util.HashMap;
import java.util.List;

public interface Constrainable	{

	//public HashMap<Timeable, Double> constraints = new HashMap<Timeable, Double>();
	
	public HashMap getConstraint();
	public HashMap getConstraint(List source);
	
	//private void updateConstraint();
}
