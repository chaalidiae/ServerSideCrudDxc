package ma.dxc.orchestration;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ma.dxc.dto.ContactDTO;
import ma.dxc.model.Contact;
import ma.dxc.service.ContactServiceImpl;

@Service
public class ContactOrchestration {
	CycleAvoidingMappingContext  context = new CycleAvoidingMappingContext();
	/**
	 * On déclare un objet de la classe ContactServiceImpl qui va lui meme faire appel à la couche DAO
	 * afin d'interagir avec la base de données.
	 */
	@Autowired
	private ContactServiceImpl contactservice;
	
	/**
	 * cette fonction nous retourne la liste des contacts.
	 * @return
	 */
	public List<ContactDTO> getContacts(){
		return ContactMapper.INSTANCE.toContactDTOs(contactservice.findAll(),context);
	}
	
	/**
	 * cette fonction nous retourne le contact qui correspond à l'ID de l'entrée
	 * @param id
	 * @return
	 */
	public ContactDTO getContact(Long id){
		return ContactMapper.INSTANCE.toContactDTO(contactservice.findOne(id),context);
	}
	
	/**
	 * cette fonction prend un contact comme argument et puis elle le stock dans la base de donnée.
	 * @param contactDTO
	 * @return
	 */
	public ContactDTO saveContact(ContactDTO contactDTO){
		Contact contact = ContactMapper.INSTANCE.toContact(contactDTO,context);
		contact = contactservice.save(contact);
		return ContactMapper.INSTANCE.toContactDTO(contact,context);
	}
	
	/**
	 * cette fonction supprime le contact qui correspond à l'id de l'entrée
	 * @param id
	 * @return
	 */
	public Boolean deleteContact(Long id){
		contactservice.delete(id);
		return true;
	}
	
	/**
	 * cette fonction fait la mise à jour d'un contact
	 * @param id
	 * @param contactDTO
	 * @return
	 */
	public ContactDTO updateContact(Long id, ContactDTO contactDTO){
		Contact contact = ContactMapper.INSTANCE.toContact(contactDTO,context);
		contactDTO =  ContactMapper.INSTANCE.toContactDTO(contactservice.update(id, contact),context);
		return contactDTO;
	}
	
	/**
	 * cette fonction fait la recherche sur un ou plusieurs contacts selon un mot clé saisie, on precise aussi les
	 * paramètres size et page.
	 * @param mc
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<ContactDTO> searchContact( String mc,int page,int size,String column){
		Page<Contact> contacts = contactservice.search(mc, page, size, column);
		List<ContactDTO> contactDTOs = ContactMapper.INSTANCE.toContactDTOs(contacts.getContent(),context);
		return new PageImpl<>(contactDTOs,PageRequest.of(page, size),contacts.getTotalElements());
	}
	
	public Page<ContactDTO> searchTwoKeywords( String mc1,String mc2,int page,int size,String column) throws ParseException{
		Page<Contact> contacts = contactservice.searchTwoKeywords(mc1, mc2, page, size, column);
		List<ContactDTO> contactDTOs = ContactMapper.INSTANCE.toContactDTOs(contacts.getContent(),context);
		return new PageImpl<>(contactDTOs,PageRequest.of(page, size),contacts.getTotalElements());
	}
	
	
	public Page<ContactDTO> getPageofContacts(int page,int size){
		Page<Contact> contacts = contactservice.findAllPageable(page, size);
		Page<ContactDTO> contactDTOs = ContactMapper.INSTANCE.toContactDTOsPageable(contacts,context);
		return contactDTOs;
	}
	
	

}
