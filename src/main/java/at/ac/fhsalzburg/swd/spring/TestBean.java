package at.ac.fhsalzburg.swd.spring;

import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TestBean implements Serializable{
	
	int HashCode;
	
	public TestBean()
	{
		HashCode=System.identityHashCode(this);
	}
	
	@Bean(name="singletonBean")	
	public TestBean getSingletonBean()
	{
		return new TestBean();
	}	
	
	@Bean(name="prototypeBean")	
	@Scope("prototype")
	public TestBean getPrototypeBean()
	{
		return new TestBean();
	}
	
	public int getHashCode()
	{
		return HashCode;
	}
		
	@Bean(name="sessionBean")
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public TestBean getSessionBean() {
	    return new TestBean();
	}
}
