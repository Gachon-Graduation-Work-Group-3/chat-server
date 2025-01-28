package whenyourcar.storage.mysql.repository.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whenyourcar.storage.mysql.data.entity.User;

import java.util.Optional;

@Repository
public interface UserCommonRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findUserById(Long userId);
}
