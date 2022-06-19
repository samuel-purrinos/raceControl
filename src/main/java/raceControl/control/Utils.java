package raceControl.control;

/**
 * Comprueba el el dato introducido por el usuario sea de tipo entero mayor que 0
 * @since 2022/6/2
 * @author Samuel Purri�os
 * @version 1.0
 */
public class Utils {
	
	/**
	 * Comprueba el el dato introducido por el usuario sea de tipo entero mayor que 0
	 * @param el n�mero introducido por el usuario
	 * @return true si es un n�mero de tipo entero mayor que 0 y false en el resto de los casos
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
