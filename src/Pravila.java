
public class Pravila {

	
	public static boolean lahkoVzamem (Igralec player, Polje polje, Igra g) {
		if (!g.jeNasprotnikovo(polje))
			return false;
		else 
			return true;	
	}
	
	public static boolean jePraznoPolje (Igralec player, Polje polje, Igra g) {
		if (player.faza != 2) {
			if (polje.zasedenost != Polje.prazno)
				return false;
		}
		else {
			if (polje.zasedenost != Polje.prazno) {
				System.out.println("Ni prazno");
				return false;			
			}
		}
		return true;
	}
	
	public static boolean lahkoPremaknem (Polje zacetno, Polje koncno, Igra g) {
		Igralec player = g.naPotezi;
		if (!jePraznoPolje(player, koncno, g)) 
			return false;
		if (player.faza == 2) {
			if (!IgralnaPloscaInfo.staPovezana(zacetno, koncno)) {
				System.out.println("Nista povezana");
				return false;
			}
		}
		return true;			
	}
}
