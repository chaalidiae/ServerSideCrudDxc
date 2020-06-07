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
import ma.dxc.model.AppUser;
import ma.dxc.repository.UserRepository;
import ma.dxc.repository.specs.SearchCriteria;
import ma.dxc.repository.specs.SearchOperation;
import ma.dxc.repository.specs.UserSpecification;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleServiceImpl appRoleServiceImpl;

	@Override
	public List<AppUser> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public AppUser findOne(long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public AppUser save(AppUser appUser) {
		// TODO Auto-generated method stub
		return userRepository.save(appUser);
	}

	@Override
	public Page<AppUser> search(String mc, int page, int size, String column) {
		//recevoire toute la liste
		userRepository.findAll();
		Pageable pageable = PageRequest.of(page, size);
		UserSpecification userSpecification = new UserSpecification();
		if(column.equals("roles")){
			List<AppRole> appRoles = appRoleServiceImpl.search(mc, page, size, "roleName").getContent();
			AppRole appRole = appRoles.get(0);
			List<AppUser> allUsers = userRepository.findAll();
			List<AppUser> users = new ArrayList<AppUser>();
			for (AppUser user : allUsers) {
				if(user.getRoles().contains(appRole)) {
					users.add(user);
				}
			}
			Page<AppUser>appUserPage = new PageImpl<AppUser>(users, pageable, users.size());
			return appUserPage;
		}else if(isNumeric(mc)) {
			userSpecification.add(new SearchCriteria(column, mc, SearchOperation.EQUAL));
		}else {
			userSpecification.add(new SearchCriteria(column, mc, SearchOperation.MATCH));
		}
		//pagination des resultats
		Page<AppUser> msTitleList = userRepository.findAll(userSpecification,pageable);
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
	public AppUser update(Long id, AppUser appUser) {
		// TODO Auto-generated method stub
		appUser.setId(id);
		return userRepository.save(userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new)
                .updateProperties(appUser));
	}

	@Override
	public Page<AppUser> findAllPageable(int page, int size) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(page, size);
		return userRepository.findAll(pageable);
	}

}
