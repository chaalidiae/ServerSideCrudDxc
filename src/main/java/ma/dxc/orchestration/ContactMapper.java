package ma.dxc.orchestration;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import ma.dxc.dto.ContactDTO;
import ma.dxc.model.Contact;

@Mapper
public interface ContactMapper {
	@InheritInverseConfiguration
	public ContactDTO toContactDTO(Contact contact, @Context CycleAvoidingMappingContext context);
	@InheritInverseConfiguration
	public List<ContactDTO> toContactDTOs(List<Contact> contacts, @Context CycleAvoidingMappingContext context);
	
	public Contact toContact(ContactDTO contactDTO, @Context CycleAvoidingMappingContext context);
	@InheritInverseConfiguration
	public Page<ContactDTO> toContactDTOsPageable(Page<Contact> contacts, @Context CycleAvoidingMappingContext context);
	
	ContactMapper INSTANCE = Mappers.getMapper( ContactMapper.class );
	
	
}
