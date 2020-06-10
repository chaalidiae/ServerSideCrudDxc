package ma.dxc.repository.specs;
/**
 * Cette enum représente l'ensemble des opérations de recherche.
 * @author dchaa
 *
 */
public enum SearchOperation {
	GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_EQUAL,
    LESS_THAN_EQUAL,
    NOT_EQUAL,
    EQUAL,
    MATCH,
    MATCH_START,
    MATCH_END,
    IN,
    NOT_IN,
    IS_NOT_EMPTY,
    MATCH_OPERATION,
    EQUAL_ROLENAME,
    MAX
}
