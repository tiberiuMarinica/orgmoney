package ro.orgmoney.persist.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.orgmoney.model.dtos.TransactionDto;
import ro.orgmoney.persist.entities.Transaction;
import ro.orgmoney.persist.entities.User;
import ro.orgmoney.persist.repositories.TransactionRepository;
import ro.orgmoney.persist.repositories.UserRepository;
import ro.orgmoney.persist.services.abs.PersistService;

@Service
public class PersistServiceImpl implements PersistService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Override
	@Transactional
	public void create(TransactionDto transactionDto) {
		
		System.out.println("Persisting transaction " + transactionDto);
		
		Transaction transaction = convertFromDto(transactionDto);
		
		transactionRepository.save(transaction);
	}
	
	private Transaction convertFromDto(TransactionDto transactionDto) {
		
		Transaction transaction = new Transaction();
		transaction.setDescription(transactionDto.getDescription());
		transaction.setSum(transactionDto.getSum());
		transaction.setType(transactionDto.getType());
		
		//normal ar fi trebuit sa fie userii in DB, si as fi facut doar legatura de referinta
		
		//User payer = userRepository.getOne(transactionDto.getPayer().getId());
		//transaction.setPayer(payer);
		
		//dar deoarece nu sunt, ii voi crea cu fiecare tranzactie (daca nu sunt deja in DB)
		
		User payer = userRepository.findUserByCNP(transactionDto.getPayer().getCNP());
		if(payer == null) {
			payer = new User();
			payer.setName(transactionDto.getPayer().getName());
			payer.setCNP(transactionDto.getPayer().getCNP());
			payer.setIBAN(transactionDto.getPayer().getIBAN());
			userRepository.save(payer);
		}
		
		User payee = userRepository.findUserByCNP(transactionDto.getPayee().getCNP());
		if(payee == null) {
			payee = new User();
			payee.setName(transactionDto.getPayee().getName());
			payee.setCNP(transactionDto.getPayee().getCNP());
			payee.setIBAN(transactionDto.getPayee().getIBAN());
			userRepository.save(payee);
		}
		
		transaction.setPayer(payer);
		transaction.setPayee(payee);
		
		return transaction;
	}

}
