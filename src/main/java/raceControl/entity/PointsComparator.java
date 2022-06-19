package raceControl.entity;

import java.util.Comparator;
/**
 * Esta clase compara los puntos obtenidos por dos coches dados
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class PointsComparator implements Comparator<Car> {

	public int compare(Car o1, Car o2) {
        if (o1.getPoints()>o2.getPoints()) return -1;
        if (o1.getPoints()<o2.getPoints()) return 1;
		return 0;
	}

}
