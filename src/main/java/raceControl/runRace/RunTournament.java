package raceControl.runRace;

import java.util.*;

import raceControl.entity.*;

public class RunTournament {
	private Tournament currentTournament;
	private List<Car> tournamentPartipants;

	public RunTournament(Tournament currentTournament) {
		this.currentTournament = currentTournament;
		tournamentPartipants = new ArrayList<Car>();
	}
	
	public void startTournament() {
		runStandardRaces();
		runEliminatoryRaces();
		getTournamentPodium();
		
	}
	private void runStandardRaces() {
		if(currentTournament.getStandardRaces().size()>=1) {
			for(StandardRace s : currentTournament.getStandardRaces()) {
				RunStandardRace newStandardRace = new RunStandardRace(s);
				newStandardRace.startRace(s.getRaceTime());
				tournamentPartipants.addAll(s.getParticipants());
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void runEliminatoryRaces() {
		if(currentTournament.getEliminatoryRaces().size()>=1) {
			for(EliminatoryRace e : currentTournament.getEliminatoryRaces()) {
				RunEliminatoryRace newEliminatoryRace = new RunEliminatoryRace(e);
				newEliminatoryRace.startRace(e.getRaceTime(),e.getHeatTime());
				tournamentPartipants.addAll(e.getParticipants());
			}
		}
	}
	
	private void getTournamentPodium() {
		PointsComparator comparator = new PointsComparator();
		tournamentPartipants.sort(comparator);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("El podio del torneo es : ");
		for(int i=0;i<=2;i++) {
			System.out.println(i+1+" "+tournamentPartipants.get(i).toString()+" con "+tournamentPartipants.get(i).getPoints()+" puntos.");
		}
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
	
}
