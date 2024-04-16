package com.example.exam_client_account_rest.dbo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client_table")
public class Client implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;
    @Basic
    @NotBlank(message = "Поле не може бути порожнім")
    private String name;
    @Basic
    @NotBlank(message = "Поле не може бути порожнім")
    private String address;
    @Basic
    @NotBlank(message = "Поле не може бути порожнім")
    @Pattern(regexp ="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Перевірте правильність задання e-mail")
    private String email;
    @Basic
    @NotBlank(message = "Поле не може бути порожнім")
    private String phone;
    @OneToMany(mappedBy = "client", cascade =  CascadeType.ALL,  orphanRemoval = true )
    private Set<Account> accounts=new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setMessages(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
