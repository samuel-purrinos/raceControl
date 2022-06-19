package raceControl.entity;

import java.io.Serializable;

import java.util.*;
/**
 * Esta clase representa un torneo identificado por su nombre y formada por las carreras de las que se compone el torneo. Contiene los garajes
 * que participarán en el torneo y una lista con los podios de las diferentes carreras
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class Tournament implements Serializable{
	private String name;
	private List<StandardRace> standardRaces;
	private List<EliminatoryRace> eliminatoryRaces;
	private List<Garage> garages;
	private List<Car[]> podiums;
	
	
	public Tournament() {
	}

	public Tournament(String name) {
		this.name = name;
		standardRaces=new ArrayList<StandardRace>();
		garages=new ArrayList<Garage>();
		eliminatoryRaces = new ArrayList<EliminatoryRace>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<StandardRace> getStandardRaces() {
		return standardRaces;
	}

	public List<Garage> getGarages() {
		return garages;
	}

	public void setGarages(List<Garage> garages) {
		this.garages = garages;
	}

	public void setStandardRaces(List<StandardRace> standardRaces) {
		this.standardRaces = standardRaces;
	}

	public List<EliminatoryRace> getEliminatoryRaces() {
		return eliminatoryRaces;
	}

	public void setEliminatoryRaces(List<EliminatoryRace> eliminatoryRaces) {
		this.eliminatoryRaces = eliminatoryRaces;
	}
	
	
	
	
	
}
