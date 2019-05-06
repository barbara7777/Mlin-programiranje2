import java.util.*;

/*
* 0 --------- 1 --------- 2
* |           |           |
* |   3 ----- 4 ----- 5   |
* |   |       |       |   |
* |   |   6 - 7 - 8   |   |
* |   |   |       |   |   |
* 9 - 10- 11      12- 13- 14
* |   |   |       |   |   |
* |   |   15- 16- 17  |   |
* |   |       |       |   |
* |   18----- 19----- 20  |
* |           |           |
* 21--------- 22--------- 23
*/

public class IgralnaPloscaInfo {
	public static Polje[] tabela = new Polje[24];
	private static Set<Set<Integer>> nepovezaneTrojice = new HashSet<>(); 
	
	// na i-tem mestu v tem vectorju je množica neurejenih parov indeksov polj, ki skupaj z i-tim poljem tvorijo trojico
	 static Vector<Set<Set<Integer>>> kandidatiZaMlin;
	
	
	// main za testiranje
	public static void main(String[] args) {
		IgralnaPloscaInfo plosca = new IgralnaPloscaInfo();
		System.out.println(plosca.kandidatiZaMlin);
	}
	
	// konstruktor
	public IgralnaPloscaInfo() {
		// sestavi množico trojic
		for (int i : (new int[] {0, 2, 15, 17})) { //izhodišèni indeksi za te trojice
			Set<Integer> trojica = new HashSet<>();
			for (int j = 0; j <= 6; j = j + 3) 
				trojica.add(i + j);
			nepovezaneTrojice.add(trojica);
		}
		
		// sestavi tabelo in v njo spravi polja
		for (int i = 0; i < 24; ++i) {
			tabela[i] = new Polje(i);
		}
			
		// sestavi Vector trojic, kandidatov za mlin
		naredi();
		ustvariPovezave();
	}
	
	public static boolean staPovezana (Polje prvo, Polje drugo) { // preveri, èe je med poljema povezava
		if ((prvo.vrstica == 3 && (prvo.stolpec == drugo.stolpec || Math.abs(prvo.indeks - drugo.indeks) == 1)) ||
		(drugo.vrstica == 3 && (drugo.stolpec == prvo.stolpec || Math.abs(drugo.indeks - prvo.indeks) == 1)))
			return true;
		else if ((prvo.stolpec == 3 && (prvo.vrstica == drugo.vrstica || Math.abs(prvo.vrstica - drugo.vrstica) == 1)) ||
				(drugo.stolpec == 3 && (drugo.vrstica == prvo.vrstica || Math.abs(drugo.vrstica - prvo.vrstica) == 1)))
			return true;
		else return false;
	}
	
	public static void ustvariPovezave () { 
		for (Polje prvo : tabela) {
			for (Polje drugo : tabela) {
				if (staPovezana(prvo, drugo)) {
					prvo.povezave.add(drugo);
					drugo.povezave.add(prvo);
				}
			}
		}
	}

	
	private static boolean soZaporednaSt (int a, int b, int c) { // pomozna funkcija
		int min = Math.min(a, Math.min(b, c));
		int max = Math.max(a, Math.max(b, c));
		return max - min == 2 && a != b && a != c && b != c;
	}
	
	public static boolean tvorijoTrojico(Polje a, Polje b, Polje c) { 
		if (nepovezaneTrojice.contains(new HashSet<>(Arrays.asList(a.indeks, b.indeks, c.indeks))))
			return true;
		else {
			if ((a.vrstica == b.vrstica && b.vrstica == c.vrstica && 
				 a.vrstica != 3) ||
				(a.stolpec == b.stolpec && b.stolpec == c.stolpec &&
				 a.stolpec != 3)) 
				return true;
			else if ((a.vrstica == 3 && b.vrstica == 3 && c.vrstica == 3 &&
					soZaporednaSt(a.stolpec, b.stolpec, c.stolpec)) ||
					(a.stolpec == 3 && b.stolpec == 3 && c.stolpec == 3 &&
					soZaporednaSt(a.vrstica, b.vrstica, c.vrstica)))
					return true;
		}
		return false;
	}
	
	private static void naredi() {
		kandidatiZaMlin = new Vector<>();
		for (int i = 0; i < 24; ++i) {
			Set<Set<Integer>> zaIzbranoPolje = new HashSet<Set<Integer>>();
			for (int j = 0; j < 24; ++j) {
				for (int k = 0; k < 24; ++k) {
					if (i != j && j != k && k != i) {
						if (tvorijoTrojico(tabela[i], tabela[j], tabela[k])) {
							Set<Integer> zacasni = new HashSet<>();
							zacasni.addAll(Arrays.asList(j, k));	
							zaIzbranoPolje.add(zacasni);
						}
					}
				}
			}
			kandidatiZaMlin.add(zaIzbranoPolje);
		} 
	}

}
