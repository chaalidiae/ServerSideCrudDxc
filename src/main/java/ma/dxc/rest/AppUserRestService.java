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

import ma.dxc.dto.AppUserDTO;
import ma.dxc.orchestration.AppUserOrchestration;

/**
 * C'est la classe responsable de l'API Rest qui joue le role de l'intermédiaire entre la partie backend et la partie 
 * frontend.
 * @author dchaa
 *
 */
@RestController
@CrossOrigin("*")
@PreAuthorize("isAuthenticated()") 
public class AppUserRestService {
	
	/**
	 * On déclare un objet de la classe AppUserOrchestration qui va lui meme faire appel à la couche AppUserServiceImpl
	 * pour alimenter les fonction ci dessous
	 */
	@Autowired
	private AppUserOrchestration AppUserOrchestration;
	
	
	
	
	
	/**
	 * cette fonction nous retourne la liste des AppUsers.
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/users")
	public List<AppUserDTO> getAppUsers(){
		System.out.println(AppUserOrchestration.getAppUsers());
		return AppUserOrchestration.getAppUsers();
	}
	
	/**
	 * cette fonction nous retourne le AppUser qui correspond à l'ID de l'entrée
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/users/{id}")
	public AppUserDTO getAppUser(@PathVariable Long id){
		return AppUserOrchestration.getAppUser(id);
	}
	
	/**
	 * cette fonction prend un AppUser comme argument et puis elle le stock dans la base de donnée.
	 * @param AppUser
	 * @return
	 */
	@PreAuthorize("hasAuthority('ADD')")
	@PostMapping(value="/users")
	public AppUserDTO saveAppUser(@RequestBody AppUserDTO appUserDTO){
		return AppUserOrchestration.saveAppUser(appUserDTO);
	}
	
	/**
	 * cette fonction fait la mise à jour d'un AppUser
	 * @param id
	 * @param AppUser
	 * @return
	 */
	@PreAuthorize("hasAuthority('UPDATE')")
	@PutMapping(value="/users/{id}")
	public AppUserDTO updateAppUser(@PathVariable Long id, @RequestBody AppUserDTO appUserDTO){
		return AppUserOrchestration.updateAppUser(id, appUserDTO);
	}
	
	/**
	 * cette fonction fait la recherche sur un ou plusieurs AppUsers selon un mot clé saisie, on precise aussi les
	 * paramètres size et page.
	 * @param mc
	 * @param page
	 * @param size
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/searchForUsers")
	public Page<AppUserDTO> searchForUsers( 
			@RequestParam(name="keyword",defaultValue = "")String keyword,
			@RequestParam(name="page",defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue = "5")int size,
			@RequestParam(name="column")String column
			){
		return AppUserOrchestration.searchAppUser(keyword, page, size, column);
	}
	
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/getPageOfUsers")
	public Page<AppUserDTO> getPageOfUsers( 
			@RequestParam(name="page",defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue = "5")int size
			){
		return AppUserOrchestration.getPageOfUsers(page, size);
	}
	
	/**
	 * cette fonction supprime le contact qui correspond à l'id de l'entrée
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('DELETE')")
	@DeleteMapping(value="/users/{id}")
	public Boolean deleteUser(@PathVariable Long id){
		AppUserOrchestration.deleteUser(id);
		return true;
	}
	
	
	

}
