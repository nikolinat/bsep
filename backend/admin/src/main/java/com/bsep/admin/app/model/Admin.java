package com.bsep.admin.app.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "users")
public class Admin extends User {

    public Admin(String lastName, String name, String emailAddress, String password, List<Role> roles, byte[] salt) {
        super(lastName, name, emailAddress, password, roles, salt);
    }

    public Admin() {
        super();
    }
}