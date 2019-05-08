import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUIigralnaPlosca extends JPanel implements MouseListener, MouseMotionListener {
	
	int sirina, visina;
	static Igra igra;
	Polje izbranoPolje;
	Poteza aktivnaPoteza;
	Polje zacetno;
	Polje koncno;
	//int delPoteze;
	
	Set<Polje> ploscki1; // mnozica polj v igri za prvega igralca
	Set<Polje> ploscki2;
	
	Color barvaPlosckov1;
	Color barvaPlosckov2;
	Color barvaCrt;
	Color barvaOzadja;
	Color barvaIzbranegaPolja;
	Color barvaMlina;
	
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
		aktivnaPoteza = new Poteza(null, null, null);

		
		barvaPlosckov1 = new Color(110, 165, 77);
		barvaPlosckov2 = new Color(168, 79, 120);
		barvaCrt = Color.DARK_GRAY;
		barvaOzadja = Color.WHITE;
		barvaIzbranegaPolja = Color.GREEN;
		barvaMlina = Color.YELLOW;	
		
		
		polmer = 10;
		polmerPloscka = 20;
		debelinaPovezave = 3; 
		debelinaRoba = 1;
		
		
		// dodati moramo �e metode, ki sledijo mi�ki in tipkovnici; to platno je tisto, ki presre�e te metode
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
	}
	

	
	
	public void narisi() {
		repaint(); // poklice paintComponent
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
		
		// kaj nari�emo
		g2.setStroke(new BasicStroke(debelinaPovezave));
		g.setColor(barvaCrt);
		
		// risem igralno plo��o
		for (Polje polje : igra.plosca.tabela) {
			g.fillOval(pretvori(polje.vrstica) - polmer, pretvori(polje.stolpec) - polmer,  2 * polmer, 2 * polmer);
			for (Polje sosed : polje.povezave) {
				g.drawLine(pretvori(polje.vrstica), pretvori(polje.stolpec), pretvori(sosed.vrstica), pretvori(sosed.stolpec));
			}
		}
		
		// ri�em plo��ke
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
		// ozna�i izbrano polje
		if (izbranoPolje != null) {
			g.setColor(barvaIzbranegaPolja);
			g.drawOval(pretvori(izbranoPolje.stolpec) - polmerPloscka, pretvori(izbranoPolje.vrstica) - polmerPloscka,
					2 * polmerPloscka, 2 * polmerPloscka);
		}
		// ozna�i mlin
		
		/*
		Vector<Integer> mlin = igra.jeMlin(izbranoPolje.indeks);
		if (mlin.size() != 0) {
			for (int i : mlin) {
				Polje p = igra.plosca.tabela[i];
				g.setColor(barvaMlina);
				g.drawOval(pretvori(p.stolpec) - polmerPloscka, pretvori(p.vrstica) - polmerPloscka,
						2 * polmerPloscka, 2 * polmerPloscka);
					
			}
		}
	*/
	}

	
	private void narisiPolje() {
		// ???
	}
	
	private static void nastaviIgro (Igra game) {
		igra = game;
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

	@Override
	public void mousePressed(MouseEvent e) {
		klikX = e.getX();
		klikY = e.getY();
		
		for (Polje polje : igra.plosca.tabela) {
			if ((Math.abs(pretvori(polje.vrstica) - klikY) < 30) && Math.abs(pretvori(polje.stolpec) - klikX) < 30) {
				izbranoPolje = polje;
				//System.out.println("Kliknila si  " + polje.indeks);		
			}
		}
		if (izbranoPolje == null) { // kliknila si mimo vsakega polja. izmislim si polje, da se izognem erroru
			izbranoPolje = new Polje(-1);
			izbranoPolje.zasedenost = "neveljavno polje";
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
			igra.premikam = !igra.premikam; // ko prvi� kliknem, nastavim premikam na true
			if (igra.premikam) { //ko prvi� kliknem
				zacetno = izbranoPolje;
				if(!igra.jeIgralcevo(izbranoPolje)) {
					System.out.println("To ni tvoja plo��ica!");
					igra.premikam = !igra.premikam;
					break;
				}
			}
			else { // ob drugem kliku
				if (zacetno == izbranoPolje) {
					izbranoPolje = null;
					break;
				}
				koncno = izbranoPolje;
				igra.premakni(igra.naPotezi, zacetno, koncno);
			}
			repaint();
			break;
			// manjka prepoznavanje mlinv ....
			
		case 3:
			// case 3 �e ni narejen
			System.out.println("faza igralca je 3.");
			if (igra.naPotezi.delPoteze == 1) {
				aktivnaPoteza.zacetno = izbranoPolje;
				igra.naPotezi.delPoteze++;			
			}
			else if(igra.naPotezi.delPoteze  == 2) {
				aktivnaPoteza.koncno = izbranoPolje;
				igra.naPotezi.delPoteze++;
			}
			else if (igra.naPotezi.delPoteze == 3) {
				if (igra.obstajaMlin())
					aktivnaPoteza.vzemi = izbranoPolje;
				
				repaint();
				novaPoteza();
			}
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

	private void novaPoteza () {
		aktivnaPoteza.zacetno = null;
		aktivnaPoteza.koncno = null;
		aktivnaPoteza.vzemi = null;
		igra.naPotezi.delPoteze = 1;
		
	}
}
