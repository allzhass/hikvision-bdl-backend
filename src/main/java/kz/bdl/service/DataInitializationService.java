package kz.bdl.service;

import kz.bdl.entity.Role;
import kz.bdl.entity.User;
import kz.bdl.repository.RoleRepository;
import kz.bdl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeUsers();
    }

    private void initializeRoles() {
        // Create ADMIN role if it doesn't exist
        if (!roleRepository.findByName("ADMIN").isPresent()) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Administrator role with full access to all features");
            roleRepository.save(adminRole);
        }

        // Create OPERATOR role if it doesn't exist
        if (!roleRepository.findByName("OPERATOR").isPresent()) {
            Role operatorRole = new Role();
            operatorRole.setName("OPERATOR");
            operatorRole.setDescription("Operator role with access only to SentViolationViewController");
            roleRepository.save(operatorRole);
        }

        // Create MANAGER role if it doesn't exist
        if (!roleRepository.findByName("MANAGER").isPresent()) {
            Role managerRole = new Role();
            managerRole.setName("MANAGER");
            managerRole.setDescription("Manager role with full access except managing users and roles");
            roleRepository.save(managerRole);
        }

        // Create AUTO_MANAGER role if it doesn't exist
        if (!roleRepository.findByName("AUTO_MANAGER").isPresent()) {
            Role autoManagerRole = new Role();
            autoManagerRole.setName("AUTO_MANAGER");
            autoManagerRole.setDescription("Auto manager role with access to auto management and sent violations");
            roleRepository.save(autoManagerRole);
        }
    }

    private void initializeUsers() {
        // Create admin user if it doesn't exist
        if (!userRepository.findByUsername("admin").isPresent()) {
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));

            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setEmail("admin@bdl.kz");
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setIsActive(true);
            adminUser.setRole(adminRole);

            userRepository.save(adminUser);
        }

        // Create manager user if it doesn't exist
        if (!userRepository.findByUsername("manager").isPresent()) {
            Role managerRole = roleRepository.findByName("MANAGER")
                    .orElseThrow(() -> new RuntimeException("Manager role not found"));

            User managerUser = new User();
            managerUser.setUsername("manager");
            managerUser.setPassword(passwordEncoder.encode("manager123"));
            managerUser.setEmail("manager@bdl.kz");
            managerUser.setFirstName("Manager");
            managerUser.setLastName("User");
            managerUser.setIsActive(true);
            managerUser.setRole(managerRole);

            userRepository.save(managerUser);
        }

        // Create operator user if it doesn't exist
        if (!userRepository.findByUsername("operator").isPresent()) {
            Role operatorRole = roleRepository.findByName("OPERATOR")
                    .orElseThrow(() -> new RuntimeException("Operator role not found"));

            User operatorUser = new User();
            operatorUser.setUsername("operator");
            operatorUser.setPassword(passwordEncoder.encode("operator123"));
            operatorUser.setEmail("operator@bdl.kz");
            operatorUser.setFirstName("Operator");
            operatorUser.setLastName("User");
            operatorUser.setIsActive(true);
            operatorUser.setRole(operatorRole);

            userRepository.save(operatorUser);
        }

        // Create auto manager user if it doesn't exist
        if (!userRepository.findByUsername("auto_manager").isPresent()) {
            Role autoManagerRole = roleRepository.findByName("AUTO_MANAGER")
                    .orElseThrow(() -> new RuntimeException("Auto manager role not found"));

            User autoManagerUser = new User();
            autoManagerUser.setUsername("auto_manager");
            autoManagerUser.setPassword(passwordEncoder.encode("auto123"));
            autoManagerUser.setEmail("auto_manager@bdl.kz");
            autoManagerUser.setFirstName("Auto");
            autoManagerUser.setLastName("Manager");
            autoManagerUser.setIsActive(true);
            autoManagerUser.setRole(autoManagerRole);

            userRepository.save(autoManagerUser);
        }
    }
} 