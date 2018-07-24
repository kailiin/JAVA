package controller;

import java.util.List;

import model.Change;

public interface GameObjectObserver {
	public void update(List<? extends Change> o);
}
