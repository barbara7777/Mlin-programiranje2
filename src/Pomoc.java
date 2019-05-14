import java.util.concurrent.ThreadLocalRandom;

public abstract class Pomoc {
	
	static String[] vzpodbude = new String[] {
			"Uspelo ti bo!",
			"Na dobri poti si", 
			"<html>Samo malo se<br>še potrudi</html>", 
			"Dobro razmišljaš!", 
			"Le tako naprej!", 
			"Odlièno ti gre!", 
			"Super!",
			"<html>Zelo dobro<br>igraš</html>",
			"Dobro premisli!", 
			"<html>Pred oèmi imej<br>zmago!</html>",
			"Krasno izvedeno.", 
			"Premišljena poteza!", 
			"Dobro ti gre.",
			"<html>Malo še in<br>zmaga bo tvoja.</html>",
			"Vztrajaj!",
			"Ne obupaj!",
			"Potrudi se!", 
			"<html>Naslednja boteza<br>bo zmagovalna!</html>",
			"Zmoreš."
	};
	
	
	public static String pomagaj() {
		int n = ThreadLocalRandom.current().nextInt(0, vzpodbude.length);
		return vzpodbude[n];
	}
}
