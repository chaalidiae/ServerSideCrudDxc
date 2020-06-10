package ma.dxc.dto;

import java.util.ArrayList;
import java.util.Collection;


import ma.dxc.model.Permission;

/**
 * Cette classe est identique à AppRole, mais cette dernière ne peut pas communiquer avec la couche REST, donc, 
 * on ajoute une autre classe AppRoleDTO qui va nous rendre ce service.
 * @author dchaa
 *
 */
public class AppRoleDTO {
	
	private Long id;
	private String roleName;
	private Collection<Permission> permissions = new ArrayList<>();
	
	public AppRoleDTO() {
		super();
	}
	public AppRoleDTO(Long id, String roleName, Collection<Permission> permissions) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.permissions = permissions;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Collection<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
	}
}
