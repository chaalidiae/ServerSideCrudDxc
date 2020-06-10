package ma.dxc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ma.dxc.model.Contact;

/**
 * Cet interface hérite de JpaRepository qui contient des fonctions prédéfinies qui sert à effectuer des actions sur la base
 * de données telles que (save,findOne,findAll...)
 * @author dchaa
 *
 */
public interface ContactRepository extends JpaRepository<Contact, Long>,JpaSpecificationExecutor<Contact> {
	
	
}
