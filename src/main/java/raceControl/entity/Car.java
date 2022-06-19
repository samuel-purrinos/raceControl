package raceControl.entity;

import java.io.Serializable;
import java.util.Random;
/**
 * Esta clase representa un coche, con una marca, modelo y garaje que lo identifica, la habilidad que tendrá su piloto, su velocidad máxima, 
 * los puntos consegidos en cada carrera y su velocidad en el momento de disputar una carrera
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */

public class Car implements Serializable, Runnable{
	/**
	 * @since 2022/6/2
	 * Sets up the maximum speed of each car
	 */
	private int maxSpeed = 240;
	private String brand;
	private String model;
	private String garage; 
	private double distance;
	private int speed;
	private int pilotSkill;
	private int points;
	private int raceTime;
	
	
	
	public Car() {
	}

	public Car(String marca, String modelo,int pilotSkill) {
		this.brand = marca;
		this.model = modelo;
		this.pilotSkill=pilotSkill;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getGarage() {
		return garage;
	}

	public void setGarage(String garage) {
		this.garage = garage;
	}

	public double getDistance() {
		return distance;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPilotSkill() {
		return pilotSkill;
	}

	public void setPilotSkill(int pilotSkill) {
		this.pilotSkill = pilotSkill;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	
	public void setRaceTime(int raceTime) {
		this.raceTime = raceTime;
	}

	public int getRaceTime() {
		return raceTime;
	}

	@Override
	public String toString() {
		return "Garaje :"+garage+". Marca : "+brand+". Modelo : "+model;
	}
	
	/**
	 * @since 2022/6/2
	 * @param car
	 * this method decides if a car breaks o accelerates based on the pilot skills and in a 
	 * random number
	 */
	public void gasOrBreak() {
		Random random = new Random();
		int action = random.nextInt(100);
		if(action<getPilotSkill()) {speedUp();
		}else {reduce();}
	}
	
	
	/**
	 * @since 2022/6/2
	 * @param car
	 * this method increases the speed of a given car and the distance traveled by the car
	 * based on the current velocity
	 */
	protected void speedUp() {
		if(getSpeed()<=getMaxSpeed()) {
			setSpeed(getSpeed()+10);
			setDistance(getDistance()+(0.5*getSpeed()));
		}
	}
	

	/**
	 * @since 2022/6/2
	 * @param car
	 * this method reduces the speed of a given car and increases the distance traveled by the car
	 * based on the current velocity
	 */
	protected void reduce() {
		if(getSpeed()==0) { return;
		}else {
			setSpeed(getSpeed()-10);
			setDistance(getDistance()+(0.5*getSpeed()));
		}
	}

	public void run() {
		for(int i=0;i<raceTime;i++) {
		gasOrBreak();
		}
	}
	
}
