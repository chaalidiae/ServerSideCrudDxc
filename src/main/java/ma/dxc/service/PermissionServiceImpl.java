package ma.dxc.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.dxc.model.Permission;
import ma.dxc.repository.PermissionRepository;
import ma.dxc.repository.specs.PermissionSpecification;
import ma.dxc.repository.specs.SearchCriteria;
import ma.dxc.repository.specs.SearchOperation;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionRepository permissionrepository ;

	@Override
	public List<Permission> findAll() {
		// TODO Auto-generated method stub
		return permissionrepository.findAll();
	}

	@Override
	public Permission findOne(long id) {
		// TODO Auto-generated method stub
		return permissionrepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Permission save(Permission permission) {
		// TODO Auto-generated method stub
		return permissionrepository.save(permission);
	}

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

	@Override
	public Permission update(Long id, Permission permission) {
		// TODO Auto-generated method stub
		permission.setId(id);
		return permissionrepository.save(permissionrepository.findById(id)
                .orElseThrow(EntityNotFoundException::new)
                .updateProperties(permission));
	}

	@Override
	public Page<Permission> findAllPageable(int page, int size) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(page, size);
		return permissionrepository.findAll(pageable);
	}

}
