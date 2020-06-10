package ma.dxc.service.audit;

import java.util.List;

import org.springframework.data.domain.Page;

import ma.dxc.model.Audit;

/**
 * Cette interface contient l'ensemble des fonctions qu'on va utiliser sur l'entité Audit.
 * @author dchaa
 *
 */
public interface AuditService {
	public List<Audit> findAll();
	public Page<Audit> findAllPageable(int page,int size);
	public Audit findOne(long id);
	public Audit save(Audit audit);
	public Page<Audit> search(String mc, int page, int size,String column);
	public Audit update(Long id,Audit audit);
	public Page<Audit> searchTwoKeywords(String mc1, String mc2, int page, int size, String column);
	public Long getMaxId();
}
