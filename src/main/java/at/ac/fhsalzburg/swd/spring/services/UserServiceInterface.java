package at.ac.fhsalzburg.swd.spring.services;

import java.util.Date;
import at.ac.fhsalzburg.swd.spring.dao.User;
import at.ac.fhsalzburg.swd.spring.security.DemoPrincipal;

public interface UserServiceInterface {

    public abstract String doSomething();

    public abstract boolean addUser(String firstName, String lastName, String eMail, String Tel,
            Date BirthDate, String password, String role);

    public abstract boolean addUser(User user);

    public abstract Iterable<User> getAll();

    public abstract boolean hasCredit(User user);
    
    public abstract User getByUsername(String username);

}
