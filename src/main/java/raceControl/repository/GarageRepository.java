package raceControl.repository;

import java.io.*;

import java.util.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import raceControl.entity.*;
import raceControl.persistence.InitialData;

/**
 * Guarda garajes en un archivo json almacenado en el disco duro, los lee de este mismo archivo, carga una serie de datos iniciales en caso
 * de que este archivo no exista, añade un coche a un garaje ya guardado y establece los puntos de los coches a cero antes de guardar el garaje
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */


public class GarageRepository {
	
	ObjectMapper mapper;
	List<Garage> garageData;
	File garageDataFile;
	InitialData initialData;
	
	public GarageRepository() {
		this.mapper = new ObjectMapper();
		this.garageData = new ArrayList<Garage>();
		garageDataFile = new File("garageData.json");
		if(garageDataFile.exists()) {
			read();
		}else {
			loadInitialData();
			read();
		}
	}

	/**
	 * guarda un garaje en un archivo json almacenado en el disco duro del usuario y establece los puntos de todos los coches a cero antes de guardar
	 * los garajes
	 * @param el garaje que va a ser guardado en el archivo json
	 * @return nada
	 */
	public void save(Garage garage) {
		read();
		setCarPointsToZero(garageData);
		garageData.add(garage);
		try {
			mapper.writeValue(garageDataFile, garageData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * lee los garajes guardados en un archivo json almacenado en el disco duro del usuario
	 * @param nada
	 * @return una lista con los garajes guardados
	 */
	public List<Garage> read() {
		try {
			garageData =new ArrayList<Garage>(Arrays.asList(mapper.readValue(garageDataFile, Garage[].class)));
			setCarPointsToZero(garageData);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return garageData;
	}
	
	/**
	 * Guarda un coche en el garaje que viene dado por su posición en la lista
	 * @param el índice en el que se encuentra en garaje en el que va a ser guardado el coche
	 * @param eñ coche que va ser guardado en el garaje dado por el índice
	 */
	public void addCarToGarage(int index, Car car) {
		car.setGarage(garageData.get(index).getName());
		garageData.get(index).getCars().add(car);
		try {
			mapper.writeValue(garageDataFile, garageData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Establece los puntos de todos los coches del garaje dado a cero
	 * @param la lista de garajes guardados
	 * @return nada
	 */
	private void setCarPointsToZero(List<Garage> garageData) {
		for(Garage g : garageData) {
			for(Car c : g.getCars()) {
				c.setPoints(0);
			}
		}
	}

	/**
	 * Crea un archivo json con una serie de garajes iniciales en el caso de que éste no exista
	 * @param nada
	 * @return nada
	 */
	private void loadInitialData() {
		initialData = new InitialData();
		try {
			mapper.writeValue(garageDataFile, initialData.createGarages());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Garage> getGarageData() {
		return garageData;
	}
	
	/***
	 * Guarda toda la información al cerrar el programa
	 */
	public void saveData() {
		try {
			mapper.writeValue(garageDataFile, garageData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
