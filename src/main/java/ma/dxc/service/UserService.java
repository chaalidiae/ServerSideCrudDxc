package ma.dxc.service;

import java.util.List;

import org.springframework.data.domain.Page;

import ma.dxc.model.AppUser;

/**
 * Cette interface contient l'ensemble des fonctions qu'on va utiliser pour effectuer les opération CRUD.
 * @author dchaa
 *
 */
public interface UserService {
	public List<AppUser> findAll();
	public AppUser findOne(long id);
	public Page<AppUser> findAllPageable(int page,int size);
	public AppUser save(AppUser appUser);
	public Page<AppUser> search(String mc, int page, int size,String column);
	public AppUser update(Long id,AppUser c);
	public AppUser delete(Long id);
}
