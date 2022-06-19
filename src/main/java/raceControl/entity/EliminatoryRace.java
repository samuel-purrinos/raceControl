package raceControl.entity;
/**
 * Esta clase representa una carrera de eliminación, identificada por su nombre y con el tiempo que durará la carrera y el tiempo dedicado al
 * calentamiento
 * @since 2022/6/2
 * @author Samuel Purriños
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
