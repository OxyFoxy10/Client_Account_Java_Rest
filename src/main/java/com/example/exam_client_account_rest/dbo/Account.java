package com.example.exam_client_account_rest.dbo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "account_table")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( unique = true, nullable = false)
    private long id;
    @Column(nullable = false)
    private String accountType;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.mm.yyyy")
    private Date expirationDate;
    @Column(nullable = false)
    private String currency;
    @Column(nullable = false)
    private Double sum;

    @ManyToOne (cascade = CascadeType.PERSIST)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Client client;

    public Account() {
        client=new Client();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

//    public String getExpirationDate() {
//        return expirationDate;
//    }

//    public void setExpirationDate(String expirationDate) {
////        SimpleDateFormat sdf=new SimpleDateFormat("dd.mm.yyyy");
////        try {
////            this.expirationDate=sdf.parse(expirationDate);
////        } catch (ParseException e) {
////            throw new RuntimeException(e);
////        }
//        this.expirationDate = expirationDate;
//    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
