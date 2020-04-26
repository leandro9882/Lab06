package it.polito.tdp.meteo.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestModel {

	public static void main(String[] args) {
		
		Model2 m = new Model2();
	
	//	System.out.println(m.getUmiditaMedia(12));
		List<Rilevamento> l=new ArrayList<>(m.trovaSequenza(12));
		for(Rilevamento r:l) {
		System.out.println(r.toString());
		}

	}

}
