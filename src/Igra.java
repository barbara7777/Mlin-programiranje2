import java.util.Arrays;
import java.util.Set;
import java.util.Vector;


public class Igra {

	IgralnaPloscaInfo plosca;
	int[][] trojice = new int[20][3];
	public static Igralec igralec, racunalnik;
	public Igralec naPotezi;
	public Igralec nasprotnik;
	
	boolean imamMlin;
	boolean premikam;
	
	
	public Igra() {
		plosca = new IgralnaPloscaInfo();
		
		igralec = new Igralec("igralec");
		racunalnik = new Igralec("racunalnik");
		naPotezi = igralec;
		nasprotnik = racunalnik;
		imamMlin = false;
		premikam = false;
	}
	
	/* Preveri, èe smo s tem ko smo zasedli polje, naredili mlin. 
	Vrne vector s trojicami, ki sestavljajo mlin. Ker lahko z eno potezo naredimo veè kot en mlin, 
	so v vektorji po tri zaporedna polja skupaj en mlin. Torej lahko dobimo vector s tremi, šestimi 
	ali devetimi elementi. Èe ni mlina, vrne null.
	*/ 
	public Vector<Integer> jeMlin(int polje) {
		Vector<Integer> mlin = new Vector<>(3, 6);
		Set<Set<Integer>> kandidati = IgralnaPloscaInfo.kandidatiZaMlin.get(polje); // množica parov, ki skupajs poljem polje tvorijo trojico

		for (Set<Integer> par : kandidati) { 
			Vector<Integer> a = new Vector<>(3);
			a.add(polje);
			for (int i : par) {
				if (IgralnaPloscaInfo.tabela[polje].zasedenost != IgralnaPloscaInfo.tabela[i].zasedenost) 
					break;
					a.add(i);
					// preverim èe imam 3, torej za cel mlin
					if (a.size() != 0 && a.size() % 3 == 0) mlin.addAll(a);
			}
		}
	return mlin;
	}
			
	
	public boolean jeNasprotnikovo (Polje polje) { // preveri, èe polje pripada nasprotniku
		return (polje.zasedenost == nasprotnik.ime);
	}

	public boolean jeIgralcevo (Polje polje) { // preveri, èe polje pripada tistemu, ki je na potezi
		return (polje.zasedenost == naPotezi.ime);
	}
	
	
	public void postaviPloscek(Polje koncno) {
		if (!Pravila.jePraznoPolje(naPotezi, koncno, this))
			return; // èe poteza ni veljavna, ne naredim niè
		
		if (naPotezi.faza == 1) {
			++naPotezi.stPotez1;
			Igralec.naslednjaFaza(naPotezi);
		}
		koncno.zasedenost = naPotezi.ime;
		System.out.println("postavljam gor");

		imamMlin = jeMlin(koncno.indeks).size() > 1;
		if (!imamMlin) // èe ni mlina, sem konec s potezo
			zamenjajIgralca();
	}
	
	
	public void premakni(Igralec player, Polje zacetno, Polje koncno) {
		if (Pravila.lahkoPremaknem(zacetno, koncno, this)) {
			if (zacetno.zasedenost != player.ime)
				return;
			zacetno.zasedenost = Polje.prazno;
			koncno.zasedenost = player.ime;
			if (jeMlin(koncno.indeks).size() > 1)
				imamMlin = true;
			zamenjajIgralca();
		}
		// preveri, èe je konec igre in kaj se takrat zgodi...
	}
	
	
	public void vzemiPloscek(Polje vzemi) {
		if (premikam) {
			System.out.println("izpraznim polje");
			vzemi.zasedenost = Polje.prazno;
		}
		else if (imamMlin) {
			if (!Pravila.lahkoVzamem(naPotezi, vzemi, this))
				return;
			System.out.println("ker imam mlin, vzamem enega");
			vzemi.zasedenost = Polje.prazno;
			nasprotnik.ploscki--;
			imamMlin = false;
			zamenjajIgralca();
			Igralec.naslednjaFaza(nasprotnik);
		}
		konecIgre(); //preveri, èe je že konec igre
	}
	
	
	private void zamenjajIgralca() {
		// ko je konec poteze, je na vrsti drugi igralec
		naPotezi = (naPotezi == igralec) ? racunalnik : igralec;
		nasprotnik = (naPotezi == igralec) ? racunalnik : igralec;
	}

		
	private boolean konecIgre() {
	return ((igralec.faza == 3 && igralec.ploscki < 3) ||
			(racunalnik.faza == 3 && racunalnik.ploscki < 3));
	}

	
	public boolean obstajaMlin () {
		for (Polje polje : plosca.tabela) {
			if (jeMlin(polje.indeks).size() > 1 && !plosca.tabela[jeMlin(polje.indeks).get(0)].jePrazno())
				return true;
		}
		return false;
	}
}

/* OPOMBE:
 * èe bo èas, pazi:
 * da ne moreš istegamlinèka z istimi figurami ponavljati ves èas,
 * da se neki zgodi, èe prideš v brezizhodni položaj
 */
