package ma.dxc.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.dxc.model.AppRole;
import ma.dxc.model.Permission;
import ma.dxc.repository.RoleRepository;
import ma.dxc.repository.specs.RoleSpecification;
import ma.dxc.repository.specs.SearchCriteria;
import ma.dxc.repository.specs.SearchOperation;
/**
 * Cette classe implémente la classe RoleService qui contint les différents fonctions CRUD pour l'entite Role.
 * @author dchaa
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PermissionServiceImpl permissionServiceImpl;
	/**
	 * Cette fonction permet de retourner tout les roles.
	 */
	@Override
	public List<AppRole> findAll() {
		return roleRepository.findAll();
	}
	
	/**
	 * Cette fonction permet de trouver un seul role en se basant dur son ID.
	 */
	@Override
	public AppRole findOne(long id) {
		return roleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	
	/**
	 * cette fonction permet d'ajouter un role.
	 */
	@Override
	public AppRole save(AppRole appRole) {
		return roleRepository.save(appRole);
	}
	
	/**
	 * cette fonction permet de récupérer un role en se basant sur son nom.
	 */
	@Override
	public AppRole getRoleByRoleName(String roleName) {
		return roleRepository.findByRoleName(roleName);
	}

	/**
	 * Cette fonction permet de retrouver un role en se basant sur un mot clé et un critère,
	 * elle retourne une page des roles.
	 */
	@Override
	public Page<AppRole> search(String mc, int page, int size, String column) {
				roleRepository.findAll();
				Pageable pageable = PageRequest.of(page, size);
				RoleSpecification roleSpecification = new RoleSpecification();
				 if(column.equals("permissions")){
					List<Permission> permissions = permissionServiceImpl.search(mc, page, size, "permissionName").getContent();
					Permission permission = permissions.get(0);
					List<AppRole> allRoles = roleRepository.findAll();
					List<AppRole> roles = new ArrayList<AppRole>();
					for (AppRole role : allRoles) {
						if(role.getPermissions().contains(permission)) {
							roles.add(role);
						}
					}
					Page<AppRole>appRolePage = new PageImpl<AppRole>(roles, pageable, roles.size());
					return appRolePage;
				}else if(isNumeric(mc)) {
					roleSpecification.add(new SearchCriteria(column, mc, SearchOperation.EQUAL));
				}else{
					roleSpecification.add(new SearchCriteria(column, mc, SearchOperation.MATCH));
				}
				//pagination des resultats
				Page<AppRole> msTitleList = roleRepository.findAll(roleSpecification, pageable);
		        return msTitleList;
	}
	
	/**
	 * Cette fonction permet de tester si une chaine de caractère est numérique.
	 * @param strNum
	 * @return
	 */
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Long d = Long.parseLong(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

	/**
	 * Cette fonction permet de modifier un role.
	 */
	@Override
	public AppRole update(Long id, AppRole appRole) {
		// TODO Auto-generated method stub
		appRole.setId(id);
		return roleRepository.save(roleRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new)
                .updateProperties(appRole));
	}

	 /**
	  * Cette fonction permet de retourner tout les roles.
	  */
	@Override
	public Page<AppRole> findAllPageable(int page, int size) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(page, size);
		return roleRepository.findAll(pageable);
	}
 
	/**
	 * Cette fonction permet de supprimer un role.
	 */
	@Override
	public AppRole delete(Long id) {
		AppRole role = roleRepository.findById(id).get();
		System.out.println(role.toString());
		role.setDeleted(true);
		return roleRepository.save(role);
	}

}
