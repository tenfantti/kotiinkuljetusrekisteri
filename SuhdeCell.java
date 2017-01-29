package fxKotiinkuljetus;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import kotiinkuljetus.Suhde;

/**
 * @author Janita
 * @version 13.4.2016
 */
public class SuhdeCell extends TableCell<Suhde, String>{

	interface SuhdeAsetus {

		/**
		 * Asettaa arvon tilaukselle
		 * @param suhde tilaus jolle arvo asetetaan
		 * @param kenttaNro mihin kentt‰‰n
		 * @param uusiarvo mik‰ on uusi arvo
		 * @return null jos ok, muuten virhe
		 */
		public String aseta(Suhde suhde, int kenttaNro, String uusiarvo);
	}

	/**
	 * Alustetaan TableView n‰ytt‰m‰‰n tilauksia
	 * @param tableSuhteet valmiiksi luotu TableView
	 * @param asettaja luokka joka suorittaa asetuksen. Mikli t‰t‰ ei anneta
	 * asettaa itse vastaavaan paikkaan.
	 */
	public static void alustaSuhdeTable(TableView<Suhde> tableSuhteet, SuhdeAsetus asettaja) {
		Suhde apusuhde = new Suhde();

		int eka = apusuhde.ekaKentta();
		int kenttia = apusuhde.getKenttia();

		for (int k = eka; k < kenttia ; k++) {
			TableColumn<Suhde, String> tc = new TableColumn<>(apusuhde.getKysymys(k));

			final int kenttaNro = k;
			tc.setCellFactory(column -> new SuhdeCell(kenttaNro, asettaja));
			tc.setCellValueFactory((rivi) -> {
				//	String s = rivi.getValue().anna(kenttaNro);
				String s = rivi.getValue().getAvain(kenttaNro);
				if (s.length() > 0 && s.charAt(0) == ' ') s = s.replace(' ', '0');
				//		s = "!" + s.substring(1);
				return new SimpleStringProperty(s);
			});

			tc.setPrefWidth(90);
			tc.setMaxWidth(300);

			//	if (k = 2) tableSuhteet.getColumns(); 
			tableSuhteet.getColumns().add(tc);
			tableSuhteet.setTableMenuButtonVisible(true);
		}

		tableSuhteet.setEditable(true);
		ObservableList<Suhde> data = FXCollections.observableArrayList();
		tableSuhteet.setItems(data);
	}

	/**
	 * Aktivoidaan viimeisin solu muokkaamista varten
	 * @param table mist‰ taulukosta
	 */
	public static void editLast(TableView<Suhde> table) {
		int r = table.getItems().size()-1;
		if (r < 0) return;
		@SuppressWarnings("unchecked")
		TableColumn<Suhde, String> col = (TableColumn<Suhde, String>)table.getColumns().get(0);
		table.requestFocus();
		table.getSelectionModel().select(r, col);
		Platform.runLater(() -> table.edit(r, col));
		//	table.edit(r, col); //TODO: miksi pit‰‰ olla -1?
		//	table.edit(table.getFocusModel().getFocusedCell().getRow(), col);
	}

	/**
	 * Palautetaan valitun rivin kohdalla oleva tilaus
	 * @param table taulukko josta etsit‰‰n
	 * @return tilaus kohdalla
	 */
	public static Suhde getSelected(TableView<Suhde> table) {
		return table.getFocusModel().getFocusedItem();
	}


	private TextField textField;
	private int kenttaNro;
	private SuhdeAsetus asettaja;

	/**
	 * Alustetaan kentt‰
	 * @param kenttaNro monettako kentt‰‰ kenno edustaa
	 * @param asettaja luokka joka suottaa arvon asettamisen
	 */
	public SuhdeCell(int kenttaNro, SuhdeAsetus asettaja) {
		this.kenttaNro = kenttaNro;
		this.asettaja = asettaja;
	}

	/**
	 * Oma asetus, joka asettaa oko kutsumalla annettua asettajaa tai tilauksen omaa
	 * @param til mille tilaukselle asetetaan
	 * @param knro mihin kentt‰‰n
	 * @param uusiarvo mik‰ arvo
	 * @return null jos ei virhett‰, muuten virhe
	 */
	protected String aseta(Suhde til, int knro, String uusiarvo) {
		String arvo = "";
		if (uusiarvo != null ) arvo = uusiarvo;
		if (asettaja != null) return asettaja.aseta(til, knro, uusiarvo);
		if (til == null) return "Ei tilausta";
		return til.aseta(kenttaNro, arvo);
	}

	/**
	 * @return antaa kohdalla olevan tilauksen
	 */
	protected Suhde getObject() {
		@SuppressWarnings("unchecked")
		TableRow<Suhde> row = getTableRow();
		if (row == null) return null;
		Suhde til = row.getItem();
		return til;
	}

	/**
	 * @return kohdalla olevan tilauksen kent‰n sis‰llˆn merkkijonona
	 */
	protected String getObjectItem() {
		Suhde til = getObject();
		if (til == null) return getItem();
		return til.anna(kenttaNro);
	}

	@Override
	public void startEdit() {
		if (isEmpty()) return;
		super.startEdit();
		createTextField();
		setText(null);
		setGraphic(textField);
		textField.setText(getObjectItem());
		Platform.runLater(() -> textField.requestFocus());
		textField.selectAll();
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(getObjectItem());
		setGraphic(null);
	}

	@Override
	public void updateItem(String itm, boolean empty) {
		Suhde til = getObject();
		String item = itm;
		if (til != null) til.anna(kenttaNro);

		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setStyle("");
			setGraphic(null);
			return;
		}

		if (isEditing()) {
			if (textField != null) textField.setText(getObjectItem());
			setText(null);
			setGraphic(textField);
			return;
		}

		setText(getObjectItem());
		setGraphic(null);
	}

	@SuppressWarnings("unchecked")
	private void createTextField() {
		if (textField != null) return;
		textField = new TextField();
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() *2);
		textField.focusedProperty().addListener((arg0,arg1,arg2) -> {
			if (!arg2) {
				commitEdit(textField.getText());
			}
		});
		textField.setOnAction(e -> commitEdit(textField.getText()));
		textField.setOnKeyReleased(e -> {
			if (e == null) return;
			if (textField == null) return;
			if (kenttaNro == 0) return;
			Suhde til = getObject();
			String s = textField.getText();
			String virhe = aseta(til, kenttaNro, s);
			if (virhe != null) textField.setStyle("-fx-background-color: red");
			else textField.setStyle("");
		});
		textField.setOnKeyPressed(t -> {
			if (t.getCode() == KeyCode.ENTER) {
				cancelEdit();
				t.consume();
			} else if (t.getCode() == KeyCode.ESCAPE) {
				cancelEdit();
				t.consume();
			} else if (t.getCode() == KeyCode.TAB) {
				cancelEdit();
				t.consume();
				TableView<Suhde> table = getTableView();
				if (t.isShiftDown()) table.getFocusModel().focusLeftCell();
				else table.getFocusModel().focusRightCell();
				table.edit(getTableRow().getIndex(), table.getFocusModel().getFocusedCell().getTableColumn());
			}
		});
	}
}