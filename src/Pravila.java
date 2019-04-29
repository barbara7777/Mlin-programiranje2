
public class Pravila {

	// veljavnost potez
	public static boolean jeVeljavna(Igralec player, Poteza poteza, Igra g) { 		// preveri�e, �e se res premakne
		if (player.faza == 1 || player.faza == 3) {
			if  (poteza.koncno.zasedenost != Zasedeno.PRAZNO) return false;			
		}
		else if (player.faza == 2) {
			// �e kon�no ni prazno ali nista povezana, return false
			if (poteza.koncno.zasedenost != Zasedeno.PRAZNO || 
				   !IgralnaPloscaInfo.staPovezana(poteza.zacetno, poteza.koncno)) return false;			
		}
		// �e imamo za jemati
		if (poteza.vzemi != null) {
			if (!g.jeNasprotnikovo(player, poteza.vzemi)) {
				System.out.println("Tega plo��ka ne more� vzeti!");
				return false;
			}; 
		}
		return true; // �e je vse v redu, imamo veljavno potezo
	}
}
