package ma.dxc.orchestration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ma.dxc.dto.PermissionDTO;
import ma.dxc.model.Permission;
import ma.dxc.service.PermissionServiceImpl;

@Service
public class PermissionOrchestration {
	
	/**
	 * On déclare un objet de la classe PermissionServiceImpl qui va lui meme faire appel à la couche DAO
	 * afin d'interagir avec la base de données.
	 */
	@Autowired
	private PermissionServiceImpl Permissionservice;
	
	/**
	 * cette fonction nous retourne la liste des Permissions.
	 * @return
	 */
	public List<PermissionDTO> getPermissions(){
		return PermissionMapper.INSTANCE.toPermissionDTOs(Permissionservice.findAll());
	}
	
	/**
	 * cette fonction nous retourne le Permission qui correspond à l'ID de l'entrée
	 * @param id
	 * @return
	 */
	public PermissionDTO getPermission(Long id){
		return PermissionMapper.INSTANCE.toPermissionDTO(Permissionservice.findOne(id));
	}
	
	/**
	 * cette fonction prend une permission comme argument et puis elle le stock dans la base de donnée.
	 * @param Permission
	 * @return
	 */
	public PermissionDTO savePermission(PermissionDTO permissionDTO){
		Permission permission = PermissionMapper.INSTANCE.toPermission(permissionDTO);
		permission = Permissionservice.save(permission);
		return PermissionMapper.INSTANCE.toPermissionDTO(permission);
	}
	
	/**
	 * cette fonction fait la mise à jour d'une Permission
	 * @param id
	 * @param Permission
	 * @return
	 */
	public PermissionDTO updatePermission(Long id, PermissionDTO permissionDTO){
		Permission permission = PermissionMapper.INSTANCE.toPermission(permissionDTO);
		permissionDTO =  PermissionMapper.INSTANCE.toPermissionDTO(Permissionservice.update(id, permission));
		return permissionDTO;
	}
	
	/**
	 * cette fonction fait la recherche sur un ou plusieurs permissions selon un mot clé saisie, on precise aussi les
	 * paramètres size et page.
	 * @param mc
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<PermissionDTO> searchPermission( String mc,int page,int size,String column){
		Page<Permission> permissions = Permissionservice.search(mc, page, size, column);
		List<PermissionDTO> permissionDTOs = PermissionMapper.INSTANCE.toPermissionDTOs(permissions.getContent());
		return new PageImpl<>(permissionDTOs,PageRequest.of(page, size),permissions.getTotalElements());
	}
	
	public Page<PermissionDTO> getPageOfPermissions(int page,int size){
		Page<Permission> permissions = Permissionservice.findAllPageable(page, size);
		Page<PermissionDTO> permissionDTOs = PermissionMapper.INSTANCE.toPermissionDTOsPageable(permissions);
		return permissionDTOs ;
	}
	
	public Boolean deletePermission(Long id){
		Permissionservice.delete(id);
		return true;
	}
	

}
