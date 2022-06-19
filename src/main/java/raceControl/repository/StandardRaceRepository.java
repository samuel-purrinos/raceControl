package raceControl.repository;

import java.io.File;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import raceControl.entity.*;
import raceControl.persistence.InitialData;

/**
 * Guarda carreras de tipo estándar en un archivo json almacenado en el disco duro, los lee de este mismo archivo y carga una serie de datos iniciales en caso
 * de que este archivo no exista
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class StandardRaceRepository {
	
	ObjectMapper mapper;
	List<StandardRace> standardRaceData;
	File standardRaceFile;
	InitialData initialData;
	
	public StandardRaceRepository() {
		this.mapper = new ObjectMapper();
		this.standardRaceData = new ArrayList<StandardRace>();
		standardRaceFile = new File("standardRaceData.json");
		if(standardRaceFile.exists()) {
			read();
		}else {
			loadInitialData();
			read();
		}
	}

	/**
	 * guarda una carrera de tipo estándar en un archivo json almacenado en el disco duro del usuario
	 * @param la carrera de tipo estándar que va a ser guardado en el archivo json
	 * @return nada
	 */
	public void save(StandardRace standardRace) {
		standardRaceData.add(standardRace);
		try {
			mapper.writeValue(standardRaceFile, standardRaceData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * lee las carreras de tipo estándar guardadas en un archivo json almacenado en el disco duro del usuario
	 * @param nada
	 * @return una lista con las carreras de tipo estándar guardados
	 */
	public List<StandardRace> read() {
		try {
			standardRaceData =new ArrayList<StandardRace>(Arrays.asList(mapper.readValue(standardRaceFile, StandardRace[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return standardRaceData;
	}

	/**
	 * Crea un archivo json con una serie de carreras de tipo estándar iniciales en el caso de que éste no exista
	 * @param nada
	 * @return nada
	 */
	private void loadInitialData() {
		initialData = new InitialData();
		standardRaceData.add(initialData.createStandardRace());
		try {
			mapper.writeValue(standardRaceFile, standardRaceData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<StandardRace> getStandardRaceData() {
		return standardRaceData;
	}

	public void setStandardRaceData(List<StandardRace> standardRaceData) {
		this.standardRaceData = standardRaceData;
	}
	
	/**
	 * Guarda toda la información al cerrar el programa
	 */
	public void saveData() {
		try {
			mapper.writeValue(standardRaceFile, standardRaceData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
