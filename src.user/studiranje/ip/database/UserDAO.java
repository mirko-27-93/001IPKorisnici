package studiranje.ip.database;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import studiranje.ip.exception.UserDuplicationException;
import studiranje.ip.exception.UserNotFoundException;
import studiranje.ip.lang.UserFileSystemPathConstants;
import studiranje.ip.model.UserFlags;
import studiranje.ip.model.UserInfo;
import studiranje.ip.model.UserPassword;
import studiranje.ip.model.UserRequisit;

/**
 * Адаптер за појединачне кориснике и њихове основне податке,
 * када је у питању, извор за базе података.
 * @author mirko
 * @version 1.0
 */
public class UserDAO {
	public final static String sqlInsert = "INSERT INTO ip.userinfo(username, firstname, secondname, emailaddress, passwordhash) VALUES (?,?,?,?,?)";
	public final static String sqlUpdate = "UPDATE ip.userinfo SET username=?, firstname=?, secondname=?, emailaddress=?, passwordhash=? WHERE username=?";
	public final static String sqlDelete = "DELETE FROM ip.userinfo WHERE username=?";
	public final static String sqlSelect = "SELECT username, firstname, secondname, emailaddress, passwordhash FROM ip.userinfo WHERE username=?";
	public final static String sqlGetUser = "SELECT username, firstname, secondname, emailaddress FROM ip.userinfo WHERE username=?"; 
	public final static String sqlGetRequisite = "SELECT username, telephone, city, country, profile_image_path, user_image_path, country_flag_image_path, notification_webapp_supported, notification_email_supported FROM ip.userinfo WHERE username=?";
	public final static String sqlGetPassword = "SELECT passwordhash FROM ip.userinfo WHERE username=?"; 
	public final static String sqlGetMail = "SELECT emailaddress FROM ip.userinfo WHERE username=?";
	public final static String sqlExistsUN = "SELECT count(username) FROM ip.userinfo WHERE username=?";
	public final static String sqlExistsEmail = "SELECT count(emailaddress) FROM ip.userinfo WHERE emailaddress=?";
	public final static String sqlUpdateProfileImagePath = "UPDATE ip.userinfo SET profile_image_path=? WHERE username=?"; 
	public final static String sqlUpdateUserImagePath = "UPDATE ip.userinfo SET user_image_path=? WHERE username=?"; 
	public final static String sqlUpdateCountryFlagImagePath = "UPDATE ip.userinfo SET country_flag_image_path=? WHERE username=?";
	public final static String sqlUpdateRequisit = "UPDATE ip.userinfo SET city=?, country=?, telephone=?, notification_webapp_supported=?, notification_email_supported=? WHERE username=?";
	public final static String sqlGetDescription = "SELECT short_description FROM ip.userinfo WHERE username=?"; 
	public final static String sqlUpdateDescription = "UPDATE ip.userinfo SET short_description = ? WHERE username=?"; 
	public final static String sqlGetUserFlags = "SELECT notification_webapp_supported, notification_email_supported, user_session_control FROM ip.userinfo WHERE username=?";
	public final static String sqlPutUserFlags = "UPDATE ip.userinfo SET notification_webapp_supported=?, notification_email_supported=?, user_session_control=? WHERE username=?"; 
	
	private ConnectionPool connections = ConnectionPool.getConnectionPool();

	public ConnectionPool getConnections() {
		return connections;
	}

	public void setConnections(ConnectionPool connections) {
		this.connections = connections;
	}

	public void insert(UserDTO dto) throws SQLException {
		if(dto == null) throw new NullPointerException(); 
		if(dto.getUser()==null) throw new NullPointerException();
		if(dto.getPassword() == null) throw new NullPointerException(); 
		if(get(dto.getUser().getUsername())!=null) throw new UserDuplicationException();
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlInsert)){
			statement.setString(1, dto.getUser().getUsername());
			statement.setString(2, dto.getUser().getFirstname());
			statement.setString(3, dto.getUser().getSecondname());
			statement.setString(4, dto.getUser().getEmail());
			statement.setString(5, dto.getPassword().getToPasswordRecord());
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
		connections.checkIn(connection);
	}
	
	public void update(String oldUsername, UserDTO neoUser) throws SQLException {
		if(oldUsername==null)  throw new NullPointerException(); 
		if(neoUser==null) throw new NullPointerException();
		if(neoUser.getUser()==null) throw new NullPointerException();
		if(neoUser.getPassword() == null) throw new NullPointerException(); 
		if(get(oldUsername)==null) throw new UserNotFoundException();
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdate)){
			statement.setString(1, neoUser.getUser().getUsername());
			statement.setString(2, neoUser.getUser().getFirstname());
			statement.setString(3, neoUser.getUser().getSecondname());
			statement.setString(4, neoUser.getUser().getEmail());
			statement.setString(5, neoUser.getPassword().getToPasswordRecord());
			statement.setString(6, oldUsername);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
		connections.checkIn(connection);
	}
	
	public void delete(String username) throws SQLException {
		if(username==null) throw new NullPointerException(); 
		if(get(username)==null) throw new UserNotFoundException();
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlDelete)){
			statement.setString(1, username);
			statement.execute();
			statement.close();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
		connections.checkIn(connection);
	}
	
	public UserDTO getFullDTO(String username) throws SQLException {
		UserDTO dto = get(username); 
		UserRequisit requisit = getRequisit(username); 
		if(dto==null) return dto; 
		dto.setRequisit(requisit);
		dto.setDescription(getDescription(username));
		return dto; 
	}
	
	public UserDTO get(String username) throws SQLException {
		if(username==null) throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlSelect)){
			String uname=null;
			String fname=null; 
			String sname=null; 
			String email=null;
			String passwd=null;
			statement.setString(1, username);
			try(ResultSet rs=statement.executeQuery()){
				while(rs.next()) {
					uname = rs.getString("username");
					fname = rs.getString("firstname");
					sname = rs.getString("secondname");
					email = rs.getString("emailaddress");
					passwd = rs.getString("passwordhash");
				}
				if(uname==null) return null;
				if(fname==null) fname="";
				if(sname==null) sname="";
				if(email==null) email="";
				if(passwd==null) passwd="";
				UserInfo ui = new UserInfo(uname,fname,sname,email);
				UserPassword up = new UserPassword(passwd, true);
				UserRequisit ur = new UserRequisit();
				UserDTO dto = new UserDTO(ui,up, ur);
				return dto; 
			}catch(Exception ex) {
				throw new RuntimeException(ex); 
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally{
			connections.checkIn(connection);
		}
	}
	
	public UserRequisit getRequisit(String username) throws SQLException{
		if(username==null) throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetRequisite)){
				String uname=null;
				String phone=null; 
				String city=null; 
				String country=null;
				String profileImagePath=null;
				String userImagePath=null; 
				String countryFlagImagePath=null; 
				boolean notificationWebappSupport=false; 
				boolean notificationEmailSupport=false; 
				statement.setString(1, username);
				try(ResultSet rs=statement.executeQuery()){
					while(rs.next()) {
						uname  = rs.getString("username");
						phone = rs.getString("telephone");
						city = rs.getString("city");
						country = rs.getString("country");
						profileImagePath = rs.getString("profile_image_path");
						userImagePath = rs.getString("user_image_path"); 
						countryFlagImagePath = rs.getString("country_flag_image_path");
						notificationWebappSupport = rs.getBoolean("notification_webapp_supported");
						notificationEmailSupport = rs.getBoolean("notification_email_supported");
					}
					if(uname==null) return null;
					UserRequisit ui = new UserRequisit(uname);
					ui.setTelephone(phone);
					ui.setCity(city);
					ui.setCountry(country);
					if(profileImagePath!=null) ui.setProfilePicture(new File(UserFileSystemPathConstants.PROFILE_IMAGES, profileImagePath));
					if(userImagePath!=null) ui.setUserPicture(new File(UserFileSystemPathConstants.USER_IMAGES, userImagePath));
					if(countryFlagImagePath!=null) ui.setCountryFlagPicture(new File(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES, countryFlagImagePath));
					ui.setWebappNotifications(notificationWebappSupport);
					ui.setEmailNotifications(notificationEmailSupport);
					return ui;
			}catch(Exception ex) {
				throw new RuntimeException(ex); 
			}finally{
				connections.checkIn(connection);
			}
		}
	}
	
	public UserInfo getUser(String username) throws SQLException {
		if(username==null) throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetUser)){
				String uname=null;
				String fname=null; 
				String sname=null; 
				String email=null;
				statement.setString(1, username);
				try(ResultSet rs=statement.executeQuery()){
					while(rs.next()) {
						uname = rs.getString("username");
						fname = rs.getString("firstname");
						sname = rs.getString("secondname");
						email = rs.getString("emailaddress");
					}
					if(uname==null) return null;
					if(fname==null) fname="";
					if(sname==null) sname="";
					if(email==null) email="";
					UserInfo ui = new UserInfo(uname,fname,sname,email);
					return ui; 
			}catch(Exception ex) {
				throw new RuntimeException(ex); 
			}finally{
				connections.checkIn(connection);
			}
		}
	}
	
	public UserPassword getPassword(String username) throws SQLException {
		if(username==null) throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetPassword)){
				String passwd=null;
				statement.setString(1, username);
				try(ResultSet rs=statement.executeQuery()){
					while(rs.next()) {
						passwd = rs.getString("passwordhash");
					}
					if(passwd==null) return null;
					UserPassword up = new UserPassword(passwd, true);
					return up;
			}catch(Exception ex) {
				throw new RuntimeException(ex); 
			}finally{
				connections.checkIn(connection);
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public String getEmail(String username) throws SQLException {
		if(username==null) throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetMail)){
			String email=null;
			statement.setString(1, username);
			try(ResultSet rs=statement.executeQuery()){
				while(rs.next()) {
					email = rs.getString("emailaddress");
				}
				if(email==null) return null;
				return email; 
			}catch(Exception ex) {
				throw new RuntimeException(ex); 
			}finally{
				connections.checkIn(connection);
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public boolean existsUser(String username) throws SQLException {
		boolean exists = false;
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlExistsUN)){
			statement.setString(1, username);
			try(ResultSet rs = statement.executeQuery()){ 
				while(rs.next()) {
					if(rs.getInt(1)>0) exists = true; 
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
		return exists; 
	}
	
	public boolean existsEmail(String email) throws SQLException {
		boolean exists = false;
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlExistsEmail)){
			statement.setString(1, email);
			try(ResultSet rs = statement.executeQuery()){ 
				while(rs.next()) {
					if(rs.getInt(1)>0) exists = true; 
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
		return exists; 
	}
	
	public void updateProfilePicture(String username, String profilePicturePath) throws SQLException {
		if(username==null)  throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdateProfileImagePath)){
			statement.setString(1, profilePicturePath);
			statement.setString(2, username);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	public void updateUserPicture(String username, String userPicturePath) throws SQLException {
		if(username==null)  throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdateUserImagePath)){
			statement.setString(1, userPicturePath);
			statement.setString(2, username);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	public void updateCountryFlagPicture(String username, String countryFlagImage) throws SQLException {
		if(username==null)  throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdateCountryFlagImagePath)){
			statement.setString(1, countryFlagImage);
			statement.setString(2, username);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	public void updateRequisite(String oldUsername, UserRequisit neoData) throws SQLException {
		if(oldUsername==null)  throw new NullPointerException(); 
		if(neoData==null) throw new NullPointerException(); 
		if(get(oldUsername)==null) throw new UserNotFoundException();
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdateRequisit)){
			statement.setString(1, neoData.getCity());
			statement.setString(2, neoData.getCountry());
			statement.setString(3, neoData.getTelephone());
			statement.setBoolean(4, neoData.isWebappNotifications());
			statement.setBoolean(5, neoData.isEmailNotifications());
			statement.setString(6, oldUsername);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		} finally {
			connections.checkIn(connection);
		}
	}
	
	public String getDescription(String username) throws SQLException {
		if(username==null) 		throw new NullPointerException(); 
		if(get(username)==null) throw new UserNotFoundException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetDescription)){
			statement.setString(1, username);
			try(ResultSet resultSet = statement.executeQuery()){
				String description = "";
				while(resultSet.next()) 
					description = resultSet.getString(1); 
				return description; 
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	public void setDescription(String username, String description) throws SQLException {
		if(username==null) 		throw new NullPointerException(); 
		if(get(username)==null) throw new UserNotFoundException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdateDescription)){
			statement.setString(1, description);
			statement.setString(2, username);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	public UserFlagsDTO getUserFlags(String username) throws SQLException {
		if(username==null) 		throw new NullPointerException(); 
		if(get(username)==null) throw new UserNotFoundException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetUserFlags)){
			UserFlagsDTO dto = new UserFlagsDTO(); 
			UserFlags flags =  new UserFlags(username);
			boolean found = false; 
			dto.setConfigurations(flags);
			
			statement.setString(1, username);
			try(ResultSet resultSet=statement.executeQuery()){
				while(resultSet.next()) {
					found=true;
					boolean notificationWebAppSupport = resultSet.getBoolean("notification_webapp_supported"); 
					boolean notificationEmailSupport = resultSet.getBoolean("notification_email_supported"); 
					boolean userSessionControlSupport = resultSet.getBoolean("user_session_control"); 
					flags.setEmailNotifications(notificationEmailSupport);
					flags.setWebNotifications(notificationWebAppSupport);
					flags.setUserSessionsControl(userSessionControlSupport);
				}
			}
			if(!found) return null; 
			return dto; 
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connections.checkIn(connection);
		}
	}
	
	public void putUserFlags(UserFlagsDTO dto) throws SQLException {
		if(dto==null) throw new NullPointerException("DTO NOT FOUND"); 
		if(dto.getConfigurations()==null) throw new NullPointerException("DATA NOT FOUND");
		if(dto.getConfigurations().getUsername()==null) throw new NullPointerException("USER NOT FOUND");
		if(dto.getConfigurations().getUsername().trim().length()==0) throw new NullPointerException("USER NOT FOUND");
		
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlPutUserFlags)){
			statement.setBoolean(1, dto.getConfigurations().isWebNotifications());
			statement.setBoolean(2, dto.getConfigurations().isEmailNotifications());
			statement.setBoolean(3, dto.getConfigurations().isUserSessionsControl());
			statement.setString(4, dto.getConfigurations().getUsername());
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connections.checkIn(connection);
		}
	}
}
