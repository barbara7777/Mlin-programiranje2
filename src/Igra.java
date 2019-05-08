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
	
	/* Preveri, �e smo s tem ko smo zasedli polje, naredili mlin. 
	Vrne vector s trojicami, ki sestavljajo mlin. Ker lahko z eno potezo naredimo ve� kot en mlin, 
	so v vektorji po tri zaporedna polja skupaj en mlin. Torej lahko dobimo vector s tremi, �estimi 
	ali devetimi elementi. �e ni mlina, vrne null.
	*/ 
	public Vector<Integer> jeMlin(int polje) {
		Vector<Integer> mlin = new Vector<>(3, 6);
		Set<Set<Integer>> kandidati = IgralnaPloscaInfo.kandidatiZaMlin.get(polje); // mno�ica parov, ki skupajs poljem polje tvorijo trojico

		for (Set<Integer> par : kandidati) { 
			Vector<Integer> a = new Vector<>(3);
			a.add(polje);
			for (int i : par) {
				if (IgralnaPloscaInfo.tabela[polje].zasedenost != IgralnaPloscaInfo.tabela[i].zasedenost) 
					break;
					a.add(i);
					// preverim �e imam 3, torej za cel mlin
					if (a.size() != 0 && a.size() % 3 == 0) mlin.addAll(a);
			}
		}
	return mlin;
	}
			
	
	public boolean jeNasprotnikovo (Polje polje) { // preveri, �e polje pripada nasprotniku
		return (polje.zasedenost == nasprotnik.ime);
	}

	
	
	
	public void postaviPloscek(Polje koncno) {
		if (!Pravila.lahkoPostavim(naPotezi, koncno, this))
			return; // �e poteza ni veljavna, ne naredim ni�
		
		if (naPotezi.faza == 1) {
			++naPotezi.stPotez1;
			Igralec.naslednjaFaza(naPotezi);
		}
		koncno.zasedenost = naPotezi.ime;
		System.out.println("postavljam gor");

		imamMlin = jeMlin(koncno.indeks).size() > 1;
		if (!imamMlin) // �e ni mlina, sem konec s potezo
			zamenjajIgralca();
	}
	
	public void vzemiPloscek(Polje vzemi) {
		//if (!Pravila.jeVeljavna(naPotezi, new Poteza(null, null, vzemi), this))
			//return;	
		
		//if (!premikam)
			//zamenjajIgralca();
		
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
		konecIgre(); //preveri, �e je �e konec igre
	}
	
	
	// prej moras preveriti da je poteza veljavna (predenj pokli�e� to funkcijo) (�e je kon�no prazno, ...)
	public void narediPotezo(Poteza poteza) {
		System.out.println("Igra " + naPotezi + " proti " + nasprotnik);
		if (!Pravila.jeVeljavna(naPotezi, poteza, this)) return; // �e poteza ni veljavna, ne naredim ni�
		
		
		if (poteza.zacetno != null) {
			poteza.zacetno.zasedenost = Polje.prazno; // �e imamo za�etno polje, ga izpraznimo
		}
		else { // �e nimamo za�etnega polja, ustrezno spremenimo �tevilo potez na prvi stopnji za igralca
			++naPotezi.stPotez1;
			Igralec.naslednjaFaza(naPotezi); // in preverimo, �e je �e dosegel drugo stopnjo 
		}
		
		// izvedemo premik
		Poteza.dodajPloscek(naPotezi, poteza.koncno);
		
		// od�tejem plo��ek nasprotniku od tistega, ki ima mlin
		if (poteza.vzemi != null) {
			//poteza.vzemi.zasedenost = Polje.prazno;
			Poteza.vzemiPloscek(poteza.vzemi);
			--nasprotnik.ploscki;
			imamMlin = false; 
			naPotezi.vzemi = false;
		} 
			
		// to ne sodi sem
		if (jeMlin(poteza.koncno.indeks).size() != 0) {
			naPotezi.vzemi = true;
			System.out.println("Imamo mlin! " + jeMlin(poteza.koncno.indeks)); 
			imamMlin = true;
		}// dodaj kaj se zgodi, �e je mlin
		
		
		naPotezi.delPoteze = 1; // ko opravim potezo, mora biti spet del poteze 1.
		
		// dodaj if konecIgre() se nekaj zgodi.
		if (konecIgre()) {
			System.out.println("KONEC IGRE!");
			System.out.println("Zmagal je " + naPotezi);
		}
	/*		
		for (int i = 0; i < plosca.tabela.length; i++) {
			if (i%3 == 0)
				System.out.println();
			System.out.print(plosca.tabela[i].zasedenost + "  ");
		}
	*/
		
	}
	private void zamenjajIgralca() {
		// ko je konec poteze, je na vrsti drugi igralec
		naPotezi = (naPotezi == igralec) ? racunalnik : igralec;
		nasprotnik = (naPotezi == igralec) ? racunalnik : igralec;
		// dodaj spreminjanje parametrov pri igralcu, ce je mlin ipd.
		
	}
	
	private boolean konecIgre() {
	return ((igralec.faza == 3 && igralec.ploscki < 3) ||
			(racunalnik.faza == 3 && racunalnik.ploscki < 3));
	}
	
	public boolean moveReady () {
		return (naPotezi.delPoteze == 3);
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
 * �e bo �as, pazi:
 * da ne more� istegamlin�ka z istimi figurami ponavljati ves �as,
 * da se neki zgodi, �e pride� v brezizhodni polo�aj
 */
