package at.ac.fhsalzburg.swd.spring;

import org.springframework.stereotype.Service;

@Service
public class TestService implements TestServiceI {
    
	int i;
    
    public TestService() {
    	i=0;
    }
	
	@Override
	public String doSomething()	{
		i++;
    	return Integer.toString(i);
    	
	}
}
