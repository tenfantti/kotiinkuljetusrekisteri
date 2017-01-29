package kotiinkuljetus;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Kotiinkuljetus-luokka, joka huolehtii asiakkaista. Pääosin kaikki
 * metodit ovat vain "välittäjämetodeja" asiakkailta
 * 
 * @author Janita
 * @version 1.3.2016
 */
public class Kotiinkuljetus {

	private Asiakkaat asiakkaat = new Asiakkaat();
//	private Tilaukset tilaukset = new Tilaukset();
	private Suhteet suhteet = new Suhteet();
	
	/**
	 * Pääohjelma Kotiinkuljetus-luokalle
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Kotiinkuljetus kotiinkuljetus = new Kotiinkuljetus();
		
		try {
			Asiakas pertti = new Asiakas();
			Asiakas maija = new Asiakas();
			pertti.rekisteroi();
			pertti.vastaaPerttiAro();
			maija.rekisteroi();
			maija.vastaaPerttiAro();
			
			kotiinkuljetus.lisaa(pertti);
			kotiinkuljetus.lisaa(maija);
	//		int id1 = pertti.getTunnusNro();
		//	int id2 = maija.getTunnusNro();
			
/*			Tilaus pizza1 = new Tilaus(id1); pizza1.rekisteroi(); pizza1.vastaaPizza(id1); kotiinkuljetus.lisaa(pizza1);
			Tilaus pizza2 = new Tilaus(id1); pizza2.rekisteroi(); pizza2.vastaaPizza(id1); kotiinkuljetus.lisaa(pizza2);
			Tilaus pizza3 = new Tilaus(id2); pizza3.rekisteroi(); pizza3.vastaaPizza(id2); kotiinkuljetus.lisaa(pizza3);
			Tilaus pizza4 = new Tilaus(id2); pizza4.rekisteroi(); pizza4.vastaaPizza(id2); kotiinkuljetus.lisaa(pizza4); 
			Suhde suhde1 = new Suhde(pizza1.getTunnusNro()); suhde1.rekisteroi(); suhde1.vastaaSuhde(pizza1.getTunnusNro()); kotiinkuljetus.lisaa(suhde1);
			Suhde suhde2 = new Suhde(pizza2.getTunnusNro()); suhde2.rekisteroi(); suhde2.vastaaSuhde(pizza2.getTunnusNro()); kotiinkuljetus.lisaa(suhde2);
			Suhde suhde3 = new Suhde(pizza3.getTunnusNro()); suhde3.rekisteroi(); suhde3.vastaaSuhde(pizza3.getTunnusNro()); kotiinkuljetus.lisaa(suhde3);
			Suhde suhde4 = new Suhde(pizza4.getTunnusNro()); suhde4.rekisteroi(); suhde4.vastaaSuhde(pizza4.getTunnusNro()); kotiinkuljetus.lisaa(suhde4); */
			
		System.out.println("============== Kotiinkuljetuksen testi ================");
		
		for (int i = 0; i < kotiinkuljetus.getAsiakkaat(); i++ ) {
			Asiakas asiakas = kotiinkuljetus.annaAsiakas(i);
			System.out.println("Asiakas paikassa: " + i);
			asiakas.tulosta(System.out);
		//	List<Tilaus> loytyneet = kotiinkuljetus.annaTilaukset(asiakas);
	/*		for (Tilaus tilaus : loytyneet) {
				tilaus.tulosta(System.out);
				List<Suhde> tilaukset = kotiinkuljetus.annaSuhteet(tilaus);
				for (Suhde suhde : tilaukset) suhde.tulosta(System.out);
			} */

		}
		} catch (SailoException e) {
			// e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Lukee kuljetuksen tiedot tiedostoista
	 * @param nimi jota käytetään lukemisessa
	 * @throws SailoException jos lukeminen epäonnistuu
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * #import java.io.*;
	 * #import java.util.*;
	 * 
	 * Kotiinkuljetus kuljetus = new Kotiinkuljetus();
	 * Asiakas asiakas1 = new Asiakas(), asiakas2 = new Asiakas();
	 * asiakas1.vastaaPerttiAro(); asiakas2.vastaaPerttiAro();
	 * asiakas1.rekisteroi(); asiakas2.rekisteroi();
	 * Suhde suhde1 = new Suhde(asiakas1.getTunnusNro()); suhde1.rekisteroi(); suhde1.vastaaSuhde(asiakas1.getTunnusNro());
	 * Suhde suhde2 = new Suhde(asiakas1.getTunnusNro()); suhde2.rekisteroi(); suhde2.vastaaSuhde(asiakas1.getTunnusNro());
	 * Suhde suhde3 = new Suhde(asiakas2.getTunnusNro()); suhde3.rekisteroi(); suhde3.vastaaSuhde(asiakas2.getTunnusNro());
	 * Suhde suhde4 = new Suhde(asiakas2.getTunnusNro()); suhde4.rekisteroi(); suhde4.vastaaSuhde(asiakas2.getTunnusNro());
	 * String hakemisto = "testi";
	 * File dir = new File(hakemisto);
	 * File ftied = new File(hakemisto+"/yhteystiedot.dat");
	 * File fstied = new File(hakemisto+"/suhteet.dat");
	 * dir.mkdir();
	 * ftied.delete();
	 * fstied.delete();
	 * kuljetus.lueTiedostosta(hakemisto); #THROWS SailoException
	 * kuljetus.lisaa(asiakas1);
	 * kuljetus.lisaa(asiakas2);
	 * kuljetus.lisaa(suhde1);
	 * kuljetus.lisaa(suhde2);
	 * kuljetus.lisaa(suhde3);
	 * kuljetus.lisaa(suhde4);
	 * kuljetus.tallenna();
	 * kuljetus = new Kotiinkuljetus();
	 * kuljetus.lueTiedostosta(hakemisto);
	 * Collection<Asiakas> kaikki = kuljetus.etsi("", -1);
	 * Iterator<Asiakas> it = kaikki.iterator();
	 * it.next() === asiakas1;
	 * it.next() === asiakas2;
	 * it.hasNext() === false;
	 * kuljetus.lisaa(asiakas2);
	 * kuljetus.lisaa(suhde2);
	 * kuljetus.tallenna();
	 * ftied.delete() === true;
	 * fstied.delete() === true;
	 * File fbak = new File(hakemisto+"/yhteystiedot.bak");
	 * File fsbak = new File(hakemisto+"/suhteet.bak");
	 * fbak.delete() === true;
	 * fsbak.delete() === true;
	 * </pre>
	 */
	public void lueTiedostosta(String nimi) throws SailoException {
		asiakkaat = new Asiakkaat();
//		tilaukset = new Tilaukset();
		suhteet = new Suhteet();
		
		setTiedosto(nimi);
		asiakkaat.lueTiedostosta();
//		tilaukset.lueTiedostosta();
		suhteet.lueTiedostosta();
	}
	
	/**
	 * Poistaa asiakkaista ja kuljetuksista ne joilla on nro. Kesken.
	 * @param nro viitenumero, jonka mukaan poistetaan
	 * @return montako asiakasta poistettiin
	 */
/*	public int poista(int nro) {
		return 0;
	} */
	
	/**
	 * Annetaan kotiinkuljetuksen i's asiakas
	 * @param i mones asiakas annetaan
	 * @return i's jäsen
	 */
	public Asiakas annaAsiakas(int i) {
		return asiakkaat.anna(i);
	}
	
	/**
	 * Haetaan kaikki asiakkaan tilaukset
	 * @param asiakas halutun asiakkaan tilaukset
	 * @return tietorakenne jossa viitteet löydettyihin tilauksiin
	 * @throws SailoException jos tulee ongelmia
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * #import java.util.*;
	 * </pre>
	 */
/*	public List<Tilaus> annaTilaukset(Asiakas asiakas) throws SailoException{
		return tilaukset.annaTilaukset(asiakas.getTunnusNro());
	}
	
	public Tilaus annaTilaus(Asiakas asiakas) {
		List<Tilaus> loytyneet = tilaukset.annaTilaukset(asiakas.getTunnusNro());
		if (loytyneet ==  null || loytyneet.size() <= 0 ) return null;
		return (Tilaus) loytyneet.get(0);
	} */

	
	/**
	 * Haetaan kaikki suhteet
	 * @param asiakas halutun asiakkaan suhteet
	 * @return tietorakenne jossa viitteet löydettyihin suhteisiin
	 * @throws SailoException jos tulee ongelmia
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * #import java.util.*;
	 * </pre>
	 */
/*	public List<Suhde> annaSuhteet(Tilaus tilaus) throws SailoException{
		if (tilaus == null) return null;
		return suhteet.annaSuhteet(tilaus.getTunnusNro());
	} */
	
	/**
	 * Haetaan kaikki suhteet
	 * @param asiakas halutun asiakkaan suhteet
	 * @return tietorakenne jossa viitteet löydettyihin suhteisiin
	 * @throws SailoException jos tulee ongelmia
	 */
	public List<Suhde> annaSuhteet(Asiakas asiakas) throws SailoException {
		return suhteet.annaSuhteet(asiakas.getTunnusNro());
	} 
	
	/**
	 * @return asiakkaiden lukumäärä
	 */
	public int getAsiakkaat() {
		return asiakkaat.getLkm();
	}

	/**
	 * Lisätään uusi asiakas
	 * @param asiakas lisättävä asiakas
	 * @throws SailoException poikkeustilanteessa ilmoittaa virheestä
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * Kotiinkuljetus kuljetus = new Kotiinkuljetus();
	 * Asiakas asiakas1 = new Asiakas(), asiakas2 = new Asiakas();
	 * asiakas1.rekisteroi(); asiakas2.rekisteroi();
	 * kuljetus.getAsiakkaat() === 0;
	 * kuljetus.lisaa(asiakas1); kuljetus.getAsiakkaat() === 1;
	 * kuljetus.lisaa(asiakas2); kuljetus.getAsiakkaat() === 2;
	 * kuljetus.lisaa(asiakas1); kuljetus.getAsiakkaat() === 3;
	 * kuljetus.annaAsiakas(0) === asiakas1;
	 * kuljetus.annaAsiakas(1) === asiakas2;
	 * kuljetus.annaAsiakas(2) === asiakas1;
	 * Collection<Asiakas> loytyneet = kuljetus.etsi("", -1);
	 * Iterator<Asiakas> it = loytyneet.iterator();
	 * it.next() === asiakas1;
	 * it.next() === asiakas2;
	 * it.next() === asiakas1;
	 * </pre>
	 */
	public void lisaa(Asiakas asiakas) throws SailoException {
		asiakkaat.lisaa(asiakas);	
	}
	
	/**
	 * Lisätään uusi tilaus
	 * @param tilaus lisättävä tilaus
	 * @throws SailoException jos tulee ongelmia
	 */
/*	public void lisaa(Tilaus tilaus) throws SailoException{
		tilaukset.lisaa(tilaus);
	} */
	
	/**
	 * Lisätään uusi suhde
	 * @param suhde lisättävä suhde
	 * @throws SailoException jos tulee ongelmia
	 */
	public void lisaa(Suhde suhde) throws SailoException{
		suhteet.lisaa(suhde);
	}
	
	/**
	 * Palauttaa "taulukossa" hakuehtoon vastaavien asiakkaiden viitteet
	 * @param hakuehto hakuehto
	 * @param k etsittävän kentän indeksi
	 * @return tietorakenteen löytyneistä asiakkaista
	 * @throws SailoException jos jokin menee pieleen antaa poikkeuksen 
	 */
	public Collection<Asiakas> etsi(String hakuehto, int k) throws SailoException {
		return asiakkaat.etsi(hakuehto, k);
	}
	
	/**
	 * Asettaa tiedostojen perusnimet
	 * @param nimi uusi nimi
	 */
	public void setTiedosto(String nimi) {
		File dir = new File(nimi);
		dir.mkdirs();
		String hakemistonNimi = "";
		if (!nimi.isEmpty()) hakemistonNimi = nimi + "/";
		asiakkaat.setTiedostonPerusNimi(hakemistonNimi + "yhteystiedot");
//		tilaukset.setTiedostonPerusNimi(hakemistonNimi + "tilaukset");
		suhteet.setTiedostonPerusNimi(hakemistonNimi + "suhteet");
	}
	
	/**
	 * Asetetaan tilauksen id suhteeseen
	 * @param tilaus kyseessä oleva tilaus
	 * @param suhde luodettava suhde
	 */
/*	public void aseta(Tilaus tilaus, Suhde suhde) {
		int id = suhde.getTunnusNro();
		if (tilaus.getAsiakasNro() == id) return;
		tilaus.setAsiakasNro(id);
	} */
	
	/**
	 * Tallettaa kuljetuksen tiedot tiedostoon.
	 * Vaikka jokin tallettamisesta epäonnistuisi, yritetään
	 * silti tallentaa loput ennen poikkeuksien heittämistä.
	 * @throws SailoException jos tallettamisessa ongelmia
	 */
	public void tallenna() throws SailoException {
		String virhe = "";
		try {
			asiakkaat.tallenna();
		} catch (SailoException e) {
			virhe = e.getMessage();
		}
		
	/*	try {
			tilaukset.tallenna();
		} catch (SailoException e) {
			virhe += e.getMessage();
		} */
		
		try {
			suhteet.tallenna();
		} catch (SailoException e) {
			virhe += e.getMessage();
		}
		
		if ( !"".equals(virhe) ) throw new SailoException(virhe);
	}
	
	/**
	 * Korvaa asiakkaan tietorakenteessa.
	 * Ottaa asiakkaan omistukseensa.
	 * Etsitään samalla tunnusnumerolla oleva asiakas.
	 * Jos ei löydy, niin lisätään uuten asiakkaana.
	 * @param asiakas lisättävän asiakkaan viite
	 * @throws SailoException jos tietorakenne on jo täynnä
	 */
	public void korvaaTaiLisaa(Asiakas asiakas) throws SailoException {
		asiakkaat.korvaaTaiLisaa(asiakas);
	}
	
	/**
	 * Korvaa tilauksen tietorakenteessa.
	 * Ottaa tilauksen omistukseensa.
	 * Etsitään samalla tunnusnumerolla oleva tilaus.
	 * Jos ei löydy, niin lisätään uutena tilauksena.
	 * @param asiakas lisättävän tilauksen viite
	 * @throws SailoException jos tietorakenne on jo täynnä
	 */
/*	public void korvaaTaiLisaa(Tilaus tilaus) throws SailoException {
		tilaukset.korvaaTaiLisaa(tilaus);
	}*/
	
	/**
	 * Laitetaan tilaukset muuttuneeksi, niin pakotetaan tallentamaan
	 */
/*	public void setTilaustenMuutos() {
		tilaukset.setMuutos();
	} */
	
	public void setTilaustenMuutos() {
		suhteet.setMuutos();
	} 
	
	/**
	 * Poistaa asiakkaista ja tilauksista ne joilla on numero
	 * @param id viitenumero jonka mukaan poistetaan
	 * @return montako asiakasta poistettiin
	 */
	public int poista(int id) {
		int ret = asiakkaat.poista(id);
	//	int ret = asiakkaat.poista(id);
	//	tilaukset.poista(id);
		suhteet.poista(id);
		return ret;
	}
	
	/**
	 * Poistaa halutun tilauksen
	 * @param tilaus poistettava tilaus
	 */
/*	public void poistaTilaus(Tilaus tilaus) {
		tilaukset.poista(tilaus);
	} */
	
	/**
	 * Poistaa halutun tilauksen
	 * @param suhde poistettava tilaus
	 */
	public void poistaTilaus(Suhde suhde) {
		suhteet.poista(suhde);
	}
	
	/**
	 * @return kuljetuksen koko nimi
	 */
	public String getNimi() {
		return asiakkaat.getKokoNimi();
	}
	
	/**
	 * @param id asiakkaan id, jota haetaan
	 * @return asiakas jolla on valittu id
	 */
	public Asiakas annaAsiakasId(int id) {
		return asiakkaat.annaId(id);
	}
}
