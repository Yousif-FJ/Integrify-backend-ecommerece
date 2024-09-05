package com.backend.ecommerce.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "payment", schema = "ecommerce")
public class Payment {

  public Payment() {}

  public Payment(float amount, String city, String street, String postNumber){
    this.amount = amount;
    this.city = city;
    this.street = street;
    this.postNumber = postNumber;
    this.paymentStatus = false;
  }

  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne
  private Order order;

  @Column(name="amount", columnDefinition = "numeric(24,2)")
  private float amount;

  private String city;
  private String street;

  @Column(name = "post_number", columnDefinition="bpchar(5)")
  private String postNumber;

  @Column(name = "payment_Status")
  private boolean paymentStatus;
}
