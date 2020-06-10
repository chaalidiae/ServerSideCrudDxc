package ma.dxc.dto;

/**
 * Cette classe est identique à Permission, mais cette dernière ne peut pas communiquer avec la couche REST, donc, 
 * on ajoute une autre classe PermissionDTO qui va nous rendre ce service.
 * @author dchaa
 *
 */
public class PermissionDTO {
	
	private Long id;
	private String permissionName;
	public PermissionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PermissionDTO(Long id, String permissionName) {
		super();
		this.id = id;
		this.permissionName = permissionName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
}
