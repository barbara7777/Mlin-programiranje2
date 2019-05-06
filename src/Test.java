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
		
		
		// igrajmo
		igra.narediPotezo(dodaj[0]);
		igra.narediPotezo(dodaj[13]);
		igra.narediPotezo(dodaj[10]);
		igra.narediPotezo(dodaj[4]);
		igra.narediPotezo(dodaj[9]);
		igra.narediPotezo(dodaj[21]);
		igra.narediPotezo(new Poteza(null, IgralnaPloscaInfo.tabela[11], IgralnaPloscaInfo.tabela[21]));
		igra.narediPotezo(dodaj[21]);
		igra.narediPotezo(dodaj[5]);
		igra.narediPotezo(dodaj[23]);
		igra.narediPotezo(dodaj[22]);
		igra.narediPotezo(dodaj[14]);
		igra.narediPotezo(dodaj[12]);
		igra.narediPotezo(dodaj[19]);
		igra.narediPotezo(dodaj[2]);
		igra.narediPotezo(dodaj[1]);
		igra.narediPotezo(dodaj[7]);
		igra.narediPotezo(premakni[11][6][24]);
		igra.narediPotezo(premakni[13][20][10]);
		igra.narediPotezo(premakni[11][6][24]);
		igra.narediPotezo(premakni[12][8][21]);
		igra.narediPotezo(premakni[19][16][24]);
		igra.narediPotezo(premakni[22][21][18]);
		igra.narediPotezo(premakni[23][22][24]);
		igra.narediPotezo(premakni[6][11][24]);
		igra.narediPotezo(premakni[20][19][11]);
		igra.narediPotezo(premakni[9][10][24]);
		igra.narediPotezo(premakni[12][13][24]);
		igra.narediPotezo(premakni[10][9][13]);
		igra.narediPotezo(premakni[19][18][24]);
		igra.narediPotezo(premakni[19][18][24]);
	
		for (Polje polje : IgralnaPloscaInfo.tabela) 
		System.out.print(polje.zasedenost + "  ");
		
		
	}

}
