import java.util.concurrent.ThreadLocalRandom;

public abstract class Pomoc {
	
	static String[] vzpodbude = new String[] {
			"Uspelo ti bo!",
			"Na dobri poti si", 
			"<html>Samo malo se<br>�e potrudi</html>", 
			"Dobro razmi�lja�!", 
			"Le tako naprej!", 
			"Odli�no ti gre!", 
			"Super!",
			"<html>Zelo dobro<br>igra�</html>",
			"Dobro premisli!", 
			"<html>Pred o�mi imej<br>zmago!</html>",
			"Krasno izvedeno.", 
			"Premi�ljena poteza!", 
			"Dobro ti gre.",
			"<html>Malo �e in<br>zmaga bo tvoja.</html>",
			"Vztrajaj!",
			"Ne obupaj!",
			"Potrudi se!", 
			"<html>Naslednja boteza<br>bo zmagovalna!</html>",
			"Zmore�."
	};
	
	
	public static String pomagaj() {
		int n = ThreadLocalRandom.current().nextInt(0, vzpodbude.length);
		return vzpodbude[n];
	}
}
