package com.example.exam_client_account_rest;


import com.example.exam_client_account_rest.dbo.Account;
import com.example.exam_client_account_rest.dbo.AccountRepository;
import com.example.exam_client_account_rest.dbo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {
    private AccountRepository accountRepository;
    private ClientRepository clientRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountRepository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> showMessageById(@PathVariable long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (!accountOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(accountOptional.get());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Account> editAccount(@PathVariable long id, @RequestBody Account account) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (!optionalAccount.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Account savedAccount = optionalAccount.get();
        savedAccount.setAccountType(account.getAccountType());
        savedAccount.setCurrency(account.getCurrency());
        savedAccount.setSum(account.getSum());

        savedAccount.setExpirationDate(account.getExpirationDate());

        accountRepository.save(savedAccount);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedAccount.getId()).toUri();
        return ResponseEntity.created(location).body(savedAccount);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Account> deleteAccountById(@PathVariable long id) {
        Optional<Account> deleteAccount = accountRepository.findById(id);
        if (!deleteAccount.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Account account = deleteAccount.get();

        accountRepository.deleteById(account.getId());
        //  messageRepository.delete(deleteMessage.get());
        return ResponseEntity.noContent().build();
    }
}
