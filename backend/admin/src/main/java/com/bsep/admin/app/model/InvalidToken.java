package com.bsep.admin.app.model;

import javax.persistence.*;

// Dodati kad istice i onda hron dzob za brisanje isteklih
@Entity
public class InvalidToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 10485760)
    private String token;

    public InvalidToken(String token) {
        this.token = token;
    }

    public InvalidToken() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
