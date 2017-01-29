package kotiinkuljetus;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.IntKentta;
import kanta.JonoKentta;
import kanta.Kentta;
import kanta.Tietue;

/**
 * Suhdeluokka, joka on yhdistävä relaatio muille luokille
 * @author Janita
 * @version 5.3.2016
 */

public class Suhde implements Cloneable, Tietue {
	private Kentta kentat[] = {
			new IntKentta("tunnusNro"),
			new IntKentta("TilausID"),
			new JonoKentta("Tuote"),
			new IntKentta("Kpl")
		}; 
	
/*	private int tunnusNro;
//	private int suhdeID;
	private int kpl;
	private int tilausID;
	private String tuote; */
	
	private static int seuraavaNro = 1;
	
	/**
	 * Alustaa suhteen
	 */
	public Suhde() {
		// ei tee vielä mitään 
	}

	/**
	 * Alustetaan tietty suhde
	 * @param suhdeID suhteen viitenumero
	 */
/*	public Suhde(int suhdeID) {
		this.tunnusNro = suhdeID;
	} */
	

	/**
	 * Alustetaan tietty suhde
	 * @param tilausID suhteen viitenumero
	 */
	public Suhde(int tilausID) {
	//	this.tilausID = tilausID;
		getTilausNroKentta().setValue(tilausID);
	}
	
	/**
	 * @return ensimmäisen sijoitettavan kentän indeksi
	 */
	@Override
	public int ekaKentta() {
		return 2;
	}
	
	/**
	 * @return tunnusnumeron arvo
	 */
	private IntKentta getTunnusNroKentta() {
		return (IntKentta)(kentat[0]);
	}
	
	/**
	 * @return tilausnumeron arvo
	 */
	private IntKentta getTilausNroKentta() {
		return (IntKentta)(kentat[1]);
	}
	
	/**
	 * @return kaikkien kenttien lukumäärä
	 */
	@Override
	public int getKenttia() {
		return 4;
	}
	
	/**
	 * Antaa k:n kentän sisällön avain-merkkijonona
	 * jonka perusteella voi lajitella
	 * @param k moneenko kentän sisältö palautetaan
	 * @return kentän sisältö merkkijonona
	 */
	public String getAvain(int k) {
		try {
			return kentat[k].getAvain();
		} catch (Exception e) {
			return "";
		}
	} 
	
	/**
	 * Apumetodi, jolla saadaan täytettyä testiarvot Suhteelle.
	 * @param nro viite suhteseen, jonka kuljetuksesta on kyse
	 */
	public void vastaaSuhde(int nro) {
	/*	tilausID = nro;
		kpl = 1;
	//	tunnusNro = 2;
		tuote = "Pizza"; */
		aseta(0, "2");
		aseta(2, "Pizza");
		aseta(1, "" + nro);
		aseta(3, "1");
	}
	
	/**
	 * Tulostetaan tuotteen tiedot
	 * @param out tietovirta johon tulostetaan
	 */
	public void tulosta(PrintStream out) {
	//	out.println("TilausID = " + tilausID + ", tuote = " + tuote + " ja kappalemäärä = " + kpl);
		String erotin = "";
		for (int k = ekaKentta(); k < getKenttia(); k++) {
			out.println(erotin + anna(k));
			erotin = " ";	
		}
		out.println(); 
	}
	
	/**
	 * Annetaan haluttu kentän arvo
	 * @param k kentän indeksi
	 * @return kentän arvo
	 */
	@Override
	public String anna(int k) {
			try {
				return kentat[k].toString();
			} catch (Exception e) {
				return "";
			}
		} 
	
	/**
	 * Tulostetaan tuotteen tiedot
	 * @param os tietovirta johon tulostetaan
	 */
	public void tulosta(OutputStream os) {
		tulosta(new PrintStream(os));
	}
	
	/**
	 * Antaa suhteelle seuraavan rekisterinumeron
	 * @return suhteen uusi tunnusNro
	 * @example
	 * <pre name="test">
	 * Suhde suhde1 = new Suhde();
	 * suhde1.getTunnusNro() === 0;
	 * suhde1.rekisteroi();
	 * Suhde suhde2 = new Suhde();
	 * suhde2.rekisteroi();
	 * int n1 = suhde1.getTunnusNro();
	 * int n2 = suhde2.getTunnusNro();
	 * n1 === n2-1;
	 * </pre>
	 */
	public int rekisteroi() {
	/*	if (tunnusNro > 0) return tunnusNro;
		tunnusNro = seuraavaNro;
		seuraavaNro++;
		return tunnusNro; */
		return setTunnusNro(seuraavaNro);
	}
	
	/**
	 * Asettaa tunnusnumeron ja varmistaa, että
	 * seuraava numero on aina suurempi kuin aiempi
	 * suurin
	 * @param nr asetettava tunnusnumero
	 * @return asetettu numero
	 */
	private int setTunnusNro(int nr) {
		IntKentta k = ((IntKentta)(kentat[0]));
		k.setValue(nr);
		if (nr >= seuraavaNro) seuraavaNro = nr + 1;
		return k.getValue();
	} 
	
	/**
	 * Asettaa merkkijonon tiettyyn kenttään
	 */
	@Override
	public String aseta(int k, String s) {
		try {
			String virhe = kentat[k].aseta(s.trim());
			if (virhe == null && k == 0) setTunnusNro(getTunnusNro());
			if (virhe == null) return virhe;
			return getKysymys(k) + ": " + virhe;
		} catch (Exception e) {
			return "Virhe: " + e.getMessage();
		}
	}
	
	/**
	 * @param k minkä kentan kysymys halutaan
	 * @return valitun kentän kysymysteksti
	 */
	@Override
	public String getKysymys(int k) {
		try {
			return kentat[k].getKysymys();
		} catch (Exception e) {
			return "???";
		}
	} 
	
	/**
	 * Palautetaan suhteen oma tunnusNro
	 * @return suhteen tunnusNro
	 */
/*	public int getTunnusNro(){
		return tunnusNro;
	}*/
	
	/**
	 * Palautetaan tilauksen id
	 * @return tilauksen id
	 */
/*	public int getTilausID() {
		return tilausID;
	} */
	
	/**
	 * Palautetaan mille asiakkaalle tilaus kuuluu
	 * @return asiakkaan id
	 */
/*	public int getSuhdeNro() {
		return suhdeID;
	} */
	
	/**
	 * Asettaa tunnusnumeron ja varmistaa että seuraava numero on aina suurempi
	 * kuin aikaisempi suurin.
	 * @param nr asetettava tunnusNro
	 */
/*	private void setTunnusNro(int nr) {
		tunnusNro = nr;
		if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
	} */
	
	/**
	 * Palauttaa tilauksen tiedot merkkijonona jonka voi tallentaa tiedostoon.
	 * @return tilaus tolppaeroteltuna merkkijonona
	 * @example
	 * <pre name="test">
	 * Suhde suhde = new Suhde();
	 * suhde.parse("1      |   1    |   2     |     2");
	 * suhde.toString() === "1|1|2|2"; 
	 * </pre>
	 */
	@Override
	public String toString() {
	//	return "" + getTunnusNro() + "|" + tuote + "|" + tilausID + "|" + kpl;
		StringBuffer sb = new StringBuffer("");
		String erotin = "";
		for (int k = 0; k < getKenttia(); k++) {
			sb.append(erotin);
			sb.append(anna(k));
			erotin = "|";
		}
		return sb.toString(); 
	}
	
	/**
	 * Selvittää tilauksen tiedot | erotellusta merkkijonosta.
	 * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
	 * @param rivi josta tilauksen tiedot otetaan
	 * @example
	 * <pre name="test">
	 * Suhde suhde = new Suhde();
	 * suhde.parse("1      |   1    |   2     |     2");
	 * suhde.toString() === "1|1|2|2"; 
	 * suhde.getTunnusNro() === 1;
	 * </pre>
	 */
	public void parse(String rivi) {
	/*	StringBuffer sb = new StringBuffer(rivi);
		setTunnusNro(Mjonot.erota(sb,  '|', getTunnusNro()));
		tuote = Mjonot.erota(sb, '|', tuote);
		tilausID = Mjonot.erota(sb, '|', tilausID);
		kpl = Mjonot.erota(sb, '|', kpl); */
		StringBuffer sb = new StringBuffer(rivi);
		for (int k = 0; k < getKenttia(); k++) {
			aseta(k, Mjonot.erota(sb, '|'));
		} 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		return this.toString().equals(obj.toString());
	}
	
	@Override
	public int hashCode() {
	//	return tunnusNro;
		return getTunnusNro();
	}
	
	/**
	 * Palautetaan suhteen oma tunnusNro
	 * @return suhteen tunnusNro
	 */
	public int getTunnusNro(){
		return getTunnusNroKentta().getValue();
	}
	
	/**
	 * @return tilausID
	 */
	public int getTilausNro() {
		return getTilausNroKentta().getValue();
	} 
	
	/**
	 * Tehdään identtinen klooni suhteesta
	 * @return object kloonattu suhde
	 * @example
	 * <pre name="test">
	 * #THROWS CloneNotSupportedException
	 * Suhde til = new Suhde();
	 * til.parse("  1| 1 | Pizza | 2");
	 * Suhde kopio = til.clone();
	 * til.parse("  1|  1 | Kebab | 2");
	 * kopio.toString().equals(til.toString()) === false;
	 * </pre>
	 */
	@Override
	public Suhde clone() throws CloneNotSupportedException {
		// return (Tilaus) super.clone();
		Suhde uusi;
		uusi = (Suhde)super.clone();
		uusi.kentat = kentat.clone();
		
		for (int k = 0; k < getKenttia(); k++) {
			uusi.kentat[k] = kentat[k].clone();
		}
		
		return uusi;
	} 
	
	/**
	 * Testipääohjelma suhteelle
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Suhde suhde = new Suhde();
		suhde.vastaaSuhde(1);
		suhde.tulosta(System.out);
	}
}
