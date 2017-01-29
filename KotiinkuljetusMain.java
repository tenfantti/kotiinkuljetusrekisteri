package fxKotiinkuljetus;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kotiinkuljetus.Kotiinkuljetus;

/**
 * Pääohjelma Kotiinkuljetus-ohjelman käynnistämiseksi
 * @author jahasall
 * @version 8.2.2016
 */
public class KotiinkuljetusMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader ldr = new FXMLLoader(getClass().getResource("KotiinkuljetusGUIView.fxml"));
			final Pane root = ldr.load();
			final KotiinkuljetusGUIController kotiinkuljetusCtrl = (KotiinkuljetusGUIController)ldr.getController();
			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("kotiinkuljetus.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Kotiinkuljetus");
			Kotiinkuljetus kotiinkuljetus = new Kotiinkuljetus();
			kotiinkuljetusCtrl.setKotiinkuljetus(kotiinkuljetus);
			primaryStage.show();
	//		Application.Parameters params = getParameters();
			kotiinkuljetusCtrl.lueTiedosto("kotiinkuljetus");
	//		if (params.getRaw().size() > 0 ) kotiinkuljetusCtrl.lueTiedosto(params.getRaw().get(0));
	//		else if ( !kotiinkuljetusCtrl.avaa() ) Platform.exit();
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
	
	/**
	 * Käynnistetään käyttöliittymä
	 * @param args Komentorivin parametrit
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
