
public class Pravila {

	// veljavnost potez
	public static boolean jeVeljavna(Igralec player, Poteza poteza, Igra g) {
		if(poteza.zacetno == poteza.koncno) {
			System.out.println("Mora� se premakniti.");
			return false;
		}
		if (player.faza == 1 || player.faza == 3) {
			if  (poteza.koncno.zasedenost != Polje.prazno) return false;			
		}
		else if (player.faza == 2) {
			// �e kon�no ni prazno ali nista povezana, return false
			if (poteza.koncno.zasedenost != Polje.prazno || 
				   !IgralnaPloscaInfo.staPovezana(poteza.zacetno, poteza.koncno)) return false;			
		}
		// �e imamo za jemati
		if (poteza.vzemi != null) {
			if (!g.jeNasprotnikovo(poteza.vzemi)) {
				System.out.println("Tega plo��ka ne more� vzeti!");
				return false;
			};
			if (poteza.vzemi.zasedenost == Polje.prazno) {
				System.out.println("Na tem polju ni plo��ka.");				
			}
		}
		return true; // �e je vse v redu, imamo veljavno potezo
	}
}
/* zadnji del bi lahko na kraj�e napisali:
 *  if (poteza.vzemi != null)
 *  	return (poteza.vzemi.zasedenost == g.nasprotnik.ime);
 *  */

