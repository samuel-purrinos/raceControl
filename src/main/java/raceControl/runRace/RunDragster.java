package raceControl.runRace;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import raceControl.entity.Car;
import raceControl.entity.StandardRace;

public class RunDragster extends RunStandardRace {

	public RunDragster(StandardRace currentRace) {
		super(currentRace);
	}
	
	@Override
	public void startRace(int raceTime){
		setParticipants(currentRace.getGarages());
		ExecutorService race = Executors.newFixedThreadPool(currentRace.getParticipants().size());
		for(Car c : currentRace.getParticipants()) {
			c.setRaceTime(raceTime);
			race.submit(c);
		}
		race.shutdown();
		
		
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
}
