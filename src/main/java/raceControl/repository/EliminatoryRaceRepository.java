package raceControl.repository;

import java.io.*;

import java.util.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import raceControl.entity.*;
import raceControl.persistence.InitialData;

/**
 * Guarda carreras de tipo eliminación en un archivo json almacenado en el disco duro, los lee de este mismo archivo y carga una serie de datos iniciales en caso
 * de que este archivo no exista
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class EliminatoryRaceRepository {
	ObjectMapper mapper;
	List<EliminatoryRace> eliminatoryRaceData;
	File eliminatoryRaceFile;
	InitialData initialData;
	
	public EliminatoryRaceRepository() {
		this.mapper = new ObjectMapper();
		this.eliminatoryRaceData = new ArrayList<EliminatoryRace>();
		eliminatoryRaceFile = new File("eliminatoryRaceData.json");
		if(eliminatoryRaceFile.exists()) {
			read();
		}else {
			loadInitialData();
			read();
		}
	}

	/**
	 * guarda una carrera de tipo eliminación en un archivo json almacenado en el disco duro del usuario
	 * @param la carrera de eliminación que va a ser guardado en el archivo json
	 * @return nada
	 */
	public void save(EliminatoryRace eliminatoryRace) {
		eliminatoryRaceData.add(eliminatoryRace);
		try {
			mapper.writeValue(eliminatoryRaceFile, eliminatoryRaceData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * lee las carreras de tipo eliminación guardadas en un archivo json almacenado en el disco duro del usuario
	 * @param nada
	 * @return una lista con las carreras de tipo eliminación guardados
	 */
	public List<EliminatoryRace> read() {
		try {
			eliminatoryRaceData =new ArrayList<EliminatoryRace>(Arrays.asList(mapper.readValue(eliminatoryRaceFile, EliminatoryRace[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eliminatoryRaceData;
	}
	
	/**
	 * Crea un archivo json con una serie de carreras de tipo eliminación iniciales en el caso de que éste no exista
	 * @param nada
	 * @return nada
	 */
	private void loadInitialData() {
		initialData = new InitialData();
		eliminatoryRaceData.add(initialData.createEliminatoryRace());
		try {
			mapper.writeValue(eliminatoryRaceFile, eliminatoryRaceData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<EliminatoryRace> getEliminatoryRaceData() {
		return eliminatoryRaceData;
	}
	
	public void saveData() {
		try {
			mapper.writeValue(eliminatoryRaceFile, eliminatoryRaceData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
