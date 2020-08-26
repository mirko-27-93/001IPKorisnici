package studiranje.ip.util.test;

import studiranje.ip.util.test.model.CloneModel;

/**
 * Тестирање клонабилних обичних објеката. 
 * @author mirko
 * @version 1.0
 */
public class CloneableAnonymusTest002 {
	public static void main(String ... args) throws CloneNotSupportedException {
		CloneModel deletion1 = new CloneModel();
		CloneModel deletion2 = deletion1.clone(); 
		System.out.println(deletion1.toString());
		System.out.println(deletion2.toString());
		System.out.println(deletion1.equals(deletion2));
		System.out.println(deletion1==deletion2);
	}
}
