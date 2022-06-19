package raceControl.runRace;

import java.util.*;
import raceControl.entity.*;

/**
 * @author Samuel Purriños
 * @version 1.0
 * @since 2022/6/2
 * This class sets up and controls the races created in the main program, and establishes the
 * podium with the three best cars in each race
 * @param car
 */
public class RunStandardRace {
	/**
	 * @since 2022/6/2
	 * This variable contains all the elements needed to run a race
	 */
	protected StandardRace currentRace;
	protected Random random = new Random();
	/**
	 * @since 2022/6/2
	 * Points assigned for each position of the podium
	 */
	protected final int WINNER=25;
	protected final int SECOND=18;
	protected final int THIRD=15;
	
	/**
	 * @since 2022/6/3
	 * @param currentRace
	 * 
	 */
	public RunStandardRace(StandardRace currentRace) {
		super();
		this.currentRace = currentRace;
	}

	/**
	 * @since 2022/6/2
	 * @param participants
	 * @param raceTime
	 * @return a list with the finish positions of the cars after the race
	 * this method is the main brain of the race control. Breaks or accelerate the cars in a time
	 * previously determined and returns a the podium of the race
	 */
	public void startRace(int raceTime){
		setParticipants(currentRace.getGarages());
		
		for(int i=0; i<=raceTime; i++) {
			System.out.println("------------------------------------------------------------------------------------------------------------");
			System.out.println("Vuelta carrera : "+i+". Posiciones");
			System.out.println();
			for(Car c : currentRace.getParticipants()) {
				c.gasOrBreak();
			}
			int j = 1;
			for(Car car : getPositions(currentRace.getParticipants())) {
				System.out.println(j+" "+car.toString()+". Distancia :"+car.getDistance());
				j++;
			}
		}
		Car[] podium=getPodium(currentRace.getParticipants());
		setCarPoints(podium);
		System.out.println("Resultados de la carrera : ");
		System.out.println("---------------------------------------------------------------------");
		int position=1;
		for(Car c : podium){
			System.out.println(position+". Garaje : "+c.getGarage()+". "+c.getBrand()+" "+c.getModel()+". Distancia recorrida : "+c.getDistance()
			+". Puntos acumulados : "+c.getPoints());
			position++;
		}
		setDistanceAndSpeedToZero(currentRace.getParticipants());
		currentRace.setPodium(podium);
	}
	
	/**
	 * @since 2022/6/2
	 * @param cars
	 * This method sets the cars which will compete in the race
	 */
	protected void setParticipants(List<Garage> garages){
		System.out.println("Tamaño participantes :"+currentRace.getParticipants().size());
		if(garages.size()==1) { currentRace.getParticipants().addAll(garages.get(0).getCars());
		}else {
		for(Garage g : garages) {
			
			
				int randomPosition = random.nextInt(g.getCars().size());
				currentRace.getParticipants().add(g.getCars().get(randomPosition));
		}
			
	}
	}
	
	protected Car[] getPodium(List<Car> participants) {
		Car[] podium = new Car[3];
		DistanceComparator sortByDistance = new DistanceComparator();
		participants.sort(sortByDistance);
		for(int i=0;i<=2;i++) {
			if(participants.size()==1) {
				podium[0]=participants.get(0);
				return podium;
			}
				if(participants.size()==2) {
					podium[0]=participants.get(0);
					podium[1]=participants.get(1);
					return podium;
				}else {
					podium[0]=participants.get(0);
					podium[1]=participants.get(1);
					podium[2]=participants.get(2);
	
					}
		}
		return podium;
	}
	
	protected List<Car> getPositions(List<Car> participants) {
		List<Car> positions = participants;
		Car[] podium = new Car[3];
		DistanceComparator sortByDistance = new DistanceComparator();
		positions.sort(sortByDistance);
		return positions;
	}
	
	protected void setDistanceAndSpeedToZero(List<Car> participants) {
		for(Car c : participants) {
			c.setDistance(0);
			c.setSpeed(0);
		}
	}
	
	protected void setCarPoints(Car[] podium) {
		if(podium[0]!=null)podium[0].setPoints(podium[0].getPoints()+WINNER);
		if(podium[1]!=null)podium[1].setPoints(podium[1].getPoints()+SECOND);
		if(podium[2]!=null)podium[2].setPoints(podium[2].getPoints()+THIRD);
		
	}
}
