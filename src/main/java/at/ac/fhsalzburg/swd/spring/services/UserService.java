package at.ac.fhsalzburg.swd.spring.services;



import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.repository.UserRepository;
import at.ac.fhsalzburg.swd.spring.security.DemoPrincipal;
import at.ac.fhsalzburg.swd.spring.security.TokenService;


@Service
public class UserService implements UserServiceInterface {

	@Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

	private TokenService tokenService; // autowired using setter/field injection

    public final static String DEFAULT_ROLE = "USER";

    @Value("${myapp.medias.per.person.limit}") private Integer mediasPerPersonLimit;

	int i;


    @Autowired
    private UserRepository repo;
//TODO change to constructor ijection
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
//TODO disable overwriting users by creating a new one with the same username (e.g. admin) + tests
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
    public boolean addUser(User user) {

    	if (user.getRole()==null) user.setRole(DEFAULT_ROLE);

    	if ((user.getUsername()!=null) && (user.getUsername().length()>0))
    	{
    		DemoPrincipal userDetails = new DemoPrincipal(user.getUsername(), user.getPassword(), user.getRole(), null);
    		userDetails.setJwtToken(tokenService.generateToken(userDetails));
    		user.setJwttoken(userDetails.getJwtToken());
    	}
    	user.setPassword(passwordEncoder.encode(user.getPassword()));


        repo.save(user);

        return false;

    }

    @Override
    public Collection<User> getAll() {
    	List<User> result =
    			  StreamSupport.stream(repo.findAll().spliterator(), false)
    			    .collect(Collectors.toList());

        return result;
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

    @Override
    public Integer howManyMoreMediaCanBorrow(String username) {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("No user with such a name");
        }
        if (mediasPerPersonLimit < user.getLoans().size()) {
             throw new IllegalStateException("User has more Loaned medias than allowed");
         }
        return mediasPerPersonLimit - user.getLoans().size();
    }

    @Autowired
    public void setTokenService(@Lazy TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public TokenService getTokenService() {
        return tokenService;
    }

	@Override
	public boolean deleteUser(String username) {

		repo.deleteById(username);

		return true;
	}

/////////////////////Mine \/


}
