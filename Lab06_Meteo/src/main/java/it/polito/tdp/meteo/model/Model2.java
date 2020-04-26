package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.sun.javafx.collections.MappingChange.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model2 {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	MeteoDAO dao;
	List<Rilevamento> rilevamenti;
	List<Rilevamento> soluzione;
	private int bestCosto;
	private List<Rilevamento> bestSoluzione;

	public Model2() {
		dao = new MeteoDAO();
		bestCosto=10000;
		bestSoluzione=null;
	}

	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
		String risultato = "";
		for (String s : dao.getMediaUmidita(mese)) {
			if (s != null) {
				risultato += s + "\n";
			}
		}
		return risultato;
	}

	// of course you can change the String output with what you think works best
	public List<Rilevamento> trovaSequenza(int mese) {
		// caso iniziale
		int livello = 0;
		Set<Citta> disponibili = new HashSet<>();
		disponibili.add(new Citta("Milano", dao.getAllRilevamentiLocalitaMese(mese, "Milano")));
		disponibili.add(new Citta("Torino", dao.getAllRilevamentiLocalitaMese(mese, "Torino")));
		disponibili.add(new Citta("Genova", dao.getAllRilevamentiLocalitaMese(mese, "Genova")));
		this.soluzione = new ArrayList<>();
		List<Rilevamento> parziale = new LinkedList<>();
		cerca(parziale, livello, disponibili);
		return bestSoluzione;
	}

	private void cerca(List<Rilevamento> parziale, int livello, Set<Citta> disponibili) {
		int counter = 0;

		// condizione di terminazione
		if (livello == this.NUMERO_GIORNI_TOTALI) {
			int costo = calcolaCosto(parziale);
			if (costo < bestCosto) {
				bestSoluzione = new LinkedList<>(parziale);
				bestCosto = costo;
			}

		} else {
			for (Citta prova : disponibili) {
				if (aggiuntaValida(prova, parziale)) {
					parziale.add(prova.getRilevamenti().get(livello));
					cerca(parziale, livello + 1, disponibili);
					parziale.remove(parziale.size() - 1);
				}
			}
		}
	}

	private boolean aggiuntaValida(Citta prova, List<Rilevamento> parziale) {
		// verifica giorni massimi
		int conta = 0;
		for (Rilevamento precedente : parziale)
			if (precedente.getLocalita().compareTo(prova.getNome())==0)
				conta++;
		if (conta >= NUMERO_GIORNI_CITTA_MAX)
			return false;

		// verifica giorni minimi
		if (parziale.size() == 0) // primo giorno
			return true;
		if (parziale.size() == 1 || parziale.size() == 2) { // secondo o terzo giorno: non posso cambiare
			return parziale.get(parziale.size() - 1).getLocalita().equals(prova.getNome());
		}
		if (parziale.get(parziale.size() - 1).getLocalita().equals(prova.getNome())) // giorni successivi, posso SEMPRE rimanere
			return true;
		// sto cambiando citta
		if (parziale.get(parziale.size() - 1).getLocalita().equals(parziale.get(parziale.size() - 2).getLocalita())
				&& parziale.get(parziale.size() - 2).getLocalita().equals(parziale.get(parziale.size() - 3).getLocalita()))
			return true;
		return false;
	}

	private int calcolaCosto(List<Rilevamento> parziale) {
		int costo = 0;
		for (int i = 0; i < parziale.size(); i++) {
			costo += parziale.get(i).getUmidita();
			if (i != 0 && parziale.get(i).getLocalita().compareTo(parziale.get(i - 1).getLocalita()) != 0) {
				costo += this.COST;
			}
		}
		return costo;
	}

}

/*
 * Soluzione parziale: Una parte del tragitto Livello:numero di giorni di cui è
 * composta la soluzione parziale Dato di partenza: periodo di 15 giorni
 * Soluzione finale: soluzione di durata 15gg Caso terminale: salvare le
 * soluzione trovate Generazione delle nuove soluzioni: provare ad aggiungere
 * una città nella sol.parziale, scegliendo secondo i filtri
 */
