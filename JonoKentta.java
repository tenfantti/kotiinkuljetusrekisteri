package kanta;

/**
 * @author Janita
 * @version 5.4.2016
 */
public class JonoKentta extends PerusKentta {
	String jono = "";

	/**
	 * Alustetaan kysymyksell�
	 * @param kysymys joka esitet��n kentt�� kysytt�ess�
	 */
	public JonoKentta(String kysymys) {
		super(kysymys);
	}
	
	/**
	 * @return kent�n sis�lt�
	 */
	@Override
	public String toString() {
		return jono;
	}

	/**
	 * @param mjono merkkijono joka asetetaan kent�n arvoksi
	 * @see kanta.PerusKentta#aseta(String)
	 */
	@Override
	public String aseta(String mjono) {
		this.jono = mjono;
		return null;
	}

	@Override
	public int getSijainti(int k) {
		return 0;
	}

}
