import java.util.*;

public class Test {
	static Poteza[] dodaj;
	static Poteza[][][] premakni; // elementi not so : iz i premaknem na j, vzamem na k
	Igra igra;
	
	
	public Test() {
	Igra igra = new Igra();
	
	}
	
	public static void main(String[] args) {
		Igra igra = new Igra();
		
		Poteza[] dodaj = new Poteza[24];
		Poteza[][][] premakni = new Poteza[24][24][25];
		
		for (int i = 0; i < 24; ++i) {
			dodaj[i] = new Poteza(null, IgralnaPloscaInfo.tabela[i], null);
		}
		
		
		for (int i = 0; i < 24; ++i) { 
			for (int j = 0; j < 24; ++j) {
				for (int k = 0; k < 24; ++k) {
					premakni[i][j][k] = new Poteza(IgralnaPloscaInfo.tabela[i], IgralnaPloscaInfo.tabela[j], IgralnaPloscaInfo.tabela[k]);
				}
				premakni[i][j][24] = new Poteza(IgralnaPloscaInfo.tabela[i], IgralnaPloscaInfo.tabela[j], null);
			}
		}
	
		for (Polje polje : IgralnaPloscaInfo.tabela) 
		System.out.print(polje.zasedenost + "  ");
		
		
	}

}
