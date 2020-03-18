package essoh.Controller;

import essoh.Modele.ChargerGrille;
import essoh.Modele.MotsCroisesTP6;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ControleurTP6 {


	private MotsCroisesTP6 motcroises;

	private ChargerGrille chargeG;

	@SuppressWarnings("unused")
	private TextField case_courant ;

	@FXML
	private GridPane monGridPane;

	@FXML
	private MenuBar MenuBar;

	@FXML
	private void initialize() {


	//Chargement d'une grille
		chargeG = new ChargerGrille();
		motcroises = chargeG.extraireGrille();
		recreerGrille();
		init();


		Menu menu = MenuBar.getMenus().get(0);
		for (MenuItem menuitem : menu.getItems()) {
			menuitem.setOnAction((e) -> {this.clicMenu(e);});;
		}
	}

	//  Creation d'une IHM JavaFX minimale
	private void init() {

		for (Node n : monGridPane.getChildren()) {
			if (n instanceof TextField) {
				TextField tf = (TextField) n;
				int lig = ((int) n.getProperties().get("gridpane-row")) + 1;
				int col = ((int) n.getProperties().get("gridpane-column")) + 1;

				//  Saisie des propositions
				tf.textProperty().bindBidirectional(motcroises.propositionProperty(lig, col));
				// Fin 1.3

				//  Affichage des infobulles
				String texte = getInfobulles(lig,col);
				if (texte != null)
					tf.setTooltip(new Tooltip(texte));


				//  Révélation d'une solution sur demande
				tf.setOnMouseClicked((e) -> {this.clicCase(e);});



			}
		}
	}

	// 1.4 Affichage des infobulles
	private String getInfobulles(int lig, int col) {
		String texte = null;
		if (!motcroises.estCaseNoire(lig, col)) {
			String horizontal = motcroises.getDefinition(lig,col,true);
			String vertical = motcroises.getDefinition(lig,col,false);

			if (horizontal != null && vertical !=null)
				texte = horizontal + "/" + vertical;

			if (horizontal != null && vertical == null)
				texte = horizontal.toString();

			if (horizontal == null && vertical != null)
				texte = vertical.toString();
		}
		return texte;
	}


	@FXML
	public void clicCase(MouseEvent e) {
		if (e.getButton() == MouseButton.MIDDLE) {
			TextField casse = (TextField) e.getSource();
			int lig = ((int) casse.getProperties().get("gridpane-row"))+1;
			int col = ((int) casse.getProperties().get("gridpane-column"))+1;
			motcroises.reveler(lig, col);
		}
	}


	//  Chargement d'une grille
	private void recreerGrille() {
		TextField modele = (TextField) monGridPane.getChildren().get(0);
		monGridPane.getChildren().clear();

		for (int lig = 0; lig < motcroises.getHauteur(); lig++) {
			for (int col = 0; col< motcroises.getLargeur(); col++) {
				if (!motcroises.estCaseNoire(lig+1, col+1)) {
					TextField nouveau = new TextField();

					nouveau.setPrefWidth(modele.getPrefWidth());
					nouveau.setPrefHeight(modele.getPrefHeight());

					for (Object cle : modele.getProperties().keySet()) {
						nouveau.getProperties().put(cle, modele.getProperties().get(cle));

                        nouveau.textProperty().addListener(
		                      (observable,oldValue,newValue)-> {
		                    	  if(newValue.length() >2)
		                    		  nouveau.setText(oldValue.toUpperCase());
						           });

					}
					monGridPane.add(nouveau, col, lig);
				}
			}
		}
	}


	@FXML
	public void clicMenu(ActionEvent e) {
		MenuItem m = (MenuItem) e.getSource();
		int index = Integer.parseInt(m.getId());
		if (index < 11) {
			if (index == 0) {
				double random = (Math.random()*100)%10;
				index = (int) random;
				if (index == 0)
					index = 10;
			}
			motcroises = chargeG.extraireGrille(index);
			recreerGrille();
			init();
		}
		else {
			Platform.exit();
		}
	}


}