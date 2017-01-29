package kanta;

/**
 * @author Janita
 * @version 13.4.2016
 */
public interface Tietue {

	/**
	 * @return tietueen kenttien lukum��r�
	 * @example
	 * <pre name="test">
	 * #import kotiinkuljetus.Tilaus;
	 * Tilaus til = new Tilaus();
	 * til.getKenttia() === 4;
	 * </pre>
	 */
	public abstract int getKenttia();
	
	/**
	 * @param k mink� kent�n kysymys halutaan
	 * @param s valitun kent�n kysymysteksti
	 * @return null jos ok, muutoin virheteksti
	 */
	public abstract String aseta(int k, String s);
	
	/**
	 * @return ensimm�inen k�ytt�j�n sy�tett�v�n kent�n indeksi
	 * @example
	 * <pre name="test">
	 * #import kotiinkuljetus.Tilaus;
	 * Tilaus til = new Tilaus();
	 * til.ekaKentta() === 1;
	 * </pre>
	 */
	public abstract int ekaKentta();
	
	/**
	 * @param k mink� kent�n kysymys halutaan
	 * @return valitun kent�n kysymysteksti
	 * @example
	 * <pre name="test">
	 * #import kotiinkuljetus.Tilaus;
	 * Tilaus til = new Tilaus();
	 * til.getKysymys(1) === "Aika";
	 * </pre>
	 */
	public abstract String getKysymys(int k);
	
	
	/**
	 * Asetetaan valitun kent�n sis�lt�. Mik�li asettaminen onnistuu, 
	 * palautetaan null ja muutoin virheteksti
	 * @param k mink� kent�n sis�lt� asetetaan
	 * @return null jos ok, muuten virheteksti
	 * @example
	 * <pre name="test">
	 * #import kotiinkuljetus.Tilaus;
	 * Tilaus til = new Tilaus();
	 * til.parse("1       |  12.3.16 18:00  |Kortti   |     1 ");
	 * til.anna(0) === "1";
	 * til.anna(1) === "12.3.16 18:00";
	 * til.anna(2) === "Kortti";
	 * til.anna(3) === "1";
	 * </pre>
	 */
	public abstract String anna(int k);
	
	/**
	 * Tehd��n identtinen klooni tietueesta
	 * @return kloonattu tietue
	 * @throws CloneNotSupportedException jos kloonausta ei tueta
	 */
	public abstract Tietue clone() throws CloneNotSupportedException;
	
	/**
	 * Palauttaa tietueen tiedot merkkijonona jonka voi tallentaa tiedostoo
	 * @return tietue tolppaeroteltuna merkkijonona
	 */
	@Override
	public abstract String toString();
}
