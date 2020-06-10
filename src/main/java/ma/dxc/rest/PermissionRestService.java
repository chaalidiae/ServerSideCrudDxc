package ma.dxc.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.dxc.dto.PermissionDTO;
import ma.dxc.orchestration.PermissionOrchestration;


/**
 * C'est la classe responsable de l'API Rest qui joue le role de l'intermédiaire entre la partie backend et la partie 
 * frontend.
 * @author dchaa
 *
 */
@RestController
@CrossOrigin("*")
@PreAuthorize("isAuthenticated()") 
public class PermissionRestService {
	
	/**
	 * On déclare un objet de la classe PermissionOrchestration qui va lui meme faire appel à la couche PermissionServiceImpl
	 * pour alimenter les fonction ci dessous
	 */
	@Autowired
	private PermissionOrchestration PermissionOrchestration;
	
	
	
	
	
	/**
	 * cette fonction nous retourne la liste des Permissions.
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/permissions")
	public List<PermissionDTO> getPermissions(){
		return PermissionOrchestration.getPermissions();
	}
	
	/**
	 * cette fonction nous retourne le Permission qui correspond à l'ID de l'entrée
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/permissions/{id}")
	public PermissionDTO getPermission(@PathVariable Long id){
		return PermissionOrchestration.getPermission(id);
	}
	
	/**
	 * cette fonction prend un Permission comme argument et puis elle le stock dans la base de donnée.
	 * @param Permission
	 * @return
	 */
	@PreAuthorize("hasAuthority('ADD')")
	@PostMapping(value="/permissions")
	public PermissionDTO savePermission(@RequestBody PermissionDTO PermissionDTO){
		return PermissionOrchestration.savePermission(PermissionDTO);
	}
	
	/**
	 * cette fonction fait la mise à jour d'un Permission
	 * @param id
	 * @param Permission
	 * @return
	 */
	@PreAuthorize("hasAuthority('UPDATE')")
	@PutMapping(value="/permissions/{id}")
	public PermissionDTO updatePermission(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO){
		return PermissionOrchestration.updatePermission(id, permissionDTO);
	}
	
	/**
	 * cette fonction fait la recherche sur un ou plusieurs Permissions selon un mot clé saisie, on precise aussi les
	 * paramètres size et page.
	 * @param mc
	 * @param page
	 * @param size
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/searchForPermissions")
	public Page<PermissionDTO> searchForPermissions( 
			@RequestParam(name="keyword",defaultValue = "")String keyword,
			@RequestParam(name="page",defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue = "5")int size,
			@RequestParam(name="column")String column
			){
		return PermissionOrchestration.searchPermission(keyword, page, size, column);
	}
	
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/getPageOfPermissions")
	public Page<PermissionDTO> getPageOfPermissions( 
			@RequestParam(name="page",defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue = "5")int size
			){
		return PermissionOrchestration.getPageOfPermissions(page, size);
	}
	
	@PreAuthorize("hasAuthority('DELETE')")
	@DeleteMapping(value="/permissions/{id}")
	public Boolean deletePermission(@PathVariable Long id){
		PermissionOrchestration.deletePermission(id);
		return true;
	}

}
