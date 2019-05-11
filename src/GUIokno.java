import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GUIokno extends JFrame implements ActionListener {
	
	
	int sirina = 70;
	int visina = 70;
	
	
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
	private JPanel orodjarna1;
	private JPanel orodjarna2;
	private JPanel orodjarna3;
	
	private GUIigralnaPlosca plosca;
	
	public GUIokno(int sirina, int visina) {
		super();
		this.setTitle("Igra mlin");
		setSize(sirina, visina);
		
		plosca = new GUIigralnaPlosca(sirina - 160, visina);
		//this.add(this.plosca);
		this.getContentPane().add(plosca, "West");
		
		barva1 = plosca.barvaPlosckov1;
		System.out.println("barva prvega je " + barva1);
		barva2 = plosca.barvaPlosckov2;
		
		// razdelek za orodjarno
		orodjarna = new JPanel();
		orodjarna.setSize(100, 700);
		//orodjarna.setBackground(new Color(204, 204, 255));
		orodjarna.setBorder(new LineBorder(Color.white, 3));
		orodjarna.setLayout(new GridLayout(10, 1, 10, 10));
		this.add(this.orodjarna);
		//this.getContentPane().add(orodjarna, "East");
		
		
		//(new GridLayout(2,2,20,10)); // height, width , hgap, vgap
		
		// deli orodjarne 
		orodjarna1 = new JPanel();
		//orodjarna1.setSize(100, 500);
		//orodjarna1.setBackground(Color.red);
		//orodjarna1.setBorder(new LineBorder(Color.CYAN, 3));
		orodjarna1.setLayout(new GridLayout(4, 1, 10, 10));
		//orodjarna.add(orodjarna1);
		//
		orodjarna2 = new JPanel();
		//orodjarna1.setSize(100, 700);
		//orodjarna2.setBackground(Color.ORANGE);
		//orodjarna2.setBorder(new LineBorder(barva1, 3));
		orodjarna2.setLayout(new GridLayout(2, 1, 10, 10));
		//orodjarna.add(orodjarna2);
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
		
		
		naPotezi = new JLabel("Igra: \n" + plosca.igra.naPotezi.ime);
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
		
		//orodjarna1.add(new JLabel(""));
		//orodjarna1.add(new JLabel(""));

		orodjarna.add(labelIgralec1);
		orodjarna.add(izberiBarvo1);
		
		orodjarna.add(labelIgralec2);
		orodjarna.add(izberiBarvo2);
		
		
		
		
		//getContentPane().add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, plosca, orodjarna));
		
		novaIgra.addActionListener(this);
		pomoc.addActionListener(this);
		izberiBarvo1.addActionListener(this);
		izberiBarvo2.addActionListener(this);
		
		// Izbor barv 
		
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
			System.out.println("Vem, da zmoreš!");
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
			plosca.igra = new Igra();
			repaint();
		}
		
		
		
		
		
	}
	
	
	public static void main(String[] args) {
	    GUIokno o = new GUIokno(840, 700);
	    
		//o.setSize(700, 700);
	}
}