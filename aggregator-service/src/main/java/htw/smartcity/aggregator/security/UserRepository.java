package htw.smartcity.aggregator.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
    /**
     * Find user by username user.
     *
     * @param username the username
     * @return the user
     */
    public User findUserByUsername(String username);

    /**
     * Find admins list.
     *
     * @return the list
     */
    @Query(value = "select u from User u join Roles r on r member of u.roles where r.role like 'admin'")
    public List<User> findAdmins();
}

