package fr.phenix333.semantop.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.phenix333.semantop.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findUserById(Long id);

	User findUserByEmail(String email);

}
