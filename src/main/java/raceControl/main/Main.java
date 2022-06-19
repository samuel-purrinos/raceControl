package raceControl.main;
/**
 * Muestra al usuario un menú que le permite navegar por las distintas opciones que tiene este programa de gestión de pruebas automovilísticas
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
import java.util.Scanner;


import raceControl.control.*;
import raceControl.repository.*;

public class Main {
	
	public static void main(String[] args) {
		GarageRepository garageRepo = new GarageRepository();
		StandardRaceRepository standardRepo = new StandardRaceRepository();
		EliminatoryRaceRepository eliminatoryRepo = new EliminatoryRaceRepository();
		CarRepository carRepo = new CarRepository();
		TournamentRepository tourRepo = new TournamentRepository();
		DragsterRaceRepository dragsterRepo = new DragsterRaceRepository();
		Scanner read = new Scanner(System.in);
		int option;
		String selectedOption="";
		Utils validation = new Utils();
		TournamentMenu tournamentMenu = new TournamentMenu();
		GarageMenu garageMenu = new GarageMenu();
		CarMenu carMenu = new CarMenu();
		RaceMenu raceMenu = new RaceMenu();
		DragsterRaceMenu dragsterMenu = new DragsterRaceMenu();
		boolean exit=false;
		while(!exit) {
		System.out.println("Bienvenido/a a la aplicación para gestionar carreras de Náscar. Qué deseas hacer ?");
		System.out.println("1. Gestionar Torneos");
		System.out.println("2. Gestionar carreras");
		System.out.println("3. Gestionar carreras dragster");
		System.out.println("4. Gestionar garages");
		System.out.println("5. Gestionar coches");
		System.out.println("6. Salir");
		selectedOption=read.nextLine();
		if(validation.checkInt(selectedOption)) {
			option=Integer.parseInt(selectedOption);
			if(option<=0 || option>6) {
				System.out.println("\nDebes seleccionar una opción entre 1 y 6. Por favor, vuelve a intentarlo.\n");
			}
			if(option==1) tournamentMenu.mainMenu();
			if(option==2) raceMenu.mainMenu();
			if(option==3) dragsterMenu.mainMenu();
			if(option==4) garageMenu.mainMenu();
			if(option==5) carMenu.mainMenu();
			if(option==6) {
				read.close();
				exit=true;
			}else System.out.println("Debes seleccionar una opción entre la 1 y la 5. Por favor, vuelve a intentarlo.\n");
		}
		}
		garageRepo.saveData();
		carRepo.saveData();
		tourRepo.saveData();
		standardRepo.saveData();
		eliminatoryRepo.saveData();
		dragsterRepo.saveData();
		
	}

}
