package ma.dxc.orchestration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ma.dxc.dto.AppUserDTO;
import ma.dxc.model.AppUser;
import ma.dxc.service.UserServiceImpl;

@Service
public class AppUserOrchestration {
	
	/**
	 * On déclare un objet de la classe AppUserServiceImpl qui va lui meme faire appel à la couche DAO
	 * afin d'interagir avec la base de données.
	 */
	@Autowired
	private UserServiceImpl userservice;
	
	/**
	 * cette fonction nous retourne la liste des AppUsers.
	 * @return
	 */
	public List<AppUserDTO> getAppUsers(){
		return AppUserMapper.INSTANCE.toAppUserDTOs(userservice.findAll());
	}
	
	/**
	 * cette fonction nous retourne le AppUser qui correspond à l'ID de l'entrée
	 * @param id
	 * @return
	 */
	public AppUserDTO getAppUser(Long id){
		return AppUserMapper.INSTANCE.toAppUserDTO(userservice.findOne(id));
	}
	
	/**
	 * cette fonction prend une AppUser comme argument et puis elle le stock dans la base de donnée.
	 * @param AppUser
	 * @return
	 */
	public AppUserDTO saveAppUser(AppUserDTO UserDTO){
		AppUser User = AppUserMapper.INSTANCE.toAppUser(UserDTO);
		User = userservice.save(User);
		return AppUserMapper.INSTANCE.toAppUserDTO(User);
	}
	
	/**
	 * cette fonction fait la mise à jour d'une AppUser
	 * @param id
	 * @param AppUser
	 * @return
	 */
	public AppUserDTO updateAppUser(Long id, AppUserDTO UserDTO){
		AppUser User = AppUserMapper.INSTANCE.toAppUser(UserDTO);
		UserDTO =  AppUserMapper.INSTANCE.toAppUserDTO(userservice.update(id, User));
		return UserDTO;
	}
	
	/**
	 * cette fonction fait la recherche sur un ou plusieurs AppUsers selon un mot clé saisie, on precise aussi les
	 * paramètres size et page.
	 * @param mc
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<AppUserDTO> searchAppUser( String mc,int page,int size,String column){
		Page<AppUser> users = userservice.search(mc, page, size, column);
		List<AppUserDTO> userDTOs = AppUserMapper.INSTANCE.toAppUserDTOs(users.getContent());
		return new PageImpl<>(userDTOs,PageRequest.of(page, size),users.getTotalElements());
	}
	
	
	public Page<AppUserDTO> getPageOfUsers(int page,int size){
		Page<AppUser> users = userservice.findAllPageable(page, size);
		Page<AppUserDTO> userDTOs = AppUserMapper.INSTANCE.toAppUserDTOsPageable(users);
		return userDTOs;
	}
	
	public Boolean deleteUser(Long id){
		userservice.delete(id);
		return true;
	}
	

}
