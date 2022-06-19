package raceControl.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import raceControl.entity.DragsterRace;
import raceControl.entity.StandardRace;
import raceControl.persistence.InitialData;

public class DragsterRaceRepository {
	ObjectMapper mapper;
	List<DragsterRace> dragsterRaceData;
	File dragsterRaceFile;
	InitialData initialData;
	
	public DragsterRaceRepository() {
		this.mapper = new ObjectMapper();
		this.dragsterRaceData = new ArrayList<DragsterRace>();
		dragsterRaceFile = new File("dragsterRaceData.json");
		if(dragsterRaceFile.exists()) {
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
	public void save(DragsterRace dragsterRace) {
		dragsterRaceData.add(dragsterRace);
		try {
			mapper.writeValue(dragsterRaceFile, dragsterRaceData);
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
	public List<DragsterRace> read() {
		try {
			dragsterRaceData =new ArrayList<DragsterRace>(Arrays.asList(mapper.readValue(dragsterRaceFile, DragsterRace[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dragsterRaceData;
	}

	/**
	 * Crea un archivo json con una serie de carreras de tipo estándar iniciales en el caso de que éste no exista
	 * @param nada
	 * @return nada
	 */
	private void loadInitialData() {
		initialData = new InitialData();
		dragsterRaceData.add(initialData.createDragsterRace());
		try {
			mapper.writeValue(dragsterRaceFile, dragsterRaceData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<DragsterRace> getDragsterRaceData() {
		return dragsterRaceData;
	}

	
	/**
	 * Guarda toda la información al cerrar el programa
	 */
	public void saveData() {
		try {
			mapper.writeValue(dragsterRaceFile, dragsterRaceData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
