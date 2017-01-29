package fxKotiinkuljetus;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Janita
 * @version 15.4.2016
 * 
 */
public class LisaaAsiakasController implements ModalControllerInterface<String> {
	   @FXML private TextField etunimiText;
	   @FXML private TextField sukunimiText;
	   @FXML private TextField puhelinText;
	   @FXML private TextField osoiteText;
	   @FXML private TextField asuinalueText;

	    @FXML void handleTallenna() {
	    	tallenna();
	    }

	    /**
	     * Tallentaa halutun tekstin
	     */	
	    public void tallenna(){
	    	Dialogs.showMessageDialog("Osaan tallentaa myöhemmin!");
	    }

		@Override
		public String getResult() {
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
		public void setDefault(String oletus) {
			etunimiText.setText(oletus);
			
		}
		
	    /**
	     * @param modalityStage oletuksena null
	     * @param oletus mitä merkkijonoa käytetään oletuksena
	     * @return merkkijono saaduista tiedoista
	     */
		public static String kysyAsiakas(Stage modalityStage, String oletus) {
	    	return ModalController.showModal(TilausController.class.getResource("LisaaAsiakasView.fxml"), "Asiakas", modalityStage, oletus);
	    }

		/**
		public static void kysyAsiakas(Object modalityStage, Asiakas asiakasKohdalla) {
			return ModalController.showModal(TilausController.class.getResource("LisaaAsiakasView.fxml"), "Asiakas", modalityStage, asiakasKohdalla);
			
		} **/
}
