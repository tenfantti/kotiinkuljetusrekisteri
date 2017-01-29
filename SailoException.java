/**
 * 
 */
package kotiinkuljetus;

/**
 * @author Janita
 * @version 29.2.2016
 */
public class SailoException extends Exception{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
	 * käytettävä viesti
	 * @param viesti poikkeuksen viesti
	 */
	public SailoException(String viesti) {
		super(viesti);
	}
}
