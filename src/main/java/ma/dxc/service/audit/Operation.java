package ma.dxc.service.audit;
/**
 * Cette enum contient l'ensemble des opérations sur l'ensemble des entités.
 * @author dchaa
 *
 */
public enum Operation {
	//User operations
	INSERTE_USER,
    UPDATE_USER,
    DELETE_USER,
    //Role operations
    INSERTE_ROLE,
    UPDATE_ROLE,
    DELETE_ROLE,
    //Permission operations
    INSERTE_PERMISSION,
    UPDATE_PERMISSION,
    DELETE_PERMISSION,
    //Contact operations
    INSERTE_CONTACT,
    UPDATE_CONTACT,
    DELETE_CONTACT,
	}