package htw.smartcity.aggregator.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * The type Roles.
 */
@Entity
@Table(name = "roles")
@JsonIgnoreProperties(value = "user")
public class Roles {
    @Id
    @Column(name = "role")
    private String role;

    @ManyToOne
    private User user;

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param authority the authority
     */
    public void setRole(String authority) {
        this.role = authority;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
