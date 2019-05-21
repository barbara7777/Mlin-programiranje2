public class Igra {
	
	public static Igralec igralec1, igralec2;
	public static Igralec naPotezi;
	public static Igralec nasprotnik;
	static GUIigralnaPlosca GUI = new GUIigralnaPlosca(700, 700);
	
	static boolean imamMlin = false;
	static boolean igramProtiAI = true;
	static boolean zamrzni;
	static boolean oznaciMoznePoteze;
	
	public static Polje zacetno = null;
	public static Polje koncno = null;
	public static Polje vzemi = null;
	
	
	public static void main(String[] args) {
		novaIgra();
		new GUIokno(840, 710);
	}
	
	public static void novaIgra () {
		IgralnaPloscaInfo.ustvariIgralnoPlosco();
		naPotezi = igralec1 = new Igralec("igralec");
		nasprotnik = igralec2 = new Igralec("racunalnik");
		zamrzni = false;
		oznaciMoznePoteze = false;
		
		prt("Nova igra");
	}
	public Igra(Igra igra) {
		Polje[] tabela = new Polje[24];
		for (int i = 0; i < 24; i++) {
			tabela[i] = IgralnaPloscaInfo.tabela[i];
		}
		this.naPotezi = igra.naPotezi;
	}
	static void prt (Object o) {
		System.out.println(o);
	}
	
	public static boolean jePoljeNasprotnikovo (Polje polje) { // preveri, èe polje pripada nasprotniku
		return (polje.zasedenost == nasprotnik.ime);
	}	
	
	public static boolean jePoljeIgralcevo (Polje polje) { // preveri, èe polje pripada tistemu, ki je na potezi
		return (polje.zasedenost == naPotezi.ime);
	}
		
	public static void zamenjajIgralca() {
		// ko je konec poteze, je na vrsti drugi igralec
		naPotezi = nasprotnik;
		nasprotnik = (naPotezi == igralec1) ? igralec2 : igralec1;
		
		if(naPotezi == igralec2 && igramProtiAI) {
			// ce je na potezi racunalnik, on izvede svojo potezo
			AI.narediRandomPotezo();
		}
	}
	
	public static boolean klikNaPolje (Polje polje) {
		//prt("Klik na polje: " + polje.indeks);
		narediPotezo(polje);
		return false;
	}
	
	public static void postaviPloscek(Polje koncno) {
		System.out.println(IgralnaPloscaInfo.tabela[1].zasedenost);
		if (!Pravila.jePraznoPolje(naPotezi, koncno))
			return; // èe poteza ni veljavna, ne naredim niè	
		if (naPotezi.faza == 1) {
			++naPotezi.stPotez1;
			Igralec.naslednjaFaza(naPotezi);
		}
		koncno.zasedenost = naPotezi.ime;
		prt("postavljam gor");

		imamMlin = Pravila.jeMlin(koncno.indeks).size() > 1;
		if (!imamMlin) // èe ni mlina, sem konec s potezo
			zamenjajIgralca();
	}

	public static void vzemiPloscek(Polje vzemi) {
		if (imamMlin) {
			if (!jePoljeNasprotnikovo(vzemi))
				return;
			prt("ker imam mlin, vzamem enega");
			vzemi.zasedenost = Polje.prazno;
			nasprotnik.ploscki--;
			imamMlin = false;
			Igralec.naslednjaFaza(nasprotnik);
			zamenjajIgralca();
		}
		konecIgre(); //preveri, èe je že konec igre
	}
	
	public static boolean premakni(Igralec player, Polje zacetno, Polje koncno) {
		if (Pravila.lahkoPremaknem(zacetno, koncno, naPotezi)) {
			if (zacetno.zasedenost != player.ime)
				return false;
			zacetno.zasedenost = Polje.prazno;
			koncno.zasedenost = player.ime;
			if (Pravila.jeMlin(koncno.indeks).size() > 1)
				imamMlin = true;
			//zamenjajIgralca();
			return true;
		}
		// preveri, èe je konec igre in kaj se takrat zgodi...
		return false;
	}

	// glavna funkcija, ki dela poteze
	public static void narediPotezo(Polje izbranoPolje) {
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
				if (!jePoljeNasprotnikovo(izbranoPolje)) {
					prt("Poskusal si vzeti ploscek ki ni nasprotikov. Ponovi.");
					break;
				}
				vzemiPloscek(izbranoPolje);
				zacetno = null;
				koncno = null;
				break;
			}
			if (zacetno == null) { // Zacetno je treba nastaviti
				if (jePoljeIgralcevo(izbranoPolje)) {
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
				if (jePoljeIgralcevo(izbranoPolje)) {
					zacetno = izbranoPolje;
					break;
				}
				if (!Pravila.jePraznoPolje(naPotezi, izbranoPolje)) {
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
	
	public static boolean konecIgre() {
		if (naPotezi.ploscki < 3 || nasprotnik.ploscki < 3) {
			System.out.println("konec igre");
			zamrzni = true;
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
