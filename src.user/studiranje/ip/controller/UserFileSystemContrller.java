package studiranje.ip.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import studiranje.ip.exception.MemoryMismatchException;
import studiranje.ip.lang.UserFileSystemPathConstants;

/**
 * Контрола која се односи на меморијска ограничења, када је у питању
 * заузеће фолдера у који се смјештају слике. 
 * @author mirko
 * @version 1.0
 */
public class UserFileSystemContrller {
	public static long RESTRICT_PROFILE_MEMORY    = 1024*1024*1024; 
	public static long RESTRICT_USER_MEMORY        = 1024*1024*1024; 
	public static long RESTRICT_COUNTRY_FLAG_MEMORY = 1024*1024*1024; 
	
	/**
	 * Служи да опише слике за корисника, као различите.
	 * Заглавље које се додаје називу слике на почетак. 
	 * Низ од 10 декадних случајних цифара. 
	 * @return idH.
	 */
	public static String randomFilenameHeader() {
		String idH = "";
		for(int i=0; i<10; i++) 
			idH+=""+Math.abs(new Random().nextInt()%10); 
		return idH; 
	}
	
	public long loadProfileMemorySize() {
		try(FileInputStream fis = new FileInputStream(UserFileSystemPathConstants.PROFILE_IMAGES_INFO)){
			JsonParser parser = new JsonParser();
			JsonObject root = parser.parse(new InputStreamReader(fis,"UTF-8")).getAsJsonObject();
			return Long.parseLong(root.get("profile.images.size").getAsString()); 
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public long loadUserMemorySize() {
		try(FileInputStream fis = new FileInputStream(UserFileSystemPathConstants.USER_IMAGES_INFO)){
			JsonParser parser = new JsonParser();
			JsonObject root = parser.parse(new InputStreamReader(fis,"UTF-8")).getAsJsonObject();
			return Long.parseLong(root.get("user.images.size").getAsString()); 
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public long loadCountryFlagMemorySize() {
		try(FileInputStream fis = new FileInputStream(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES_INFO)){
			JsonParser parser = new JsonParser();
			JsonObject root = parser.parse(new InputStreamReader(fis,"UTF-8")).getAsJsonObject();
			return Long.parseLong(root.get("country_flag.images.size").getAsString()); 
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void storeProfileMemorySize(long size) {
		if(size<0) size = 0; 
		try(FileOutputStream fos = new FileOutputStream(UserFileSystemPathConstants.PROFILE_IMAGES_INFO)){
			JsonObject object = new JsonObject(); 
			object.addProperty("profile.images.size", size);
			fos.write(object.toString().getBytes("UTF-8"));
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void storeUserMemorySize(long size) {
		if(size<0) size = 0; 
		try(FileOutputStream fos = new FileOutputStream(UserFileSystemPathConstants.USER_IMAGES_INFO)){
			JsonObject object = new JsonObject(); 
			object.addProperty("user.images.size", size);
			fos.write(object.toString().getBytes("UTF-8"));
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void storeCountryFlagMemorySize(long size) {
		if(size<0) size = 0; 
		try(FileOutputStream fos = new FileOutputStream(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES_INFO)){
			JsonObject object = new JsonObject(); 
			object.addProperty("user.images.size", size);
			fos.write(object.toString().getBytes("UTF-8"));
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void recordProfileImage(String filename, InputStream stream) throws MemoryMismatchException, IOException{
		File image = new File(UserFileSystemPathConstants.PROFILE_IMAGES, filename);
		if(image.exists()) try{eraseProfileImage(filename);}catch(Exception ex) {ex.printStackTrace();}
		long memory = loadProfileMemorySize();
		memory+=stream.available();
		try(FileOutputStream fos=new FileOutputStream(image)){
			fos.write(stream.readAllBytes());
		}
		storeProfileMemorySize(memory);
	}
	public void eraseProfileImage(String filename) throws FileNotFoundException{
		long memory = loadProfileMemorySize();
		File image = new File(UserFileSystemPathConstants.PROFILE_IMAGES, filename);
		if(!image.exists()) throw new FileNotFoundException();
		memory-=image.length(); 
		image.delete();
		storeProfileMemorySize(memory);
	}
	public void recordUserImage(String filename, InputStream stream) throws MemoryMismatchException, IOException{
		File image = new File(UserFileSystemPathConstants.USER_IMAGES, filename);
		if(image.exists()) try{eraseUserImage(filename);}catch(Exception ex) {ex.printStackTrace();}
		long memory = loadUserMemorySize();
		memory+=stream.available();
		try(FileOutputStream fos=new FileOutputStream(image)){
			fos.write(stream.readAllBytes());
		}
		storeUserMemorySize(memory);
	}
	public void eraseUserImage(String userimage) throws FileNotFoundException {
		long memory = loadUserMemorySize();
		File image = new File(UserFileSystemPathConstants.USER_IMAGES, userimage);
		if(!image.exists()) throw new FileNotFoundException();
		memory-=image.length(); 
		image.delete();
		storeUserMemorySize(memory);
	}
	public void recordCountryFlagImage(String filename, InputStream stream) throws MemoryMismatchException, IOException{
		File image = new File(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES, filename);
		if(image.exists()) try{eraseCountryFlagImage(filename);}catch(Exception ex) {ex.printStackTrace();}
		long memory = loadCountryFlagMemorySize();
		memory+=stream.available();
		try(FileOutputStream fos=new FileOutputStream(image)){
			fos.write(stream.readAllBytes());
		}
		storeCountryFlagMemorySize(memory);
	}
	public void eraseCountryFlagImage(String filename) throws FileNotFoundException{
		long memory = loadCountryFlagMemorySize();
		File image = new File(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES, filename);
		if(!image.exists()) throw new FileNotFoundException();
		memory-=image.length(); 
		image.delete();
		storeCountryFlagMemorySize(memory);
	}
	
}
