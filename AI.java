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
	
		// Funkcija, ki pove vse možne poteze - za igralca na potezi vrne list polj, ki jih lahko izbere
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
		
		
		private static final int ZMAGA = (1 << 20); // vrednost zmage, veÄ kot vsaka druga ocena pozicije
		private static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
		
		public static List<OcenjenaPoteza> oceniPoteze(Igra igra, int globina, Igralec jaz) {
			List<OcenjenaPoteza> ocenjenePoteze = new LinkedList<OcenjenaPoteza> ();
			ArrayList<Polje> moznePoteze = moznePoteze();
			for (Polje p: moznePoteze) {
				Igra tempIgra = new Igra(igra);
				tempIgra.narediPotezo (p);
				int ocena = minimaxPozicijo (tempIgra, globina-1, jaz);
				ocenjenePoteze.add(new OcenjenaPoteza(p, ocena));			
			}
			System.out.println(ocenjenePoteze);
			return ocenjenePoteze;
		}
		
		public static int minimaxPozicijo(Igra igra, int globina, Igralec jaz) {
			// Nekdo je na potezi
			if (globina == 0) {return oceniPozicijo(igra, jaz);}
			// globina > 0
		    List<OcenjenaPoteza> ocenjenePoteze = oceniPoteze(igra, globina, jaz);
			if (igra.naPotezi == jaz) {return maxOcena(ocenjenePoteze);}
			else {return minOcena(ocenjenePoteze);}		
			}
		
		public static int maxOcena(List<OcenjenaPoteza> ocenjenePoteze) {
			int max = ZGUBA;
			for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
				if (ocenjenaPoteza.vrednost > max) {max = ocenjenaPoteza.vrednost;}
			}
			return max;
		}
		
		public static Polje maxPoteza(List<OcenjenaPoteza> ocenjenePoteze) {
			int max = ZGUBA;
			Polje poteza = null;
			for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
				if (ocenjenaPoteza.vrednost >= max) {
					max = ocenjenaPoteza.vrednost;
					poteza = ocenjenaPoteza.poteza;			
				}
			}
			return poteza;
		}
		
		public static int minOcena(List<OcenjenaPoteza> ocenjenePoteze) {
			int min = ZMAGA;
			for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
				if (ocenjenaPoteza.vrednost < min) {min = ocenjenaPoteza.vrednost;}
			}
			return min;
		}
		
		// Metoda oceniPozicijo je odvisna od igre
		
		public static int oceniPozicijo(Igra igra, Igralec jaz) {
			int ocena = 0;
			for (Polje p : IgralnaPloscaInfo.tabela) {
				ocena = ocena + oceniPolja(p, igra, jaz);
			}
			return ocena;	
		}
		
		public static void racunalnikovaPoteza() {
			List<OcenjenaPoteza> ocenjenePoteze = oceniPoteze (igra, 2, Igra.naPotezi);
			Polje poteza = maxPoteza(ocenjenePoteze);
			igra.narediPotezo(poteza);
		}
		
		public static int oceniPolja (Polje p, Igra igra, Igralec jaz) {
			Polje[] tabela = IgralnaPloscaInfo.getTabela();
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
