package raceControl.runRace;

import java.util.List;


import raceControl.entity.Car;
import raceControl.entity.DistanceComparator;
import raceControl.entity.EliminatoryRace;

public class RunEliminatoryRace extends RunStandardRace {

	public RunEliminatoryRace(EliminatoryRace currentRace) {
		super(currentRace);
	}
	
	public void startRace(int raceTime, int heatingTime){
		setParticipants(currentRace.getGarages());
		List<Car> participantsRemoveList = currentRace.getParticipants();
		for(int i=0; i<=heatingTime; i++) {
			System.out.println("Vuelta calentamiento : "+i);
			for(Car c : participantsRemoveList) {
				c.gasOrBreak();
			}
		}
		setDistanceToZero(participantsRemoveList);
		for(int i=0; i<=raceTime; i++) {
			for(Car c : participantsRemoveList) {
				c.gasOrBreak();
			}
			System.out.println("------------------------------------------------------------------------------------------------------------");
			System.out.println("Vuelta carrera : "+i+". Posiciones");
			int j =1;
			for(Car car :participantsRemoveList) {
				System.out.println(j+" "+car.toString()+" Distancia : "+car.getDistance());
				j++;
			}
			if(participantsRemoveList.size()>3 &&i>(raceTime-participantsRemoveList.size()))eliminateCar(participantsRemoveList);
		}
		Car[] podium=getPodium(participantsRemoveList);
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
	
	protected void eliminateCar(List<Car> participants) {
		DistanceComparator sortByDistance = new DistanceComparator();
		participants.sort(sortByDistance);
		System.out.println("**************************************************************************************************************");
		System.out.println("Eliminado : "+participants.get(participants.size()-1).toString()+" "
				+ "Distancia recorrida : "+participants.get(participants.size()-1).getDistance());
		System.out.println("***************************************************************************************************************");
		participants.remove(participants.size()-1);
		System.out.println("Tamaño de la lista : "+participants.size());
	}
	private void setDistanceToZero(List<Car> participants) {
		for(Car c : participants) {
			c.setDistance(0);
		}
	}
}
