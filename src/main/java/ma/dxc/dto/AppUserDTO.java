package ma.dxc.dto;

import java.util.ArrayList;
import java.util.Collection;


import ma.dxc.model.AppRole;

/**
 * Cette classe est identique à AppUser, mais cette dernière ne peut pas communiquer avec la couche REST, donc, 
 * on ajoute une autre classe AppUserDTO qui va nous rendre ce service.
 * @author dchaa
 *
 */
public class AppUserDTO {
	
	private Long id;
	
	private String username;
	private String password;
	
	private Collection<AppRole> roles = new ArrayList<>();

	public AppUserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppUserDTO(Long id, String username, String password, Collection<AppRole> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Collection<AppRole> getRoles() {
		return roles;
	}

	public void setRoles(Collection<AppRole> roles) {
		this.roles = roles;
	}
}
