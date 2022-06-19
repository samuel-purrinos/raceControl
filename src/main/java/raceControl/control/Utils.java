package raceControl.control;

/**
 * Comprueba el el dato introducido por el usuario sea de tipo entero mayor que 0
 * @since 2022/6/2
 * @author Samuel Purriños
 * @version 1.0
 */
public class Utils {
	
	/**
	 * Comprueba el el dato introducido por el usuario sea de tipo entero mayor que 0
	 * @param el número introducido por el usuario
	 * @return true si es un número de tipo entero mayor que 0 y false en el resto de los casos
	 */
	public boolean checkInt(String number) {
		boolean valid=false;
        try {
            valid= Integer.parseInt(number) >= 0;
        } catch (NumberFormatException e) {
        }
        return valid;
	}
}
