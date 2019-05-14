import java.util.Set;
import java.util.Vector;


public class Igra {

	IgraAI racunalnikAI;
	IgralnaPloscaInfo plosca;
	int[][] trojice = new int[20][3];
	public static Igralec igralec, racunalnik;
	public Igralec naPotezi;
	public Igralec nasprotnik;
	
	boolean imamMlin;
	boolean premikam;
	boolean zamrzni;
	
	Polje zacetno = null;
	Polje koncno = null;
	Polje vzemi = null;
	
	
	public Igra() {
		plosca = new IgralnaPloscaInfo();
		
		igralec = new Igralec("igralec");
		racunalnik = new Igralec("racunalnik");
		naPotezi = igralec;
		nasprotnik = racunalnik;
		racunalnikAI = new IgraAI(this);
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
			
	// premiki in poteze v igri ----------------------------------------------------------
	
	public boolean jeNasprotnikovo (Polje polje) { // preveri, �e polje pripada nasprotniku
		return (polje.zasedenost == nasprotnik.ime);
	}

	
	public boolean jeIgralcevo (Polje polje) { // preveri, �e polje pripada tistemu, ki je na potezi
		return (polje.zasedenost == naPotezi.ime);
	}
	

	public boolean obstajaMlin () {
		for (Polje polje : plosca.tabela) {
			if (jeMlin(polje.indeks).size() > 1 && !plosca.tabela[jeMlin(polje.indeks).get(0)].jePrazno())
				return true;
		}
		return false;
	}
	
	
	public void zamenjajIgralca() {
		// ko je konec poteze, je na vrsti drugi igralec
		naPotezi = (naPotezi == igralec) ? racunalnik : igralec;
		nasprotnik = (naPotezi == igralec) ? racunalnik : igralec;
	}
	
	
	public void postaviPloscek(Polje koncno) {
		if (!Pravila.jePraznoPolje(naPotezi, koncno, this))
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
			Igralec.naslednjaFaza(nasprotnik);
			zamenjajIgralca();
		}
		konecIgre(); //preveri, �e je �e konec igre
	}
	
	
	public boolean premakni(Igralec player, Polje zacetno, Polje koncno) {
		if (Pravila.lahkoPremaknem(zacetno, koncno, this)) {
			if (zacetno.zasedenost != player.ime)
				return false;
			zacetno.zasedenost = Polje.prazno;
			koncno.zasedenost = player.ime;
			if (jeMlin(koncno.indeks).size() > 1)
				imamMlin = true;
			//zamenjajIgralca();
			return true;
		}
		// preveri, �e je konec igre in kaj se takrat zgodi...
		return false;
	}

	public void prt (String str) {
		System.out.println(str);
	}

	
	// glavna funkcija, ki dela poteze
	public void narediPotezo(Polje izbranoPolje) {
		switch (naPotezi.faza) { 
		case 1:
			if (imamMlin) {
				vzemiPloscek(izbranoPolje);
			}
			else{
				postaviPloscek(izbranoPolje);
			}
			break;
			
		case 2:
		case 3:
			//na zacetku so vsi null, to so: zacetno, koncno,vzemi
			//Ce je zacetno null, ga nastavim. Tu mlina ne more biti
			//Ce zacetno ni null, koncno pa je, potem moram nastaviti konco. Tudi tu mlina ni
			//Ce zacetno ni null in koncno ni null, potem ga je treba prestavit (samo ce mlina ni 
			//ker ce mlin je, potem morem enega vzeti). Ce ni mlina, lahko vse resitiram
			//Ce pa je mlin, moram enega vzeti
			
			if (imamMlin){
				//Poberemo ploscico, ce je poteza veljavna
				if (!jeNasprotnikovo(izbranoPolje)) {
					prt("Poskusal si vzeti ploscek ki ni nasprotikov. Ponovi.");
					break;
				}
				vzemiPloscek(izbranoPolje);
				zacetno = null;
				koncno = null;
				break;
			}
			if (zacetno == null) { // Zacetno je treba nastaviti
				if (jeIgralcevo(izbranoPolje)) {
					prt("Izbral si zacetno polje premika");
					zacetno = izbranoPolje;
					break;
				}
				else
					izbranoPolje = null;
					prt("Za zacetno polje si izbral neveljavno. Ponovi.");
				break;
			}
			if (koncno == null) { // Koncno polje je treba nastaviti
				//Koncno polje je lahko samo prazno polje
				if (jeIgralcevo(izbranoPolje)) {
					zacetno = izbranoPolje;
					break;
				}
				if (!Pravila.jePraznoPolje(naPotezi, izbranoPolje, this)) {
					prt("Za koncno polje nisi izbral praznega polja. Ponovi izbiro koncega polja");
					zacetno = null;
					izbranoPolje = null;
					break;
				}
				koncno = izbranoPolje;
				prt("Izbral si koncno polje");
			}
			//Tukaj je treba torej ploscico prestaviti, ce mlina ni (pri prestavitvi ga ne sme se biti)
			if (!imamMlin) {  //Prestavimo ploscico
				if (!premakni(naPotezi,  zacetno, koncno)) {
					prt("Poteza ni mozna, ker polja nista povezana. Ponovi potezo");
					koncno = null;
					zacetno = null;
					break;
				}
				//Ce zdaj ni mlina na novo, potem
				//lahko resitiram vse. Ce je, potrebujem nov klik
				if (imamMlin) { //Ker je mlin, potrebujem klik
					prt("Naredil si mlin. Vzemi en ploscek");
					break;
				}
				zacetno = null;
				koncno = null;
				zamenjajIgralca();
			}
			break;
		}
	}
	
	
	public boolean konecIgre() {
	if ((igralec.faza == 3 && igralec.ploscki < 3) ||
		(racunalnik.faza == 3 && racunalnik.ploscki < 3)) {
		System.out.println("konec igre");
		zamrzni = true;
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
