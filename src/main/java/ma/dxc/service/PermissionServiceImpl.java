package ma.dxc.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.dxc.model.AppUser;
import ma.dxc.model.Contact;
import ma.dxc.model.Permission;
import ma.dxc.repository.PermissionRepository;
import ma.dxc.repository.specs.ContactSpecification;
import ma.dxc.repository.specs.PermissionSpecification;
import ma.dxc.repository.specs.SearchCriteria;
import ma.dxc.repository.specs.SearchOperation;

/**
 * Cette classe représente l'implémentation des fonctions pour effectuer les opérations CRUD sur 
 * l'entité Permission.
 * @author dchaa
 *
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionRepository permissionrepository ;
	/**
	 * Cette fonction permet de retourner la liste des permissions.
	 */
	@Override
	public List<Permission> findAll() {
		return permissionrepository.findAll();
	}
	
	/**
	 * Cette fonction permet de retourner une permission en se basant sur son ID.
	 */
	@Override
	public Permission findOne(long id) {
		return permissionrepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
 
	/**
	 * Cette fonction permet d'ajouter une permission.
	 */
	@Override
	public Permission save(Permission permission) {
		return permissionrepository.save(permission);
	}
	
	/**
	 * Cette fonction permet de faire la recherche par mot clé et aussi par critère, 
	 * elle retourne une page de permissions.
	 */
	@Override
	public Page<Permission> search(String mc, int page, int size, String column) {
		//recevoire toute la liste
				permissionrepository.findAll();
				Pageable pageable = PageRequest.of(page, size);
				PermissionSpecification permissionSpecification = new PermissionSpecification();
				if(isNumeric(mc)) {
					permissionSpecification.add(new SearchCriteria(column, mc, SearchOperation.EQUAL));
				}else {
					permissionSpecification.add(new SearchCriteria(column, mc, SearchOperation.MATCH));
				}
				//pagination des resultats
				Page<Permission> msTitleList = permissionrepository.findAll(permissionSpecification, pageable);
		        return msTitleList;
	}
	
	/**
	 * Cette fonction permet de tester si une valeur est numérique ou pas.
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
	 * Cette fonction sert à modifier une permission en se basant sur son ID.
	 */
	@Override
	public Permission update(Long id, Permission permission) {
		permission.setId(id);
		return permissionrepository.save(permissionrepository.findById(id)
                .orElseThrow(EntityNotFoundException::new)
                .updateProperties(permission));
	}
 
	/**
	 * Cette fonction nous permet de retourner tout les permissions sous forme de pages.
	 */
	@Override
	public Page<Permission> findAllPageable(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return permissionrepository.findAll(pageable);
	}
	
	/**
	 * Cette fonction nous permet de supprimer une permission.
	 */
	@Override
	public Permission delete(Long id) {
		Permission permission = permissionrepository.findById(id).get();
		System.out.println(permission.toString());
		permission.setDeleted(true);
		return permissionrepository.save(permission);
	}

}
