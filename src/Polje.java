import java.util.*;

public class Polje {
	int vrstica, stolpec; // koordinate
	int indeks; // pove katero polje po vrsti je
	Zasedeno zasedenost;
	
	Set<Polje> povezave;
	
	public Polje(int i) {
		indeks = i;
		vrstica = i / 3 - i / 12;
		if (vrstica == 3) stolpec = i % 3 + 4 * (i / 12);			
		else stolpec = 3 + (i % 3 - 1) * Math.abs(vrstica - 3);
	
		povezave = new HashSet<Polje>();
	}
	
	public boolean jePrazno() {
		return (zasedenost == Zasedeno.PRAZNO);
	}
	
	public static void main(String[] args) {
		Polje a = new Polje(23);
		System.out.println(a.vrstica + " " + a.stolpec + " " + a.indeks);
	}
}
