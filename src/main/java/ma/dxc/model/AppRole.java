package ma.dxc.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Where;


@Entity
@Where(clause = "deleted = 0")
public class AppRole {
	
	@Id @GeneratedValue
	private Long id;
	private String roleName;
	private boolean deleted = false;

	
	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Permission> permissions = new ArrayList<>();
	public AppRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppRole(Long id, String roleName, Collection<Permission> permissions) {
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
	
	@Override
	public String toString() {
		return "AppRole [id=" + id + ", roleName=" + roleName + ", permissions=" + permissions + "]";
	}
	
	public AppRole updateProperties(AppRole approle) {
		this.roleName = approle.roleName;
		this.permissions = approle.permissions;
		return this;
	}
}

