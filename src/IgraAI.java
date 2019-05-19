import java.util.concurrent.ThreadLocalRandom;

import javax.swing.SwingWorker;

public class IgraAI {
	
	Igra igra;
	IgralnaPloscaInfo plosca;
	SwingWorker<Polje, Void> worker;
	
	public IgraAI(Igra igra) {
		this.igra = igra;		
	
	}
	
	public void naredi() {
		if (igra.naPotezi.ime.equals("racunalnik")) {
			int i = ThreadLocalRandom.current().nextInt(0, 24);
			// èe si na vrsti, naredi nekaj
			//for (Polje polje : igra.plosca.tabela) {
				//if (polje.zasedenost.equals(Polje.prazno)) {
					//igra.narediPotezo(polje);
				//}
			
			//}
			igra.narediPotezo(igra.plosca.tabela[i]);
			igra.naPotezi = igra.igralec;
			//igra.zamenjajIgralca();
		}
	}
		
	public void racunalnikovaPoteza() {
		SwingWorker<Polje, Void> worker = new SwingWorker<Polje, Void> () {
			private Igra zacetnaIgra = igra;
			@Override
			protected Polje doInBackground() {
				//return AlphaBeta.alphabetaVrzi (igra, clovek.nasprotnik());
				return igra.plosca.tabela[1]; //returnal bi naj neko optimalneo polje
			}
			
			@Override
			protected void done () {
				Polje polje;
				try {
					polje = get();
					if (polje != null && zacetnaIgra == igra) {
						igra.narediPotezo(polje);
					}
				} catch (Exception e) {};		
			}		
		};
		worker.execute();
	}
	
			
}
