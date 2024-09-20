package com.example.bookshop.security;

import com.example.bookshop.utils.constants.PredefinedRole;
import com.example.bookshop.users.models.RoleEntity;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.users.repositories.RoleRepository;
import com.example.bookshop.users.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin123";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                roleRepository.save(RoleEntity.builder()
                        .roleName(PredefinedRole.USER)
                        .roleDescription("User role")
                        .build());

                RoleEntity adminRole = roleRepository.save(RoleEntity.builder()
                        .roleName(PredefinedRole.ADMIN)
                        .roleDescription("Admin role")
                        .build());

                var roles = new HashSet<RoleEntity>();
                roles.add(adminRole);

                UserEntity user = UserEntity.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .image_url("https://stcv4.hnammobile.com/downloads/a/cach-chup-anh-selfie-dep-an-tuong-ban-nhat-dinh-phai-biet-81675319567.jpg")
                        .firstName("Admin")
                        .lastName("Admin")
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
