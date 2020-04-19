package ro.orgmoney.persist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ro.orgmoney.persist.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public abstract User findUserByCNP(String cnp);
	
	@Query("SELECT DISTINCT(u) FROM User u LEFT JOIN FETCH u.transactionsAsPayer t LEFT JOIN FETCH t.payee")
	public abstract List<User> getAllUsersEagerWithTransactionsAsPayers();
}
