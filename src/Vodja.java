
public class Vodja {
	
	public Igra igra;
	
	private Igralec clovek;
	private Igralec racunalnik;
	public boolean clovekNaVrsti;
		
	public Vodja() {
		clovekNaVrsti = false;
	}
	
	public void novaIgra(Igralec clovek) {
		// Ustvarimo novo igro
		this.igra = new Igra();
		this.clovek = clovek;
		igramo();
	}
	
	public void igramo () {
		
	}
	
	public void racunalnikovaPoteza() {

	}
	
	public void clovekovaPoteza(Poteza poteza) {
			igramo();
		}
		
	
	

}
