package at.ac.fhsalzburg.swd.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import at.ac.fhsalzburg.swd.spring.dao.User;
import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired private UserServiceInterface userService;

    @GetMapping("/users")
    public @ResponseBody List<User> allUsers() {

        return (List<User>) userService.getAll();
    }

    @RequestMapping(value = {"/users/{username}"}, method = RequestMethod.GET)
    public @ResponseBody User getCustomer(@PathVariable String username) {
        User customer = userService.getByUsername(username);

        return customer;
    }

    @RequestMapping(value = {"/users/{id}"}, method = RequestMethod.PUT)
    public String setCustomer(@RequestBody User customer) {

        userService.addUser(customer);

        return "redirect:/users";
    }

    // @DeleteMapping annotation maps HTTP DELETE requests onto
    // specific handler methods. It is a composed annotation that
    // acts as a shortcut for @RequestMapping(method =
    // RequestMethod.DELETE).
    /// TODO
    /*
    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable String id) {
        Long customerid = Long.parseLong(id);
        userService.deleteById(customerid);
        return "redirect:/users";
    }
    */
}
