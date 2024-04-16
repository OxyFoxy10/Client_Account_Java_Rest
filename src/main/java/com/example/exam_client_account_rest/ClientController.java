package com.example.exam_client_account_rest;

import com.example.exam_client_account_rest.dbo.Account;
import com.example.exam_client_account_rest.dbo.AccountRepository;
import com.example.exam_client_account_rest.dbo.Client;
import com.example.exam_client_account_rest.dbo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/clients")
public class ClientController {
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
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Client> addCLient(@RequestBody Client client) {
        Client savedClient = clientRepository.save(client);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedClient.getId()).toUri();
        return ResponseEntity.created(location).body(savedClient);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable long id) {
        Optional<Client> savedUser = clientRepository.findById(id);
        if (!savedUser.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(savedUser.get());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Client> editUser(@PathVariable long id, @RequestBody Client client) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Client savedClient = optionalClient.get();
        savedClient.setName(client.getName());
        savedClient.setEmail(client.getEmail());
        savedClient.setPhone(client.getPhone());
        savedClient.setAddress(client.getAddress());
        clientRepository.save(savedClient);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedClient.getId()).toUri();
        return ResponseEntity.created(location).body(savedClient);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Account> addAccount(@PathVariable long id, @RequestBody Account account) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Client savedClient = optionalClient.get();
        savedClient.getAccounts().add(account);
        account.setClient(savedClient);
        account = accountRepository.save(account);
        clientRepository.save(savedClient);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}")
                .buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(location).body(account);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Client> deleteClientById(@PathVariable long id) {
        Optional<Client> deleteClient = clientRepository.findById(id);
        if (!deleteClient.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Client client = deleteClient.get();
        if (!client.getAccounts().isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        // repository.deleteById(id);
        clientRepository.delete(deleteClient.get());
        return ResponseEntity.noContent().build();
    }

}
