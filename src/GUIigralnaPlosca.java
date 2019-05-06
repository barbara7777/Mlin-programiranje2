import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUIigralnaPlosca extends JPanel implements MouseListener, MouseMotionListener {
	
	int sirina, visina;
	static Igra igra;
	Polje izbranoPolje;
	
	Set<Polje> ploscki1; // mnozica polj v igri za prvega igralca
	Set<Polje> ploscki2;
	
	Color barvaPlosckov1;
	Color barvaPlosckov2;
	Color barvaCrt;
	Color barvaOzadja;
	Color barvaIzbranegaPolja;
	
	int polmer;
	float debelinaPovezave; 
	float debelinaRoba;
	private int klikX, klikY;
	private int premikX, premikY;
	
	
	public GUIigralnaPlosca(int sirina, int visina) {
		super();
		this.sirina = sirina;
		this.visina = visina;
		igra = new Igra();
		
		izbranoPolje = null;
		
		barvaPlosckov1 = Color.YELLOW;
		barvaPlosckov2 = Color.PINK;
		barvaCrt = Color.DARK_GRAY;
		barvaOzadja = Color.WHITE;
		barvaIzbranegaPolja = Color.GREEN;
		
		polmer = 10;
		debelinaPovezave = 3; 
		debelinaRoba = 1;
		
		// dodati moramo še metode, ki sledijo miški in tipkovnici; to platno je tisto, ki presreže te metode
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
		super.paintComponent(g); // super klièe objekt iz nadrazreda
		setBackground(barvaOzadja);
		
		// kaj narišemo
		
		g2.setStroke(new BasicStroke(debelinaPovezave));
		g.setColor(barvaCrt);
		
		for (Polje polje : IgralnaPloscaInfo.tabela) {
			//for (Tocka sosednja : tocka.sosedi) {
				//if (tocka.ime.compareTo(sosednja.ime) > 0) { //rezultat primerjanja je stevilo
					//g.drawLine(round(tocka.x), round(tocka.y), round(sosednja.x), round(sosednja.y));
				//}
			}
		
		// risem polja
		for (Polje polje : igra.plosca.tabela) {
			g.fillOval(pretvori(polje.vrstica) - polmer, pretvori(polje.stolpec) - polmer,  2 * polmer, 2 * polmer);
			
			for (Polje sosed : igra.plosca.tabela) {
				if (igra.plosca.staPovezana(polje, sosed))
			//for (Polje sosed : polje.povezave) {
				g.drawLine(pretvori(polje.vrstica), pretvori(polje.stolpec), pretvori(sosed.vrstica), pretvori(sosed.stolpec));
			}
		}
		
	}
	private static void nastaviIgro (Igra game) {
		igra = game;
	}
	
	private int pretvori(int x) {
		return 25 + x*100;
	}
	
	private void narisiPolje() {
		
		}
		
	 /// test

	  public static void main(String[] args) {
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    JFrame frame = new JFrame("Draw Oval and Circle");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setBackground(Color.white);
	    frame.setSize(700, 700);
	 
	    GUIigralnaPlosca panel = new GUIigralnaPlosca(650, 650);
	 
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
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
