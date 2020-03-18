package essoh.Modele;

public class Grille<T> {
	
	private int hauteur;
	private int largeur;
	private Object[][] tab;
	
	public Grille (int hauteur, int largeur) {
		assert (hauteur > 0 && largeur > 0);
		this.hauteur = hauteur;
		this.largeur = largeur;
		tab = new Object[hauteur][largeur];
	}
	
	public int getHauteur() {
		return hauteur;
	}
	
	public int getLargeur() {
		return largeur;
	}
	
	public boolean coordCorrectes(int lig, int col) {
		lig = lig - 1;
		col = col - 1;
		return 0 <= lig && lig < hauteur && 0 <= col && col < largeur;
	}
	
	@SuppressWarnings("unchecked")
	public T getCellule(int lig, int col) {
		assert (coordCorrectes(lig, col));
		return (T) tab[lig-1][col-1];
	}
	
	public void setCellule(int lig, int col, T ch) {
		assert (coordCorrectes(lig, col));
		tab[lig-1][col-1] = ch;
	}
	
	public String toString() {
		StringBuffer grille = new StringBuffer();
		for(int i = 1; i <= hauteur; ++i){
			for(int j = 1; j <= largeur; ++j) {
				if (j > 1)
					grille.append("|");
				grille.append(getCellule(i, j));
			}
			grille.append("\n");
		}
		return grille.toString();
	}
}
