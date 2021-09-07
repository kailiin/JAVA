package avaj;

import java.util.*;

public class Tower {
	private List<Flyable> observers = new ArrayList<Flyable>() ;
	
	public void register(Flyable flyable) {
		observers.add(flyable);
	}
	
	public void unregister(Flyable flyable) {
		observers.remove(flyable);
	}

	protected void conditionsChanged() {
		int obsSize;
		for (int i = 0; i < observers.size(); i ++) {
			obsSize = observers.size();
			observers.get(i).updateConditions();
			if (obsSize > observers.size())
				i--;
		}
	}
}
