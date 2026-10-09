package com.vsc.vehicleservicecenter.auth.config;
import com.vsc.vehicleservicecenter.auth.entity.AppUser;import com.vsc.vehicleservicecenter.auth.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Value;import org.springframework.boot.*;import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration public class BootstrapAdmin {
 @Bean CommandLineRunner bootstrap(AppUserRepository repo,PasswordEncoder encoder,
 @Value("${app.admin.username:admin}") String username,@Value("${app.admin.password:}") String password){return args->{
  if(password.isBlank())throw new IllegalStateException("Set ADMIN_PASSWORD environment variable before starting");
  if(repo.findByUsername(username).isEmpty()){AppUser u=new AppUser();u.setUsername(username);u.setPasswordHash(encoder.encode(password));u.setRole("ADMIN");repo.save(u);}
 };}
}
