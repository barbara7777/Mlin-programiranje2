
public class Igra {

	IgralnaPlosca plosca;
	int[][] trojice = new int[20][3];
	public Igralec igralec, racunalnik;
	public Igralec naPotezi;
	
	public Igra() {
		plosca = new IgralnaPlosca();
		
		igralec = new Igralec();
		racunalnik = new Igralec();
		naPotezi = igralec;
	}
	
	
	//
	// sestavi seznam polj, ki skupaj sestavljajo mlin. 
	private void dodajVTrojice() {
		for (int i = 0; i <= 8; i++) {
			trojice[i][0] = i * 3;
			trojice[i][1] = i * 3 + 1;
			trojice[i][2] = i * 3 + 2;
		}
		dodajTrojice(9, 0, 9, 21);
		dodajTrojice(10, 1, 4, 7);
		dodajTrojice(11, 2, 14, 23);
		dodajTrojice(12, 3, 10, 18);
		dodajTrojice(13, 6, 11, 15);
		dodajTrojice(14, 8, 12, 17);
		dodajTrojice(15, 5, 13, 20);
		dodajTrojice(16, 16, 19, 22);
		dodajTrojice(17, 0, 3, 6);
		dodajTrojice(18, 2, 5, 8);
		dodajTrojice(19, 15, 18, 21);
		dodajTrojice(20, 17, 20, 23);
	}
	
	private void dodajTrojice(int indeks, int a, int b, int c) {
		trojice[indeks][0] = a;
		trojice[indeks][1] = b;
		trojice[indeks][2] = c;
	}
	
	// preveri, èe je na igralni plošèi kak mlin. 
	public int[] jeMlin(int prvo, int drugo, int tretje) {
		for (int[] trojica : trojice) { 
			// ko imaš definirano barvo zadnjega igralca,
			//spremeni prvi pogoj v == barva trenutnega igralca
			if (plosca.polje(trojica[0]).zasedenost != Zasedeno.PRAZNO && 
				(plosca.polje(trojica[0]).zasedenost == 
				plosca.polje(trojica[1]).zasedenost) && 
				(plosca.polje(trojica[1]).zasedenost == 
				plosca.polje(trojica[2]).zasedenost)) {
				return trojica;
			}
		}
		return null;
	}
	//
	
	public boolean jeNasprotnikovo (Igralec player, Polje polje) { // preveri, èe polje pripada nasprotniku
		if (player == racunalnik) 
			return (polje.zasedenost == Zasedeno.IGRALEC);
		else 
			return (polje.zasedenost == Zasedeno.RACUNALNIK);
	}
	
	// prej moras preveriti da je poteza veljavna (predenj poklièeš to funkcijo) (èe je konèno prazno, ...)
	public void narediPotezo(Poteza poteza) {
		if (!Pravila.jeVeljavna(naPotezi, poteza, this)) return; // èe poteza ni veljavna, ne naredim niè
	
		if (poteza.zacetno != null) {
			poteza.zacetno.zasedenost = Zasedeno.PRAZNO; // èe imamo zaèetno polje, ga izpraznimo
			// DODAJ dejansko potezo npr premakni();
		}
		else { // èe nimamo zaèetnega polja, ustrezno spremenimo število potez na prvi stopnji za igralca
			++naPotezi.stPotez1;
			Igralec.naslednjaFaza(naPotezi); // in preverimo, èe je že dosegel drugo stopnjo 
		}
		poteza.koncno.zasedenost = (naPotezi == igralec) ? Zasedeno.IGRALEC : Zasedeno.RACUNALNIK;
		
		// odštejem plošèek nasprotniku od tistega, ki ima mlin
		if (poteza.vzemi != null) {
			poteza.vzemi.zasedenost = Zasedeno.PRAZNO;
			if (naPotezi == igralec) --igralec.ploscki;
			else --racunalnik.ploscki; 
			
		// ko je konec poteze, je na vrsti drugi igralec
		naPotezi = (naPotezi == igralec) ? racunalnik : igralec;
		} 
		// dodaj spreminjanje parametrov pri igralcu ipd
	
	}
	
	private boolean konecIgre() {
	return ((igralec.faza == 3 && igralec.ploscki < 3) ||
			(racunalnik.faza == 3 && racunalnik.ploscki < 3));
	}
	
}

/* OPOMBE:
 * èe bo èas, pazi:
 * da ne moreš istegamlinèka z istimi figurami ponavljati ves èas,
 * da se neki zgodi, èe prideš v brezizhodni položaj
 */
