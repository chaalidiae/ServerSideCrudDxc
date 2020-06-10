package ma.dxc.dto;

/**
 * Cette classe est identique à RegisterForm, mais cette dernière ne peut pas communiquer avec la couche REST, donc, 
 * on ajoute une autre classe RegisterFormDTO qui va nous rendre ce service.
 * @author dchaa
 *
 */
public class RegisterFormDTO {
	private String username;
	private String password;
	private String repassword;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
}
