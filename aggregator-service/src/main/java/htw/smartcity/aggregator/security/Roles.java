package htw.smartcity.aggregator.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@JsonIgnoreProperties(value = "user")
public class Roles {
    @Id
    @Column(name = "role")
    private String role;

    @ManyToOne
    private User user;

    public String getRole() {
        return role;
    }

    public void setRole(String authority) {
        this.role = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
