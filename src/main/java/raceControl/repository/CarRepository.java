package raceControl.repository;

import java.io.File;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import raceControl.entity.Car;
import raceControl.entity.Tournament;
import raceControl.persistence.InitialData;

/**
 * Guarda coches en un archivo json almacenado en el disco duro, los lee de este mismo archivo, carga una serie de datos iniciales en caso
 * de que este archivo no exista y establece los puntos del coche que se va a guardar a cero
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class CarRepository {
	ObjectMapper mapper;
	List<Car> carData;
	File carDataFile;
	InitialData initialData;
	
	
	public CarRepository() {
		this.mapper = new ObjectMapper();
		this.carData = new ArrayList<Car>();
		carDataFile = new File("carData.json");
		if(carDataFile.exists()) {
			read();
		}else {
			loadInitialData();
			read();
		}
	}

	/**
	 * guarda un coche en un archivo json almacenado en el disco duro del usuario
	 * @param el coche que va a ser guardado en el archivo json
	 * @return nada
	 */
	public void save(Car car) {
		carData.add(car);
		try {
			mapper.writeValue(carDataFile, carData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * lee los coches guardados en un archivo json almacenado en el disco duro del usuario y establece los puntos de todos los coches a 0
	 * @param nada
	 * @return una lista con los coches guardados
	 */
	public List<Car> read() {
		try {
			carData =new ArrayList<Car>(Arrays.asList(mapper.readValue(carDataFile, Car[].class)));
			setCarPointsToZero(carData);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return carData;
	}
	
	/**
	 * Establece los puntos de todos los coches guardados a cer cuando éstos se leen desde el archivo json
	 * @param carData
	 */
	private void setCarPointsToZero(List<Car> carData) {
		for(Car c : carData) {
			c.setPoints(0);
		}
	}
	
	/**
	 * Crea un archivo json con una serie de coches iniciales en el caso de que éste no exista
	 * @param nada
	 * @return nada
	 */
	private void loadInitialData() {
		initialData = new InitialData();
		try {
			mapper.writeValue(carDataFile, initialData.createCars());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public List<Car> getCarData() {
		return carData;
	}


	/**
	 * Guarda toda la información al cerrar el programa
	 */
	public void saveData() {
		try {
			mapper.writeValue(carDataFile, carData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
