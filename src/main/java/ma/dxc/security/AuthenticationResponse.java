package ma.dxc.security;

/**
 * Cette classe représente la réponse du serveur, normalement cette réponse est sous forme d'un Token JWT.
 * @author dchaa
 *
 */
public class AuthenticationResponse {
	
	private final String jwt;
	
	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}
	
	public String getJwt() {
		return jwt;
	}

}
