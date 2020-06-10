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

import ma.dxc.dto.AppRoleDTO;
import ma.dxc.orchestration.AppRoleOrchestration;

/**
 * C'est la classe responsable de l'API Rest qui joue le role de l'intermédiaire entre la partie backend et la partie 
 * frontend.
 * @author dchaa
 *
 */
@RestController
@CrossOrigin("*")
@PreAuthorize("isAuthenticated()") 
public class AppRoleRestService {
	
	/**
	 * On déclare un objet de la classe AppRoleOrchestration qui va lui meme faire appel à la couche AppRoleServiceImpl
	 * pour alimenter les fonction ci dessous
	 */
	@Autowired
	private AppRoleOrchestration AppRoleOrchestration;
	
	
	
	
	
	
	/**
	 * cette fonction nous retourne la liste des AppRoles.
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/roles")
	public List<AppRoleDTO> getAppRoles(){
		return AppRoleOrchestration.getAppRoles();
	}
	
	/**
	 * cette fonction nous retourne le AppRole qui correspond à l'ID de l'entrée
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/roles/{id}")
	public AppRoleDTO getAppRole(@PathVariable Long id){
		return AppRoleOrchestration.getAppRole(id);
	}
	
	/**
	 * cette fonction nous retourne le AppRole qui correspond à l'roleName de l'entrée
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/getRoleByroleName")
	public AppRoleDTO getAppRoleByRoleName(@RequestParam(name="roleName",defaultValue = "")String roleName){
		return AppRoleOrchestration.getAppRoleByRoleName(roleName);
	}
	
	/**
	 * cette fonction prend un AppRole comme argument et puis elle le stock dans la base de donnée.
	 * @param AppRole
	 * @return
	 */
	@PreAuthorize("hasAuthority('ADD')")
	@PostMapping(value="/roles")
	public AppRoleDTO saveAppRole(@RequestBody AppRoleDTO appRoleDTO){
		return AppRoleOrchestration.saveAppRole(appRoleDTO);
	}
	
	/**
	 * cette fonction fait la mise à jour d'un AppRole
	 * @param id
	 * @param AppRole
	 * @return
	 */
	@PreAuthorize("hasAuthority('UPDATE')")
	@PutMapping(value="/roles/{id}")
	public AppRoleDTO updateAppRole(@PathVariable Long id, @RequestBody AppRoleDTO appRoleDTO){
		return AppRoleOrchestration.updateAppRole(id, appRoleDTO);
	}
	
	/**
	 * cette fonction fait la recherche sur un ou plusieurs AppRoles selon un mot clé saisie, on precise aussi les
	 * paramètres size et page.
	 * @param mc
	 * @param page
	 * @param size
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/searchForRoles")
	public Page<AppRoleDTO> searchForRoles( 
			@RequestParam(name="keyword",defaultValue = "")String keyword,
			@RequestParam(name="page",defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue = "5")int size,
			@RequestParam(name="column")String column
			){
		return AppRoleOrchestration.searchAppRole(keyword, page, size, column);
	}
	
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/getPageOfRoles")
	public Page<AppRoleDTO> getPageOfRoles( 
			@RequestParam(name="page",defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue = "5")int size
			){
		return AppRoleOrchestration.getPageOfRoles(page, size);
	}
	
	@PreAuthorize("hasAuthority('DELETE')")
	@DeleteMapping(value="/roles/{id}")
	public Boolean deleteRole(@PathVariable Long id){
		AppRoleOrchestration.deleteRole(id);
		return true;
	}
	
	

}
