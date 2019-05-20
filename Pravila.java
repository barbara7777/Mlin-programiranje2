import java.util.Set;
import java.util.Vector;

public class Pravila {

	//static int[][] trojice = new int[20][3];

	public static Vector<Integer> jeMlin(int polje) {
		Vector<Integer> mlin = new Vector<>(3, 6);
		Set<Set<Integer>> kandidati = IgralnaPloscaInfo.kandidatiZaMlin.get(polje); // množica parov, ki skupajs poljem polje tvorijo trojico

		for (Set<Integer> par : kandidati) { 
			Vector<Integer> a = new Vector<>(3);
			a.add(polje);
			for (int i : par) {
				if (IgralnaPloscaInfo.tabela[polje].zasedenost != IgralnaPloscaInfo.tabela[i].zasedenost) 
					break;
					a.add(i);
					// preverim èe imam 3, torej za cel mlin
					if (a.size() != 0 && a.size() % 3 == 0) mlin.addAll(a);
			}
		}
	return mlin;
	}
			
	public boolean obstajaMlin () {
		for (Polje polje : IgralnaPloscaInfo.tabela) {
			if (jeMlin(polje.indeks).size() > 1 && !IgralnaPloscaInfo.tabela[jeMlin(polje.indeks).get(0)].jePrazno())
				return true;
		}
		return false;
	}
	
	public static boolean jePraznoPolje (Igralec player, Polje polje) {
		if (player.faza != 2) {
			if (polje.zasedenost != Polje.prazno)
				return false;
		}
		else {
			if (polje.zasedenost != Polje.prazno) {
				return false;			
			}
		}
		return true;
	}
	
	public static boolean lahkoPremaknem (Polje zacetno, Polje koncno, Igralec igralecNaPotezi) {
		if (!jePraznoPolje(igralecNaPotezi, koncno)) 
			return false;
		if (igralecNaPotezi.faza == 2) {
			if (!IgralnaPloscaInfo.staPovezana(zacetno, koncno)) {
				return false;
			}
		}
		return true;			
	}
}
