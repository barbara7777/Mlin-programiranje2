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
	public static ArrayList <Integer> kotni = new ArrayList<Integer>();
	public static ArrayList <Integer> kotni_sredina = new ArrayList<Integer>();
	public static ArrayList <Integer> trojnikot = new ArrayList<Integer>();
	public static ArrayList <Integer> cetvornikot = new ArrayList<Integer>();
	
	// na i-tem mestu v tem vectorju je mno�ica neurejenih parov indeksov polj, ki skupaj z i-tim poljem tvorijo trojico
	 static Vector<Set<Set<Integer>>> kandidatiZaMlin;
	
	
	public static void ustvariIgralnoPlosco() 
	{
		// sestavi mno�ico trojic
		for (int i : (new int[] {0, 2, 15, 17})) { //izhodi��ni indeksi za te trojice
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
		narediKandidateZaMlin();
		ustvariPovezave();
		dodaj_v_vektorje();
	}
	
	public static Polje[] getTabela() {
		return tabela;
	}
	public static void dodaj_v_vektorje(){ 
		kotni.add(0);
		kotni.add(2);
		kotni.add(23);
		kotni.add(21);
		kotni.add(6);
		kotni.add(8);
		kotni.add(15);
		kotni.add(17);
		kotni_sredina.add(3);
		kotni_sredina.add(18);
		kotni_sredina.add(20);
		kotni_sredina.add(5);
		trojnikot.add(1);
		trojnikot.add(9);
		trojnikot.add(14);
		trojnikot.add(22);
		trojnikot.add(7);
		trojnikot.add(11);
		trojnikot.add(12);
		trojnikot.add(16);
		cetvornikot.add(4);
		cetvornikot.add(10);
		cetvornikot.add(13);
		cetvornikot.add(19);
	}
	public static boolean staPovezana (Polje prvo, Polje drugo) { // preveri, �e je med poljema povezava
		// kako bi to funkcijo napisala na drug na�in - preveri, �e sta vrstici isti, �e sta vzemi switch, preglej kiri vrstici sta isti....
		// isto ponovi za stolpce
		boolean vrstice = prvo.vrstica == drugo.vrstica;
		boolean stolpci = prvo.stolpec == drugo.stolpec;
		boolean razlikaSt = Math.abs(prvo.stolpec - drugo.stolpec) == 1;
		boolean razlikaVr = Math.abs(prvo.vrstica - drugo.vrstica) == 1;
		
		if ((prvo.vrstica == 3 && (stolpci || (drugo.vrstica == 3 && razlikaSt)))
				|| // ponovim �e za zamenjan vrstni red polj
			(drugo.vrstica == 3 && (stolpci || (prvo.vrstica == 3 && razlikaSt)))) 
			return true;
		else if ((prvo.stolpec == 3 && (vrstice || (drugo.stolpec == 3 && razlikaVr))) ||
				(drugo.stolpec == 3 && (vrstice || (prvo.stolpec == 3 && razlikaVr))))
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
	
	private static void narediKandidateZaMlin() {
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
