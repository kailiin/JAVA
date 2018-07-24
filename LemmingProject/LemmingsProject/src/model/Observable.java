package model;

import java.util.ArrayList;
import java.util.List;

import controller.GameObjectObserver;

public abstract class Observable {
	
	private ArrayList<Change> changes = new ArrayList<>();

	private ArrayList<GameObjectObserver> observers = new ArrayList<>();	
	
	protected void addChange(Change c) {
		changes.add(c);
	}
	
	public void registerObserver(GameObjectObserver observer) {
		observers.add(observer);
	}
	
	public void unregisterObserver(GameObjectObserver observer) {
		observers.remove(observer);
	}	

	public void notifyObserver() {
		List<Change> ch= getChanges();
		for(GameObjectObserver obs : observers){
			obs.update(ch);
		}
	}
	
	private List<Change> getChanges() {
		List<Change> result = new ArrayList<>(changes);
		changes.clear();
		return result;
	}
}
