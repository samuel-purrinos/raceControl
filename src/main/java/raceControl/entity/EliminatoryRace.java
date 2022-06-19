package raceControl.entity;
/**
 * Esta clase representa una carrera de eliminaci�n, identificada por su nombre y con el tiempo que durar� la carrera y el tiempo dedicado al
 * calentamiento
 * @since 2022/6/2
 * @author Samuel Purri�os
 * @version 1.0
 *
 */
public class EliminatoryRace extends StandardRace {
	private int heatTime;
	
	public EliminatoryRace() {
		
	}
	public EliminatoryRace(String name, int raceTime,int heatTime) {
		super(name,raceTime);
		this.heatTime=heatTime;
	}

	public int getHeatTime() {
		return heatTime;
	}

	public void setHeatTime(int heatTime) {
		this.heatTime = heatTime;
	}
	
}
