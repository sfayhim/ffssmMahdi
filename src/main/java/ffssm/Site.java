package FFSSM;

import lombok.Data;
import lombok.NonNull;

// On utilise lombok pour générer les getters et setters...
// La bibliothèque est importée dans le fichier pom.xml

// Lombok : génère getter / setter / constructeur, toString
// https://projectlombok.org/features/Data
@Data
public class Site {
	@NonNull
	private String nom;

	@NonNull
	private String details;

	public static void main(String[] args) { // Un exemple des méthodes générées par lombok

		// Constructeur avec les paramètres obligatoires
		Site site = new Site("Site1", "Site1 details");

		// Setters
		site.setNom("Mont Saint Michel");
		site.setDetails("Plongee autour du Mont Saint Michel");

		// toString()
		System.out.printf("Le site : %s\n", site);
	}
}