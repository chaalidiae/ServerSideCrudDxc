package ma.dxc.orchestration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ma.dxc.dto.AppRoleDTO;
import ma.dxc.model.AppRole;
import ma.dxc.service.RoleServiceImpl;

@Service
public class AppRoleOrchestration {
	
	/**
	 * On déclare un objet de la classe AppRoleServiceImpl qui va lui meme faire appel à la couche DAO
	 * afin d'interagir avec la base de données.
	 */
	@Autowired
	private RoleServiceImpl roleservice;
	
	/**
	 * cette fonction nous retourne la liste des AppRoles.
	 * @return
	 */
	public List<AppRoleDTO> getAppRoles(){
		return AppRoleMapper.INSTANCE.toAppRoleDTOs(roleservice.findAll());
	}
	
	/**
	 * cette fonction nous retourne le AppRole qui correspond à l'ID de l'entrée
	 * @param id
	 * @return
	 */
	public AppRoleDTO getAppRole(Long id){
		return AppRoleMapper.INSTANCE.toAppRoleDTO(roleservice.findOne(id));
	}
	
	/**
	 * cette fonction prend une AppRole comme argument et puis elle le stock dans la base de donnée.
	 * @param AppRole
	 * @return
	 */
	public AppRoleDTO saveAppRole(AppRoleDTO roleDTO){
		AppRole role = AppRoleMapper.INSTANCE.toAppRole(roleDTO);
		role = roleservice.save(role);
		return AppRoleMapper.INSTANCE.toAppRoleDTO(role);
	}
	
	/**
	 * cette fonction fait la mise à jour d'une AppRole
	 * @param id
	 * @param AppRole
	 * @return
	 */
	public AppRoleDTO updateAppRole(Long id, AppRoleDTO roleDTO){
		AppRole role = AppRoleMapper.INSTANCE.toAppRole(roleDTO);
		roleDTO =  AppRoleMapper.INSTANCE.toAppRoleDTO(roleservice.update(id, role));
		return roleDTO;
	}
	
	/**
	 * cette fonction fait la recherche sur un ou plusieurs AppRoles selon un mot clé saisie, on precise aussi les
	 * paramètres size et page.
	 * @param mc
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<AppRoleDTO> searchAppRole( String mc,int page,int size,String column){
		Page<AppRole> roles = roleservice.search(mc, page, size, column);
		List<AppRoleDTO> roleDTOs = AppRoleMapper.INSTANCE.toAppRoleDTOs(roles.getContent());
		return new PageImpl<>(roleDTOs,PageRequest.of(page, size),roles.getTotalElements());
	}
	
	
	public Page<AppRoleDTO> getPageOfRoles(int page,int size){
		Page<AppRole> roles = roleservice.findAllPageable(page, size);
		Page<AppRoleDTO> roleDTOs = AppRoleMapper.INSTANCE.toAppRoleDTOsPageable(roles);
		return roleDTOs;
	}
	
	/**
	 * cette fonction nous retourne le AppRole qui correspond à l'roleName de l'entrée
	 * @param id
	 * @return
	 */
	public AppRoleDTO getAppRoleByRoleName(String roleName){
		return AppRoleMapper.INSTANCE.toAppRoleDTO(roleservice.getRoleByRoleName(roleName));
	}
	
	public Boolean deleteRole(Long id){
		roleservice.delete(id);
		return true;
	}

}
