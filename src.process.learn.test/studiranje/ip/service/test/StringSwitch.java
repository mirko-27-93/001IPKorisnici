package studiranje.ip.service.test;

/**
 * Тестирање како се понашају несимболичке константе (нелитерали) стрингова 
 * у свич петљи. 
 * @author mirko
 * @version 1.0
 */
public class StringSwitch {
	public static final String CONSTANT = "Constant"; 
	
	public static void main(String ... args) {
		String var = new String("Constanta");
		switch(var) {
			case CONSTANT: 
				System.out.println("Constant");
				break;
			default: 
				System.out.println("Default");
		}
	}
}
