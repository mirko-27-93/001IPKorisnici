package studiranje.ip.controller.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import studiranje.ip.controller.LocalDefinitionListController;

/**
 * Локалне дефиниције листе програм. 
 * @author mirko
 * @version 1.0
 */
public class LocalDefinitionListProgram {
	private final static Scanner scanner = new Scanner(System.in);
	private final static LocalDefinitionListController ctrl = new LocalDefinitionListController(); 
	
	public static void menu() {
		System.out.println("\nMENI: ");
		System.out.println("0. Izlaz");
		System.out.println("1. Dodavanje podatka naziva drzave");
		System.out.println("2. Brisanje podatka naziva drzave");
		System.out.println("3. Izmjene podataka naziva drzave"); 
		System.out.println("4. Listanje podataka naziva drzave");
		System.out.println("5. Detalji podataka naziva drzave");
		System.out.println("6. Ucitavanje podataka");
		System.out.println("7. Zapis podataka");
		System.out.println("8. Ciscenje"); 
	}
	
	public static String input(String header) {
		System.out.print(header);
		return scanner.nextLine(); 
	}
	
	public static void output(String text) {
		System.out.print(text);
	}
	
	public static void outputln(String text) {
		System.out.println(text);
	}
	
	public static void main(String ... args) {
		int izbor = 0;
		System.out.println("ZDRAVO");
		do {
			try {
				menu(); System.out.print("\nIZBOR : "); 
				izbor = Integer.parseInt(scanner.nextLine());
			}catch(Exception ex) {
				izbor = -1; 
			}
			System.out.println(); 
			switch(izbor) {
				case 0:
					System.out.println("PODZDRAV.");
					break;
				case 1:
					add(); 
					break;
				case 2:
					remove(); 
					break;
				case 3:
					update(); 
					break;
				case 4:
					list(); 
					break; 
				case 5:
					info(); 
					break;
				case 6: 
					load(); 
					break;
				case 7: 
					store(); 
					break;
				case 8:
					clear(); 
					break;
				default:
					System.out.println("POGRESAN IZBOR."); 
					break;
			}
		}while(izbor!=0);
	}
	
	public static void add() {
		System.out.println("DODAVANJE PODATAKA O DRZAVI");
		String code    = input("Kod    : ");
		String country = input("Zemlja : "); 
		String picture = input("Zastava (putanja ili prazno) : ");
		try {
			if(code==null || code.trim().length()==0)  throw new NullPointerException("COUNTRY-CODE-ID"); 
			if(country==null || country.trim().length()==0) throw new NullPointerException("COUNTRY-NAME"); 
			if(picture.trim().length()==0) picture = null; 
			ctrl.setCountryInfo(code, country, picture);
			outputln("DODAVANJE IZVRSENO.");
		}catch(Exception ex) {
			System.out.println("\nGRESKA PRI DODAVANJU");
			ex.printStackTrace(System.out);
		}
	}
	
	public static void remove() {
		System.out.println("BRISANJE PODATAKA O DRZAVI");
		String code = input("Kod : ");
		try {
			if(code==null || code.trim().length()==0)  throw new NullPointerException("COUNTRY-CODE-ID"); 
			ctrl.removeCountryInfo(code);
			outputln("\nBRISANJE IZVRSENO.");
		}catch(Exception ex) {
			System.out.println("\nGRESKA PRI BRISANJU");
			ex.printStackTrace(System.out);
		}
	}
	
	public static void update() {
		System.out.println("IZMJENA PODATAKA O DRZAVI"); 
		String old 	   = input("Trenutni kod      [opbavezno]: ");
		String code    = input("Novi kod          [za prazno nema izmjene]: ");
		String country = input("Novi naziv zemlje [za prazno nema izmjene]: "); 
		String picture = input("Nova zastava (putanja ili prazno - nece se promeniti) : ");
		try {
			if(old==null || old.trim().length()==0)  throw new NullPointerException("COUNTRY-CODE-ID"); 
			if(!ctrl.existsCountry(old)) throw new NullPointerException("COUNTRY-CODE-OLD-NOT FOUND"); 
			if(country==null || country.trim().length()==0) throw new NullPointerException("COUNTRY-NAME"); 
			if(picture.trim().length()==0) picture = null; 
			if(code.trim().length()==0) code = null; 
			if(country.trim().length()==0) code = null; 
			if(picture.trim().length()==0) picture = null; 
			ctrl.updateCountryInfo(old, code, country, picture);
			outputln("IZMJENA IZVRSENA.");
		}catch(Exception ex) {
			System.out.println("\nGRESKA PRI DODAVANJU");
			ex.printStackTrace(System.out);
		}
	}
	
	public static void list() {
		System.out.println("PREGLED DRZAVA"); 
		ArrayList<String> cicds = new ArrayList<>(ctrl.countryMap().keySet());
		Collections.sort(cicds);
		for(String code: cicds) {
			outputln(code);
		}
		outputln("LISTANJE IZVRSENO");
	}
	
	public static void info() {
		System.out.println("PREGLED DRZAVE");
		String code = input("Trazeni kod: ");
		String cname = ctrl.get(code);
		if(cname==null) {output("Drzava sa datim kodom nije pronadjena."); return;}
		File pict = ctrl.getPicture(code); 
		outputln("Kod: "+code);
		outputln("Naziv: "+cname);
		if(pict!=null) outputln("Slika : "+pict.getName()); 
		outputln("PRIKAZ PODATAKA ZAVRSEN."); 
	}
	
	public static void load() {
		System.out.println("UCITAVANJE SVIH DRZAVA");
		try {
			ctrl.load();
			outputln("UCITAVANJE SVIH DRZAVA - IZVRSENO");
		}catch(Exception ex) {
			outputln("\nUCITAVANJE SVIH DRZAVA - GRESKA"); 
			ex.printStackTrace(System.out);
		}
	}
	
	public static void store() {
		System.out.println("ARHIVIRANJE SVIH DRZAVA");
		try {
			ctrl.store();
			outputln("ARHIVIRANJE SVIH DRZAVA - IZVRSENO");
		}catch(Exception ex) {
			outputln("ARHIVIRANJE SVIH DRZAVA - GRESKA");
			ex.printStackTrace(System.out);
		}
	}
	
	public static void clear() {
		System.out.println("BRISANJE SVIH DRZAVA");
		outputln("BRISANJE SVIH DRZAVA - IZVRSENO");
	}
}
