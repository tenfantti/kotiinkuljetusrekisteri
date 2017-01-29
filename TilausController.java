package fxKotiinkuljetus;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Luokka uuden tilauksen tekemiselle 
 * @author Janita
 * @version 12.2.2016
 */
public class TilausController implements ModalControllerInterface<String> {
	@FXML private TextField tuoteText;
    @FXML private TextField tuoteNroText;
    @FXML private TextField kplText;
    @FXML private TextField aikaText;
    @FXML private TextField maksutapaText;
    
    @FXML void handlePoista() {
    	Dialogs.showMessageDialog("Poistan my�hemmin!");
    }

    @FXML void handleTallenna() {
    	tallenna();
    }

	@Override
	public String getResult() {
		return null;
	}

	/**
	 * Mit� tekee sen j�lkeen kun uudet tiedot ovat tallennettu	
	 */
	@Override public void handleShown() {
		// osaa tehd� jotain asialle my�hemmin
		
	}

	@Override public void setDefault(String oletus) {
		tuoteText.setText(oletus);	
	}
	
	/**
     * Tallentaa halutun tekstin
     */
    public void tallenna(){
    	Dialogs.showMessageDialog("Osaan tallentaa my�hemmin!");
    }
    
    /**
     * @param modalityStage oletuksena null
     * @param oletus mit� merkkijonoa k�ytet��n oletuksena
     * @return merkkijono saaduista tiedoista
     */
    public static String kysyTilaus(Stage modalityStage, String oletus) {
    	return ModalController.showModal(TilausController.class.getResource("TilausView.fxml"), "Tilaus", modalityStage, oletus);
    }
}
