package htw.smartcity.aggregator.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    public User findUserByUsername(String username);
}

