import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
public class AI {
	
		public static Igra igra;
		private static final int vrednostzunanjihKotov = 1;
		private static final int vrednostSrednjihzunanjihKotov = 2;
		private static final int vrednostKrizisc3 = 3;
		private static final int vrednostKrizisc4 = 4;
		private static final float max = -Float.MAX_VALUE;
		private static final float min = Float.MIN_VALUE;
	
		// Funkcija, ki pove vse možne poteze - za igralca na potezi vrne list polj, ki jih lahko izbere
		
		
		public static Polje dobiKoncnoPolje(Poteza poteza){
			Polje polje = poteza.koncno;
			return polje;
		}
		
		
		public static ArrayList<Polje> moznePoteze () { 
			System.out.println("na potezi je " + Igra.naPotezi.ime);
			
			ArrayList<Polje> poteze = new ArrayList<Polje>();
			
			// ce imam mlin, so mozne poteze neodvisne od faze igre
			if (Igra.imamMlin){ 
				for (Polje polje : IgralnaPloscaInfo.tabela) {
					if (polje.zasedenost == Igra.nasprotnik.ime)
						poteze.add(polje);
				}
				return poteze;
			}
			// mozna poteza v odvisnosti od faze igralca
			switch (Igra.naPotezi.faza) {
			case 1: 
				// postavim se lahko na katerokoli prazno polje
				for (Polje polje : IgralnaPloscaInfo.tabela) { 
						if (polje.zasedenost == Polje.prazno)
							poteze.add(polje);
				}
				break;
				
			case 2:
				if (Igra.zacetno == null) { // Zacetno je treba izbrati
					for (Polje polje : IgralnaPloscaInfo.tabela) { 
						// ko hocem premakniti, lahko zacnem le s svojim poljem 
						// AMPAK to polje mora biti povezano s praznim!!
						if (polje.zasedenost == Igra.naPotezi.ime) { // za moja polja
							for (Polje polje2 : polje.povezave) { // pogledam povezana
								if (polje2.zasedenost == Polje.prazno) 
										poteze.add(polje);	
							}
						}
					}
				}
				else { // koncnega je treba izbrati
					for (Polje polje : Igra.zacetno.povezave) {
						if (polje.zasedenost == Polje.prazno)
							poteze.add(polje);
					}
				}
				break;
				
			case 3:
				if (Igra.zacetno == null) {
					// zacnem lahko s katerimkoli svojim plosckom
					for (Polje polje : IgralnaPloscaInfo.tabela) { 
						if (polje.zasedenost == Igra.naPotezi.ime)
							poteze.add(polje);
					}
				}
				else {
					for (Polje polje : IgralnaPloscaInfo.tabela) { 
						if (polje.zasedenost == Polje.prazno)
							poteze.add(polje);
					}
				}
				break;
				
			}
			return poteze;
	}
		
		public static ArrayList<Poteza> moznePoteze2 () { 
			System.out.println(Igra.naPotezi.faza);
			List<Polje> polja = pridobipraznapolja();
			ArrayList<Poteza> poteze = new ArrayList<Poteza>();
			
			// mozna poteza v odvisnosti od faze igralca
			switch (Igra.naPotezi.faza) {
			case 1: 
				// postavim se lahko na katerokoli prazno polje
				for (Polje polje : polja) { 
						if (polje.zasedenost == Polje.prazno)
							poteze.add(new Poteza(null, polje, null));
				}
				break;
				
			case 2:
				if (Igra.zacetno == null) { // Zacetno je treba izbrati
					for (Polje polje : IgralnaPloscaInfo.tabela) { 
						// ko hocem premakniti, lahko zacnem le s svojim poljem 
						// AMPAK to polje mora biti povezano s praznim!!
						if (polje.zasedenost == Igra.naPotezi.ime) { // za moja polja
							for (Polje polje2 : polje.povezave) { // pogledam povezana
								if (polje2.zasedenost == Polje.prazno) 
										poteze.add(new Poteza(polje, polje2, null));	
							}
						}
					}
				}
				break;
				
			case 3:
				if (Igra.zacetno == null) {
					// zacnem lahko s katerimkoli svojim plosckom
					for (Polje polje : IgralnaPloscaInfo.tabela) { 
						if (polje.zasedenost == Igra.naPotezi.ime) {
							for (Polje polje2 : IgralnaPloscaInfo.tabela) { // pogledam povezana
								if (polje2.zasedenost == Polje.prazno) 
										poteze.add(new Poteza(polje, polje2, null));	
							}
						}
					}
				}
				break;
				
			}
			return poteze;
	}
		
		
		//s to funkcijo želimo pridobiti naslednji premik, ki bi bil za računalnik najboljši
		
		public static Poteza pridobiNaslednjiPremik() {
			List<Poteza> poteze = pridobiRacpoteze();
			float maxi = max;
			Poteza najPoteza = null;
			for (Poteza poteza : poteze) {
				
				poteza.koncno.zasedenost = "racunalnik";
				poteza.zacetno.zasedenost = Polje.prazno;
				if (Igra.imamMlin) {
					poteza.koncno.zasedenost = Polje.prazno;
					poteza.zacetno.zasedenost = "racunalnik";
					Igra.imamMlin = false;
					poteza.vzemi = vzemiPloscek();
					System.out.println("tukaj");
					return poteza;
				}
				
				float ocena = pridobiOcenoZaPremik();
				poteza.koncno.zasedenost = Polje.prazno;
				poteza.zacetno.zasedenost = "racunalnik";
				if (ocena > maxi) {
					maxi = ocena;
					najPoteza = poteza;
					
				}
				System.out.println(poteza);
			}
			return najPoteza;
		}
		
		//funkcija, ki vzame random plošček, če pride do mlina
		
		private static Polje vzemiPloscek() {
			List <Polje> igrPolja = pridobiIgrpolja();
			int n = ThreadLocalRandom.current().nextInt(0, igrPolja.size());
			Polje randomPolje = igrPolja.get(n);
			return randomPolje;
		}

		private static float pridobiOcenoZaPremik() {
			System.out.println("tukaj");
			return pridobiOcenoZaPotezoRac(1);
		}
		
		
		//s to funkcijo pridobimo oceno za neko potezo računalnika - vrnemo maksimalno
		
		private static float pridobiOcenoZaPotezoRac(int globina) {
			if (globina == 0) {
				return oceniPlosco();
			}
			List<Poteza> moznePoteze = pridobiRacpoteze();

			float maxOcena = max;
			for (Poteza poteza : moznePoteze) {
				poteza.koncno.zasedenost = "racunalnik";
				poteza.zacetno.zasedenost = Polje.prazno;
				if (Igra.imamMlin) {
					poteza.koncno.zasedenost = Polje.prazno;
					poteza.zacetno.zasedenost = "racunalnik";
					Igra.imamMlin = false;
					return 100;
				}
				float ocena = pridobiOcenoZaPotezoIgr(globina);
				if (ocena > maxOcena) {
					maxOcena = ocena;
				}

				poteza.koncno.zasedenost = Polje.prazno;
				poteza.zacetno.zasedenost = "racunalnik";
			}
			return maxOcena;
		}
		
		
		//s to funkcijo pridobimo oceno za potezo igralca - vrnemo minimalno
		private static float pridobiOcenoZaPotezoIgr(int globina) {
			List<Poteza> poteze = pridobiIgrpoteze();
			float minOcena = min;

			for (Poteza poteza : poteze) {
				poteza.koncno.zasedenost = "igralec";
				poteza.zacetno.zasedenost = Polje.prazno;
				if (Igra.imamMlin) {
					poteza.koncno.zasedenost = Polje.prazno;
					poteza.zacetno.zasedenost = "igralec";
					Igra.imamMlin = false;
					return -100;
				}
				float ocena = pridobiOcenoZaPotezoRac(globina - 1);
				poteza.koncno.zasedenost = Polje.prazno;
				poteza.zacetno.zasedenost = "igralec";
				if (ocena < minOcena) {
					minOcena = ocena;
				}
			}
			System.out.println(minOcena);
			return minOcena;
		}
		
		
		
		
		public static void racunalnikovaPoteza() {
			
			Poteza poteza = pridobiNaslednjiPremik();
			Igra.narediPotezoRac(poteza);
			
		}
		
		//oceni celotno plosco koliko je vredna za racunalnik iz ocen polj
		
		public static int oceniPlosco() {
			Polje [] tabela = IgralnaPloscaInfo.getTabela();
			int ocena = 0;
			for (Polje polje : tabela) {
				if (polje.zasedenost == "racunalnik") {
					ocena+= oceniPolje(polje);
				}
				if (polje.zasedenost == "igralec") {
					ocena-= oceniPolje(polje);
				}
			}
			return ocena;
		}
		
		//pridobi racunalniska polja
		
		public static List<Polje> pridobiRacpolja(){
			List<Polje> polja = new LinkedList<>();
			for (Polje polje : IgralnaPloscaInfo.getTabela()) {
				if (polje.zasedenost == "racunalnik") {
					polja.add(polje);
				}
			}
			return polja;
		}
		
		//pridobi igralceva polja
		
		public static List<Polje> pridobiIgrpolja(){
			List<Polje> polja = new LinkedList<>();
			for (Polje polje : IgralnaPloscaInfo.getTabela()) {
				if (polje.zasedenost == "igralec") {
					polja.add(polje);
				}
			}
			return polja;
		}
		
		public static List<Polje> pridobipraznapolja(){
			List<Polje> polja = new LinkedList<>();
			for (Polje polje : IgralnaPloscaInfo.getTabela()) {
				if (polje.zasedenost == Polje.prazno) {
					polja.add(polje);
				}
			}
			return polja;
		}
		
		public static ArrayList<Poteza> pridobiIgrpoteze() {
			ArrayList<Poteza> poteze = moznePoteze2();
			return poteze;
		}
		
		public static ArrayList<Poteza> pridobiRacpoteze() {
			ArrayList<Poteza> poteze = moznePoteze2();
			return poteze;
		}
		
		//oceni koliko je polje vredno
		
		public static int oceniPolje (Polje p) {
			int indeks = p.indeks;
			for (int element : IgralnaPloscaInfo.kotni) {
					if (indeks == element) {
						return vrednostzunanjihKotov;
				}
				
			}
			for (int element : IgralnaPloscaInfo.kotni_sredina) {

					if (indeks == element) {
						return vrednostSrednjihzunanjihKotov;
				} 
			}
			for (int element : IgralnaPloscaInfo.trojnikot) {
					if (indeks == element) {
						return vrednostKrizisc3;
				}
			}
			for (int element : IgralnaPloscaInfo.cetvornikot) {
					if (indeks == element) {
						return vrednostKrizisc4;
				}
			}
			return 0;
		}
		
		// narediRacPotezo() se ne uporablja, ker vrača vrednosti null
		
		
		public static void narediRacPotezo() {
		int i = 0;
		Polje randomPolje = null;
		while(i < 3) {
			
			ArrayList<Polje> vseMozne = moznePoteze();
			if (vseMozne.size() == 0) {
				
				return;
			}
			Poteza poteza = pridobiNaslednjiPremik();
			switch (i) {
			
			case 0:
				if (poteza.zacetno == null) {
					++i;
					break;
				}
				else {
					randomPolje = poteza.zacetno;
				}
				
			case 1:
				if (poteza.koncno == null) {
					++i;
					break;
				}
				else {
					randomPolje = poteza.koncno;
				}
			case 2:
				
					randomPolje = poteza.vzemi;
					
			Igra.narediPotezo(randomPolje);
			
		}
	}
		}
		
		
		public static void narediRandomPotezo() {
			while(Igra.naPotezi==Igra.igralec2) {
				ArrayList<Polje> vseMozne = moznePoteze();
				if (vseMozne.size() == 0) {
					System.out.println("ni moznih potez");
					
					return;
				}
				int n = ThreadLocalRandom.current().nextInt(0, vseMozne.size());
				Polje randomPolje = vseMozne.get(n);
				Igra.narediPotezo(randomPolje);
				
			}
		}
}
