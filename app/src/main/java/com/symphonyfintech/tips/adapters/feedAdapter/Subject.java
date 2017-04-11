package com.symphonyfintech.tips.adapters.feedAdapter;

public interface Subject {

	void registerObserver(Observer observer);

	void notifyObserver();

	void unRegisterObserver(Observer observer);

	Object getUpdate();

}
