package com.inn.store.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@DynamicUpdate @DynamicInsert @AllArgsConstructor @NoArgsConstructor
@Table(name = "bill")
public class Bill implements Serializable {

    private static final long serializableUID =1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "paymentmethod")
    private String paymentMethod;

    @Column(name="total")
    private Integer total;

    @Column(name = "productdetails")
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(name="bill_products" , joinColumns = {@JoinColumn(name="bill_id")}, inverseJoinColumns = {@JoinColumn(name="product_id")})
    private List<Product> productdetails=new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_fk",nullable = false)
    private User createdBy;


}
