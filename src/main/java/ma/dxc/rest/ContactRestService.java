package ma.dxc.rest;

import java.text.ParseException;
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

import ma.dxc.dto.ContactDTO;
import ma.dxc.orchestration.ContactOrchestration;

/**
 * C'est la classe responsable de l'API Rest qui joue le role de l'intermédiaire entre la partie backend et la partie 
 * frontend.
 * @author dchaa
 *
 */
@RestController
@CrossOrigin("*")
@PreAuthorize("isAuthenticated()") 
public class ContactRestService {
	
	/**
	 * On déclare un objet de la classe ContactOrchestration qui va lui meme faire appel à la couche ContactServiceImpl
	 * pour alimenter les fonction ci dessous
	 */
	@Autowired
	private ContactOrchestration contactOrchestration;
	
	
	
	
	
	/**
	 * cette fonction nous retourne la liste des contacts.
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/contacts")
	public List<ContactDTO> getContacts(){
		return contactOrchestration.getContacts();
	}
	
	/**
	 * cette fonction nous retourne le contact qui correspond à l'ID de l'entrée
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/contacts/{id}")
	public ContactDTO getContact(@PathVariable Long id){
		return contactOrchestration.getContact(id);
	}
	
	/**
	 * cette fonction prend un contact comme argument et puis elle le stock dans la base de donnée.
	 * @param contact
	 * @return
	 */
	@PreAuthorize("hasAuthority('ADD')")
	@PostMapping(value="/contacts")
	public ContactDTO saveContact(@RequestBody ContactDTO contactDTO){
		return contactOrchestration.saveContact(contactDTO);
	}
	
	/**
	 * cette fonction supprime le contact qui correspond à l'id de l'entrée
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('DELETE')")
	@DeleteMapping(value="/contacts/{id}")
	public Boolean deleteContact(@PathVariable Long id){
		contactOrchestration.deleteContact(id);
		return true;
	}
	
	/**
	 * cette fonction fait la mise à jour d'un contact
	 * @param id
	 * @param contact
	 * @return
	 */
	@PreAuthorize("hasAuthority('UPDATE')")
	@PutMapping(value="/contacts/{id}")
	public ContactDTO updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO){
		return contactOrchestration.updateContact(id, contactDTO);
	}
	
	/**
	 * cette fonction fait la recherche sur un ou plusieurs contacts selon un mot clé saisie, on precise aussi les
	 * paramètres size et page.
	 * @param mc
	 * @param page
	 * @param size
	 * @return
	 */
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/searchForContactsWithOnekeyword")
	public Page<ContactDTO> searchForContactsWithOnekeyword( 
			@RequestParam(name="keyword",defaultValue = "chaali")String keyword,
			@RequestParam(name="page",defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue = "5")int size,
			@RequestParam(name="column",defaultValue = "nom")String column
			){
		return contactOrchestration.searchContact(keyword, page, size, column);
	}
	
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/searchForContactsWithTwoKeywords")
	public Page<ContactDTO> searchForContactsWithTwoKeywords( 
			@RequestParam(name="keyword1",defaultValue = "1000-01-01")String keyword1,
			@RequestParam(name="keyword2",defaultValue = "9999-12-31")String keyword2,
			@RequestParam(name="page",defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue = "5")int size,
			@RequestParam(name="column",defaultValue = "nom")String column
			) throws ParseException{
		return contactOrchestration.searchTwoKeywords(keyword1, keyword2, page, size, column);
	}
	
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping(value="/getPageOfContacts")
	public Page<ContactDTO> getPageOfContacts( 
			@RequestParam(name="page",defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue = "5")int size
			){
		return contactOrchestration.getPageofContacts(page, size);
	}
	
	
	

}
