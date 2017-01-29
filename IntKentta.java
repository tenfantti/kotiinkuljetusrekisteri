package kanta;

import javax.swing.SwingConstants;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Janita
 * @version 5.4.2016
 */
public class IntKentta extends PerusKentta{
	private int arvo;
	
	/**
	 * Alustetaan kentt� kysymyksell�
	 * @param kysymys joka n�ytet��n kentt�� kysyess�
	 */
	public IntKentta(String kysymys) {
		super(kysymys);
	}
	
	/**
	 * @return kent�n arvo kokonaislukuna
	 */
	public int getValue() {
		return arvo;
	}

	/**
	 * Asetetaan kent�n arvo kokonaislukuna
	 * @param value asetettava kokonaislukuarvo
	 */
	public void setValue(int value) {
		arvo = value;
	}
	
	/**
	 * @return merkkijonona kokonaisluvun
	 */
	@Override
	public String toString(){
		return "" + arvo;
	}
	
	/**
	 * Palauttaa kent�n tiedot vertailtavana merkkijonona
	 * @return vertailtava merkkijono kent�st�
	 */
	@Override
	public String getAvain() {
		return Mjonot.fmt(arvo, 10);
	}
	

	/**
	 * Asetetaan kent�n arvo merkkijonosta. Jos
	 * jono kunnollinen, palautetaan null. Jos ei ole
	 * kunnollinen int-sy�te, palautetaan virheilmoitus.
	 * T�ll�in kent�n alkuper�inen arvo j�� voimaan
	 */
	@Override
	public String aseta(String jono) {
		StringBuffer sb = new StringBuffer(jono);
		try {
			this.arvo = Mjonot.erota(sb, ' ', 0);
			return null;
		} catch (NumberFormatException e) {
			return "Ei kokonaisluku (" + jono + ")";
		}
	}
	
	/**
	 * @return vaakasuuntainen sijainti kent�lle
	 */
	@Override
	public int getSijainti() {
		return SwingConstants.RIGHT;
	}
	
	/**
	 * @return syv�kopio oliosta
	 */
	@Override
	public IntKentta clone() throws CloneNotSupportedException {
		return (IntKentta) super.clone();
	}

	@Override
	public int getSijainti(int k) {
		return 0;
	}
	
}
