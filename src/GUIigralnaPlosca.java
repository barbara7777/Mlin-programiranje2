import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Set;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUIigralnaPlosca extends JPanel implements MouseListener, MouseMotionListener {
	
	int sirina, visina;
	Igra igra;
	Polje izbranoPolje;
	Polje zacetno = null;
	Polje koncno = null;
	Polje vzemi = null;
	
	Set<Polje> ploscki1; // mnozica polj v igri za prvega igralca
	Set<Polje> ploscki2;
	
	Color barvaPlosckov1;
	Color barvaPlosckov2;
	Color barvaCrt;
	Color barvaOzadja;
	Color barvaIzbranegaPolja;
	
	int polmer;
	int polmerPloscka;
	float debelinaPovezave; 
	float debelinaRoba;
	private int klikX, klikY;
	
	
	public GUIigralnaPlosca(int sirina, int visina) {
		super();
		this.sirina = sirina;
		this.visina = visina;
		igra = new Igra();
		izbranoPolje = null;
		
		barvaPlosckov1 = new Color(110, 165, 77);
		barvaPlosckov2 = new Color(195, 114, 249);
		barvaCrt = Color.DARK_GRAY;
		barvaOzadja = Color.WHITE;
		barvaIzbranegaPolja = Color.GREEN;
		
		polmer = 10;
		polmerPloscka = 20;
		debelinaPovezave = 3; 
		debelinaRoba = 1;
		
		// dodati moramo še metode, ki sledijo miški in tipkovnici; to platno je tisto, ki presreže te metode
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
	}

	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(sirina, visina);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g); 
		setBackground(barvaOzadja);
		
		// kaj narišemo
		g2.setStroke(new BasicStroke(debelinaPovezave));
		g.setColor(barvaCrt);
		
		// risem igralno plošèo
		for (Polje polje : igra.plosca.tabela) {
			g.fillOval(pretvori(polje.vrstica) - polmer, pretvori(polje.stolpec) - polmer,  2 * polmer, 2 * polmer);
			for (Polje sosed : polje.povezave) {
				g.drawLine(pretvori(polje.vrstica), pretvori(polje.stolpec), pretvori(sosed.vrstica), pretvori(sosed.stolpec));
			}
		}

		// rišem plošèke
		for (Polje polje : igra.plosca.tabela) {
			if (polje.zasedenost.equals("racunalnik")) {
				g.setColor(barvaPlosckov1);
				g.fillOval(pretvori(polje.stolpec) - polmerPloscka, pretvori(polje.vrstica) - polmerPloscka,
						2 * polmerPloscka, 2 * polmerPloscka);
			}
			else if (polje.zasedenost.equals("igralec")) {
				g.setColor(barvaPlosckov2);
				g.fillOval(pretvori(polje.stolpec) - polmerPloscka, pretvori(polje.vrstica) - polmerPloscka,
				2 * polmerPloscka, 2 * polmerPloscka);
			}
		}	
		// oznaèi izbrano polje
		if (izbranoPolje != null) {
			g.setColor(barvaIzbranegaPolja);
			g.drawOval(pretvori(izbranoPolje.stolpec) - polmerPloscka, pretvori(izbranoPolje.vrstica) - polmerPloscka,
					2 * polmerPloscka, 2 * polmerPloscka);
		}
	}

	
	private int pretvori(int x) {
		return 60 + x*90;
	}
	
	 /// test
	  public static void main(String[] args) {
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    JFrame frame = new JFrame("Igra mlin");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setBackground(Color.WHITE);
	    frame.setSize(700, 700);
	 
	    GUIigralnaPlosca panel = new GUIigralnaPlosca(600, 600);
	 
	    frame.add(panel);
	    frame.setVisible(true);
	  }
	
	// Spodaj so funkcije ob klikih, ....
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	static void prt (String s) 
	{	
		System.out.println(s);
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (igra.zamrzni) return;
		klikX = e.getX();
		klikY = e.getY();
		
		for (Polje polje : igra.plosca.tabela) {
			if ((Math.abs(pretvori(polje.vrstica) - klikY) < 30) && Math.abs(pretvori(polje.stolpec) - klikX) < 30) {
				izbranoPolje = polje;		
			}
		}
		if (izbranoPolje == null) { // klik mimo vsakega polja. izmislim si polje, da se izognem erroru
			return;
			//izbranoPolje = new Polje(-1);
			//izbranoPolje.zasedenost = "neveljavno polje";
			}
		
		
		switch (igra.naPotezi.faza) { 
		case 1:
			if (igra.imamMlin) {
				igra.vzemiPloscek(izbranoPolje);
			}
			else{
				igra.postaviPloscek(izbranoPolje);
			}
			repaint();
			break;
			
		case 2:
		case 3:
			//na zacetku so vsi null, to so: zacetno, koncno,vzemi
			//Ce je zacetno null, ga nastavim. Tu mlina ne more biti
			//Ce zacetno ni null, koncno pa je, potem moram nastaviti konco. Tudi tu mlina ni
			//Ce zacetno ni null in koncno ni null, potem ga je treba prestavit (samo ce mlina ni 
			//ker ce mlin je, potem morem enega vzeti). Ce ni mlina, lahko vse resitiram
			//Ce pa je mlin, moram enega vzeti
			
			if (zacetno == null) { // Zacetno je treba nastaviti
				if (igra.jeIgralcevo(izbranoPolje)) {
					prt("Izbral si zacetno polje premika");
					zacetno = izbranoPolje;
				}
				else
					prt("Za zacetno polje si izbral neveljavno. Ponovi.");
				break;
			}
			if (koncno == null) { // Koncno polje je treba nastaviti
				//Koncno polje je lahko samo prazno polje
				if (!Pravila.jePraznoPolje(igra.naPotezi, izbranoPolje, igra)) {
					prt("Za koncno polje nisi izbral praznega polja. Ponovi izbiro koncega polja");
					break;
				}
				koncno = izbranoPolje;
				prt("Izbral si koncno polje");
			}
			//Tukaj je treba torej ploscico prestaviti, ce mlina ni (pri prestavitvi ga ne sme se biti)
			if (!igra.imamMlin) {  //Prestavimo ploscico
				if (!igra.premakni(igra.naPotezi,  zacetno, koncno)) {
					prt("Poteza ni mozna, ker polja nista povezana. Ponovi potezo");
					koncno = null;
					zacetno = null;
					break;
				}
				//Ce zdaj ni mlina na novo, potem
				//lahko resitiram vse. Ce je, potrebujem nov klik
				if (igra.imamMlin) { //Ker je mlin, potrebujem klik
					prt("Naredil si mlin. Vzemi en ploscek");
					break;
				}
				zacetno = null;
				koncno = null;
				igra.zamenjajIgralca();
			}
			else {
				//Poberemo ploscico, ce je poteza veljavna
				if (!igra.jeNasprotnikovo(izbranoPolje)) {
					prt("Poskusal si vzeti ploscek ki ni nasprotikov. Ponovi.");
					break;
				}
				igra.vzemiPloscek(izbranoPolje);
				zacetno = null;
				koncno = null;
				break;
			}
			repaint();
			break;
		}
		repaint(); 
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
