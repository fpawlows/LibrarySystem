package at.ac.fhsalzburg.swd.spring;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String eMail;
    private String tel;

    protected Customer() {}

    public Customer(String firstName, String lastName, String eMail, String tel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.tel = tel;
        
    }

    /*
    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
    */

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setEMail(String eMail)
	{
		this.eMail = eMail;
	}
	
	public String getEMail() {
		return this.eMail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
