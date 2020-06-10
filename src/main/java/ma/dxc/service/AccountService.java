package ma.dxc.service;

import ma.dxc.model.AppRole;
import ma.dxc.model.AppUser;

/**
 * Cette interface contient les différents fonctions pour l'ajout des utilisateurs, des roles, ainsi que 
 * pour l'ajout d'un role à un utilisateur.
 * @author dchaa
 *
 */
public interface AccountService {
	public AppUser saveUser(AppUser user);
	public AppRole saveRole(AppRole role);
	public void addRoleToUser(String username, String roleName);
	public AppUser findUserByUsername(String username);
}
