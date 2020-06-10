package ma.dxc.security;

/**
 * Cette classe représente une requete d'authentification qui se carfactérise généralement par un nom d'utilisateur
 * et un mot de passe.
 * @author dchaa
 *
 */
public class AuthenticationRequest {
	
	private String username;
	private String password;
	public AuthenticationRequest() {
		super();
	}
	public AuthenticationRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
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
}
