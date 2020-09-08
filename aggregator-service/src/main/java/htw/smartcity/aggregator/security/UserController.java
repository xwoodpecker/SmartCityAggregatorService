package htw.smartcity.aggregator.security;

import htw.smartcity.aggregator.temperature.TemperatureNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

/**
 * The type User controller.
 */
@RestController
@RequestMapping(path="/users")
@SecurityRequirement(name = "basic")
@Tag(name = "User Management", description = "Endpoint to manage Users")
public class UserController {
    private UserDetailsService userDetailsService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserResourceAssembler userResourceAssembler;

    /**
     * Instantiates a new User controller.
     *
     * @param userDetailsService    the user details service
     * @param userRepository        the user repository
     * @param passwordEncoder       the password encoder
     * @param userResourceAssembler the user resource assembler
     */
    public UserController(UserDetailsService userDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder, UserResourceAssembler userResourceAssembler) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userResourceAssembler = userResourceAssembler;
    }

    /**
     * Create new user entity model.
     *
     * @param username the username
     * @param password the password
     * @param email    the email
     * @param isAdmin  the is admin
     * @return the entity model
     */
    @Operation(summary = "Create a new User")
    @Secured("ROLE_ADMIN")
    @PostMapping("/")
    EntityModel<User> createNewUser(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam boolean isAdmin){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setEmail(email);
        Roles userRole = new Roles();
        userRole.setRole("USER");
        user.getRoles().add(userRole);
        if(isAdmin){
            Roles adminRole = new Roles();
            adminRole.setRole("ADMIN");
            user.getRoles().add(adminRole);
        }
        user = userRepository.save(user);
        EntityModel<User> model = userResourceAssembler.toModel(user);
        return(model);
    }

    /**
     * Change own password entity model.
     *
     * @param principal   the principal
     * @param newPassword the new password
     * @return the entity model
     */
    @Operation(summary = "Change the password of a user")
    @PostMapping("/password/own")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    void changeOwnPassword(UsernamePasswordAuthenticationToken principal, @RequestParam String newPassword){
        UserDetails userDetails = (UserDetails) principal.getPrincipal();
        User user = userRepository.findUserByUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Change someones password entity model.
     *
     * @param username    the username
     * @param newPassword the new password
     * @return the entity model
     */
    @Operation(summary = "Change password of a specified user. Admin Role required.")
    @Secured("ROLE_ADMIN")
    @PostMapping("/password/other")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    void changeSomeonesPassword(String username, String newPassword){
        User user = userRepository.findUserByUsername(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
