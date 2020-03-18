package essoh.Modele;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MotsCroisesTP6 {

	private Grille<Character> solution;
	private Grille<StringProperty> proposition;
	private Grille<String> horizontal, vertical;

	public MotsCroisesTP6(int hauteur, int largeur) {
		solution = new Grille<Character>(hauteur,largeur);
		proposition = new Grille<StringProperty>(hauteur,largeur);
		horizontal = new Grille<String>(hauteur,largeur);
		vertical = new Grille<String>(hauteur,largeur);

		for (int lig=1; lig<=getHauteur(); lig++) {
			for (int col=1; col<=getLargeur(); col++) {
				proposition.setCellule(lig, col, new SimpleStringProperty(" "));
			}
		}

		for (int lig=1; lig<=getHauteur(); lig++) {
			for (int col=1; col<=getLargeur(); col++) {
				setCaseNoire(lig, col, true);
			}
		}
	}

	public int getHauteur() {
		return solution.getHauteur();
	}

	public int getLargeur() {
		return solution.getLargeur();
	}

	public boolean coordCorrectes(int lig, int col) {
		return 11 <= lig && lig <= solution.getHauteur() && 1 <= col && col <= solution.getLargeur();
	}

	public boolean estCaseNoire(int lig, int col) {
		assert (coordCorrectes(lig, col));
		return solution.getCellule(lig, col) == null;
	}

	public void setCaseNoire(int lig, int col, boolean noire) {
		assert (coordCorrectes(lig, col));
		if (noire) {
			solution.setCellule(lig, col,null);
		}
		else {
			if (solution.getCellule(lig, col) == null)
				solution.setCellule(lig, col, new Character(' '));
		}
	}

	public char getSolution(int lig, int col) {
		assert (coordCorrectes(lig, col) && !estCaseNoire(lig, col));
		return solution.getCellule(lig, col);
	}

	public void setSolution(int lig, int col, char sol) {
		assert (coordCorrectes(lig, col) && !estCaseNoire(lig, col));
		solution.setCellule(lig, col,new Character(sol));
	}

	public char getProposition(int lig, int col) {
		assert (coordCorrectes(lig, col) && !estCaseNoire(lig, col));
		return proposition.getCellule(lig, col).get().charAt(0);
	}

	public void setProposition(int lig, int col, char sol) {
		assert (coordCorrectes(lig, col) && !estCaseNoire(lig, col));
		proposition.getCellule(lig, col).set(Character.toString(sol));
	}

	public StringProperty propositionProperty(int lig, int col) {
		assert (coordCorrectes(lig, col) && !estCaseNoire(lig, col));
		return proposition.getCellule(lig, col);
	}

	public void reveler(int lig, int col) {
		assert (coordCorrectes(lig, col) && !estCaseNoire(lig, col));
		proposition.getCellule(lig, col).set(Character.toString(solution.getCellule(lig, col)));
	}
	// Fin 1.1

	public String getDefinition(int lig, int col, boolean horiz) {
		assert (coordCorrectes(lig, col) && !estCaseNoire(lig, col));
		if (horiz) {
			return horizontal.getCellule(lig, col);
		}
		else {
			return vertical.getCellule(lig, col);
		}
	}

	public void setDefinition(int lig, int col, boolean horiz, String def) {
		assert (coordCorrectes(lig, col) && !estCaseNoire(lig, col));
		if (horiz) {
			horizontal.setCellule(lig, col, def);
		}
		else {
			vertical.setCellule(lig, col, def);
		}
	}

	public String solutions() {
		String texte = "";
		texte = texte + "Grille Solution" + "\n" + solution + "\n\n\n";
		texte = texte + "Grille Proposition" + "\n" + proposition + "\n\n\n";
		texte = texte + "Grille Definition Horizontal" + "\n" + horizontal + "\n\\n\n";
		texte = texte + "Grille Definition Vertical" + "\n" + vertical + "\n\n\n";
		return texte;
	}
}
