package at.ac.fhsalzburg.swd.spring.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Qualifier;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String eMail;
    private String tel;
    private Long credit;

    protected Customer() {}

    public Customer(String firstName, String lastName, String eMail, String tel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.tel = tel;
        this.setCredit((long) 0);
        
    }

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

	public Long getCredit() {
		return credit;
	}

	public void setCredit(Long credit) {
		this.credit = credit;
	}
	
	
}
