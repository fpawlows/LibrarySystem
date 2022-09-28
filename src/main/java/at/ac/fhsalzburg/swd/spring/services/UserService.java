package at.ac.fhsalzburg.swd.spring.services;


import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import at.ac.fhsalzburg.swd.spring.dao.User;
import at.ac.fhsalzburg.swd.spring.dao.UserRepository;
import at.ac.fhsalzburg.swd.spring.security.DemoPrincipal;
import at.ac.fhsalzburg.swd.spring.security.TokenService;


@Service
public class UserService implements UserServiceInterface {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private TokenService tokenService; // autowired using setter/field injection
	
    public final static String DEFAULT_ROLE = "USER";
	
	int i;
    

    @Autowired
    private UserRepository repo;

    public UserService() {
        i = 0;
    }

    @Override
    public String doSomething() {
        i++;
        return Integer.toString(i);
    }

    @Override
    public boolean addUser(String username, String fullName, String eMail, String Tel,
            Date BirthDate, String password, String role) {

        if (username != null && username.length() > 0 //
                && fullName != null && fullName.length() > 0) {
        	DemoPrincipal userDetails = new DemoPrincipal(username, password, role, null);
        	userDetails.setJwtToken(tokenService.generateToken(userDetails));
            User newCustomer = new User(username, fullName, eMail, Tel, BirthDate,            		
            		passwordEncoder.encode(password), role, userDetails.getJwtToken());
            
            repo.save(newCustomer);
            return true;
        }

        return false;

    }

    @Override
    public boolean addUser(User customer) {

        repo.save(customer);

        return false;

    }

    @Override
    public Iterable<User> getAll() {
        return repo.findAll();
    }



    @Override
    public boolean hasCredit(User customer) {
        if (customer.getCredit() > 0)
            return true;
        else
            return false;
    }

	@Override
	public User getByUsername(String username) {
		return repo.findByUsername(username);
	}
	
	@Autowired
    public void setTokenService(@Lazy TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public TokenService getTokenService() {
        return tokenService;
    }
	

   
}
