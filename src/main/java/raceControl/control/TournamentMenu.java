package raceControl.control;

import java.util.*;


import raceControl.entity.*;
import raceControl.repository.*;
import raceControl.runRace.RunTournament;
/**
 * Muestra un menú que permite al usuario crear nuevos torneos ,añadirle los garajes que participararán en las carreras, añadirle las carreras, 
 *  tanto de eliminación como estándar y guardar el torneo con todos estos datos en un archivo json almacenado en el disco duro junto a los otros
 *  torneos ya existentes, así como mostrar un menú que permite ejecutar torneos ya guardados.
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class TournamentMenu {
	private Scanner read;
	private Utils validation;
	private TournamentRepository repository;
	private RunTournament execute ;
	private Tournament newTournament;
	private StandardRaceRepository standardRepository;
	private EliminatoryRaceRepository eliminatoryRepository;
	private GarageRepository garageRepository;

	public TournamentMenu() {
		this.read = new Scanner(System.in);
		this.validation=new Utils();
		repository=new TournamentRepository();
		standardRepository=new StandardRaceRepository();
		eliminatoryRepository=new EliminatoryRaceRepository();
		garageRepository = new GarageRepository();
	}
	
	/**
	 * Muestra un menú al usuario que le permite escoger entre crear un torneo o ejecutar un torneo previamente guardado, comprobando que el usuario
	 * introduce datos válidos y mostrando un mensaje de error en caso de que esto no sea así
	 * @param nada
	 * @return nada
	 */
	public void mainMenu() {
		int selectedOption=showMainMenu();
		switch (selectedOption) {
		case 1 : {createTournament();showMainMenu();break;}
		case 2 : {runExistingTournament();showMainMenu();break;}
		case 3 : {printStoredTournaments();showMainMenu();break;}
		case 4 : {break;}
		}	
	}
	
	private int showMainMenu() {
		boolean exit = false;
		int option=0;
		String selectedOption="";
		do {
		System.out.println("¿Deseas crear un nuevo torneo o ejecutar uno ya guardado?");
		System.out.println("1. Crear torneo");
		System.out.println("2. Ejecutar torneo guardado");
		System.out.println("3. Ver los torneos guardados");
		System.out.println("4. Volver al menú principal");
		selectedOption=read.nextLine();
		if(validation.checkInt(selectedOption)) {
			option=Integer.parseInt(selectedOption);
				if(option<=0 || option>4) {System.err.println("Debes seleccionar una opción entre 1 y 4. Vuelve a intentarlo....");
				}else {exit=false;}
			}else {
				System.err.println("Debes seleccionar una opción entre 1 y 4. Vuelve a intentarlo....");
			}}while(exit);
		return option;
	}
	/**
	 * Muestra un menú permitiéndole a escoger al usuario si quiere añadir una carrera de eliminación o de tipo estándar, comprobando que los datos
	 * introducidos por el usuario sean correctos y mostrando un mensaje de error en caso de que esto no sea así. Añade los garajes del torneo en todas
	 * las carreras del torneo y guarda el torneo recién creado con todos sus datos en un archivo json almacenado en el disco duro
	 * @param nada
	 * @return nada
	 */
	private void createTournament() {
		String tournamentName=askForTournamentName();
		newTournament=new Tournament(tournamentName);
		addGarages();
		askForAddRaces();
		setGaragesOnRaces();
		repository.save(newTournament);
		System.out.println("El torneo con nombre "+newTournament.getName()+" ha sido creado y guardado correctamente");
	}
	private void askForAddRaces() {
		boolean addRace=true;
		int option;
		String selectedOption;
		while(addRace) {
			System.out.println("Qué tipo de carrera deseas añadir : ");
			System.out.println("1. Carrera estándar");
			System.out.println("2. Carrera de eliminación ");
			System.out.println("3. Terminar");
			selectedOption=read.nextLine();
			if(validation.checkInt(selectedOption)) {
				option=Integer.parseInt(selectedOption);
				if(option<1||option>3) {
					System.err.println("La opción escogida debe ser un número entero entre 1 y 3. Vuelve a intentarlo...");
				}else {
					if(option==1) addStandardRace();
					if(option==2) addEliminatoryRace();
					if(option==3)addRace=false;
				}
			}else {
				System.err.println("La opción escogida debe ser un número entero entre 1 y 3. Vuelve a intentarlo...");
			}
		}
	}
	private String askForTournamentName() {
		boolean tournamentExists;
		String tournamentName="";
		do {
			System.out.println("Teclea el nombre que tendrá el nuevo torneo : ");
			tournamentName=read.nextLine();
			tournamentExists=checkIfTournamentExists(tournamentName);
			if(tournamentExists) System.err.println("Ya existe un torneo con ese nombre. Por favor, vuelve a intentarlo....");
		}while(tournamentExists);
		return tournamentName;
	}
	/**
	 * Muestra un menú que permite añadir los garajes que participarán en el torneo, mostrando un mensaje de confirmación cuando la operación
	 * se ha completado correctamente
	 * @param nada
	 * @return nada
	 */
	public void addGarages() {
		int index=0;
		String selectedIndex;
		boolean exit=true;;
		while(exit) {
			int i =1;
			System.out.println("Selecciona el garaje que quieres añadir al torneo  :");
			printStoredGarages();
			selectedIndex=read.nextLine();
			if(validation.checkInt(selectedIndex)) {
				index=Integer.parseInt(selectedIndex);
				if(index<0||index>garageRepository.getGarageData().size()) {
					System.err.println("El garaje seleccionado debe ser un número entre 1 y "+garageRepository.getGarageData().size()+". Por favor, vuelve a intentarlo");
				}else {
					newTournament.getGarages().add(garageRepository.getGarageData().get(index-1));
					System.out.println("El garage "+garageRepository.getGarageData().get(index-1).getName()+" ha sido correctamente añadido al torneo");
					garageRepository.getGarageData().remove(index-1);
					exit=showGaragesMessage();
					}
				}
	}
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
	 * Muestra un menú que permite al usuario añadir una carrera estándar previamente guardada al torneo en creación, mostrando un mensaje de
	 * confirmación si la operación se ha completado con éxito
	 * @param nada
	 * @return nada
	 */
	private void addStandardRace() {
		int index=0;
		String selectedIndex;
		boolean exit=true;;
		while(exit) {
			if(standardRepository.getStandardRaceData().size()==0) {System.err.println("Debes crear una carrera estándar para poder añadirla");return;}
			int i =1;
			System.out.println("Selecciona la carrera estándar que quieres añadir al torneo :");
			printStoredStandardRaces();
			selectedIndex=read.nextLine();
			if(validation.checkInt(selectedIndex)) {
				index=Integer.parseInt(selectedIndex);
	
					if(index<0||index>standardRepository.getStandardRaceData().size()) {
						System.err.println("La carrera seleccionada debe ser un número entre 1 y "+standardRepository.getStandardRaceData().size()+". Por favor, vuelve a intentarlo.");
					}else {
						newTournament.getStandardRaces().add(standardRepository.getStandardRaceData().get(index-1));
						System.out.println("La carrera estándar ha sido añadida correctamente");
						standardRepository.getStandardRaceData().remove(index-1);
						exit=showRacesMessage();
						}
						}
		}
	}
	
	/**
	 * Muestra un menú que permite al usuario añadir una carrera de eliminación previamente guardada al torneo en creación, mostrando un mensaje de
	 * confirmación si la operación se ha completado con éxito
	 * @param nada
	 * @return nada
	 */
	private void addEliminatoryRace() {
		int index=0;
		String selectedIndex;
		boolean exit=true;;
		while(exit) {
			if(eliminatoryRepository.getEliminatoryRaceData().size()==0) {System.err.println("Debes crear una carrera de eliminación para poder añadirla");return;}
			int i =1;
			System.out.println("Selecciona la carrera de eliminación que quieres añadir al torneo :");
			printStoredEliminatoryRaces();
			selectedIndex=read.nextLine();
			if(validation.checkInt(selectedIndex)) {
				index=Integer.parseInt(selectedIndex);
				if(index<0||index>eliminatoryRepository.getEliminatoryRaceData().size()) {
					System.err.println("La carrera seleccionada debe ser un número entre 1 y "+eliminatoryRepository.getEliminatoryRaceData().size()+". Por favor, vuelve a intentarlo.");
				}else {
					newTournament.getEliminatoryRaces().add(eliminatoryRepository.getEliminatoryRaceData().get(index-1));
					System.out.println("La carrera de eliminación ha sido añadida correctamente");
					eliminatoryRepository.getEliminatoryRaceData().remove(index-1);
					exit=showRacesMessage();
					}
				}
	}
		
	}
	
	private boolean showRacesMessage() {
		boolean exit=false;
		String response;
		boolean addAnotherRace = false;
		while(!exit) {
			System.out.println("Deseas añadir otra carrera? S/N");
			response=read.nextLine();
			if(response.trim().equals("S") || response.trim().equals("s")) {
				addAnotherRace=true;
				exit=true;
			}else if(response.trim().equals("N") || response.trim().equals("n")) {
				addAnotherRace=false;
				exit=true;
			}else {
				System.err.println("Debes teclear s o S para aceptar o N o n para cancelar. Por favor, inténtalo otra vez...");
			}
		}
		return addAnotherRace;
	}
	
	/**
	 * Muestra al usuario un menú que le permite ejecutar un torneo previamente guardado
	 * @param nada
	 * @return nada
	 */
	private void runExistingTournament() {
		int option;
		String selectedOption = "";
		boolean valid=true;
		while(valid) {
			System.out.println("Escoge el torneo que deseas ejecutar :");
			int i = 1;
			for(Tournament t : repository.getTournamentData()) {
				System.out.println(i+". "+t.getName());
				i++;
			}
			selectedOption=read.nextLine();
			if(validation.checkInt(selectedOption)) {
				option=Integer.parseInt(selectedOption);
				if(option<=0||option>repository.getTournamentData().size()) {
					System.err.println("El torneo a ejecutar debe ser uno entre 1 y "+repository.getTournamentData().size());
				}else {
					valid=false;
					execute = new RunTournament(repository.getTournamentData().get(option-1));
					execute.startTournament();
				}	
				}else {
					System.err.println("La opción seleccionada no es válida. Vuelve a intentarlo...");
			}
		}
		
	}
	
	/**
	 * Añade los garajes del torneo a todas sus carreras
	 * @param nada
	 * @return nada
	 */
	private void setGaragesOnRaces() {
		List<StandardRace> standardRaceData = newTournament.getStandardRaces();
		List<EliminatoryRace> eliminatoryRaceData = newTournament.getEliminatoryRaces();
		if(standardRaceData.size()>=1) {
				newTournament.setStandardRaces(standardRaceData);
		}
		if(eliminatoryRaceData.size()>=1) {
			newTournament.setEliminatoryRaces(eliminatoryRaceData);;
		}
		
	}
	
	/**
	 * Comprueba si hay almacenado un torneo con ese nombre
	 * @param name el nombre que identifca al torneo
	 * @return true si ya existe un torneo con ese nombre y false en caso contrario
	 */
	private boolean checkIfTournamentExists(String name) {
		List<Tournament> storedTournaments = repository.getTournamentData();
		for(Tournament t : storedTournaments) {
			if(t.getName().trim().toLowerCase().compareTo(name.trim().toLowerCase())==0) {
				return true;
			}
		}
		return false;
	}
	private void printStoredGarages() {
		
	}
	
	private void printStoredStandardRaces() {
		
	}
	
	private void printStoredEliminatoryRaces() {
		
	}
	/**
	 * Muestra por consola todos los torneos guardados con sus carreras y garajes
	 */
	private void printStoredTournaments() {
		int index =1;
		for(Tournament t : repository.getTournamentData()) {
				System.out.println(index+". Torneo : "+t.getName());
				index++;
				System.out.println("Carreras del torneo : "+t.getName());
				for(StandardRace s : t.getStandardRaces()) {
					System.out.println("Carrera estándar : "+s.getName());
				}
				for(EliminatoryRace e : t.getEliminatoryRaces()) {
					System.out.println("Carrera de eliminación : "+e.getName());
				}
				System.out.println("Garajes del torneo : "+t.getName());
				for(Garage g : t.getGarages()) {
					System.out.println("Garaje : "+g.getName());
				}
			}
	}
}
