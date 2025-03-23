package com.marketplace.POJO;

import com.marketplace.dao.UserDao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serial;
import java.io.Serializable;
@Getter
@NamedQuery(
        name = "User.findByAccountEmail",
        query = "SELECT u FROM User u WHERE u.email = :email"
)
@NamedQuery(
        name = "User.getAllAdmin",
        query = "SELECT new com.marketplace.wrapper.UserWrapper(u.id,u.email,u.name,u.phoneNumber,u.status) from User u where u.role='admin'"
)
@NamedQuery(
        name = "User.updateUserStatus",
        query = "UPDATE User u set u.status = :status where u.id = :id"
)

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "users")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "status")
    private String status;

}
