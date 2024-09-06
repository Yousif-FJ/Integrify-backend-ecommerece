package com.backend.ecommerce.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product", schema = "ecommerce")
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private int quantity;
    @Column(name = "price", columnDefinition = "numeric(18,2)")
    private double price;
    private int discount;
    private String description;
    @ManyToOne(optional = true)
    private Category category;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProduct = new ArrayList<OrderProduct>();
}
