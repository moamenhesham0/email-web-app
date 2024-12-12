package emailBackend.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import emailBackend.example.backend.repository.EmailAppRepository;
import emailBackend.example.backend.service.EmailAppService;

@SpringBootApplication
@ComponentScan(basePackages = "emailBackend.example.backend")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		
		// EmailAppRepository emailAppRepository = new EmailAppRepository();
		// EmailAppService emailAppService = new EmailAppService(emailAppRepository);
		// emailAppService.checkLogin("muhannd@com", "123");
		

		// EmailAppService emailAppService2 = new EmailAppService();
		// emailAppService.checkLogin("muhanndSa", "321");
	}

}
