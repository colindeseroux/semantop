package fr.phenix333.semantop.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.phenix333.semantop.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findUserById(Long id);

	User findUserByEmail(String email);

	@Query("SELECT CONCAT('User', COUNT(u)) FROM User u")
	String getNextPseudo();

}
