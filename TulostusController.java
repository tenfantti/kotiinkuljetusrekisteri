package fxKotiinkuljetus;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Tulostuksen hoitava luokka
 * @author Janita
 * @version 12.2.2016
 */
public class TulostusController implements ModalControllerInterface<String> {

	@FXML TextArea tulostusAlue;

    @FXML private void handleTulosta() {
    	Dialogs.showMessageDialog("Osaan myöhemmin tulostaa");
    }

    @Override
    public void setDefault(String oletus) {
    if (oletus == null) return;
    tulostusAlue.setText(oletus);
    }
    
    
    /** 
     * Näyttää tulostusalueessa tekstiä
     * @param tulostus tulostettava teksti
     */
    public static void tulosta1(String tulostus) {
    	ModalController.showModeless(TulostusController.class.getResource("TulostaView.fxml"), "Tulostus", tulostus);
    } 
    
    /**
     * @param tulostus mitä tulostetaan
     * @return TulostusController
     */
    public static TulostusController tulosta(String tulostus) {
    	TulostusController tulostusCtrl =
    	(TulostusController) ModalController.showModeless(TulostusController.class.getResource("TulostaView.fxml"), "Tulostus", tulostus);
    	return tulostusCtrl;
    }

	@Override
	public String getResult() {	
		return null;
	}
	
	/**
	 * @return alue johon tulostetaan
	 */
	public TextArea getTextArea() {
		return tulostusAlue;
	}

	/**
	 * Mitä tehdään kun dialogi on näytetty
	 */
	@Override
	public void handleShown() {
		// tekee jotain myöhemmin
	}
	
    /**
     * @param modalityStage oletuksena null
     * @param oletus mitä merkkijonoa käytetään oletuksena
     * @return merkkijono saaduista tiedoista
     */
	public static String kysyTulostus(Stage modalityStage, String oletus) {
    	return ModalController.showModal(TulostusController.class.getResource("TulostaView.fxml"), "Tulosta", modalityStage, oletus);
    }
}
