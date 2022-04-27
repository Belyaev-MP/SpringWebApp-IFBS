package my.self.springapp.data.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import my.self.springapp.domain.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    long countByEmail(String email);

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndEnabledTrue(String email);
}
