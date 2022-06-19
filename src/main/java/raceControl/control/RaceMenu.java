package raceControl.control;

import java.util.*;

import raceControl.entity.*;
import raceControl.repository.*;
/**
 * Muestra un menú que permite crear carreras tanto estándar como de eliminación y las guarda en sendos archivos json almacenados en el disco duro
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class RaceMenu {
	private Scanner read;
	private StandardRaceRepository standardRaceRepository;
	private EliminatoryRaceRepository eliminatoryRaceRepository;
	private Utils validation;
	private EliminatoryRace eliminatoryRace;
	private StandardRace standardRace;
	
	public RaceMenu() {
		this.read = new Scanner(System.in);
		this.standardRaceRepository = new StandardRaceRepository();
		this.eliminatoryRaceRepository = new EliminatoryRaceRepository();
		this.validation = new Utils();
	}

	/**
	 * Muestra al usuario un menú que le permite seleccionar qué tipo de carrera desea crear, llamando al método que se encarga de pedir los datos
	 * al usuario y crearla
	 * @param nada
	 * @return nada
	 */
	public void mainMenu() {
		int optionSelected=showMainMenu();
		switch (optionSelected) {
			case 1 : {createRace(false);showMainMenu();break;}
			case 2 : {createRace(true);showMainMenu();break;}
			case 3 : {printStoredStandardRaces();showMainMenu();break;}
			case 4 : {printStoredEliminatoryRaces();showMainMenu();break;}
			case 5 : {break;}
			}
	}
	
		private int showMainMenu() {
			boolean exit = false;
			int option = 0;
			String selectedOption="";
			do {
				System.out.println("Qué tipo de carrera deseas hacer?");
				System.out.println("1. Crear carrera estándar");
				System.out.println("2. Crear carrera de eliminación");
				System.out.println("3. Ver las carreras estándar guardadas");
				System.out.println("4. Ver las carreras de eliminación guardadas");
				System.out.println("5. Volver al menú principal");
				selectedOption=read.nextLine();
				if(validation.checkInt(selectedOption)) {
					option=Integer.parseInt(selectedOption);
					if(option<=0||option>5) {
						System.out.println("La opción seleccionada debe ser un número entero entre 1 y 5. Por favor, vuelve a intentarlo\n");
					}else exit=true;
				}
			}while(!exit);
			return option;
		}
			
	/**
	 * Pide a los usuarios los datos necesarios para crear una nueva carrera y la guarda junto a las demás carreras previamente creadas en sendos
	 * archivos json almacenados en el disco duro 
	 * @param eliminatory que define si la carrera que será creada será de eliminación (true) o estándar (false)
	 * @return true si se ha creado correctamente y false en caso contrario
	 */
	private boolean createRace(boolean eliminatory) {
		String name=askForRaceName();
		int raceTime=askForRaceTime();
		if(eliminatory) {
			int heatTime =askForHeatTime();
			eliminatoryRace = new EliminatoryRace(name,raceTime,heatTime);
			eliminatoryRaceRepository.save(eliminatoryRace);
			System.out.println("La carrera de eliminación se ha creado y guardado correctamente.\n");
			return true;
		}
		standardRace= new StandardRace(name,raceTime);
		standardRaceRepository.save(standardRace);
		System.out.println("La carrera estándar se ha creado y guardado correctamente.\n");
		return false;
	}
	
	private String askForRaceName() {
		String raceName="";
		boolean raceExists;
		do {
			System.out.println("Por favor, teclea el nombre de la nueva carrera : ");
			raceName=read.nextLine();
			raceExists=(checkIfStandardRaceExists(raceName) || checkIfEliminatoryRaceExists(raceName));
			if(raceExists) System.err.println("Ya existe una carrera llamada "+raceName+". Por favor, vuelve a intentarlo...");
		}while(raceExists);
		return raceName;
	}
	
	/**
	 * Pide al usuario que teclee el tiempo que durará la carrera que se está creando y lee el dato por teclado comprobando que el dato introducido
	 * sea un número entero mayor que 0, mostrando un mensaje de error en caso contrario
	 * @param nada
	 * @return el tiempo que durará la carrera introducido por el usuario
	 */
	private int askForRaceTime() {
		boolean valid=false;
		int raceTime = 0;
		String raceTimeString="";
		while(!valid) {
			System.out.println("Por favor, teclea los minutos que durará la carrera : ");
			raceTimeString=read.nextLine();
			if(validation.checkInt(raceTimeString)) {
				valid=true;
				raceTime=Integer.parseInt(raceTimeString);
			}else {
				System.err.println("El tiempo debe ser un número entero mayor que 0. Por favor, vuelve a intentarlo.");
			}
		}
		return raceTime;
	}
	
	/**
	 * Pide al usuario que teclee el tiempo de calentamiento que durará la carrera de eliminación que se está creando y lee el dato por teclado 
	 * comprobando  que el dato introducido sea un número entero mayor que 0, mostrando un mensaje de error en caso contrario
	 * @param nada
	 * @return el tiempo de calentamiento que durará la carrera introducido por el usuario
	 */
	private int askForHeatTime() {
		boolean valid=false;
		int heatTime = 0;
		String heatTimeString="";
		while(!valid) {
			System.out.println("Por favor, teclea los minutos que durará el calentamiento : ");
			heatTimeString=read.nextLine();
			if(validation.checkInt(heatTimeString)) {
				valid=true;
				heatTime=Integer.parseInt(heatTimeString);
			}else {
				System.err.println("El tiempo debe ser un número entero mayor que 0. Por favor, vuelve a intentarlo.");
			}
		}
		return heatTime;
	}
	
	/**
	 * Comprueba si hay almacenada una carrera estándar con ese nombre
	 * @param name el nombre que identifca a la carrera
	 * @return true si ya existe una carrera estándar con ese nombre y false en caso contrario
	 */
	private boolean checkIfStandardRaceExists(String name) {
		for(StandardRace s : getStoredStandardRaces()) {
			if(s.getName().trim().toLowerCase().compareTo(name.trim().toLowerCase())==0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Comprueba si hay almacenada una carrera de eliminación con ese nombre
	 * @param name el nombre que identifca a la carrera de eliminación
	 * @return true si ya existe una carrera con ese nombre y false en caso contrario
	 */
	private boolean checkIfEliminatoryRaceExists(String name) {
		for(EliminatoryRace e : getStoredEliminatoryRaces()) {
			if(e.getName().trim().toLowerCase().compareTo(name.trim().toLowerCase())==0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Muestra por consola todas las carreras de eliminación guardadas en el archivo json
	 */
	private void printStoredEliminatoryRaces() {
		int index =1;
		for(EliminatoryRace e : getStoredEliminatoryRaces()) {
				System.out.println(index+". "+e.getName());
				index++;
			}
	}
	
	/**
	 * Muestra por consola todas las carreras de estándar guardadas en el archivo json
	 */
	private void printStoredStandardRaces() {
		int index =1;
		for(StandardRace s : getStoredStandardRaces()) {
				System.out.println(index+". "+s.getName());
				index++;
			}
	}
	
	private List<EliminatoryRace> getStoredEliminatoryRaces(){
		return eliminatoryRaceRepository.getEliminatoryRaceData();
	}
	
	private List<StandardRace> getStoredStandardRaces(){
		return standardRaceRepository.getStandardRaceData();
	}
}
	
	
