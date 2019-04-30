import java.util.Arrays;
import java.util.Set;
import java.util.Vector;


public class Igra {

	IgralnaPloscaInfo plosca;
	int[][] trojice = new int[20][3];
	public static Igralec igralec, racunalnik;
	public Igralec naPotezi;
	public Igralec nasprotnik;
	
	public Igra() {
		plosca = new IgralnaPloscaInfo();
		
		igralec = new Igralec("igralec");
		racunalnik = new Igralec("racunalnik");
		naPotezi = igralec;
		nasprotnik = racunalnik;
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
		
	
	// prej moras preveriti da je poteza veljavna (predenj pokli�e� to funkcijo) (�e je kon�no prazno, ...)
	public void narediPotezo(Poteza poteza) {
		System.out.println("Igra " + naPotezi + " proti " + nasprotnik);
		if (!Pravila.jeVeljavna(naPotezi, poteza, this)) return; // �e poteza ni veljavna, ne naredim ni�

		if (poteza.zacetno != null) {
			poteza.zacetno.zasedenost = Polje.prazno; // �e imamo za�etno polje, ga izpraznimo
		}
		else { // �e nimamo za�etnega polja, ustrezno spremenimo �tevilo potez na prvi stopnji za igralca
			++naPotezi.stPotez1;
			Poteza.dodajPloscek(naPotezi,  poteza.koncno);
			Igralec.naslednjaFaza(naPotezi); // in preverimo, �e je �e dosegel drugo stopnjo 
		}
		
		// da je kon�no polje prazno, preverimo �e v veljavnosti poteze
		Poteza.dodajPloscek(naPotezi, poteza.koncno);
		
		// od�tejem plo��ek nasprotniku od tistega, ki ima mlin
		if (poteza.vzemi != null) {
			poteza.vzemi.zasedenost = Polje.prazno;
			if (naPotezi == igralec) --igralec.ploscki;
			else --racunalnik.ploscki; 
		} 
			
		// ko je konec poteze, je na vrsti drugi igralec
		naPotezi = (naPotezi == igralec) ? racunalnik : igralec;
		nasprotnik = (naPotezi == igralec) ? racunalnik : igralec;
		// dodaj spreminjanje parametrov pri igralcu, ce je mlin ipd.
		if (jeMlin(poteza.koncno.indeks).size() != 0) 
			System.out.println("Imamo mlin! " + jeMlin(poteza.koncno.indeks));  
		// dodaj kaj se zgodi, �e je mlin
		
		// dodaj if konecIgre() se nekaj zgodi.
	}
	
	private boolean konecIgre() {
	return ((igralec.faza == 3 && igralec.ploscki < 3) ||
			(racunalnik.faza == 3 && racunalnik.ploscki < 3));
	}
	
}

/* OPOMBE:
 * �e bo �as, pazi:
 * da ne more� istegamlin�ka z istimi figurami ponavljati ves �as,
 * da se neki zgodi, �e pride� v brezizhodni polo�aj
 */
