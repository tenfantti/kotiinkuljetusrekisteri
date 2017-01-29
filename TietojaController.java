package fxKotiinkuljetus;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Luokka uuden tilauksen tekemiselle 
 * @author Janita
 * @version 12.2.2016
 */
public class TietojaController implements ModalControllerInterface<String> {
	 @FXML private TextArea textVuosi;
	 @FXML private TextArea textVersio;
	 @FXML private TextArea textTekija;
	 
	 @FXML void handlePoistu() {
		 // Ei tarvetta poistu-napille
	    }



	@Override
	public String getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Mitä tekee sitten kun uudet tiedot ovat tallennettu
	 */
	@Override
	public void handleShown() {
		// näyttää seuraamukset myöhemmin
		
	}	

	@Override
	public void setDefault(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param modalityStage oletuksena null
	 * @param oletus mitä käytetään oletuksena
	 * @return merkkijonon saaduista tiedoista
	 */
	public static String kysyTietoja(Stage modalityStage, String oletus) {
	    	return ModalController.showModal(TietojaController.class.getResource("TietojaView.fxml"), "Tietoja", modalityStage, oletus);
	}

}
