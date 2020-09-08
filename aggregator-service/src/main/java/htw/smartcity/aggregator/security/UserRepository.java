package htw.smartcity.aggregator.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    User findUserByUsername(String username);

    /**
     * Find admins list.
     *
     * @return the list
     */
    @Query(value = "select u from User u join Roles r on r member of u.roles where r.role like 'admin'")
    List<User> findAdmins();
}

