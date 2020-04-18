package ro.orgmoney.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.orgmoney.persist.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
