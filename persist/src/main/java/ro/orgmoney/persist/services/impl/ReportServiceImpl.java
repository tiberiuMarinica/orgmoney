package ro.orgmoney.persist.services.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.orgmoney.model.dtos.FileDto;
import ro.orgmoney.model.dtos.TransactionDto.Type;
import ro.orgmoney.persist.entities.Transaction;
import ro.orgmoney.persist.entities.User;
import ro.orgmoney.persist.repositories.UserRepository;
import ro.orgmoney.persist.services.abs.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public FileDto getAllTransactionsReport() {
		
		List<User> usersWithTransactionsAsPayers = userRepository.getAllUsersEagerWithTransactionsAsPayers();
		
		FileDto fileDto = new FileDto();
		
		try {
		    // Create temp file.
		    File temp = File.createTempFile("tmp", ".txt");

		    // Write to temp file
		    BufferedWriter out = new BufferedWriter(new FileWriter(temp));
		    
		    out.newLine();
		    
		    for(User u : usersWithTransactionsAsPayers) {
		    	Map<Type, List<Transaction>> typeWithTransactionsMap = arrangeTransactionsByType(u.getTransactionsAsPayer());
		    	out.write("Nume: " + u.getName());
		    	out.newLine();
		    	out.newLine();
		    	
		    	out.write("CNP: " + u.getCNP());
		    	out.newLine();
		    	out.newLine();
		    	
		    	out.write("IBAN: " + u.getIBAN());
		    	out.newLine();
		    	out.newLine();
		    	
		    	out.write("Tranzacții: ");
		    	out.newLine();
		    	out.newLine();
		    	
		    	int i = 1;
		    	for(Map.Entry<Type, List<Transaction>> entry : typeWithTransactionsMap.entrySet()) {
		    		Double transactionSum = entry.getValue().stream().map(t -> t.getSum()).reduce(0.0d, Double::sum);
		    		out.write("	" + i + ". " + entry.getKey() + " | " + entry.getValue().size() + " | " + transactionSum + " RON");
		    		out.newLine();
		    		
		    		int j = 'a';
		    		for(Transaction t : entry.getValue()) {
		    			out.write("		" + (char) j + ". " + t.getPayee().getName() + ", suma: " + t.getSum());
		    			out.newLine();
		    			
		    			j++;
		    		}
		    		
		    		i++;
		    	}
		    	
		    	out.write("--------------------------------------------------------------------------------------------");
		    	out.newLine();
		    	
		    }
		    
		    out.close();
		    
		    FileInputStream fis = new FileInputStream(temp);
		    byte[] content = new byte[(int) temp.length()];
		    fis.read(content); //read file into bytes[]
		    fis.close();
		    
		    temp.delete();
		    
		    fileDto.setContent(content);
		    fileDto.setName("All Transactions Report " + Instant.now().toEpochMilli());
		    fileDto.setMimeType("text/plain");
		    fileDto.setSize(temp.length());
		    
		    return fileDto;
		    
		} catch (IOException e) {
		} finally {
		}
		
		return new FileDto();
	}
	
	private Map<Type, List<Transaction>> arrangeTransactionsByType(List<Transaction> transactions){
		
		Map<Type, List<Transaction>> typeWithTransactionsMap = new HashMap<>();
		
		//initialize map
		for(Type t : Type.values()) {
			typeWithTransactionsMap.put(t, new ArrayList<>());
		}
		
		for(Transaction t : transactions) {
			typeWithTransactionsMap.get(t.getType()).add(t);
		}
		
		return typeWithTransactionsMap;
	}

	
	
}
