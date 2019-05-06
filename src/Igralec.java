
public class Igralec {
	int ploscki;
	int stPotez1;
	int faza;
	String ime;
	
	public Igralec(String ime) {
		this.ime = ime;
		ploscki = 9;
		stPotez1 = 0;
		faza = 1;
	}
	
	public static void naslednjaFaza(Igralec igralec) {
		if (igralec.faza == 2 && igralec.ploscki == 3) ++igralec.faza;
		else if  (igralec.stPotez1 == 9) ++igralec.faza;
		
	}
	
	public String toString () {
		return ime;
	}

}
