package ma.dxc.repository.specs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.springframework.data.jpa.domain.Specification;
import ma.dxc.model.Audit;
import ma.dxc.service.audit.Operation;

public class AuditSpecification implements Specification<Audit> {
	
	
	private static final long serialVersionUID = 1L;
	private List<SearchCriteria> list;
	
	public AuditSpecification() {
		super();
		this.list = new ArrayList<>();
	}
	
	public void add(SearchCriteria criteria) {
        list.add(criteria);
	}
	/**
	 * Cette fonction nous permet de créer une nouvelle liste de prédicats, puis on ajoute critéria à cette liste,
	 * puis on traite tout les cas possibles.
	 */
	@Override
	public Predicate toPredicate(Root<Audit> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		//creation d'une nouvelle predicate list
        List<Predicate> predicates = new ArrayList<>();

        //ajouter criteria à predicates
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
            	
				try {
					String dateValue = criteria.getValue().toString();
					Date date1 = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
	                predicates.add(builder.greaterThan(
	                		root.get(criteria.getKey()), date1));
				} catch (ParseException e) {
					e.printStackTrace();
				}  
                
                
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
				try {
					String dateValue = criteria.getValue().toString();
					Date date1 = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
	                predicates.add(builder.lessThan(
	                		root.get(criteria.<Date>getKey()), date1));
				} catch (ParseException e) {
					e.printStackTrace();
				}  
                
                
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
            	try {
					String dateValue = criteria.getValue().toString();
					Date date1 = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
	                predicates.add(builder.greaterThanOrEqualTo(
	                		root.get(criteria.getKey()), date1));
				} catch (ParseException e) {
					e.printStackTrace();
				}
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
            	try {
					String dateValue = criteria.getValue().toString();
					System.out.println("dateValue : "+dateValue);
					Date date1 = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
	                predicates.add(builder.lessThanOrEqualTo(
	                		root.get(criteria.<Date>getKey()), date1));
				} catch (ParseException e) {
					e.printStackTrace();
				}
            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                predicates.add(builder.notEqual(
                        root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(
                        root.get(criteria.getKey()), Long.parseLong(criteria.getValue().toString())));
            }else if (criteria.getOperation().equals(SearchOperation.MATCH_OPERATION)) {
                predicates.add(builder.equal(
                        root.get(criteria.getKey()), Operation.valueOf(criteria.getValue().toString())));
            }else if (criteria.getOperation().equals(SearchOperation.IS_NOT_EMPTY)) {
                predicates.add(builder.isNotEmpty(
                		root.get(criteria.getKey())));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase()));
            } else if (criteria.getOperation().equals(SearchOperation.MAX)) {
                predicates.add(
                		builder.max(
                					root.get(criteria.getKey())
                					).isNotNull()
                		);
                
            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
            }
            
        }

        return builder.and(predicates.toArray(new Predicate[0]));
	}

}
