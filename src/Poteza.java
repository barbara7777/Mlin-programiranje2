
public class Poteza {
	Polje zacetno = null;
	Polje koncno = null;
	Polje vzemi = null;
	
	
	public Poteza(Polje zacetno, Polje koncno, Polje vzemi) {
		// Bi bilo lažje z Poteza(int zacetno, int koncno, int vzemi) in
		// zacetno = IgralnaPloscaInfo.tabela[zacetno]  ???
		this.zacetno = zacetno;
		this.koncno = koncno;
		this.vzemi = vzemi;
	}	
	
	public static void dodajPloscek(Igralec player, Polje polje) {
		polje.zasedenost = player.ime;
	}
	
	public static void vzemiPloscek(Polje polje) {
		polje.zasedenost = Polje.prazno;
		
		
	}
}
