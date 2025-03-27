package com.project.forKnowledegeTesting;

import com.project.forKnowledegeTesting.models.Role;
import com.project.forKnowledegeTesting.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ForKnowledegeTestingApplication {

    public ForKnowledegeTestingApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(ForKnowledegeTestingApplication.class, args);
	}


	private final RoleRepository roleRepository;

	@Bean
	public CommandLineRunner initializeRoles() {
		return args -> {
				Role userRole = new Role();
				userRole.setName(String.valueOf(Role.RoleType.USER));
				roleRepository.save(userRole);
				System.out.println("USER role inserted.");


				Role adminRole = new Role();
				adminRole.setName(String.valueOf(Role.RoleType.ADMIN));
				roleRepository.save(adminRole);
				System.out.println("ADMIN role inserted.");

		};
	}

}
