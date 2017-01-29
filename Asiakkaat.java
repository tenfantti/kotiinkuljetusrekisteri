package kotiinkuljetus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

/**
 * @author Janita
 * @version 29.2.2016
 * @version 1.4.2016
 */
public class Asiakkaat implements Iterable<Asiakas> {

	private String tiedostonPerusNimi = "yhteystiedot";
	//	private static final int MAX_ASIAKKAITA	= 18;
	private int MAX_ASIAKKAITA = Integer.MAX_VALUE;
	private int lkm = 0;
	private String kokoNimi = "kotiinkuljetus";
	//	private Asiakas[] alkiot = new Asiakas[MAX_ASIAKKAITA];
	private Asiakas[] alkiot = new Asiakas[5];
	private boolean muutettu = false;




	/** Pääohjelma asiakkaille
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Asiakkaat asiakkaat = new Asiakkaat();

		Asiakas pertti = new Asiakas();
		Asiakas maija = new Asiakas();
		pertti.rekisteroi();
		pertti.vastaaPerttiAro();
		maija.rekisteroi();
		maija.vastaaPerttiAro();

		try {
			asiakkaat.lisaa(pertti);
			asiakkaat.lisaa(maija);
		} catch (SailoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		System.out.println("============== Asiakkaat ================");

		int i = 0;
		for(Asiakas asiakas : asiakkaat) {
			//	Asiakas asiakas = asiakkaat.anna(i);
			System.out.println("Asiakkaan nro: "+ i++);
			asiakas.tulosta(System.out);
		}

		try {
			asiakkaat.tallenna();
		} catch (SailoException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Alustaa asiakkaat
	 */
	public Asiakkaat() {
		// On vain olemassa
	}

	/**
	 * Muodostaja jolle maksimikoko voidaan asettaa
	 * @param koko asiakkaiden maxkoko
	 */
	public Asiakkaat(int koko) {
		MAX_ASIAKKAITA = koko;
		alkiot = new Asiakas[koko];
	}

	/**
	 * Palauttaa kuljetuksen koko nimen
	 * @return kuljetuksen nimi merkkijonona
	 */
	public String getKokoNimi() {
		return kokoNimi;
	}


	/**
	 * Antaa halutun asiakkaan, jos taulukko on täynnä niin luo uuden.
	 * @param i indeksiluku
	 * @return taulukon asiakas indeksin kohdalla
	 * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
	 */
	protected Asiakas anna(int i) throws IndexOutOfBoundsException {
		if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: "+i);
		return alkiot[i];
	}


	/**
	 * Antaa asiakkaiden lukumäärän
	 * @return lkm
	 */
	public int getLkm() {
		return lkm;
	}

	/**
	 * Poistaa asiakkaan jolla on valittu tunnusnumero
	 * @param id poistettavan asiakkaan tunnusnumero
	 * @return 1 jos poistettiin, 0 jos ei löydy
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * Asiakkaat asiakkaat = new Asiakkaat(5);
	 * Asiakas as1 = new Asiakas(), as2 = new Asiakas(), as3 = new Asiakas();
	 * as1.rekisteroi(); as2.rekisteroi(); as3.rekisteroi();
	 * int id1 = as1.getTunnusNro();
	 * asiakkaat.lisaa(as1); asiakkaat.lisaa(as2); asiakkaat.lisaa(as3);
	 * asiakkaat.poista(id1+1) === 1;
	 * asiakkaat.annaId(id1+1) === null; asiakkaat.getLkm() === 2;
	 * asiakkaat.poista(id1) === 1; asiakkaat.getLkm() === 1;
	 * asiakkaat.poista(id1+3) === 0; asiakkaat.getLkm() === 1;
	 * </pre>
	 */
	public int poista(int id) {
		int ind = etsiId(id);
		if (ind < 0) return 0;
		lkm--;
		for (int i = ind; i < lkm; i++) {
			alkiot[i] = alkiot[i+1];
		}
		alkiot[lkm] = null;
		muutettu = true;
		return 1;
	}

	/**
	 * Etsii asiakkaan id:n perusteella
	 * @param id tunnusnumero, jonka mukaan etsitään
	 * @return jolla etsittävä id tai null
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * Asiakkaat asiakkaat = new Asiakkaat(5);
	 * Asiakas as1 = new Asiakas(), as2 = new Asiakas(), as3 = new Asiakas();
	 * as1.rekisteroi(); as2.rekisteroi(); as3.rekisteroi();
	 * int id1 = as1.getTunnusNro();
	 * asiakkaat.lisaa(as1); asiakkaat.lisaa(as2); asiakkaat.lisaa(as3);
	 * asiakkaat.annaId(id1) == as1 === true;
	 * asiakkaat.annaId(id1+1) == as2 === true;
	 * asiakkaat.annaId(id1+2) == as3 === true;
	 * </pre>
	 */
	public Asiakas annaId(int id) {
		for (Asiakas asiakas : this) {
			if (id == asiakas.getTunnusNro()) return asiakas;
		}
		return null;
	}

	/**
	 * Etsii asiakkaan id:n perusteella
	 * @param id asiakasnumero, jonka mukaan etsitään
	 * @return löytyneen asiakkaan indeksi tai -1 jos ei löydy
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * Asiakkaat asiakkaat = new Asiakkaat(5);
	 * Asiakas as1 = new Asiakas(), as2 = new Asiakas(), as3 = new Asiakas();
	 * as1.rekisteroi(); as2.rekisteroi(); as3.rekisteroi();
	 * int id1 = as1.getTunnusNro();
	 * asiakkaat.lisaa(as1); asiakkaat.lisaa(as2); asiakkaat.lisaa(as3);
	 * asiakkaat.etsiId(id1+1) === 1;
	 * asiakkaat.etsiId(id1+2) === 2;
	 * </pre>
	 */
	public int etsiId(int id) {
		for (int i = 0; i < lkm; i++) {
			if (id == alkiot[i].getTunnusNro()) return i;
		}
		return -1;
	}

	/**
	 * Tallentaa asiakkaat tiedostoon
	 * @throws SailoException jos tallentaminen epäonnistuu
	 * Tiedoston muoto:
	 * <pre>
	 * ; mahdollinen kommenttirivi
	 * 1|Aro|Pertti|0405559903|Liikemiehentie 6|Kuokkala
	 * 2|Sallamo|Maija|0554678201|Murkinakatu 13 B|Kortepohja 
	 * 3|Väisänen|Mikko|0446678999|Isokatu 33|Keskusta
	 * </pre>
	 */
	public void tallenna() throws SailoException {
		if (!muutettu) return;

		File fbak = new File(getBakNimi());
		File ftied = new File(getTiedostonNimi());
		fbak.delete();
		ftied.renameTo(fbak);

		try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))) {
			for (Asiakas asiakas : this) {
				fo.println(asiakas.toString());
			}
		} catch ( FileNotFoundException e ) {
			throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
		} catch ( IOException e ) {
			throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
		}
		muutettu = false;
	}

	/**
	 * Tallentaa asiakkaat tiedostoon
	 * @param tied tiedoston nimi
	 * @throws SailoException heittää poikkeuksen jos lukeminen ei onnistu
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * #import java.io.File;
	 * Asiakkaat asiakkaat = new Asiakkaat();
	 * Asiakas asiakas1 = new Asiakas(); asiakas1.vastaaPerttiAro();
	 * Asiakas asiakas2 = new Asiakas(); asiakas2.vastaaPerttiAro();
	 * Asiakas asiakas3 = new Asiakas(); asiakas3.vastaaPerttiAro();
	 * Asiakas asiakas4 = new Asiakas(); asiakas4.vastaaPerttiAro();
	 * Asiakas asiakas5 = new Asiakas(); asiakas5.vastaaPerttiAro();
	 * String tiedNimi = "testi";
	 * File ftied = new File(tiedNimi+".dat");
	 * ftied.delete();
	 * asiakkaat.lueTiedostosta(tiedNimi); #THROWS SailoException
	 * asiakkaat.lisaa(asiakas1);
	 * asiakkaat.lisaa(asiakas2);
	 * asiakkaat.lisaa(asiakas3);
	 * asiakkaat.lisaa(asiakas4);
	 * asiakkaat.lisaa(asiakas5);
	 * asiakkaat.tallenna();
	 * asiakkaat = new Asiakkaat();
	 * asiakkaat.lueTiedostosta(tiedNimi);
	 * Iterator<Asiakas> i = asiakkaat.iterator();
	 * i.next().toString() === asiakas1.toString();
	 * i.next().toString() === asiakas2.toString();
	 * i.next().toString() === asiakas3.toString();
	 * i.next().toString() === asiakas4.toString();
	 * i.next().toString() === asiakas5.toString();
	 * i.hasNext() === false;
	 * asiakkaat.lisaa(asiakas5);
	 * asiakkaat.tallenna();
	 * ftied.delete() === true;
	 * File fbak = new File(tiedNimi+ ".bak");
	 * fbak.delete() === true;
	 * </pre>
	 */
	public void lueTiedostosta(String tied) throws SailoException{
		setTiedostonPerusNimi(tied);
		try (BufferedReader br = new BufferedReader(new FileReader(getTiedostonNimi()))) {
			String rivi = "";
			while ((rivi = br.readLine()) != null ) {
				rivi = rivi.trim();
				if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
				Asiakas asiakas = new Asiakas();
				asiakas.parse(rivi);
				lisaa(asiakas);
			}
			muutettu = false;
		} catch ( FileNotFoundException e) {
			throw new SailoException("Tiedosto: " + getTiedostonNimi() + " ei aukea");
		} catch (IOException e) {
			throw new SailoException("Ongelmia tiedoston kanssa: "+  e.getMessage());
		}	
	}

	/**
	 * Luetaan aikaisemmin annetun nimisestä tiedostosta
	 * @throws SailoException jos tulee poikkeus
	 */
	public void lueTiedostosta() throws SailoException {
		lueTiedostosta(getTiedostonPerusNimi());
	}

	/**
	 * Lisää asiakkaan
	 * @param asiakas lisättävä asiakas
	 * @throws SailoException heittää poikkeuksen virhetilanteessa
	 */
	public void lisaa(Asiakas asiakas) throws SailoException {
		if (lkm >= MAX_ASIAKKAITA) throw new SailoException("Liikaa alkioita!");
		if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, alkiot.length * 2);
		alkiot[lkm] = asiakas;
		lkm++;
		muutettu = true;
	}

	/**
	 * Palauttaa tiedoston nimen, jota käytetään tallennuksen
	 * @return tallennustiedoston nimi
	 */
	public String getTiedostonPerusNimi() {
		return tiedostonPerusNimi;
	}

	/**
	 * Asettaa tiedoston perusnimen
	 * @param nimi tallennustiedoston nimi
	 */
	public void setTiedostonPerusNimi(String nimi) {
		tiedostonPerusNimi = nimi;
	}

	/**
	 * Palauttaa tiedoston nimen, jota käytetään tallennukseen
	 * @return tallennustiedoston nimi
	 */
	public String getTiedostonNimi() {
		return getTiedostonPerusNimi() + ".dat";
	}

	/**
	 * Palauttaa varakopiotiedoston nimen
	 * @return varakopiotiedoston nimi
	 */
	public String getBakNimi() {
		return tiedostonPerusNimi + ".bak";
	}

	/**
	 * Luokka asiakkaisen iteroimiseksi
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * #PACKAGEIMPORT
	 * #import java.util.*;
	 * 
	 * Asiakkaat asiakkaat = new Asiakkaat();
	 * Asiakas as1 = new Asiakas(), as2 = new Asiakas();
	 * as1.rekisteroi(); as2.rekisteroi();
	 * 
	 * asiakkaat.lisaa(as1);
	 * asiakkaat.lisaa(as2);
	 * asiakkaat.lisaa(as1);
	 * 
	 * StringBuffer ids = new StringBuffer(30);
	 * for (Asiakas asiakas : asiakkaat) {
	 *  ids.append(" "+asiakas.getTunnusNro());
	 * }
	 * String tulos = " " + as1.getTunnusNro() + " " + as2.getTunnusNro()+ " " + as1.getTunnusNro();
	 * 
	 * ids.toString() === tulos;
	 * 
	 * Iterator<Asiakas> i = asiakkaat.iterator();
	 * i.next() == as1 === true;
	 * i.next() == as2 === true;
	 * i.next() == as1 === true;
	 * 
	 * i.next(); #THROWS NoSuchElementException
	 *  
	 * </pre>
	 */
	public class AsiakasIterator implements Iterator<Asiakas> {
		private int kohdalla = 0;

		/**
		 * Onko olemassa vielä seuraavaa asiakasta
		 * @see java.util.Iterator#hasNext()
		 * @return true jos on vielä asiakkaita
		 */
		@Override
		public boolean hasNext() {
			return kohdalla < getLkm();
		}

		/**
		 * Annetaan seuraava asiakas
		 * @return seuraava asiakas
		 * @throws NoSuchElementException jos seuraavaa alkiota ei ole
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Asiakas next() throws NoSuchElementException {
			if ( !hasNext() )throw new NoSuchElementException("Ei ole seuraavaa alkiota");
			return anna(kohdalla++);
		}

		/**
		 * Tuhoamista ei ole toteutettu
		 * @throws UnsupportedOperationException aina
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("Ei poisteta");
		}
	}

	/**
	 * Palauttaa iteraatori asiakkaistaan
	 * @return asiakas iteraattori
	 */
	@Override
	public Iterator<Asiakas> iterator() {
		return new AsiakasIterator();
	}

	/**
	 * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet
	 * @param hakuehto millä haetaan
	 * @param k etsittävän kentän indeksi
	 * @return tietorakenteen löytyneistä jäsenistä
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * Asiakkaat asiakkaat = new Asiakkaat();
	 * Asiakas asiakas1 = new Asiakas(); asiakas1.parse("1|Aro|Pertti|0405559903|Liikemiehentie 6|Kuokkala");
	 * Asiakas asiakas2 = new Asiakas(); asiakas2.parse("2|Sallamo|Maija|0554678201|Murkinakatu 13 B|Kortepohja");
	 * Asiakas asiakas3 = new Asiakas(); asiakas3.parse("3|Väisänen|Mikko|0446678999|Isokatu 33|Keskusta");
	 * asiakkaat.lisaa(asiakas1); asiakkaat.lisaa(asiakas2); asiakkaat.lisaa(asiakas3);
	 * List<Asiakas> loytyneet;
	 * loytyneet = (List<Asiakas>)asiakkaat.etsi("*a*", 1);
	 * loytyneet.size() === 2;
	 * loytyneet.get(0) == asiakas1 === true;
	 * </pre>
	 */
	public Collection<Asiakas> etsi(String hakuehto, int k) {
		String ehto = "*";
		if (hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
		int hk = k;
		if (k < 0) hk = 1;
		//	Collection<Asiakas> loytyneet = new ArrayList<Asiakas>();
		List<Asiakas> loytyneet = new ArrayList<Asiakas>();
		for (Asiakas asiakas : this) {
			if (WildChars.onkoSamat(asiakas.anna(hk), ehto)) loytyneet.add(asiakas);
		}
		Collections.sort(loytyneet, new Asiakas.Vertailija(k));
		return loytyneet;
	}

	/**
	 * Korvaa asiakkaan tietorakenteessa. Ottaa asiakkaan omistukseensa.
	 * Etsitään samalla tunnusnumerolla oleva asiakas. Jos ei löydy,
	 * niin lisätään uutena asiakkaana
	 * @param asiakas lisättävän asiakkaan viite. 
	 * @throws SailoException jos tietorakenne on jo täynnä
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException, CloneNotSupportedException
	 * #PACKAGEIMPORT
	 * Asiakkaat asiakkaat = new Asiakkaat();
	 * Asiakas as1 = new Asiakas(), as2 = new Asiakas();
	 * as1.rekisteroi(); as2.rekisteroi();
	 * asiakkaat.getLkm() === 0;
	 * asiakkaat.korvaaTaiLisaa(as1); asiakkaat.getLkm() === 1;
	 * asiakkaat.korvaaTaiLisaa(as2); asiakkaat.getLkm() === 2;
	 * Asiakas as3 = as1.clone();
	 * as3.aseta(3,"Maija");
	 * Iterator<Asiakas> it = asiakkaat.iterator();
	 * it.next() == as1 === true;
	 * asiakkaat.korvaaTaiLisaa(as3); asiakkaat.getLkm() === 2;
	 * it = asiakkaat.iterator();
	 * Asiakas as0 = it.next();
	 * as0 === as3;
	 * as0 == as3 === true;
	 * as0 == as1 === false;
	 * </pre>
	 */
	public void korvaaTaiLisaa(Asiakas asiakas) throws SailoException {
		int id = asiakas.getTunnusNro();
		for ( int i = 0; i < lkm; i++) {
			if (alkiot[i].getTunnusNro() == id) {
				alkiot[i] = asiakas;
				muutettu = true;
				return;
			}
		}
		lisaa(asiakas);
	}

}
