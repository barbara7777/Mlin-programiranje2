
public class Pravila {

	// veljavnost potez
	public static boolean jeVeljavna(Igralec player, Poteza poteza, Igra g) { 		// preveriše, èe se res premakne
		if (player.faza == 1 || player.faza == 3) {
			if  (poteza.koncno.zasedenost != Zasedeno.PRAZNO) return false;			
		}
		else if (player.faza == 2) {
			// èe konèno ni prazno ali nista povezana, return false
			if (poteza.koncno.zasedenost != Zasedeno.PRAZNO || 
				   !IgralnaPloscaInfo.staPovezana(poteza.zacetno, poteza.koncno)) return false;			
		}
		// èe imamo za jemati
		if (poteza.vzemi != null) {
			if (!g.jeNasprotnikovo(player, poteza.vzemi)) {
				System.out.println("Tega plošèka ne moreš vzeti!");
				return false;
			}; 
		}
		return true; // èe je vse v redu, imamo veljavno potezo
	}
}
