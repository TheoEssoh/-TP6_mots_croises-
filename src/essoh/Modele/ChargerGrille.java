 package essoh.Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChargerGrille {

	private Connection Connection ;

	public ChargerGrille() {
		try {
			Connection = connectionMySQL() ;
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	public static Connection connectionMySQL() throws SQLException {
		String url = "jdbc:mysql://anteros.istic.univ-rennes1.fr/base_bousse";
		//String url = "jdbc:mysql://localhost:3306/base_bousse?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
		try {
			Class.forName ("com.mysql.cj.jdbc.Driver") ;
		}
		catch (ClassNotFoundException e) { e.printStackTrace(); }

		return DriverManager.getConnection(url, "user_jessoh", "emploirennes");
		//return DriverManager.getConnection(url, "root", "");
	}

	// 2.1  Chargement d'une grille depuis les tables TP5_GRILLE et TP5_MOT
	public static MotsCroisesTP6 extraireBD(Connection connect, int grille) {
		int largeur = 0, hauteur = 0;
		MotsCroisesTP6 motcroises = null;

		try {
			String sql = "SELECT largeur,hauteur FROM TP5_GRILLE WHERE num_grille = " + grille;
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				largeur = rs.getInt("largeur");
				hauteur = rs.getInt("hauteur");
			}

			motcroises = new MotsCroisesTP6(hauteur, largeur);

			sql = "SELECT definition,horizontal,ligne,colonne,solution FROM TP5_MOT WHERE num_grille = " + grille;
			stmt = connect.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String definition = rs.getString("definition");
				String solution = rs.getString("solution");
				int horizontal = rs.getInt("horizontal");
				int lig = rs.getInt("ligne");
				int col = rs.getInt("colonne");

				if (horizontal == 1) {
					motcroises.setDefinition(lig, col, true, definition);
					for (int i = 0 ; i < solution.length(); i++) {
						char sol = solution.charAt(i);
						motcroises.setSolution(lig,i + col, sol);
					}
				}
				else {
					motcroises.setDefinition(lig, col, false, definition);
					for (int i = 0 ; i < solution.length(); i++) {
						char sol = solution.charAt(i);
						motcroises.setSolution(i + lig, col, sol);
					}
				}
			}
			stmt.close();
		} catch (SQLException e) {}

		return motcroises;
	}

	public static final int CHOIX_GRILLE = 10 ;

	public MotsCroisesTP6 extraireGrille() {
		return extraireBD(Connection, CHOIX_GRILLE) ;
	}

	public MotsCroisesTP6 extraireGrille(int grille) {
		return extraireBD(Connection, grille) ;
	}
}
