import java.util.*;

public class IgralnaPlosca {
	public Polje[] tabela;
	
	
	public boolean povezavaMedPolji (Polje prvo, Polje drugo) {
		return prvo.povezave.contains(drugo);
	}
	
	/* USTVARI IGRALNO PLOSCO
	 *  O ---- O ---- O
	 *  |	   | 	  |
	 *  | O----O---O  |
	 *  | |  O-O-O |  |
	 *  O-O--O   O-O--O
	 *  | |  O-O-O |  |
	 *  | O----O---O  |
	 *  |	   |	  |
	 *  O ---- O ---- O
	 * 
	 *  * 
 * 0 --------- 1 --------- 2
 * |           |           |
 * |   3 ----- 4 ----- 5   |
 * |   |       |       |   |
 * |   |   6 - 7 - 8   |   |
 * |   |   |       |   |   |
 * 9 - 10- 11      12- 13- 14
 * |   |   |       |   |   |
 * |   |   15- 16- 17  |   |
 * |   |       |       |   |
 * |   18----- 19----- 20  |
 * |           |           |
 * 21--------- 22--------- 23
	 */
	
	public IgralnaPlosca(){
		tabela = new Polje[24]; // sem shranim dejanska polja iz 7 x 7 matrike
		for (int i = 0; i < 24; i++) {
			int vrstica = i / 3 - i / 12;
			int stolpec;
			if (vrstica == 3) stolpec = i % 3 + 4 * (i / 12);			
			else stolpec = 3 + (i % 3 - 1) * Math.abs(vrstica - 3); 
			
			//
			//Polje novo = new Polje(vrstica, stolpec);
			//tabela[i] = novo;
			//System.out.println(novo.x + " " + novo.y);
			//

		}
		
		dodajPovezavo(0, 1);
		dodajPovezavo(0, 9);
		dodajPovezavo(1, 2);
		dodajPovezavo(1, 4);
		dodajPovezavo(2, 14);
		dodajPovezavo(3, 4);
		dodajPovezavo(4, 5);
		dodajPovezavo(4, 7);
		dodajPovezavo(5, 13);
		dodajPovezavo(6, 7);
		dodajPovezavo(6, 11);
		dodajPovezavo(7, 8);
		dodajPovezavo(8, 12);
		dodajPovezavo(9, 10);
		dodajPovezavo(9, 21);
		dodajPovezavo(10, 11);
		dodajPovezavo(10, 18);
		dodajPovezavo(11, 15);
		dodajPovezavo(12, 13);
		dodajPovezavo(12, 17);
		dodajPovezavo(13, 14);
		dodajPovezavo(13, 20);
		dodajPovezavo(14, 23);
		dodajPovezavo(15, 16);
		dodajPovezavo(16, 17);
		dodajPovezavo(16, 19);
		dodajPovezavo(21, 22);
		dodajPovezavo(22, 23);
		

		}
	
	public void dodajPovezavo(int prva, int druga) {
		tabela[prva].povezave.add(tabela[druga]);
		tabela[druga].povezave.add(tabela[prva]);
	}

	public Polje polje (int i) {
		return tabela[i];
	}
	
	public int indeks (Polje polje) {
		for (int i = 0; i < tabela.length; i++) {
			if (tabela[i] == polje) return i;
		}
		return -1;
	}

	
	public static void main(String[] args) {
		new IgralnaPlosca();
		
		//System.out.println(Arrays.toString(tabela));
	}

}
