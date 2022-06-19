package raceControl.entity;

import java.io.Serializable;

import java.util.*;
/**
 * Esta clase representa una carrera estándar, identificada por su nombre, con el tiempo que durará la carrera, una serie de garajes y participantes
 * que son los que competirán en la carrera y con el podio resultante al finalizar la carrera
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class StandardRace implements Serializable{

	protected String name;
	protected List<Garage> garages;
	protected Car[] podium;
	protected List<Car> participants;
	protected int raceTime;
	
	public StandardRace() {
	}

	public StandardRace(String name,int raceTime) {
		this.name = name;
		garages=new ArrayList<Garage>();
		podium=new Car[2];
		participants=new ArrayList<Car>();
		this.raceTime=raceTime;
	}
	
	public String getName() {
		return name;
	}
	public List<Garage> getGarages() {
		return garages;
	}
	public Car[] getPodium() {
		return podium;
	}
	public List<Car> getParticipants() {
		return participants;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGarages(List<Garage> garages) {
		this.garages = garages;
	}

	public void setPodium(Car[] podium) {
		this.podium = podium;
	}

	public void setParticipants(List<Car> participants) {
		this.participants = participants;
	}

	public int getRaceTime() {
		return raceTime;
	}

	public void setRaceTime(int raceTime) {
		this.raceTime = raceTime;
	}
	
	
}
