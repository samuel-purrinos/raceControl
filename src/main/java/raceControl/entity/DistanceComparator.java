package raceControl.entity;

import java.util.Comparator;
/**
 * Esta clase compara la distancia recorrida por dos coches dados
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class DistanceComparator implements Comparator<Car> {

	public int compare(Car o1, Car o2) {
        if (o1.getDistance()>o2.getDistance()) return -1;
        if (o1.getDistance()<o2.getDistance()) return 1;
		return 0;
	}

}
