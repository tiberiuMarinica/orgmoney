package ro.orgmoney.persist.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.orgmoney.persist.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
