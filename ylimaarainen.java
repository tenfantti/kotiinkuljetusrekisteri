/**
 * 
 */
package kanta;

/**
 * @author Janita
 * @version 29.2.2016
 */
public class ylimaarainen {
	
	/**
	 * Arvotaan satunnainen kokonaisluku v�lille (ala, yla)
	 * @param ala arvonnan alaraja
	 * @param yla arvonnan y�raja
	 * @return satunnainen luku v�lilt� (ala,yla)
	 */
	public static int rand(int ala, int yla) {
		double n = (yla-ala)* Math.random() * ala;
		return (int)Math.round(n);
	}

}
