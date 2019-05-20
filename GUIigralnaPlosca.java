import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUIigralnaPlosca extends JPanel implements MouseListener, MouseMotionListener {
	
	int sirina, visina;
	
	Polje izbranoPolje;
	
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
		
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		
		repaint();
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
		for (Polje polje : IgralnaPloscaInfo.tabela) {
			g.fillOval(pretvori(polje.vrstica) - polmer, pretvori(polje.stolpec) - polmer,  2 * polmer, 2 * polmer);
			for (Polje sosed : polje.povezave) {
				g.drawLine(pretvori(polje.vrstica), pretvori(polje.stolpec), pretvori(sosed.vrstica), pretvori(sosed.stolpec));
			}
		}

		// rišem plošèke
		for (Polje polje : IgralnaPloscaInfo.tabela) {
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
		
		// oznaèi možne poteze
		if (Igra.oznaciMoznePoteze && izbranoPolje != null) {
			if (Igra.naPotezi == Igra.igralec2) {
				g.setColor(Color.LIGHT_GRAY);
			}
			else
				g.setColor(Color.DARK_GRAY);
			for (Polje polje : AI.moznePoteze()){
				g.drawOval(pretvori(polje.stolpec) - polmerPloscka,  pretvori(polje.vrstica) - polmerPloscka,
						2 * polmerPloscka, 2 * polmerPloscka);					
			}
		}
	}

	private int pretvori(int x) {
		return 60 + x*90;
	}
	
	
	// Spodaj so funkcije ob klikih, ....
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (Igra.zamrzni) {
			repaint();
			return;
		}
		klikX = e.getX();
		klikY = e.getY();
		
		for (Polje polje : IgralnaPloscaInfo.tabela) {
			if ((Math.abs(pretvori(polje.vrstica) - klikY) < 30) && Math.abs(pretvori(polje.stolpec) - klikX) < 30) {
				izbranoPolje = polje;		
			}
		}
		if (izbranoPolje == null) { 
			return;
			}
		Igra.klikNaPolje(izbranoPolje);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
}
