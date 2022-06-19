package raceControl.control;

import java.util.*;

import raceControl.entity.*;
import raceControl.repository.*;
/**
 * Esta clase muestra un menú para crear nuevos coches con su marca y modelo, añadirlos a un garaje previamente guardado y guardarlos en el archivo
 * json que se encarga de almacenar los coches
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class CarMenu {
	private Car newCar;
	private List<Car> carList;
	private Scanner read;
	private CarRepository carRepository;
	private GarageRepository garageRepository;
	private Utils validation;
	
	
	public CarMenu() {
		this.read = new Scanner(System.in);
		carRepository = new CarRepository();
		garageRepository=new GarageRepository();
		this.carList = carRepository.getCarData();
		validation = new Utils();
	}

	
	public void mainMenu() {
		boolean valid=false;
		int option;
		String selectedOption="";
		while(!valid) {
			System.out.println("1. Crear un nuevo coche");
			System.out.println("2. Ver un listado de los coches disponibles");
			System.out.println("3. Añadir un coche a un garaje");
			System.out.println("4. Volver al menú principal");
			selectedOption=read.nextLine();
			if(validation.checkInt(selectedOption)) {
				option=Integer.parseInt(selectedOption);
				if(option==0||option>4) {
					System.err.println("Debes escoger una opción entre 1 y 3. Por favor, vuelve a intentarlo...");
				}else {
					if(option==1) createNewCar();
					if(option==2) printStoredCars();
					if(option==3) addCarToGarageMenu();
					if(option==4) valid=true;
				}
			}
		}
	}

	/**
	 * Crea un nuevo coche con su marca y modelo ,lo añade a un garaje previamente guardado en un archivo json del disco duro y lo guarda
	 * en otro archivo json con una lista de todos los coches creados.
	 * @param nada
	 * @return nada
	 */
	private void createNewCar() {
		boolean carExists;
		String brand = "";
		String model = "";
		do  {
		brand = setBrand();
		model = setModel();
		carExists=checkIfCarExists(brand,model);
		if(checkIfCarExists(brand,model)) System.err.println("Ya existe un coche identificado por esa marca y modelo. Por favor, vuelve a intentarlo...");
		}while(carExists);
		int pilotSkill = setPilotSkill();
		Car newCar = new Car(brand, model,pilotSkill);
		System.out.println("El coche ha sido creado y guardado correctamente.");
	}
	
	/**
	 * Muestra un mensaje al usuario pidiéndole que teclee el modelo del nuevo coche y lee la entrada por teclado del usuario
	 * @param nada
	 * @return el nombre del modelo tecleado por el usuario
	 */
	private String setModel() {
		String model="";
		System.out.println("Por favor, escribe el modelo del coche : ");
		model=read.nextLine();
		return model;
	}
	
	/**
	 * Muestra un mensaje al usuario pidiéndole que teclee la marca del nuevo coche y lee la entrada por teclado del usuario
	 * @param nada
	 * @return la marca tecleada por el usuario
	 */
	private String setBrand() {
		String brand="";
		System.out.println("Por favor, escribe la marca del coche : ");
		brand=read.nextLine();
		return brand;
	}
	
	/**
	 * Muestra un mensaje al usuario pidiéndole que teclee la habilidad que tendrá el piloto del nuevo coche, lee la entrada por teclado del usuario,
	 *  y comprueba que el dato introducido por teclado es de tipo entero mostrando un mensaje de error si no lo es
	 * @return la habilidad del piloto tecleada por el usuario
	 */
	private int setPilotSkill() {
		int pilotSkill=0;
		String skillString;
		boolean valid=false;;
		while(!valid) {
			System.out.println("Por favor, teclea un número entero que representará la habilidad del piloto : ");
			skillString=read.nextLine();
			if(validation.checkInt(skillString)) {
				pilotSkill=Integer.parseInt(skillString);
				valid=true;
			}else {
				System.err.println("La habilidad del piloto debe ser un número entero mayor que 0. Por favor, vuelve a intentarlo....");
			}
		}
		return pilotSkill;
	}
	
	private void addCarToGarageMenu() {
		int carIndex = 0;
		boolean exit = false;
		do {
			System.out.println("Selecciona el coche que deseas añadir al garage : ");
			printStoredCars();
			String selectedCar;
			selectedCar = read.nextLine();
			if(validation.checkInt(selectedCar) && carIndex<=carRepository.getCarData().size()) {
				carIndex=Integer.parseInt(selectedCar);
				moveCarToGarage(carRepository.getCarData().get(carIndex-1),carIndex-1);
				exit=true;
			}else System.err.println("Debes seleccionar un coche entre 1 y "+carRepository.getCarData().size()+". Por favor, vuelve a intentarlo");	
		}while(!exit);
	}
	
	/**
	 * Muestra una lista de los garajes almacenados en un archivo json del disco duro y permite añadir el coche recién creado a uno de estos garajes,
	 * actualizando el garaje seleccionado con el coche recién añadido y guardándolo de nuevo en el disco duro, además de comprobar en el momento de
	 * seleccionar un garaje que el dato introducido por el usuario sea un número entero y esté dentro del rango de valores del tamaño de la lista
	 * de coches previamente guardados, mostrando un mensaje de error en caso de que esto no sea así
	 * @param el coche que se está creando
	 * @return nada
	 */
	private void moveCarToGarage(Car car,int carIndex) {
		int index;
		String selectedOption="";
		boolean valid=false;
		while(!valid) {
			System.out.println("Selecciona el garage al que deseas añadir el nuevo coche : ");
			int garageNumber=1;
			for(Garage g : garageRepository.getGarageData()) {
				System.out.println(garageNumber+". "+g.getName());
				garageNumber++;
			}
			selectedOption=read.nextLine();
			if(validation.checkInt(selectedOption)) {
				index=Integer.parseInt(selectedOption);
				if(index<=0 || index>garageRepository.getGarageData().size()) {
					System.err.println("El garage seleccionado debe estar entre el 1 y el "+garageRepository.getGarageData().size());
				}else {
					garageRepository.addCarToGarage(index-1, car);
					carRepository.getCarData().remove(carIndex);
					System.out.println("Coche añadido correctamente al garaje "+garageRepository.getGarageData().get(index-1).getName());
					valid=true;
				}
			}
		}
	}
	
	/**
	 * Comprueba si hay almacenado un coche con la marca y el modelo del coche que se está creando
	 * @param brand, model el modelo y la marca que identifca al coche
	 * @return true si ya existe un coche con ese modelo y marca y false en caso contrario
	 */
	private boolean checkIfCarExists(String brand,String model) {
		List<Car> storedCars = carRepository.getCarData();
		for(Car c : storedCars) {
			if(c.getBrand().trim().toLowerCase().compareTo(brand.trim().toLowerCase()) == 0
					&& c.getModel().trim().toLowerCase().compareTo(model.trim().toLowerCase())==0) {
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
	
}
