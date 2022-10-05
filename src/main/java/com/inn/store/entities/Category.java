package com.inn.store.entities;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@DynamicUpdate



@Table(name="category")
public class Category implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable=false)
    private Integer id ;


    @NotNull(message = "This field must not be null!")
    @NotBlank(message = "This field must not be blank!")
    @Column(name = "name",nullable=false)
    private String name ;
}
