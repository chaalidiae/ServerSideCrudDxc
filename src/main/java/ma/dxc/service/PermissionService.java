package ma.dxc.service;

import java.util.List;

import org.springframework.data.domain.Page;

import ma.dxc.model.Permission;

public interface PermissionService {
	
	public List<Permission> findAll();
	public Permission findOne(long id);
	public Page<Permission> findAllPageable(int page,int size);
	public Permission save(Permission permission);
	public Page<Permission> search(String mc, int page, int size,String column);
	public Permission update(Long id,Permission c);
	public Permission delete(Long id);
}
