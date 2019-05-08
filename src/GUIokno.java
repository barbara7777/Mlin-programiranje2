import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GUIokno extends JFrame implements ActionListener {
	
	private GUIigralnaPlosca plosca;
	
	public GUIokno() {
		super();
		
		this.setTitle("Igra mlin");
		plosca = new GUIigralnaPlosca(600, 600);
		add(plosca);
		this.add(plosca);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		System.out.println("Nekaj");
		
	}
}
