
public class Pravila {

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
				   !IgralnaPloscaInfo.staPovezana(poteza.zacetno, poteza.koncno)) return false;			
		}
		// èe imamo za jemati
		if (poteza.vzemi != null) {
			if (!g.jeNasprotnikovo(poteza.vzemi)) {
				System.out.println("Tega plošèka ne moreš vzeti!");
				return false;
			};
			if (poteza.vzemi.zasedenost == Polje.prazno) {
				System.out.println("Na tem polju ni plošèka.");				
			}
		}
		return true; // èe je vse v redu, imamo veljavno potezo
	}
}
/* zadnji del bi lahko na krajše napisali:
 *  if (poteza.vzemi != null)
 *  	return (poteza.vzemi.zasedenost == g.nasprotnik.ime);
 *  */

