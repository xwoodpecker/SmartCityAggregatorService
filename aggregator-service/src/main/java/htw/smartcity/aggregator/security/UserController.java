package htw.smartcity.aggregator.security;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
@RequestMapping(path="/users")
@Tag(name = "User Management", description = "Endpoint to manage Users")
public class UserController {
    private UserDetailsService userDetailsService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserResourceAssembler userResourceAssembler;

    public UserController(UserDetailsService userDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder, UserResourceAssembler userResourceAssembler) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userResourceAssembler = userResourceAssembler;
    }

    @GetMapping("/")
    Boolean hi(Principal principal){
        UserDetails userDetails = (UserDetails) principal;
        System.out.println(userDetails);
        return true;
    }

    @SecurityRequirement(name = "basic")
    @Secured("ROLE_ADMIN")
    @PostMapping("/")
    EntityModel<User> createNewUser(@RequestParam String username, @RequestParam String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        Roles roles = new Roles();
        roles.setRole("USER");
        roles.setUser(user);
        user.getRoles().add(roles);
        user = userRepository.save(user);
        EntityModel<User> model = userResourceAssembler.toModel(user);
        return(model);
    }

    /*
    @PostMapping("/password")
    EntityModel<User> changeOwnPassword(Principal principal, @RequestParam String newPassword){
        String username = principal.getName();
        return changePassword(principal, username, newPassword);
    }
     */

    @PostMapping("/password")
    EntityModel<User> changePassword(Principal principal, @RequestParam String username, @RequestParam String newPassword){
        UserDetails userDetails = (UserDetails) principal;

        //find out if user is admin
        User user = userRepository.findUserByUsername(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        return(userResourceAssembler.toModel(userRepository.save(user)));
    }
}
