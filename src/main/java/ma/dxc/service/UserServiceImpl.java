package ma.dxc.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	BCryptPasswordEncoder encoder;
 
	/**
	 * Cette fonction permet de récuperer une liste des utilisateurs.
	 */
	@Override
	public List<AppUser> findAll() {
		return userRepository.findAll();
	}
 
	/**
	 * Cette fonction permet de récupérer un utilisateur en se basant sur son ID.
	 */
	@Override
	public AppUser findOne(long id) {
		return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	 /**
	  * Cette fonction permet d'ajouter un utilisateur.
	  */
	@Override
	public AppUser save(AppUser appUser) {
		String pw = appUser.getPassword();
		String encodedpw = encoder.encode(pw);
		appUser.setPassword(encodedpw);
		return userRepository.save(appUser);
	}
	
	/**
	 * Cette fonction permet de faire la recherche sur les utilisateur en se basant sur un mot clé et les critères.
	 */
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
	/**
	 * Cette fonction permet de faire la modification des utilisateurs.
	 */
	@Override
	public AppUser update(Long id, AppUser appUser) {
		appUser.setId(id);
		return userRepository.save(userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new)
                .updateProperties(appUser));
	}
 
	/**
	 * Cette fonction permet de retourner tout les utilisateurs.
	 */
	@Override
	public Page<AppUser> findAllPageable(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return userRepository.findAll(pageable);
	}

	/**
	 * Cette fonction permet de supprimer un utilisateur.
	 */
	@Override
	public AppUser delete(Long id) {
		AppUser user = userRepository.findById(id).get();
		System.out.println(user.toString());
		user.setDeleted(true);
		return userRepository.save(user);
	}

}
