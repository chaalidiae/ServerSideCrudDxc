package ma.dxc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Where;


@Entity
@Where(clause = "deleted = 0")
public class Permission {
	@Id @GeneratedValue
	private Long id;
	private String permissionName;
	private boolean deleted = false;

	
	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
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
