package raceControl.entity;

import java.io.Serializable;

import java.util.*;
/**
 * Esta clase representa un garaje identificado por su nombre, contiene una serie de coches que pertenecen al mismo garaje
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class Garage implements Serializable{
	private String name;
	private List<Car> cars;
	
	public Garage() {
	}

	public Garage(String name) {
		this.name=name;
		cars=new ArrayList<Car>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
	
}
