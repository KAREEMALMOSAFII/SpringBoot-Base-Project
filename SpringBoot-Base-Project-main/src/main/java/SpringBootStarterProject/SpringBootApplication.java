package SpringBootStarterProject;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.boot.autoconfigure.SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@EnableTransactionManagement

public class SpringBootApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringBootApplication.class, args);


	}

}

