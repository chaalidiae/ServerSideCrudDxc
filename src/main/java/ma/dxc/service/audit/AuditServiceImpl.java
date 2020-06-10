package ma.dxc.service.audit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ma.dxc.model.Audit;
import ma.dxc.repository.AuditRepository;
import ma.dxc.repository.specs.AuditSpecification;
import ma.dxc.repository.specs.SearchCriteria;
import ma.dxc.repository.specs.SearchOperation;

@Service
public class AuditServiceImpl implements AuditService {
	
	@Autowired
	AuditRepository auditRepository;
	
	/**
	 * Cette fonction permet de retourner une liste des utilisateurs.
	 */
	@Override
	public List<Audit> findAll() {
		return auditRepository.findAll();
	}
	
	/**
	 * Cette fonction permet de retrouver un audit en se basant sur son ID.
	 */
	@Override
	public Audit findOne(long id) {
		return auditRepository.getOne(id);
	}
	
	/**
	 * Cette fonction permet d'ajouter un audit.
	 */
	@Override
	public Audit save(Audit audit) {
		return auditRepository.save(audit);
	}
	
	@Override
	public Long getMaxId() {
		auditRepository.findAll();
		Pageable pageable = PageRequest.of(1, 1);
		AuditSpecification auditSpecification = new AuditSpecification();
		auditSpecification.add(new SearchCriteria("id", "id", SearchOperation.MAX));
		List<Audit> msTitleList = auditRepository.findAll(auditSpecification,pageable).getContent();
		return msTitleList.get(0).getId();
	}
	
	/**
	 * Cette fonction permet de faire la recherche sur les audits en se basant sur un critère et un mot clé.
	 */
	@Override
	public Page<Audit> search(String mc, int page, int size, String column) {
		auditRepository.findAll();
		Pageable pageable = PageRequest.of(page, size);
		AuditSpecification auditSpecification = new AuditSpecification();
		if(isNumeric(mc)) {
			auditSpecification.add(new SearchCriteria(column, mc, SearchOperation.EQUAL));
		}else if(column.equals("operation")){
			auditSpecification.add(new SearchCriteria(column, mc, SearchOperation.MATCH_OPERATION));
		}else {
			auditSpecification.add(new SearchCriteria(column, mc, SearchOperation.MATCH));
		}
		Page<Audit> msTitleList = auditRepository.findAll(auditSpecification,pageable);
		return msTitleList;
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Long d = Long.parseLong(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	@Override
	public Page<Audit> searchTwoKeywords(String mc1, String mc2, int page, int size, String column) {
		auditRepository.findAll();
		Pageable pageable = PageRequest.of(page, size);
		AuditSpecification auditSpecificationG = new AuditSpecification();
		AuditSpecification auditSpecificationL = new AuditSpecification();
		auditSpecificationG.add(new SearchCriteria(column, mc1, SearchOperation.GREATER_THAN_EQUAL));
		auditSpecificationL.add(new SearchCriteria(column, mc2, SearchOperation.LESS_THAN_EQUAL));
		Page<Audit> msTitleList = auditRepository.findAll(auditSpecificationL.and(auditSpecificationG),pageable);
		return msTitleList;
	}
	
	
	/**
	 * Cette fonction permet de faire la modification sur un audit.
	 */
	@Override
	public Audit update(Long id, Audit audit) {
		return auditRepository.saveAndFlush(audit);
	}
	
	/**
	 * Cette fonction permet de retourner tout les audits.
	 */
	@Override
	public Page<Audit> findAllPageable(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return auditRepository.findAll(pageable);
	}

}
