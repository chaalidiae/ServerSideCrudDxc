package ma.dxc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Permission {
	@Id @GeneratedValue
	private Long id;
	private String permissionName;
	public Permission() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Permission(Long id, String permissionName) {
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
	@Override
	public String toString() {
		return "Permission [id=" + id + ", permissionName=" + permissionName + "]";
	}
	
	public Permission updateProperties(Permission permission) {
		this.permissionName = permission.permissionName;
		return this;
	}
}
