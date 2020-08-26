package studiranje.ip.controller.test;

import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Тестирање података о државама добијеним са сервиса странице ЕУ о државама (у Европи и свету). 
 * @author mirko
 * @version 1.0
 */
public class EUCSDefinitionListTestViewProgram {
	public static final Scanner scanner = new Scanner(System.in);
	
	public static  String input(String header) {
		System.out.print(header);
		return scanner.nextLine();
	}
	
	public static void outputln(String text){
		System.out.println(text);
	}
	
	public static void output(String text) {
		System.out.print(text);
	}
	
	public static void outputln() {
		System.out.println();
	}
	
	public static void menu() {
		System.out.println(); 
		System.out.println("MENU : ");
		System.out.println("0. Izlaz");
		System.out.println("1. Ispis JSON podataka sa servisa EU.");
		System.out.println("2. Ispis imena drzava sa ovih servisa EU.");
		System.out.println("3. Ispis podataka za ime drzave sa sevisa EU.");
	}
	
	public static void main(String ... args) {
		int izbor = -1; 
		while(true) {
			if(izbor==0) break;
			try {
				menu(); 
				outputln();
				izbor = Integer.parseInt(input("IZBOR : "));
				switch(izbor) {
					case 0:
						break;
					case 1: 
						serviceReviewInformationEUDefCountries();
						break;
					case 2: 
						listCountryNamesEUDef();
						break;
					case 3: 
						countryInfoEUDef();
						break;
				}
			}catch(Exception ex) {
				izbor = -1; 
			}
		}
	}
	
	public static void serviceReviewInformationEUDefCountries() {
		try {
			System.out.println("PREGLED SADRZAJA SERVISA");
			URL url = new URL("https://restcountries.eu/rest/v2/region/europe");
			try(Scanner scan= new Scanner(url.openStream(), "UTF-8")){
				while(scan.hasNextLine()) 
					System.out.println(scan.nextLine()); 
			}
		}catch(Exception ex) {
			outputln("GRESKA");
			ex.printStackTrace(System.out);
		}
	}
	
	public static void listCountryNamesEUDef() {
		try {
			System.out.println("LISTA IMENA DRZAVA KOJE NUDI SERVIS OD EU");
			URL url = new URL("https://restcountries.eu/rest/v2/region/europe");
			String dataString = ""; 
			try(Scanner scan= new Scanner(url.openStream(), "UTF-8")){
				while(scan.hasNextLine()) 
					dataString += scan.nextLine()+"\n"; 
			}
			
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(dataString).getAsJsonArray();
			
			outputln("Broj zemalja sa spiska od EU : "+array.size()); 
			for(int i=0; i<array.size(); i++) {
				JsonObject object = array.get(i).getAsJsonObject(); 
				System.out.println(object.getAsJsonPrimitive("name").getAsString()); 
			}
		}catch(Exception ex) {
			outputln("GRESKA");
			ex.printStackTrace(System.out);
		}
	}
	
	public static void countryInfoEUDef() {
		try {
			System.out.println("LISTA IMENA DRZAVA KOJE NUDI SERVIS OD EU");
			String name = input("Ime trazene drzave : ");
			URL url = new URL("https://restcountries.eu/rest/v2/region/europe");
			String dataString = ""; 
			try(Scanner scan= new Scanner(url.openStream(), "UTF-8")){
				while(scan.hasNextLine()) 
					dataString += scan.nextLine()+"\n"; 
			}
			
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(dataString).getAsJsonArray();
			
			outputln("Broj zemalja sa spiska od EU : "+array.size()); 
			for(int i=0; i<array.size(); i++) {
				String cname = array.get(i).getAsJsonObject().getAsJsonPrimitive("name").getAsString(); 
				if(name.contentEquals(cname)) {
					JsonObject object = array.get(i).getAsJsonObject(); 
					System.out.println("Naziv     : "+ object.get("name").getAsString());
					System.out.println("Kod       : "+ object.get("alpha3Code").getAsString());
					System.out.println("Zastavica : https://www.countryflags.io/"+object.get("alpha2Code").getAsString()+"/flat/64.png");
					System.out.print("Pozivni : ");
					JsonArray ccs = object.get("callingCodes").getAsJsonArray();
					for(int j=0; j<ccs.size(); j++) {
						String cc = ccs.get(j).getAsString();
						System.out.print(cc);
					}
					System.out.println();
					System.out.print("Domeni : ");
					JsonArray tlds = object.get("topLevelDomain").getAsJsonArray();
					for(int j=0; j<tlds.size(); j++) {
						String tld = tlds.get(j).getAsString();
						System.out.print(tld);
					}
					System.out.println();
					return;
				}
			}
			
			outputln("ZEMLJA NIJE U SPISKU ZEMALJA OD OVOG SERVISA EU");
		}catch(Exception ex) {
			outputln("GRESKA");
			ex.printStackTrace(System.out);
		}
	}
}
