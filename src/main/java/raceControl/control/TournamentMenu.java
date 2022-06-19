package raceControl.control;

import java.util.*;


import raceControl.entity.*;
import raceControl.repository.*;
import raceControl.runRace.RunTournament;
/**
 * Muestra un men� que permite al usuario crear nuevos torneos ,a�adirle los garajes que participarar�n en las carreras, a�adirle las carreras, 
 *  tanto de eliminaci�n como est�ndar y guardar el torneo con todos estos datos en un archivo json almacenado en el disco duro junto a los otros
 *  torneos ya existentes, as� como mostrar un men� que permite ejecutar torneos ya guardados.
 * @since 2022/6/2
 * @author Samuel Purri�os
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
	 * Muestra un men� al usuario que le permite escoger entre crear un torneo o ejecutar un torneo previamente guardado, comprobando que el usuario
	 * introduce datos v�lidos y mostrando un mensaje de error en caso de que esto no sea as�
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
		System.out.println("�Deseas crear un nuevo torneo o ejecutar uno ya guardado?");
		System.out.println("1. Crear torneo");
		System.out.println("2. Ejecutar torneo guardado");
		System.out.println("3. Ver los torneos guardados");
		System.out.println("4. Volver al men� principal");
		selectedOption=read.nextLine();
		if(validation.checkInt(selectedOption)) {
			option=Integer.parseInt(selectedOption);
				if(option<=0 || option>4) {System.err.println("Debes seleccionar una opci�n entre 1 y 4. Vuelve a intentarlo....");
				}else {exit=false;}
			}else {
				System.err.println("Debes seleccionar una opci�n entre 1 y 4. Vuelve a intentarlo....");
			}}while(exit);
		return option;
	}
	/**
	 * Muestra un men� permiti�ndole a escoger al usuario si quiere a�adir una carrera de eliminaci�n o de tipo est�ndar, comprobando que los datos
	 * introducidos por el usuario sean correctos y mostrando un mensaje de error en caso de que esto no sea as�. A�ade los garajes del torneo en todas
	 * las carreras del torneo y guarda el torneo reci�n creado con todos sus datos en un archivo json almacenado en el disco duro
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
			System.out.println("Qu� tipo de carrera deseas a�adir : ");
			System.out.println("1. Carrera est�ndar");
			System.out.println("2. Carrera de eliminaci�n ");
			System.out.println("3. Terminar");
			selectedOption=read.nextLine();
			if(validation.checkInt(selectedOption)) {
				option=Integer.parseInt(selectedOption);
				if(option<1||option>3) {
					System.err.println("La opci�n escogida debe ser un n�mero entero entre 1 y 3. Vuelve a intentarlo...");
				}else {
					if(option==1) addStandardRace();
					if(option==2) addEliminatoryRace();
					if(option==3)addRace=false;
				}
			}else {
				System.err.println("La opci�n escogida debe ser un n�mero entero entre 1 y 3. Vuelve a intentarlo...");
			}
		}
	}
	private String askForTournamentName() {
		boolean tournamentExists;
		String tournamentName="";
		do {
			System.out.println("Teclea el nombre que tendr� el nuevo torneo : ");
			tournamentName=read.nextLine();
			tournamentExists=checkIfTournamentExists(tournamentName);
			if(tournamentExists) System.err.println("Ya existe un torneo con ese nombre. Por favor, vuelve a intentarlo....");
		}while(tournamentExists);
		return tournamentName;
	}
	/**
	 * Muestra un men� que permite a�adir los garajes que participar�n en el torneo, mostrando un mensaje de confirmaci�n cuando la operaci�n
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
			System.out.println("Selecciona el garaje que quieres a�adir al torneo  :");
			printStoredGarages();
			selectedIndex=read.nextLine();
			if(validation.checkInt(selectedIndex)) {
				index=Integer.parseInt(selectedIndex);
				if(index<0||index>garageRepository.getGarageData().size()) {
					System.err.println("El garaje seleccionado debe ser un n�mero entre 1 y "+garageRepository.getGarageData().size()+". Por favor, vuelve a intentarlo");
				}else {
					newTournament.getGarages().add(garageRepository.getGarageData().get(index-1));
					System.out.println("El garage "+garageRepository.getGarageData().get(index-1).getName()+" ha sido correctamente a�adido al torneo");
					garageRepository.getGarageData().remove(index-1);
					exit=showGaragesMessage();
					}
				}
	}
}
	
	
	/**
	 * Muestra un mensaje al usuario que le permite a�adir otro garaje al torneo
	 * @param nada
	 * @return true el caso de que el usuario desee a�adir otro gaaraje al torneo y false en caso contrario
	 */
	private boolean showGaragesMessage() {
		boolean exit=false;
		String response;
		boolean addAnotherGarage = false;
		while(!exit) {
			System.out.println("Deseas a�adir otro garaje? S/N");
			response=read.nextLine();
			if(response.trim().equals("S") || response.trim().equals("s")) {
				addAnotherGarage=true;
				exit=true;
			}else if(response.trim().equals("N") || response.trim().equals("n")) {
				addAnotherGarage=false;
				exit=true;
			}else {
				System.err.println("Debes teclear s o S para aceptar o N o n para cancelar. Por favor, int�ntalo otra vez...");
			}
		}
		return addAnotherGarage;
	}
	
	/**
	 * Muestra un men� que permite al usuario a�adir una carrera est�ndar previamente guardada al torneo en creaci�n, mostrando un mensaje de
	 * confirmaci�n si la operaci�n se ha completado con �xito
	 * @param nada
	 * @return nada
	 */
	private void addStandardRace() {
		int index=0;
		String selectedIndex;
		boolean exit=true;;
		while(exit) {
			if(standardRepository.getStandardRaceData().size()==0) {System.err.println("Debes crear una carrera est�ndar para poder a�adirla");return;}
			int i =1;
			System.out.println("Selecciona la carrera est�ndar que quieres a�adir al torneo :");
			printStoredStandardRaces();
			selectedIndex=read.nextLine();
			if(validation.checkInt(selectedIndex)) {
				index=Integer.parseInt(selectedIndex);
	
					if(index<0||index>standardRepository.getStandardRaceData().size()) {
						System.err.println("La carrera seleccionada debe ser un n�mero entre 1 y "+standardRepository.getStandardRaceData().size()+". Por favor, vuelve a intentarlo.");
					}else {
						newTournament.getStandardRaces().add(standardRepository.getStandardRaceData().get(index-1));
						System.out.println("La carrera est�ndar ha sido a�adida correctamente");
						standardRepository.getStandardRaceData().remove(index-1);
						exit=showRacesMessage();
						}
						}
		}
	}
	
	/**
	 * Muestra un men� que permite al usuario a�adir una carrera de eliminaci�n previamente guardada al torneo en creaci�n, mostrando un mensaje de
	 * confirmaci�n si la operaci�n se ha completado con �xito
	 * @param nada
	 * @return nada
	 */
	private void addEliminatoryRace() {
		int index=0;
		String selectedIndex;
		boolean exit=true;;
		while(exit) {
			if(eliminatoryRepository.getEliminatoryRaceData().size()==0) {System.err.println("Debes crear una carrera de eliminaci�n para poder a�adirla");return;}
			int i =1;
			System.out.println("Selecciona la carrera de eliminaci�n que quieres a�adir al torneo :");
			printStoredEliminatoryRaces();
			selectedIndex=read.nextLine();
			if(validation.checkInt(selectedIndex)) {
				index=Integer.parseInt(selectedIndex);
				if(index<0||index>eliminatoryRepository.getEliminatoryRaceData().size()) {
					System.err.println("La carrera seleccionada debe ser un n�mero entre 1 y "+eliminatoryRepository.getEliminatoryRaceData().size()+". Por favor, vuelve a intentarlo.");
				}else {
					newTournament.getEliminatoryRaces().add(eliminatoryRepository.getEliminatoryRaceData().get(index-1));
					System.out.println("La carrera de eliminaci�n ha sido a�adida correctamente");
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
			System.out.println("Deseas a�adir otra carrera? S/N");
			response=read.nextLine();
			if(response.trim().equals("S") || response.trim().equals("s")) {
				addAnotherRace=true;
				exit=true;
			}else if(response.trim().equals("N") || response.trim().equals("n")) {
				addAnotherRace=false;
				exit=true;
			}else {
				System.err.println("Debes teclear s o S para aceptar o N o n para cancelar. Por favor, int�ntalo otra vez...");
			}
		}
		return addAnotherRace;
	}
	
	/**
	 * Muestra al usuario un men� que le permite ejecutar un torneo previamente guardado
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
					System.err.println("La opci�n seleccionada no es v�lida. Vuelve a intentarlo...");
			}
		}
		
	}
	
	/**
	 * A�ade los garajes del torneo a todas sus carreras
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
					System.out.println("Carrera est�ndar : "+s.getName());
				}
				for(EliminatoryRace e : t.getEliminatoryRaces()) {
					System.out.println("Carrera de eliminaci�n : "+e.getName());
				}
				System.out.println("Garajes del torneo : "+t.getName());
				for(Garage g : t.getGarages()) {
					System.out.println("Garaje : "+g.getName());
				}
			}
	}
}
