package at.ac.fhsalzburg.swd.spring.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import at.ac.fhsalzburg.swd.spring.CustomerServiceInterface;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    CustomerServiceInterface customerService;


   // Initialize System with preset accounts and stocks
    @Override
    public void run(String...args) throws Exception {

    	customerService.addCustomer("Max", "Mustermann", "max@muster.man", "123");
    }
}
