import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GUIokno extends JFrame implements ActionListener, MouseListener {
	
	int sirina = 70;
	int visina = 70;
	
	String naPoteziOsnovni;
	
	private JButton novaIgra;
	private JButton pomoc;
	private JLabel labelPomoc;
	private JLabel naPotezi;
	
	private JButton izberiBarvo1;
	public Color barva1;
	private JLabel labelIgralec1;
	
	private JButton izberiBarvo2;
	public Color barva2;
	private JLabel labelIgralec2;

	private JPanel orodjarna;
	private JPanel orodjarna3;
	
	private GUIigralnaPlosca plosca;
	
	
	
	public GUIokno(int sirina, int visina) {
		super();
		this.setTitle("Igra mlin");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(sirina, visina);
		naPoteziOsnovni = "<html>Zacne<br>igralec 1</html>";
		//naPoteziOsnovni = "<html>Zacne \n\r<br> </html> igralec 1 abrakadabra cirule carule";
		
		plosca = new GUIigralnaPlosca(sirina - 160, visina);
		this.getContentPane().add(plosca, "West");
		
		barva1 = plosca.barvaPlosckov1;
		barva2 = plosca.barvaPlosckov2;
		
		// razdelek za orodjarno
		orodjarna = new JPanel();
		orodjarna.setSize(100, 700);
		orodjarna.setBorder(new LineBorder(Color.white, 3));
		orodjarna.setLayout(new GridLayout(10, 1, 10, 10));
		this.add(this.orodjarna);
		
		//
		orodjarna3 = new JPanel();
		//orodjarna3.setBackground(Color.GRAY);
		//orodjarna3.setBorder(new LineBorder(Color.GRAY, 3));
		orodjarna3.setLayout(new GridLayout(2, 1, 10, 10));
		orodjarna.add(orodjarna3);
		//
		
		novaIgra = new JButton("Nova igra");
		pomoc = new JButton("Pomoè");
		izberiBarvo1 = new JButton("Izberi barvo 1");
		izberiBarvo2 = new JButton("Izberi barvo 2");
		
		
		naPotezi = new JLabel(naPoteziOsnovni);
		naPotezi.setBackground(Color.WHITE);
		naPotezi.setOpaque(true);
		naPotezi.setHorizontalAlignment(JLabel.CENTER);
		
		labelPomoc = new JLabel("");
		labelPomoc.setHorizontalAlignment(JLabel.CENTER);
		
		labelIgralec1 = new JLabel("Igralec 1");
		labelIgralec1.setHorizontalAlignment(JLabel.CENTER);
		labelIgralec1.setOpaque(true);
		labelIgralec1.setBackground(barva2);
		
		labelIgralec2 = new JLabel("Igralec 2");
		labelIgralec2.setHorizontalAlignment(JLabel.CENTER);
		labelIgralec2.setOpaque(true);
		labelIgralec2.setBackground(barva1);

		orodjarna.add(novaIgra);
		orodjarna.add(naPotezi);
		orodjarna.add(pomoc); 
		orodjarna.add(labelPomoc);
		
		orodjarna.add(labelIgralec1);
		orodjarna.add(izberiBarvo1);
		
		orodjarna.add(labelIgralec2);
		orodjarna.add(izberiBarvo2);
		
		novaIgra.addActionListener(this);
		pomoc.addActionListener(this);
		izberiBarvo1.addActionListener(this);
		izberiBarvo2.addActionListener(this);
		
		plosca.addMouseListener(this);
		setVisible(true);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(sirina, visina);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == pomoc) {
			String pomoc = Pomoc.pomagaj();
			labelPomoc.setText(pomoc);
		}
		
		else if (source == izberiBarvo1) {
			String naslov = "Izberite barvo za 1. igralca";
				Color novaBarva = JColorChooser.showDialog(this, naslov, barva1);
				if (novaBarva != null) {
					barva1 = novaBarva;
					labelIgralec1.setBackground(barva1);
					plosca.barvaPlosckov2 = barva1;
					repaint();
				}
			}
		else if (source == izberiBarvo2) {
			String naslov = "Izberite barvo za 2. igralca";
			Color novaBarva = JColorChooser.showDialog(this, naslov, barva2);
			if (novaBarva != null) {
				barva2 = novaBarva;
				labelIgralec2.setBackground(barva2);
				plosca.barvaPlosckov1 = barva2;
				repaint();
			}
		}
		
		else if (source == novaIgra) {
			labelPomoc.setText("");
			naPotezi.setText(naPoteziOsnovni);
			
			plosca.igra = new Igra();
			repaint();
		}
	
	}
	
	public static void main(String[] args) {
	    new GUIokno(840, 700);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String ime = (plosca.igra.naPotezi.ime.equals("racunalnik")) ? "igralec 2": "igralec 1";
		String text = "<html>Na potezi je<br>"+ime+"</html>";
		naPotezi.setText(text);
		if(plosca.igra.konecIgre()) {
			String zmagal = (plosca.igra.naPotezi.ime.equals("racunalnik")) ? "igralec 1": "igralec 2";
			naPotezi.setText("<html>Zmagal je<br>"+zmagal+"</html>");
		}
	}
	
	// spodnjih funkcij ne potrebujem
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}