package raceControl.control;

import java.util.List;
import java.util.Scanner;

import raceControl.entity.Car;
import raceControl.entity.Garage;
import raceControl.entity.Tournament;
import raceControl.repository.*;
/**
 * Esta clase muestra un menú para crear nuevos garajes y añadir coches a los garajes recién creados, para después guardarlos junto a los
 * demás garajes almacenados en un archivo json del disco duro
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class GarageMenu {
	private Scanner read;
	private GarageRepository repository;
	private CarRepository carRepository;
	private Utils validation;
	private Garage newGarage;
	
	public GarageMenu() {
		this.read=new Scanner(System.in);
		this.repository=new GarageRepository();
		this.carRepository = new CarRepository();
		this.validation = new Utils();
	}
	
	public void mainMenu() {
		boolean valid=false;
		int option;
		String selectedOption="";
		while(!valid) {
			System.out.println("Qué deseas hacer?");
			System.out.println("1. Crear un nuevo garage");
			System.out.println("2. Ver un listado de los garages disponibles");
			System.out.println("3. Volver al menú principal");
			selectedOption=read.nextLine();
			if(validation.checkInt(selectedOption)) {
				option=Integer.parseInt(selectedOption);
				if(option==0||option>3) {
					System.err.println("Debes escoger una opción entre 1 y 3. Por favor, vuelve a intentarlo...");
				}else {
					switch (option) {
					case 1 : {createGarage(); break;}
					case 2 : {printStoredGarages(); break;}
					case 3 : {valid=true; break;}
					}
				}
			}
		}
	}
	
	
	/**
	 * Crea un garaje, le da un nombre, le añade coches previamente creados y lo guarda junto los demás garajes en un archivo json
	 * alojado en el disco duro
	 * @param nada
	 * @return nada
	 */
	private void createGarage() {
		String garageName=askForGarageName();
		newGarage=new Garage(garageName);
		addCarToGarage();
		repository.save(newGarage);
		System.out.println("El garage con el nombre "+garageName+" ha sido creado y guardado correctamente");
		
	}
	
	private String askForGarageName() {
		String garageName;
		boolean garageExists;
		do {
			System.out.println("Escribe el nombre que tendrá el nuevo garage :" );
			garageName=read.nextLine();
			garageExists=checkIfGarageExists(garageName);
			if(garageExists) System.err.println("El garaje "+garageName+" ya existe. Por favor, vuelve a intentarlo...");
		}while(garageExists);
		return garageName;
	}
	/**
	 * Carga una lista de coches del archivo json guardado en el disco duro y añade un coche al garaje recién creado
	 * @param nada
	 * @return nada
	 */
	private void addCarToGarage() {
		int index;
		String selectedOption="";
		boolean valid=false;
		while(!valid) {
			System.out.println("Selecciona el coche al que deseas añadir al garage : "+newGarage.getName());
			printStoredCars();
			selectedOption=read.nextLine();
			if(validation.checkInt(selectedOption)) {
				index=Integer.parseInt(selectedOption);
				if(index<=0 || index>carRepository.getCarData().size()) {
					System.err.println("El cocheseleccionado debe estar entre el 1 y el "+carRepository.getCarData().size());
				}else {
					carRepository.getCarData().get(index-1).setGarage(newGarage.getName());
					newGarage.getCars().add(carRepository.getCarData().get(index-1));
					System.out.println("Coche "+carRepository.getCarData().get(index-1).toString()+" añadido correctamente al garaje "+newGarage.getName());
					carRepository.getCarData().remove(index-1);
					valid=showCarMessage();
				}
			}
		}
	}
	
	/**
	 * Muestra un mensaje preguntándole al usuario si desea añadir otro coche al garaje.
	 * @return true en caso de que el usuario responda que sí quiere añadir otro coche y false en caso contrario
	 */
	private boolean showCarMessage() {
		boolean exit=false;
		String response;
		boolean addAnotherCar = false;
		while(!exit) {
			System.out.println("Deseas añadir otro coche? S/N");
			response=read.nextLine();
			if(response.trim().equals("S") || response.trim().equals("s")) {
				addAnotherCar=true;
				exit=true;
			}else if(response.trim().equals("N") || response.trim().equals("n")) {
				addAnotherCar=false;
				exit=true;
			}else {
				System.err.println("Debes teclear s o S para aceptar o N o n para cancelar. Por favor, inténtalo otra vez...");
			}
		}
		return !addAnotherCar;
	}
	
	/**
	 * Comprueba si hay almacenado un garaje con ese nombre
	 * @param name el nombre que identifca al garage
	 * @return true si ya existe un garage con ese nombre y false en caso contrario
	 */
	private boolean checkIfGarageExists(String name) {
		for(Garage g : repository.getGarageData()) {
			if(g.getName().trim().toLowerCase().compareTo(name.trim().toLowerCase())==0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Muestra en consola los datos de todos los coches almacenados 
	 */
	private void printStoredCars() {
		int index = 1;
		for(Car c : carRepository.getCarData()) {
			System.out.println(index+". "+c.toString());
			index++;
		}
	}
	/**
	 * Muestra en consola los datos de todos los garajes almacenados 
	 */
	private void printStoredGarages() {
		for(Garage g : repository.getGarageData()) {
			System.out.println("Coches que forman el garaje : "+g.getName());
			int index = 1;
			for(Car c : g.getCars()) {
				System.out.println(index+". "+c.toString());
				index++;
			}
		}
	}
}
