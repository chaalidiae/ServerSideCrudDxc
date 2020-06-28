package ma.dxc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ma.dxc.service.AccountService;


@SpringBootApplication
public class ContactApplication implements CommandLineRunner {
	
	@Autowired
	 AccountService accountService;
	 
	 @Bean
		public BCryptPasswordEncoder getBCPE() {
			return new BCryptPasswordEncoder();
		}

	public static void main(String[] args) {
		SpringApplication.run(ContactApplication.class, args);
	}
	
	

	@Override
	public void run(String... args) throws Exception {}

}