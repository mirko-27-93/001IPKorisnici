package studiranje.ip.util.test;

import studiranje.ip.util.UserDeletion;

/**
 * Тестирање клонабилних безимених сепцијализација. 
 * @author mirko
 * @version 1.0
 */
public class CloneableAnonymusTest001 {
	public static void main(String ... args) throws CloneNotSupportedException {
		UserDeletion deletion1 = new UserDeletion() {
			private static final long serialVersionUID = 6843076778570467145L;

			@Override
			public void delete(String username) {
				System.out.println("Delete "+username);
			}

			@Override
			public UserDeletion clone() throws CloneNotSupportedException {
				return (UserDeletion) super.clone();
			}
			
		};
		UserDeletion deletion2 = deletion1.clone(); 
		deletion1.delete("username");
		deletion2.delete("username");
		System.out.println(deletion1.toString());
		System.out.println(deletion2.toString());
		System.out.println(deletion1.equals(deletion2));
		System.out.println(deletion1==deletion2);
	}
}
