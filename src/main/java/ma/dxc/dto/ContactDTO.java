package ma.dxc.dto;

import java.util.Date;

/**
 * Cette classe est identique à Contact, mais cette dernière ne peut pas communiquer avec la couche REST, donc, 
 * on ajoute une autre classe ContactDTO qui va nous rendre ce service.
 * @author dchaa
 *
 */
public class ContactDTO {
	private Long id;
	private String nom;
	private String prenom;
	private Date dateNaissance;
	private String email;
	private String tel;
	private String photo;
	private boolean deleted = false;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public ContactDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ContactDTO(String nom, String prenom, Date dateNaissance, String email, String tel, String photo,
			boolean deleted) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.email = email;
		this.tel = tel;
		this.photo = photo;
		this.deleted = deleted;
	}
	@Override
	public String toString() {
		return "ContactDTO [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance
				+ ", email=" + email + ", tel=" + tel + ", photo=" + photo + ", deleted=" + deleted + "]";
	}
	
	

}
