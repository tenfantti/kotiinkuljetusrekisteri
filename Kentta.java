package kanta;

/**
 * Rajapinta kent�lle
 * @author Janita
 * @version 5.4.2016
 */
public interface Kentta extends Cloneable, Comparable<Kentta> {

	/**
	 * Korvataan aiempi toString metodi omalla
	 * @return haluttu kentt� merkkijonona
	 */
	@Override
	String toString();

	/**
	 * @return kent�n kysymys
	 */
	String getKysymys();

	/**
	 * Asettaa kent�n sis�ll�n ottamalla tiedot merkkijonosta.
	 * @param jono
	 * @return null jos ok, muutoin virheteksti
	 */
	String aseta(String jono);

	/**
	 * @return kent�n avain
	 */
	String getAvain();
	
	/**
	 * @return kloonin halutun kent�n sis�ll�st�
	 * @throws CloneNotSupportedException heitt�� poikkeuksen jos kloonaus ei onnistu
	 */
	Kentta clone() throws CloneNotSupportedException ;
	
	/**
	 * @param k mist� kent�st�
	 * @return vaakasuuntainen sijainti kent�lle
	 */
	int getSijainti(int k);
}
