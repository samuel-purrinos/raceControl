package raceControl.persistence;

import java.util.*;
import raceControl.entity.*;
import raceControl.repository.*;

/**
 * Crea unos datos iniciales en caso de que no existan, para que el usuario pueda empezar a utilizar el resto del programa
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class InitialData {

	ArrayList<Garage> garageData = new ArrayList<Garage>();
	/**
	 * Crea una serie de coches que serán guardados en un archivo json por los repositorios, a modo de datos iniciales
	 * @param nada
	 * @return una lista de coches, con su marca, modelo y habilidad del piloto
	 */
	public ArrayList<Car> createCars() {
		ArrayList<Car> carData=new ArrayList<Car>();
		Car mondeo,r5,sandero,leon,golf,vitara,astra,corsa,picasso,testarrosa,dino,diablo,db5,megane,ibiza,arona,corolla,tucson,patrol,zx;
		mondeo =new Car("Ford","Mondeo",80);
		mondeo.setGarage("Everham Motorsports");
		r5=new Car("Renault","5",70);
		r5.setGarage("Everham Motorsports");
		sandero=new Car("Dacia","Sandero",90);
		sandero.setGarage("Everham Motorsports");
		leon=new Car("Seat","León",60);
		leon.setGarage("Everham Motorsports");
		golf =new Car("VolsWagen","Golf",75);
		golf.setGarage("Ginn Racing");
		vitara=new Car("Suzuki","Vitara",85);
		vitara.setGarage("Ginn Racing");
		astra=new Car("Opel","Astra",55);
		astra.setGarage("Ginn Racing");
		corsa=new Car("Opel","Corsa",67);
		corsa.setGarage("Ginn Racing");
		picasso=new Car("Citroen","C4 Picasso",42);
		picasso.setGarage("Red Bull Racing");
		zx=new Car("Citroen","ZX",95);
		zx.setGarage("Red Bull Racing");
		testarrosa=new Car("Ferrari","Testarrosa",82);
		testarrosa.setGarage("Red Bull Racing");
		dino=new Car("Ferrari","Dino",55);
		dino.setGarage("Red Bull Racing");
		diablo=new Car("Lamborghini","Diablo",99);
		diablo.setGarage("Mc Gill Motorsports");
		db5=new Car("Aston Martin","DB5",91);
		db5.setGarage("Mc Gill Motorsports");
		megane=new Car("Renault","Megane",78);
		megane.setGarage("Mc Gill Motorsports");
		ibiza=new Car("Seat","Ibiza",69);
		ibiza.setGarage("Mc Gill Motorsports");
		arona=new Car("Seat","Arona",59);
		arona.setGarage("Brewco Motorsports");
		corolla=new Car("Toyota","Corolla",63);
		corolla.setGarage("Brewco Motorsports");
		tucson=new Car("Hyundai","Tucson",85);
		tucson.setGarage("Brewco Motorsports");
		patrol=new Car("Nissan","Patrol",40);
		patrol.setGarage("Brewco Motorsports");
		carData.add(leon);
		carData.add(mondeo);
		carData.add(sandero);
		carData.add(r5);
		carData.add(golf);
		carData.add(vitara);
		carData.add(astra);
		carData.add(corsa);	
		carData.add(picasso);
		carData.add(zx);
		carData.add(testarrosa);
		carData.add(dino);
		carData.add(diablo);
		carData.add(db5);
		carData.add(megane);
		carData.add(ibiza);
		carData.add(arona);
		carData.add(corolla);
		carData.add(tucson);
		carData.add(patrol);
	

		return carData;
	}
	
	/**
	 * Crea una serie de garajes con sus correspondientes coches que serán guardados en un archivo json por los repositorios, 
	 * a modo de datos iniciales
	 * @param nada
	 * @return una lista de garajes con sus coches
	 */
	public ArrayList<Garage> createGarages() {
		ArrayList<Car> carList= createCars();
		ArrayList<Car> garageMembers = new ArrayList<Car>();
		garageMembers.add(carList.get(0));
		garageMembers.add(carList.get(1));
		garageMembers.add(carList.get(2));
		garageMembers.add(carList.get(3));
		
		Garage everham = new Garage("Everham Motorsports");
		
		garageMembers.get(0).setGarage(everham.getName());
		garageMembers.get(1).setGarage(everham.getName());
		garageMembers.get(2).setGarage(everham.getName());
		garageMembers.get(3).setGarage(everham.getName());

		
		for(Car c : garageMembers) {
			c.setGarage(everham.getName());
		}
		
		everham.setCars(garageMembers);
		
		garageMembers = new ArrayList<Car>();
		
		garageMembers.add(carList.get(4));
		garageMembers.add(carList.get(5));
		garageMembers.add(carList.get(6));
		garageMembers.add(carList.get(7));
		
		Garage ginn = new Garage("Ginn Racing");
		
		garageMembers.get(0).setGarage(ginn.getName());
		garageMembers.get(1).setGarage(ginn.getName());
		garageMembers.get(2).setGarage(ginn.getName());
		garageMembers.get(3).setGarage(ginn.getName());
		
		for(Car c : garageMembers) {
			c.setGarage(ginn.getName());
		}
		
		ginn.setCars(garageMembers);
		
		garageMembers = new ArrayList<Car>();
		
		garageMembers.add(carList.get(8));
		garageMembers.add(carList.get(9));
		garageMembers.add(carList.get(10));
		garageMembers.add(carList.get(11));
		
		Garage redBull = new Garage("Red Bull Racing");
		
		garageMembers.get(0).setGarage(redBull.getName());
		garageMembers.get(1).setGarage(redBull.getName());
		garageMembers.get(2).setGarage(redBull.getName());
		garageMembers.get(3).setGarage(redBull.getName());
		
		for(Car c : garageMembers) {
			c.setGarage(redBull.getName());
		}
		
		redBull.setCars(garageMembers);
		
		garageMembers = new ArrayList<Car>();
		
		garageMembers.add(carList.get(12));
		garageMembers.add(carList.get(13));
		garageMembers.add(carList.get(14));
		garageMembers.add(carList.get(15));
		
		Garage mcgill = new Garage("Mc Gill Motorsports");
		
		garageMembers.get(0).setGarage(mcgill.getName());
		garageMembers.get(1).setGarage(mcgill.getName());
		garageMembers.get(2).setGarage(mcgill.getName());
		garageMembers.get(3).setGarage(mcgill.getName());
		
		for(Car c : garageMembers) {
			c.setGarage(mcgill.getName());
		}
		
		mcgill.setCars(garageMembers);
		
		garageMembers = new ArrayList<Car>();
		
		garageMembers.add(carList.get(16));
		garageMembers.add(carList.get(17));
		garageMembers.add(carList.get(18));
		garageMembers.add(carList.get(19));
		
		Garage brewco = new Garage("Brewco Motorsports");
		
		garageMembers.get(0).setGarage(brewco.getName());
		garageMembers.get(1).setGarage(brewco.getName());
		garageMembers.get(2).setGarage(brewco.getName());
		garageMembers.get(3).setGarage(brewco.getName());
		
		for(Car c : garageMembers) {
			c.setGarage(brewco.getName());
		}
		
		brewco.setCars(garageMembers);
		
		garageMembers = new ArrayList<Car>();
		
		garageData.add(brewco);
		garageData.add(everham);
		garageData.add(redBull);
		garageData.add(mcgill);
		garageData.add(ginn);
		
		return garageData;
	}
	
	/**
	 * Crea una carrera estándar con sus garajes  que serán guardados en un archivo json por los repositorios, a modo de datos iniciales
	 * @param nada
	 * @return una carrera estándar con su nombre, tiempo que durará la carrera y sus garajes
	 */
	public StandardRace createStandardRace() {
		StandardRace lagunaSeca = new StandardRace("Laguna Seca",180);
		lagunaSeca.setGarages(createGarages());
		return lagunaSeca;
	}
	
	/**
	 * Crea una carrera de eliminación con sus garajes  que serán guardados en un archivo json por los repositorios, a modo de datos iniciales
	 * @param nada
	 * @return una carrera de eliminación con su nombre, tiempo que durará la carrera, tiempo de calentamiento y sus garajes
	 */
	public EliminatoryRace createEliminatoryRace() {
		EliminatoryRace daytona = new EliminatoryRace("Daytona",180,30);
		daytona.setGarages(createGarages());
		return daytona;
		
	}
	
	public DragsterRace createDragsterRace() {
		DragsterRace stockholm = new DragsterRace("Stockholm");
		stockholm.setGarages(createGarages());
		return stockholm;
	}
	
	/**
	 * Crea un torneo con sus garajesy carreras que serán guardados en un archivo json por los repositorios, a modo de datos iniciales
	 * @param nada
	 * @return un torneo con su nombre, carreras y garajes
	 */
	public Tournament createTournament() {
		Tournament dummyTour = new Tournament("2022 Season");
		dummyTour.getStandardRaces().add(createStandardRace());
		dummyTour.getEliminatoryRaces().add(createEliminatoryRace());
		dummyTour.setGarages(createGarages());
		return dummyTour;
	}
}