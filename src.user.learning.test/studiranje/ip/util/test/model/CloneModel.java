package studiranje.ip.util.test.model;

/**
 * Модел који илуструје клонирање.
 * @author mirko
 * @version 1.0
 */
public class CloneModel implements Cloneable{
	@Override
	public CloneModel clone() throws CloneNotSupportedException {
		return (CloneModel) super.clone(); 
	}
}
