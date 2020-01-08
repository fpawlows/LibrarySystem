package at.ac.fhsalzburg.swd.spring;

public class CustomerForm {
 
    private String firstName;
    private String lastName;
    private String eMail;
    private String tel;
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
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