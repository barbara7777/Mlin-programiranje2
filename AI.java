import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class AI {
	
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
