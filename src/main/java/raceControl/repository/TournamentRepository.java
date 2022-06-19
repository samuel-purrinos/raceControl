package raceControl.repository;

import java.io.File;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import raceControl.entity.*;
import raceControl.persistence.InitialData;

/**
 * Guarda torneos en un archivo json almacenado en el disco duro, los lee de este mismo archivo y carga una serie de datos iniciales en caso
 * de que este archivo no exista
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class TournamentRepository {
	ObjectMapper mapper;
	List<Tournament> tournamentData;
	File tournamentDataFile;
	InitialData initialData;
	
	public TournamentRepository() {
		this.mapper = new ObjectMapper();
		this.tournamentData = new ArrayList<Tournament>();
		this.tournamentDataFile = new File("tournamentData.json");
		if(tournamentDataFile.exists()) {
			read();
		}else {
			loadInitialData();
			read();
		}
	}

	/**
	 * guarda un torneo en un archivo json almacenado en el disco duro del usuario
	 * @param el torneo que va a ser guardado en el archivo json
	 * @return nada
	 */
	public void save(Tournament tournament) {
		tournamentData.add(tournament);
		try {
			mapper.writeValue(tournamentDataFile, tournamentData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * lee los torneos guardados en un archivo json almacenado en el disco duro del usuario
	 * @param nada
	 * @return una lista con los torneos guardados
	 */
	public List<Tournament> read() {
		try {
			tournamentData =new ArrayList<Tournament>(Arrays.asList(mapper.readValue(tournamentDataFile, Tournament[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tournamentData;
	}
	
	/**
	 * Crea un archivo json con una serie de torneos iniciales en el caso de que éste no exista
	 * @param nada
	 * @return nada
	 */
	private void loadInitialData() {
		initialData = new InitialData();
		tournamentData.add(initialData.createTournament());
		try {
			mapper.writeValue(tournamentDataFile,tournamentData );
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Tournament> getTournamentData() {
		return tournamentData;
	}
	
	/**
	 * Guarda toda la información al cerrar el programa
	 */
	public void saveData() {
		try {
			mapper.writeValue(tournamentDataFile, tournamentData);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
