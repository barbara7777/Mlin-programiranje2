
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
		
	
	
	
	
	
	// veljavnost potez
	public static boolean jeVeljavna(Igralec player, Poteza poteza, Igra g) {
		if(poteza.zacetno == poteza.koncno) {
			System.out.println("Moraš se premakniti.");
			return false;
		}
		if (player.faza == 1 || player.faza == 3) {
			if  (poteza.koncno.zasedenost != Polje.prazno) return false;			
		}
		else if (player.faza == 2) {
			// èe konèno ni prazno ali nista povezana, return false
			if (poteza.koncno.zasedenost != Polje.prazno || 
				   !IgralnaPloscaInfo.staPovezana(poteza.zacetno, poteza.koncno)) {
				System.out.println("Ni prazno ali pa je predaleè.");
				return false;			
			}
		}
		// èe imamo za jemati
		if (poteza.vzemi != null) {
			if (poteza.vzemi.zasedenost == Polje.prazno) 
				System.out.println("Na tem polju ni plošèka.");
			
			if (!g.jeNasprotnikovo(poteza.vzemi)) {
				System.out.println("Tega plošèka ne moreš vzeti!");
				return false;
			}
		}
		return true; // èe je vse v redu, imamo veljavno potezo
	}
}
/* zadnji del bi lahko na krajše napisali:
 *  if (poteza.vzemi != null)
 *  	return (poteza.vzemi.zasedenost == g.nasprotnik.ime);
 *  */

