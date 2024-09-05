package com.backend.ecommerce.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "order_product", schema = "ecommerce")
public class OrderProduct {

  public OrderProduct() {}

  public OrderProduct(Order order, Product product, double price) {
    this.order = order;
    this.product = product;
    this.price = price;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne()
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private Order order;

  @ManyToOne
  @JoinColumn(name="product_id")
  private Product product;

  @Column(name="price", columnDefinition = "numeric(18,2)")
  private double price;
}
