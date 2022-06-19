package raceControl.control;

import java.util.*;

import java.util.Scanner;

import raceControl.entity.*;
import raceControl.repository.*;
import raceControl.runRace.RunDragster;

public class DragsterRaceMenu {
	private Utils validation;
	private Scanner read;
	private DragsterRaceRepository dragsterRepo;
	private DragsterRace newDragsterRace;
	
	
	public DragsterRaceMenu() {
		super();
		this.validation = new Utils();
		this.read = new Scanner(System.in);
		this.dragsterRepo = new DragsterRaceRepository();
	}
	public void mainMenu() {
		int selectedOption=showMainMenu();
		switch (selectedOption) {
		case 1 : {startRace();showMainMenu();break;}
		case 2 : {createNewDragster();showMainMenu();break;}
		case 3 : {printStoredDragsters();showMainMenu();break;}
		case 4 : {break;}
		}
	}
	
	private int showMainMenu() {
		boolean valid=false;
		int option = 0;
		String selectedOption="";
		while(!valid) {
			System.out.println("1. Comenzar una carrera dragster");
			System.out.println("2. Crear una carrera dragster");
			System.out.println("3. Listar todas las carreras dragster");
			System.out.println("4. Volver al menú principal");
			selectedOption=read.nextLine();
			if(validation.checkInt(selectedOption)) {
				option=Integer.parseInt(selectedOption);
				if(option==0||option>4) {System.err.println("Debes escoger una opción entre 1 y 3. Por favor, vuelve a intentarlo...");
				}else { valid=true;}
			}else {System.err.println("Debes escoger una opción entre 1 y 3. Por favor, vuelve a intentarlo...");}
		}
		return option;
		}
	private void startRace() {
		boolean valid = false;
		String selectedIndex="";
		int selectedRace;
		do {
			System.out.println("Escoge una carrera a ejecutar : ");
			printStoredDragsters();
			selectedIndex=read.nextLine();
			if(validation.checkInt(selectedIndex)) {
				selectedRace=Integer.parseInt(selectedIndex);
				if(selectedRace ==0 || selectedRace>dragsterRepo.getDragsterRaceData().size()) {
					System.err.println("Debes escoger una carrera entre la 1 y la "+dragsterRepo.getDragsterRaceData().size()+". Por favor, "
							+ "vuelve a intentarlo");
				}else {
					valid=true;
					RunDragster r = new RunDragster(dragsterRepo.getDragsterRaceData().get(selectedRace-1));
					r.startRace(20);
				}
			}
		}while(!valid);
		
	}
	private void createNewDragster() {
		String raceName=askForRaceName();
		newDragsterRace=new DragsterRace(raceName);
		boolean prueba = addGarages();
		System.out.println("Add garages devuelve : "+prueba);
		if(prueba) {
		dragsterRepo.save(newDragsterRace);
		System.out.println("La carrera dragster con nombre "+raceName+" ha sido creada y guardada correctamente");
		}
		
	}
	
	private String askForRaceName() {
		String name="";
		boolean raceExists;
		do {
			System.out.println("Por favor, teclea el nombre de la nueva carrera : ");
			name=read.nextLine();
			raceExists=(checkIfDragsterRaceExists(name));
			if(raceExists) System.err.println("Ya existe una carrera llamada "+name+". Por favor, vuelve a intentarlo...");
		}while(raceExists);
		return name;
	}
	
	private void printStoredDragsters() {
		int index = 1;
		for(DragsterRace d : dragsterRepo.getDragsterRaceData()) {
			System.out.println(index+" . "+d.getName());
			index++;
		}
		
	}
	
	
	/**
	 * Muestra un menú que permite añadir los garajes que participarán en el torneo, mostrando un mensaje de confirmación cuando la operación
	 * se ha completado correctamente
	 * @param nada
	 * @return nada
	 */
	public boolean addGarages() {
		GarageRepository garageRepository = new GarageRepository();
		int index=0;
		String selectedIndex;
		boolean exit=true;;
		while(exit) {
			int i =1;
			if(garageRepository.getGarageData().size()==0) {
				System.err.println("No hay garajes disponibles. Debes crear algún garaje antes de añadirlo a una carrera");
				return false;
			}
			System.out.println("Selecciona el garaje que quieres añadir al torneo  :");
			for(Garage g : garageRepository.getGarageData()) {
				System.out.println(i+". "+g.getName());i++;
			}
			selectedIndex=read.nextLine();
			if(validation.checkInt(selectedIndex)) {
				index=Integer.parseInt(selectedIndex);
				if(index<0||index>garageRepository.getGarageData().size()) {
					System.err.println("El garaje seleccionado debe ser un número entre 1 y "+garageRepository.getGarageData().size()+". Por favor, vuelve a intentarlo");
				}else {
					newDragsterRace.getGarages().add(garageRepository.getGarageData().get(index-1));
					System.out.println("El garage "+garageRepository.getGarageData().get(index-1).getName()+" ha sido correctamente añadido a<la carrera ");
					garageRepository.getGarageData().remove(index-1);
					exit=showGaragesMessage();
					if(!exit) return true;
					}
				}
	}
		return true;
}
	
	
	/**
	 * Muestra un mensaje al usuario que le permite añadir otro garaje al torneo
	 * @param nada
	 * @return true el caso de que el usuario desee añadir otro gaaraje al torneo y false en caso contrario
	 */
	private boolean showGaragesMessage() {
		boolean exit=false;
		String response;
		boolean addAnotherGarage = false;
		while(!exit) {
			System.out.println("Deseas añadir otro garaje? S/N");
			response=read.nextLine();
			if(response.trim().equals("S") || response.trim().equals("s")) {
				addAnotherGarage=true;
				exit=true;
			}else if(response.trim().equals("N") || response.trim().equals("n")) {
				addAnotherGarage=false;
				exit=true;
			}else {
				System.err.println("Debes teclear s o S para aceptar o N o n para cancelar. Por favor, inténtalo otra vez...");
			}
		}
		return addAnotherGarage;
	}
	
	/**
	 * Comprueba si hay almacenada una carrera de eliminación con ese nombre
	 * @param name el nombre que identifca a la carrera de eliminación
	 * @return true si ya existe una carrera con ese nombre y false en caso contrario
	 */
	private boolean checkIfDragsterRaceExists(String name) {
		for(DragsterRace d : dragsterRepo.getDragsterRaceData()) {
			if(d.getName().trim().toLowerCase().compareTo(name.trim().toLowerCase())==0) {
				return true;
			}
		}
		return false;
	}
}
