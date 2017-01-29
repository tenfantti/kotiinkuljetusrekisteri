package kotiinkuljetus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Janita
 * @version 7.3.2016
 */
public class Suhteet implements Iterable<Suhde>{
	private boolean muutettu = false;
	private String tiedostonPerusNimi = "suhteet";
	
	private final Collection<Suhde> alkiot = new ArrayList<Suhde>();
	
	/**
	 * Alustetaan suhteet
	 */
	public Suhteet() {
		// ei tee vielä mitään
	}
	
	/**
	 * Ottaa uuden suhteen tietorakenteeseen.
	 * @param suhde lisättävä tuote
	 */
	public void lisaa(Suhde suhde) {
		alkiot.add(suhde);
		muutettu = true;
	}
	
	/**
	 * Lukee suhteet tiedostosta
	 * @param tied tiedoston tiedoston nimen alkuosa
	 * @throws SailoException jos lukeminen epäonnistuu
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * #import java.io.File;
	 * Suhteet suhteet = new Suhteet();
	 * Suhde suhde1 = new Suhde(); suhde1.vastaaSuhde(1);
	 * Suhde suhde2 = new Suhde(); suhde2.vastaaSuhde(2);
	 * Suhde suhde3 = new Suhde(); suhde3.vastaaSuhde(1);
	 * Suhde suhde4 = new Suhde(); suhde4.vastaaSuhde(2);
	 * Suhde suhde5 = new Suhde(); suhde5.vastaaSuhde(1);
	 * String tiedNimi = "testi";
	 * File ftied = new File(tiedNimi+".dat");
	 * ftied.delete();
	 * suhteet.lueTiedostosta(tiedNimi); #THROWS SailoException
	 * suhteet.lisaa(suhde1);
	 * suhteet.lisaa(suhde2);
	 * suhteet.lisaa(suhde3);
	 * suhteet.lisaa(suhde4);
	 * suhteet.lisaa(suhde5);
	 * suhteet.tallenna();
	 * suhteet = new Suhteet();
	 * suhteet.lueTiedostosta(tiedNimi);
	 * Iterator<Suhde> i = suhteet.iterator();
	 * i.next().toString() === suhde1.toString();
	 * i.next().toString() === suhde2.toString();
	 * i.next().toString() === suhde3.toString();
	 * i.next().toString() === suhde4.toString();
	 * i.next().toString() === suhde5.toString();
	 * i.hasNext() === false;
	 * suhteet.lisaa(suhde5);
	 * suhteet.tallenna();
	 * ftied.delete() === true;
	 * File fbak = new File(tiedNimi+ ".bak");
	 * fbak.delete() === true;
	 * </pre>
	 */
	public void lueTiedostosta(String tied) throws SailoException{
		setTiedostonPerusNimi(tied);
		try (BufferedReader br = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
			String rivi;
			while((rivi = br.readLine()) != null ) {
				rivi = rivi.trim();
				if ("".equals(rivi) || rivi.charAt(0)  == ';') continue;
				Suhde suhde = new Suhde();
				suhde.parse(rivi);
				lisaa(suhde);
			}
			muutettu = false;
		} catch ( FileNotFoundException e ) {
			throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea" );
		} catch (IOException e ) {
			throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
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
	 * Asettaa tiedoston perusnimen
	 * @param tied tallennustiedoston perusnimi
	 */
	public void setTiedostonPerusNimi(String tied) {
		tiedostonPerusNimi = tied;
	}
	
	/**
	 * Laitetaan muutos, jolloin pakotetaan tallentamaan.
	 */
	public void setMuutos() {
		muutettu = true;
	}
	
	/**
	 * Poistaa kaikki tietyn asiakkaan tilaukset
	 * @param asiakasNro viite siihen, minkä asiakkaan tilaukset poistetaan
	 * @return montako poistettiin
	 */
	public int poista(int asiakasNro) {
		int n = 0;
		for (Iterator<Suhde> it = alkiot.iterator(); it.hasNext(); ) {
			Suhde til = it.next();
			if (til.getTilausNro() == asiakasNro) {
				it.remove();
				n++;
			}
		}
		if (n > 0) muutettu = true;
		return n;
	}
	
	/**
	 * Palauttaa tiedoston nimen, jota käytetään tallentamiseen
	 * @return tallennustiedoston nimi
	 */
	public String getTiedostonPerusNimi() {
		return tiedostonPerusNimi;
	}
	
	/**
	 * Palauttaa tiedoston nimen, jota käytetään tallennukseen
	 * @return tallennustiedoston nimi
	 */
	public String getTiedostonNimi() {
		return tiedostonPerusNimi + ".dat";
	}
	
	/**
	 * Palauttaa varakopiotiedoston nimen
	 * @return varakopiotiedoston nimi
	 */
	public String getBakNimi() {
		return tiedostonPerusNimi + ".bak";
	}
	
	/**
	 * Tallentaa suhteet tiedostoon
	 * @throws SailoException jos tallentaminen epäonnistuu
	 */
	public void tallenna() throws SailoException {
		if (!muutettu) return;
		
		File fbak = new File(getBakNimi());
		File ftied = new File(getTiedostonNimi());
		fbak.delete();
		ftied.renameTo(fbak);
		
		try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))) {
			for ( Suhde suhde : this ) {
				fo.println(suhde.toString());
			}
		} catch ( FileNotFoundException e) {
			throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
		} catch (IOException e ) {
			throw new SailoException("Tiedoston " + ftied.getName() +  " kirjoittamisessa ongelma");
		}
		
		muutettu = false;
	}
	
	/**
	 * Haetaan kaikki suhteet
	 * @param tunnusNro asiakkaan viitenumero jolle suhteita haetaan
	 * @return tietorakenne jossa on viitteet löydettyihin tuotteisiin
	 */
	public List<Suhde> annaSuhteet(int tunnusNro) {
		List<Suhde> loydetyt = new ArrayList<Suhde>();
		for ( Suhde suhde : alkiot ) {
			if (suhde.getTilausNro() == tunnusNro) loydetyt.add(suhde);
		}
		return loydetyt;
	}
	
	/**
	 * Poistaa valitun suhteen
	 * @param suhde poistettava suhde
	 * @return tosi jos löytyi poistettava suhde
	 */
	public boolean poista(Suhde suhde) {
		boolean ret = alkiot.remove(suhde);
		if (ret) muutettu = true;
		return ret;
	}

	/**
	 * Iteraattori suhteiden läpikäymiseen
	 * @return suhdeiteraattori
	 * @example
	 * <pre name="test">
	 * #PACKAGEIMPORT
	 * #import java.util.*;
	 * 
	 * Suhteet suhteet = new Suhteet();
	 * Suhde suhde1 = new Suhde(2); suhteet.lisaa(suhde1);
	 * Suhde suhde2 = new Suhde(1); suhteet.lisaa(suhde2);
	 * Suhde suhde3 = new Suhde(2); suhteet.lisaa(suhde3);
	 * Suhde suhde4 = new Suhde(1); suhteet.lisaa(suhde4);
	 * Suhde suhde5 = new Suhde(2); suhteet.lisaa(suhde5);
	 * 
	 * Iterator<Suhde> i2=suhteet.iterator();
	 * i2.next() === suhde1;
	 * i2.next() === suhde2;
	 * i2.next() === suhde3;
	 * i2.next() === suhde4;
	 * i2.next() === suhde5;
	 * 
	 * int n = 0;
	 * int tnrot[] = {2,1,2,1,2};
	 * 
	 * for ( Suhde suhde : suhteet) {
	 *  suhde.getTilausNro() === tnrot[n]; n++;
	 *  }
	 *  
	 *  n === 5;
	 *  
	 * </pre>
	 */
	@Override
	public Iterator<Suhde> iterator() {
		return alkiot.iterator();
	}

	/**
	 * Testiohjelma suhteille
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Suhteet suhteet = new Suhteet();
		Suhde suhde1 = new Suhde();
		suhde1.vastaaSuhde(1);
		Suhde suhde2 = new Suhde();
		suhde2.vastaaSuhde(2);
		Suhde suhde3 = new Suhde();
		suhde3.vastaaSuhde(1);
		Suhde suhde4 = new Suhde();
		suhde4.vastaaSuhde(2);
		
		suhteet.lisaa(suhde1);
		suhteet.lisaa(suhde2);
		suhteet.lisaa(suhde3);
		suhteet.lisaa(suhde4);
		
		System.out.println("============= Suhteet testi =================");
		
		List<Suhde> suhteet2 = suhteet.annaSuhteet(1);
		for ( Suhde suhde : suhteet2 ) {
			System.out.println(suhde.getTunnusNro() + " ");
			suhde.tulosta(System.out);
		}

	}
}
