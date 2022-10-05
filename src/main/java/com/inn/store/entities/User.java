package com.inn.store.entities;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@DynamicUpdate


@Table(name="user")
public class User implements Serializable {
   private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable=false)
    private Integer id ;

    @Column(name = "name",nullable=false)
    private String name ;

    @Column(name = "contactNumber",nullable=false)
    private String contactNumber;

    @Column(name = "email",nullable=false)
    private String email;

    @Column(name="password",nullable=false)
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name="role")
    private String role;
}
