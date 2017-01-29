package fxKotiinkuljetus;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import kotiinkuljetus.Asiakas;
import kotiinkuljetus.Kotiinkuljetus;
import kotiinkuljetus.SailoException;
import kotiinkuljetus.Suhde;
import kotiinkuljetus.Suhteet;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;
import fi.jyu.mit.fxgui.StringGrid.GridRowItem;
import static fxKotiinkuljetus.TietueDialogController.*;

/**
 * Luokka k‰yttˆliittym‰n tapahtumien hoitamiseksi
 * @author jahasall
 * @version 8.2.2016
 * @version 12.4.2016
 */
@SuppressWarnings("unused")

public class KotiinkuljetusGUIController implements Initializable {
	@FXML private TextField hakuText;
//  @FXML private ComboBoxChooser hakuehto;
	@FXML private ComboBoxChooser cbKentat;
    @FXML private TextField hakuehto;
    @FXML private TextField etunimiText;
    @FXML private TextField puhelinnumeroText;
    @FXML private TextField osoiteText;
    @FXML private TextField asuinalueText;
    @FXML private TextField aikaText;
    @FXML private TextField maksutapaText;
    @FXML private TextField sukunimiText;
    @FXML private ListChooser chooserAsiakkaat;
    @FXML private ScrollPane panelAsiakas;
    @FXML private Label labelVirhe;
    @FXML private StringGrid<Suhde> tableTilaukset;
    @FXML private GridPane gridAsiakas;
    @FXML private GridPane gridTilaus;
     
	/**
	 * Lis‰t‰‰n uusi asiakas
	 */
	@FXML void handleUusiAsiakas() {
		// avaaAsiakas();
		uusiAsiakas();
		
    }
	
	/**
	 * Lis‰t‰‰n uusi tilaus
	 */
    @FXML void handleUusiTilaus() {
    	// Dialogs.showMessageDialog("Myˆhemmin osataan lis‰t‰ tilaukset!");
    	// avaaTilaus();
    //	uusiTilaus();
    	uusiSuhde();
    }
    
    /**
     * Tallennetaan tiedot/muutokset
     */
    @FXML void handleTallenna() {
    	tallenna(true);
    }
    
    /**
     * Poistetaan tietty tilaus
     */
    @FXML void handlePoista() {
    	Dialogs.showQuestionDialog("Poistetaanko", "Oletko varma ett‰ haluat poistaa?", "Kyll‰", "Ei");
    	// Lis‰‰ poistamisesta myˆhemmin
    }
    
    /**
     * Tallennetaan tiedot ja lopetetaan ohjelma
     */
    @FXML void handleLopeta() {
    	tallenna(false);
    	Platform.exit();
    }

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();
		
	}

    @FXML void handleApua(ActionEvent event) {
    	avusta();
    }

    @FXML void handleAsiakas(MouseEvent event) {
    	// Myˆhemmin tehd‰‰n muutokset
    } 

  /*  @FXML
    void handleHaku() {
    	Dialogs.showMessageDialog("Osaan hakea myˆhemmin");
    } */
    
    @FXML
    void handleHakuehto() {
 //   	String hakukentta = cbKentat.getSelectionModel().getSelectedItem();
    	String ehto = hakuehto.getText();
    	if ( ehto.isEmpty() ) naytaVirhe(null);
    	hae(0);
   // 	else hae(asiakasKohdalla.getTunnusNro());
    }

    @FXML
    void handleMuokkaaTilausta(ActionEvent event) {
    //	Dialogs.showMessageDialog("Osaan muokata myˆhemmin");
    //	muokkaaTilausta();
    	Dialogs.showMessageDialog("P‰‰set muokkaamaan tilausta kun kaksoisklikkaat haluamaasi vaihtoehtoa taulukossa!");
    }

    @FXML
    void handleNaytaTiedot() {
    	avaaTietoja();
    }

    @FXML
    void handlePoistaAsiakas() {
    //	Dialogs.showMessageDialog("Osaan poistaa myˆhemmin");
    	poistaAsiakas();
    }
    
    @FXML
    void handlePoistaTilaus() {
    	poistaTilaus();
    }

    @FXML
    void handleTulosta() {
    	TulostusController tulostusCtrl = TulostusController.tulosta(null);
    	tulostaValitut(tulostusCtrl.getTextArea());
    	// avaaTulostus();
    }
    
// ====================================================================================================================================    
    private Kotiinkuljetus kotiinkuljetus;
    private ListView<Asiakas> listAsiakkaat = new ListView<Asiakas>();
    private ObservableList<Asiakas> listdataAsiakkaat = FXCollections.observableArrayList();
    private TextArea areaAsiakas = new TextArea();
    private Asiakas asiakasKohdalla = null;
//    private Tilaus tilausKohdalla = null;
    private String kuljetuksennimi = "kuljetus";
    private EditoitavaAsiakas editoitavaAsiakas = new EditoitavaAsiakas();
//    private EditoitavaTilaus editoitavaTilaus = new EditoitavaTilaus();
    private Asiakas apuasiakas = new Asiakas();
//    private Tilaus aputilaus = new Tilaus();
    private Suhde apusuhde = new Suhde();
//    private TextArea areaTilaukset = new TextArea();
    private TextField[] edits;
    private TextField[] edits2;
//    private TextField[] edits3;
    private int kentta;
    private TableView<Suhde> tableViewTilaukset = new TableView<>();

    /**
     * Luokka jossa on tallessa editoitavaksi otettu
     * tai uusi asiakas ja sen alkuper‰inen arvo.
     * Alkuper‰isest‰ tehd‰‰n klooni jota muokataan.
     * Sen j‰lkeen voidaan verrata onko editointia
     * tapahtunut ja tarvitaanko tallennusta.
     * Jos ei haluta tallenusta, niin mit‰‰n ei tapahdu.
     * 
     * @author Janita
     * @version 8.4.2016
     */
    private class EditoitavaAsiakas {
    	private Asiakas editoitava;
    	private Asiakas alkuperainen;
    	
    	public EditoitavaAsiakas() { editoitava = null; }
    	
    	/**
    	 * Asetetaan editoitava asiakas. Jos on olemassa entinen editoitava
    	 * niin pit‰‰ tarkistaa onko se muuttunut ja pit‰‰kˆ se tallentaa.
    	 * @param asiakas uusi editoitava asiakas
    	 * @param saakoHakea saako samalla hakea listan uudelleen
    	 * @return asetettu editoitava asiakas, voi olla myˆs null
    	 */
    	private boolean tallennaJaAseta(Asiakas asiakas, boolean saakoHakea) {
    		boolean oliko = tarkistaMuutos(saakoHakea);
    		this.alkuperainen = asiakas;
    		if (asiakas == null) editoitava = null;
    		else try {
    			this.editoitava = alkuperainen.clone();
    		} catch (CloneNotSupportedException e) {
    			// pit‰isi tulla aina asiakas
    		}
    		
    		return oliko;
    	}
    	
    	private boolean tarkistaMuutos(boolean saakoHakea) {
    		if ( editoitava == null ) return false;
    		if (!muuttunut() ) return false;
    		if ( !Dialogs.showQuestionDialog("Asiakkaan tiedot muuttuneet", "Tallennetaanko?", "Kyll‰", "Ei")) return false;
    		tallenna(saakoHakea);
    		editoitava = null;
    		return true;
    	}
    	
    	public boolean tallennaJaTyhjenna(boolean saakoHakea) {return tallennaJaAseta(null, saakoHakea); }
    	private boolean muuttunut() { return !editoitava.equals(alkuperainen); }
    	public Asiakas getEditoitava() { return editoitava; }
    	public boolean onkoKetaan() { return editoitava != null; }
    	public void poista() { editoitava = null;}
    	
    	
    }
    
    /**
     * Luokka jossa on tallessa editoitavaksi otettu
     * tai uusi tilaus ja sen alkuper‰inen arvo.
     * Alkuper‰isest‰ tehd‰‰n klooni jota muokataan.
     * Sen j‰lkeen voidaan verrata onko editointia
     * tapahtunut ja tarvitaanko tallennusta.
     * Jos ei haluta tallenusta, niin mit‰‰n ei tapahdu.
     * 
     * @author Janita
     */
/*        private class EditoitavaTilaus {
    	private Tilaus editoitava;
    	private Tilaus alkuperainen;

    	public EditoitavaTilaus() { editoitava = null; } */

    	/**
    	 * Asetetaan editoitava tilaus. Jos on olemassa entinen editoitava
    	 * niin pit‰‰ tarkistaa onko se muuttunut ja pit‰‰kˆ se tallentaa.
    	 * @param tilaus uusi editoitava tilaus
    	 * @param saakoHakea saako samalla hakea listan uudelleen
    	 * @return asetettu editoitava tilaus, voi olla myˆs null
    	 */
/*    	private boolean tallennaJaAseta(Tilaus tilaus, boolean saakoHakea) {
    		boolean oliko = tarkistaMuutos(saakoHakea);
    		this.alkuperainen = tilaus;
    		if (tilaus == null) editoitava = null;
    		else try {
    			this.editoitava = alkuperainen.clone();
    		} catch (CloneNotSupportedException e) {
    			// pit‰isi tulla aina tilaus
    		}

    		return oliko;
    	}

    	private boolean tarkistaMuutos(boolean saakoHakea) {
    		if ( editoitava == null ) return false;
    		if (!muuttunut() ) return false;
    		if ( !Dialogs.showQuestionDialog("Tilauksen tiedot muuttuneet", "Tallennetaanko?", "Kyll‰", "Ei")) return false;
    		tallenna(saakoHakea);
    		editoitava = null;
    		return true;
    	}

    	public boolean tallennaJaTyhjenna(boolean saakoHakea) {return tallennaJaAseta(null, saakoHakea); }
    	private boolean muuttunut() { return !editoitava.equals(alkuperainen); }
    	public Tilaus getEditoitava() { return editoitava; }
    	public boolean onkoKetaan() { return editoitava != null; }
    	public void poista() { editoitava = null;}


    } */
    
    
    
    
    /**
     * Luokka, jolla hoidellaan miten asiakas n‰ytet‰‰n listassa
     */
    public static class CellAsiakas extends ListCell<Asiakas> {
    	@Override protected void updateItem(Asiakas item, boolean empty) {
    		super.updateItem(item, empty); // muuten valinta ei n‰y
    		setText(empty ? "" : item.getNimi());
    	}
    }
    
    private void alusta() {
    	listAsiakkaat.setItems(listdataAsiakkaat);
  //  	gridAsiakas.getChildren().clear();
    	gridTilaus.getChildren().clear();
    	edits = new TextField[apuasiakas.getKenttia()];
  //  	edits2 = new TextField[apuasiakas.getKenttia()];
    	ObservableList<String> kentat = cbKentat.getItems();
    	kentat.clear();
    	for (int k = apuasiakas.ekaKentta(); k < apuasiakas.getKenttia(); k++) {
    		kentat.add(apuasiakas.getKysymys(k));
    	}
    	cbKentat.getSelectionModel().select(0); 
    	
  /*  	for (int i = 0, k = apuasiakas.ekaKentta(); k < apuasiakas.getKenttia(); k++, i++) {
    	//	Label label = new Label(apuasiakas.getKysymys(k));
    		String otsikko = apuasiakas.getKysymys(k);
    		kentat.add(otsikko);
    		Label label = new Label(otsikko);
    		gridAsiakas.add(label, 0, i);
    		TextField edit = new TextField();
    		edits[k] = edit;
    		final int kk = k;
    		edit.setOnKeyReleased(e -> kasitteleMuutosAsiakkaaseen(kk, (TextField)(e.getSource())));
    		gridAsiakas.add(edit, 1, i);
    	} */
    	
    	edits = TietueDialogController.luoKentat(gridAsiakas, apuasiakas);
    	for (TextField edit : edits)
    		if (edit != null) {
    			edit.setEditable(false);
    			edit.setOnMouseClicked(e -> { if (e.getClickCount() > 1) muokkaa(getFieldId(e.getSource(), 0)); });
    			edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit, 0));
    		} 
    	
 /*   	for (int i = 0, k = aputilaus.ekaKentta(); k < aputilaus.getKenttia()-1; k++, i++) {
    		String otsikko = aputilaus.getKysymys(k);
    		kentat.add(otsikko);
    		Label label = new Label(otsikko);
    		gridTilaus.add(label, 0, i);
    		TextField edit = new TextField();
    		edits2[k] = edit;
    		final int kk = k;
    		edit.setOnKeyReleased(e -> kasitteleMuutosTilaukseen(kk, (TextField)(e.getSource())));
    		// 		edit.setOnKeyReleased(e -> kasitteleMuutosAsiakkaaseen(kk, (TextField)(e.getSource())));
    		gridTilaus.add(edit, 1, i);
    	}  */
    	
   /*  	edits2 = TietueDialogController.luoKentat(gridTilaus, new Tilaus());
    	for (TextField edit : edits2) {
    		if (edit != null) {
    			edit.setEditable(true);
    			edit.setOnMouseClicked(e -> { if (e.getClickCount() > 1) muokkaa(getFieldId(e.getSource(), 0)); });
    			edit.focusedProperty().addListener((a,o, n) -> kentta = getFieldId(edit, 0));
    		}
    	}  */

 //   	panelAsiakas.setContent(areaAsiakas);
 //   	areaAsiakas.setFont(new Font("Courier New", 12));
 //   	panelAsiakas.setFitToHeight(true);
    	cbKentat.getSelectionModel().select(0);
    	BorderPane parent = (BorderPane)chooserAsiakkaat.getParent();
		listAsiakkaat.setPrefHeight(chooserAsiakkaat.getPrefHeight());
		listAsiakkaat.setPrefWidth(chooserAsiakkaat.getPrefWidth());
		parent.setBottom(listAsiakkaat);
		listAsiakkaat.setCellFactory(p -> new CellAsiakas());

		listAsiakkaat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				if (!editoitavaAsiakas.onkoKetaan()) {
					naytaAsiakas(null);
			//		naytaTilaus(null);
				}
				return;
			}
			if (!editoitavaAsiakas.tallennaJaTyhjenna(false)) {
				naytaAsiakas(newValue);
			//	naytaTilaus(newValue);
				return;
			}
			Platform.runLater(() -> {
				hae(0);
				naytaAsiakas(newValue);
			//	naytaTilaus(newValue);
			});

		});

		// Tilaukset tilap‰isesti tulostetaan TextAreaan
    	BorderPane parent2 = (BorderPane)tableTilaukset.getParent();
    	parent2.setCenter(tableViewTilaukset);
    //	parent2.setContent(tableTilaukset);
    /*	int eka = apusuhde.ekaKentta();
    	int lkm = apusuhde.getKenttia();
    	String[] headings = new String[lkm-eka];
    	for (int i = 0, k = eka; k < lkm; i++, k++) {
    		headings[i] = apusuhde.getKysymys(k);
    	}
    	tableTilaukset.initTable(headings);
    	tableTilaukset.setColumnSortOrderNumber(1);
    	tableTilaukset.setColumnSortOrderNumber(2);
    	tableTilaukset.setColumnWidth(1,  60);
    	tableTilaukset.setColumnWidth(2, 60);
    	tableTilaukset.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	tableTilaukset.setEditable(false);
    	tableTilaukset.setPlaceholder(new Label("Ei viel‰ tilauksia"));  */
    	tableViewTilaukset.setPlaceholder(new Label("Ei viel‰ tilauksia"));
    	tableTilaukset = null;
    	SuhdeCell.alustaSuhdeTable(tableViewTilaukset, (til, knro, arvo) -> {
    		String virhe = til.aseta(knro, arvo);
    		naytaVirhe(virhe);
    		if (virhe == null) kotiinkuljetus.setTilaustenMuutos();
    		return virhe;
    	});
    //	SuhdeCell.getSelected(tableViewTilaukset)
    }
    
    /**
     * N‰ytt‰‰ listasta valitun asiakkaan tiedot
     */
    private void naytaAsiakas(Asiakas asiakas) {
    	asiakasKohdalla = listAsiakkaat.getSelectionModel().getSelectedItem();
    	if (asiakasKohdalla == null) return;
     /*	for (int k = apuasiakas.ekaKentta(); k < apuasiakas.getKenttia(); k++) {
    		String arvo = "";
    		if (asiakas != null) arvo = asiakas.anna(k);
    		TextField edit = edits[k];
    		edit.setText(arvo);
    		Dialogs.setToolTipText(edit, "");
    		edit.getStyleClass().removeAll("virhe");
    	} */
    	TietueDialogController.naytaTietue(edits, asiakasKohdalla);
    	naytaTilaukset(asiakas);
  //  	naytaTilaus(asiakas);
  //  	gridAsiakas.setVisible(asiakas != null);
    	naytaVirhe(null);
    	
	/*	asiakasKohdalla = listAsiakkaat.getSelectionModel().getSelectedItem();
		if (asiakasKohdalla == null) {
			areaAsiakas.clear();
			return;
		}
		areaAsiakas.setText("");
		try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaAsiakas)) {
			tulosta(os,asiakasKohdalla);
		} */
		
	}

    private void muokkaa(int k){
    	if (asiakasKohdalla == null) return;
    	try {
    		Asiakas asiakas;
    		asiakas = TietueDialogController.kysyTietue(null, asiakasKohdalla.clone(), k);
    		if (asiakas == null) return;
    		kotiinkuljetus.korvaaTaiLisaa(asiakas);
    	} catch (CloneNotSupportedException e) {
    		//
    	} catch (SailoException e) {
    		Dialogs.showMessageDialog(e.getMessage());
    	}
    } 
    
    /**
     * Muokataan valittua tilausta
     */
    private void muokkaaTilausta() {
   // 	if (tableViewTilaukset == null) return;
   // 	SuhdeCell.muokkaa(tableViewTilaukset);
 /*    	int r = tableTilaukset.getRowNr();
    	if (r < 0) return;
    	Suhde suhde = tableTilaukset.getObject();
    	if (suhde == null) return;
    	int k = tableTilaukset.getColumnNr()+suhde.ekaKentta();
    	try {
    	//	tilaus = TilausController.kysyTilaus(null, tilaus.clone());
    		if (suhde == null) return;
    		kotiinkuljetus.lisaa(suhde);
    		naytaTilaukset(asiakasKohdalla);
    		tableTilaukset.selectRow(r);
   // 	} catch (CloneNotSupportedException e) {
    		// clone on tehty
    	} catch (SailoException e) {
    		Dialogs.showMessageDialog("Ongelmia lis‰‰misess‰: "+ e.getMessage());
    	} */
    }
    
    private void setTitle(String title) {
    	ModalController.getStage(hakuehto).setTitle(title);
    }
    
    /**
     * Alustaa kuljetuksen lukemalla valitusta tiedostosta
     * @param nimi tiedosto josta kuljetuksen tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstin‰
     */
    protected String lueTiedosto(String nimi) {
    	kuljetuksennimi = nimi;
    	setTitle("Kuljetus - " + kuljetuksennimi);
    	try {
    		kotiinkuljetus.lueTiedostosta(nimi);
    		hae(0);
    		return null;
    	} catch (SailoException e) {
    		hae(0);
    		String virhe = e.getMessage();
    		if ( virhe != null) Dialogs.showMessageDialog(virhe);
    		return virhe;
    	}
    }
    
    /**
    private void muokkaa(String viesti) {
    	LisaaAsiakasController.kysyAsiakas(null, asiakasKohdalla);
    }*/

	/**
     * Tallennetaan tiedot
	 * @param saakoHakea true jos saa hakea, muuten false
     * @return null jos onnistuu, muuten virhe tekstin‰
     */
    public String tallenna(boolean saakoHakea) {
    	try {
    		if ( editoitavaAsiakas.onkoKetaan() ) {
    			Asiakas editoitava = editoitavaAsiakas.getEditoitava();
        		editoitavaAsiakas.poista();
        		kotiinkuljetus.korvaaTaiLisaa(editoitava);
        		final int anro = editoitava.getTunnusNro();
        		if ( saakoHakea ) hae(anro);	
    		}
   /* 		if (editoitavaTilaus.onkoKetaan()) {
    			Tilaus editoitava = editoitavaTilaus.getEditoitava();
    			editoitavaTilaus.poista();
    			kotiinkuljetus.korvaaTaiLisaa(editoitava);
    			final int tnro = editoitava.getAsiakasNro();
    			if (saakoHakea) hae(tnro);
    		} */
    		kotiinkuljetus.tallenna();
    		return null;	
    	}   catch (SailoException e) {
    		Dialogs.showMessageDialog("Tallennuksessa ongelmia! "+e.getMessage());
    		return e.getMessage();
    	}
    }
    
    /**
     * Tallettaa nykyisen mahdollisesti muutetun asiakkaan
     * ja sitten koko tiedoston
     * @return null jos menee hyvin, muuten virheteksti
     */
    public String tallenna() {
    	return tallenna(false);
    }
    
    /**
     * N‰ytet‰‰n ohjelman suunnitelma erillisess‰ selaimessa
     */
    private void avusta() {
    	Desktop desktop = Desktop.getDesktop();
    	try {
    		URI uri = new URI("https://trac.cc.jyu.fi/projects/ohj2ht/wiki/k2016/suunnitelmat/jahasall");
    		desktop.browse(uri);
    	} catch (URISyntaxException e) {
    		return;
    	} catch(IOException e) {
    		return;
    	}
    }
    
    private void naytaVirhe(String virhe) {
    	if ( virhe == null || virhe.isEmpty() ) {
    		labelVirhe.setText("");
    		labelVirhe.getStyleClass().removeAll("virhe");
    		return;
    	}
    	labelVirhe.setText(virhe);
    	labelVirhe.getStyleClass().add("virhe");
    }
    
    /**
     * Varmistetaan voidaanko sulkea ohjelma
     * @return true jos saa sulkea sovelluksen, muuten ei
     */
    public boolean SaakoSulkea() {
    	editoitavaAsiakas.tallennaJaTyhjenna(false);
    	tallenna(false);
    	return true;
    }
    
    /**
     * Luo uuden asiakkaan jota aletaan editoimaan
     */
    private void uusiAsiakas() {
    	try {
        	Asiakas uusi = new Asiakas();
        	uusi = TietueDialogController.kysyTietue(null, uusi, 0);
        	if (uusi == null) return;
        	uusi.rekisteroi();
        	kotiinkuljetus.lisaa(uusi);
        	hae(uusi.getTunnusNro());
    	} catch (SailoException e) {
    		Dialogs.showMessageDialog("Ongelmia uuden luomisessa "+ e.getMessage());
    		return;
    	}  

      //  Asiakas uusi = new Asiakas();
 /*       try {
			uusi = ModalController.showModal(TilausController.class.getResource("LisaaAsiakasView.fxml"), "Asiakkaan lis‰‰minen", null, uusi.clone());
		} catch (CloneNotSupportedException e) {
			Dialogs.showMessageDialog("Ei onnistu avautumaan");
	//		e.printStackTrace();
		} */
  //  	uusi.vastaaPerttiAro(); // TODO: aukaise dialogi...
    //	uusi.rekisteroi(); // TODO: rekisterˆi vasta kun tullaan dialogista
  /*  	try {
    		kotiinkuljetus.lisaa(uusi);
    	}
    	catch (SailoException e) {
    		Dialogs.showMessageDialog("Ongelmia uuden luomisessa "+ e.getMessage());
    		return;
    	}
    	hae(uusi.getTunnusNro()); */
//    	editoitavaAsiakas.tallennaJaAseta(uusi, true);
//    	listAsiakkaat.getSelectionModel().clearSelection();
//    	naytaAsiakas(uusi);
    }
    
    /**
     * Tekee uuden tyhj‰n tilauksen editointia varten
     */
    public void uusiTilaus() {
   // 	Tilaus tilaus = new Tilaus();
   // 	tilaus = ModalController.showModal("", title, modalityStage, tilaus)
    	asiakasKohdalla = getAsiakasKohdalla();
    	if (asiakasKohdalla == null) return;
 /*   	Tilaus til = new Tilaus(asiakasKohdalla.getTunnusNro());
    	til.rekisteroi();
   // 	til.vastaaPizza(asiakasKohdalla.getTunnusNro());
    	try {
    		kotiinkuljetus.lisaa(til);
    	} catch (SailoException e) {
    		Dialogs.showMessageDialog("Ongelmia lis‰‰misess‰! "+e.getMessage());
    	} */
    	
    	Suhde suhde = new Suhde(asiakasKohdalla.getTunnusNro());
    	suhde.rekisteroi();
 //   	suhde.vastaaSuhde(til.getAsiakasNro());
    	try {
    		kotiinkuljetus.lisaa(suhde);
    	} catch (SailoException e) {
    		Dialogs.showMessageDialog("Ongelmia suhteen lis‰‰misess‰! " + e.getMessage());
    	}
    	naytaTilaukset(asiakasKohdalla);
    	SuhdeCell.editLast(tableViewTilaukset);
    }
    
    /**
     * Tekee uuden tyhj‰n suhteen editointia varten
     */
    private void uusiSuhde() {
  /*  	if (asiakasKohdalla == null) return;
    	try {
    		Suhde uusi = new Suhde(asiakasKohdalla.getTunnusNro());	
    		uusi = TietueDialogController.kysyTietue(null, uusi, 0);
    		if (uusi == null) return;
    		uusi.rekisteroi();
    		kotiinkuljetus.lisaa(uusi);
    		naytaTilaukset(asiakasKohdalla);
    	} catch (SailoException e) {
    		Dialogs.showMessageDialog("Lis‰‰minen ep‰onnistui: "+ e.getMessage());
    	}
    	
    	SuhdeCell.editLast(tableViewTilaukset); */
    	
  //  	Tilaus tilausKohdalla = getTilausKohdalla();
    	
    	Suhde suhde = new Suhde(asiakasKohdalla.getTunnusNro());	
    	suhde.rekisteroi();
  //  	suhde.vastaaSuhde(asiakasKohdalla.getAsiakasNro());
    	try {
    		kotiinkuljetus.lisaa(suhde);
    	} catch (SailoException e) {
    		Dialogs.showMessageDialog("Ongelmia suhteen lis‰‰misess‰! " + e.getMessage());
    	}
    	naytaTilaukset(asiakasKohdalla);
    	Platform.runLater(() -> SuhdeCell.editLast(tableViewTilaukset)); 
 //   	SuhdeCell.editLast(tableViewTilaukset); 
    	
   /* 	if (tilausKohdalla == null) return;
    	try {
    		Suhde uusi = new Suhde(tilausKohdalla.getAsiakasNro());
    		uusi = TietueDialogController.kysyTietue(null, uusi, 0);
    		if (uusi == null) return;
    		uusi.rekisteroi();
    		kotiinkuljetus.lisaa(uusi);
    		naytaTilaukset(asiakasKohdalla);
    		tableTilaukset.selectRow(1000);
    	} catch (SailoException e) {
    		Dialogs.showMessageDialog("Lis‰‰minen ep‰onnistui: " + e.getMessage());
    	} */
    }
    
    /**
     * Poistetaan listalta valittu asiakas
     */
    private void poistaAsiakas() {
    	Asiakas asiakas = getAsiakasKohdalla();
    	if (asiakas == null) return;
    	if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko asiakas: "+ asiakas.getNimi(), "Kyll‰", "Ei")) return;
    	kotiinkuljetus.poista(asiakas.getTunnusNro());
    	int index = listAsiakkaat.getSelectionModel().getSelectedIndex();
    	hae(0);
    	listAsiakkaat.getSelectionModel().select(index);
    }
    
    /**
     * Poistetaan tilaustaulukosta valittu kohdalla oleva tilaus
     */
    private void poistaTilaus() {
    	int rivi = tableViewTilaukset.getFocusModel().getFocusedCell().getRow();
    	if (rivi < 0) return;
    	Suhde tilaus = SuhdeCell.getSelected(tableViewTilaukset);
    	if (tilaus == null) return;
    	kotiinkuljetus.poistaTilaus(tilaus);
    	naytaTilaukset(getAsiakasKohdalla());
    	int tilauksia = tableViewTilaukset.getItems().size();
    	if (rivi >= tilauksia) rivi = tilauksia - 1;
    	tableViewTilaukset.getFocusModel().focus(rivi);
    	tableViewTilaukset.getSelectionModel().select(rivi);
    }
    
    /**
     * N‰ytet‰‰n tilaukset taulukkoon. Tyhjennet‰‰n ensin
     * taulukko ja sitten lis‰t‰‰n siihen kaikki harrastukset
     * @param asiakas asiakas, jonka tilaukset n‰ytet‰‰n
     */
    private void naytaTilaukset(Asiakas asiakas) {
    //	areaTilaukset.clear();
    //	tableTilaukset.clear();
    	ObservableList<Suhde> items = tableViewTilaukset.getItems();
    //	ObservableList<GridRowItem<Tilaus>> items = tableTilaukset.getItems();
    	items.clear();
    	if (asiakas == null) return;
    /*	try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTilaukset)) {
    		tulostaTilaukset(os, asiakas);
    	} */
  /*  	Tilaus hae = new Tilaus();
    	hae = kotiinkuljetus.annaTilaus(asiakas);
    	if (hae == null) {
    	 for (int i = 1; i < edits2.length-1; i++) {
    		 edits2[i].setText("");
    	 }
    	} */
    	List<Suhde> suhteet;
    	try {
    		suhteet = kotiinkuljetus.annaSuhteet(asiakas);
    		if (suhteet == null || suhteet.size() == 0) return;
    		items.addAll(suhteet);
    	/*	for (Tilaus tilaus : tilaukset)
    			naytaTilaus(tilaus); */
    	} catch (SailoException e) {
    		naytaVirhe(e.getMessage());
    	}
    }
    
/*    private void naytaTilaus(Asiakas asiakas) {
    //	tableTilaukset.clear();
    	if (asiakas == null) return;
    	Tilaus tilaus = kotiinkuljetus.annaTilaus(asiakas);
    	tilausKohdalla = tilaus;
    	
    	if (tilaus == null) return;
    	
    	for (int k = aputilaus.ekaKentta(); k < aputilaus.getKenttia()-1; k++) {
    		String arvo = "";
    		if (tilaus != null) arvo = tilaus.anna(k);
    		TextField edit = edits2[k];
    		edit.setText(arvo);
    		Dialogs.setToolTipText(edit, "");
    		edit.getStyleClass().removeAll("virhe");
    	}
    	
    	gridTilaus.setVisible(asiakas != null);
    	naytaVirhe(null);
    	
    } */
    
    /**
     * Lis‰t‰‰n yhden tilauksen tiedot taulukkoon
     * @param tilaus tilaus joka n‰ytet‰‰n
     */
/*    private void naytaTilaus(Tilaus tilaus) {
    	int kenttia = tilaus.getKenttia();
    	String[] rivi = new String[kenttia-tilaus.ekaKentta()];
    	for (int i = 0, k = tilaus.ekaKentta(); k < kenttia-1; i++, k++) {
    		rivi[i] = tilaus.anna(k);
    	}
		tableTilaukset.add(tilaus, rivi);
    } */
    
    /**
     * @param kuljetus jota k‰ytet‰‰n t‰ss‰ k‰yttˆliittym‰ss‰
     */
    public void setKuljetus(Kotiinkuljetus kuljetus) {
    	this.kotiinkuljetus = kuljetus;
   // 	naytaAsiakas();
    }
    
    /**
     * Hakee asiakkaiden tiedot listaan
     * @param tunnusNro asiakkaan nro, joka aktivoidaan haun j‰lkeen
     */
    private void hae(int tunnusNro) {
    	int anro = tunnusNro;
    	if ( anro <= 0 ) {
    		Asiakas kohdalla = getAsiakasKohdalla();
    		if (kohdalla != null) anro = kohdalla.getTunnusNro();
    	}
    	int k = cbKentat.getSelectionModel().getSelectedIndex() + apuasiakas.ekaKentta();
    	String ehto = hakuehto.getText();
    /*	if ( k > 0 || ehto.length() > 0) {
    		naytaVirhe(String.format("Ei osata hakea (kentt‰: %d, ehto: %s)", k, ehto));
    		return;
    	} */
    	if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
    	naytaVirhe(null);
    	
		listdataAsiakkaat.clear();
		listAsiakkaat.setItems(null);
		int index = 0;
		Collection<Asiakas> asiakkaat;
		try {
			asiakkaat = kotiinkuljetus.etsi(ehto, k);
			int i = 0;
			for (Asiakas asiakas : asiakkaat) {
				if (asiakas.getTunnusNro() == tunnusNro) index = i;
				listdataAsiakkaat.add(asiakas);
				i++;
			}
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Asiakkaan hakemisessa ongelmia! "+e.getMessage());
		}
		
		listAsiakkaat.setItems(listdataAsiakkaat);
		listAsiakkaat.getSelectionModel().select(index); // t‰st‰ tulee muutosviesti joka n‰ytt‰‰ asiakkaan
	}
    
    /**
     * K‰sitell‰‰n asiakkaaseen tullut muutos. Mik‰li mik‰‰n asiakas ei ole 
     * editoitu, niin tehd‰‰n kohdalla olevasta kopio ja editoidaan sit‰.
     * Jos ei ole kohdalla olevaa, niin ei editoida mit‰‰n.
     * @param k mit‰ kentt‰‰ muutos koskee
     * @param edit muuttunut kentt‰
     */
    @SuppressWarnings("synthetic-access")
	protected void kasitteleMuutosAsiakkaaseen(int k, TextField edit) {
    	if ( !editoitavaAsiakas.onkoKetaan() ) editoitavaAsiakas.tallennaJaAseta(getAsiakasKohdalla(), true);
    	if ( !editoitavaAsiakas.onkoKetaan() ) return; // ei ole muokattavaa asiakasta
    	
    	Asiakas editoitava = editoitavaAsiakas.getEditoitava();
    	String s = edit.getText();
    	String virhe = null;
    	virhe = editoitava.aseta(k, s);
    	if (virhe == null) {
    		Dialogs.setToolTipText(edit, "");
    		edit.getStyleClass().removeAll("virhe");
    		naytaVirhe(virhe);
    	} else {
    		Dialogs.setToolTipText(edit, virhe);
    		edit.getStyleClass().add("virhe");
    		naytaVirhe(virhe);
    	}
    }
    
    /**
     * K‰sitell‰‰n asiakkaaseen tullut muutos. Mik‰li mik‰‰n asiakas ei ole 
     * editoitu, niin tehd‰‰n kohdalla olevasta kopio ja editoidaan sit‰.
     * Jos ei ole kohdalla olevaa, niin ei editoida mit‰‰n.
     * @param k mit‰ kentt‰‰ muutos koskee
     * @param edit muuttunut kentt‰
     */
 /**   protected void kasitteleMuutosTilaukseen(int k, TextField edit) {
    	if ( !editoitavaTilaus.onkoKetaan() ) editoitavaTilaus.tallennaJaAseta(getTilausKohdalla(), true);
    	if ( !editoitavaTilaus.onkoKetaan() ) return; // ei ole muokattavaa tilausta
    	
    	Tilaus editoitava = editoitavaTilaus.getEditoitava();
    	String s = edit.getText();
    	String virhe = null;
    	virhe = editoitava.aseta(k, s);
    	if (virhe == null) {
    		Dialogs.setToolTipText(edit, "");
    		edit.getStyleClass().removeAll("virhe");
    		naytaVirhe(virhe);
    	} else {
    		Dialogs.setToolTipText(edit, virhe);
    		edit.getStyleClass().add("virhe");
    		naytaVirhe(virhe);
    	}
    } */
    
    /**
     * @return listasta valittu kohdalla oleva asiakas
     */
    private Asiakas getAsiakasKohdalla() {
    	return listAsiakkaat.getSelectionModel().getSelectedItem();
    }
    
  /*  private Tilaus getTilausKohdalla() {
    	return kotiinkuljetus.annaTilaus(asiakasKohdalla);
    } */

	/**
	 * @return true jos onnistuu, muuten false
	 */
	public boolean avaaAsiakas() {
    	String uusiasiakas = LisaaAsiakasController.kysyAsiakas(null, "");
    	if (uusiasiakas == null) return false;
    	return true;
    }
    
    /**
     * @return true jos onnistuu, muuten false
     */
    public boolean avaaTilaus() {
    	String uusitilaus = TilausController.kysyTilaus(null, "");
    	if (uusitilaus == null) return false;
    	return true;
    }
    
    /**
     * @return true jos onnistuu, muuten false
     */
    public boolean avaaTulostus() {
    	String uusitulostus = TulostusController.kysyTulostus(null, "");
    	if (uusitulostus == null) return false;
    	return true;
    }
    
    /**
     * @return true jos onnistuu, muuten false
     */
    public boolean avaaTietoja() {
    	String uusitieto = TietojaController.kysyTietoja(null, "");
    	if (uusitieto == null) return false;
    	return true;
    }

    /**
     * @param kotiinkuljetus mik‰ kotiinkuljetus otetaan k‰yttˆˆn
     */
	public void setKotiinkuljetus(Kotiinkuljetus kotiinkuljetus) {
		this.kotiinkuljetus = kotiinkuljetus;	
	}
 
	/**
	 * Tulostaa asiakkaan tiedot
	 * @param os tietovirta johon tulostetaan
	 * @param asiakas halutun asiakkaan tiedot
	 */
	public void tulosta(PrintStream os, final Asiakas asiakas) {
	//	os.println("-----------------------------------------");
		asiakas.tulosta(os);
		os.println("-----------------------------------------");
//		tulostaTilaukset(os, asiakas);
		
	}
	
	/**
	 * Tulostaa tilaukset tietovirtaan
	 * @param os tietovirta
	 * @param asiakas kenen tilaukset tulostetaan
	 */
/*	private void tulostaTilaukset(PrintStream os, final Asiakas asiakas) {
		try {
			List<Tilaus> tilaukset = kotiinkuljetus.annaTilaukset(asiakas);
			for (Tilaus tilaus : tilaukset) {
				tilaus.tulosta(os);
			}
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Tilausten hakemisessa ongelmia! " + e.getMessage());
		} 
	} */
	
	/**
	 * Tulostaa listassa olevat asiakkaat tekstialueeseen
	 * @param text alue johon tulostetaan 
	 */
	public void tulostaValitut(TextArea text) {
		try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
	//		os.println("Tulostetaan kaikki asiakkaat");
	//		Collection<Asiakas> asiakkaat = kotiinkuljetus.etsi("", -1);
	//		for (Asiakas asiakas : asiakkaat) {
			os.println("Tulostetaan kaikki asiakkaat");
			os.println("-----------------------------------------");
			for (Asiakas asiakas : listdataAsiakkaat) {
				tulosta(os, asiakas);
		//		os.println("\n\n");
			}
		} /* catch (SailoException e) {
			Dialogs.showMessageDialog("Asiakkaan hakemisessa ongelmia! "+e.getMessage());
		} */
	}
}
