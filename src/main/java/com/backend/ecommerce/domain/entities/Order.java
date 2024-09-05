package com.backend.ecommerce.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "order", schema = "ecommerce")
public class Order {
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne()
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @JsonIgnore
  @OnDelete(action = OnDeleteAction.CASCADE)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
  private List<OrderProduct> orderProducts = new ArrayList<>();

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = true)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "payment_id", referencedColumnName = "id")
  @JsonManagedReference
  private Payment payment;

  @Column(name = "status")
  private String status;

  @Column(name = "city")
  private String city;

  @Column(name = "street")
  private String street;

  @Column(name = "post_number", columnDefinition="bpchar(5)")
  private String postNumber;

  @Column(name = "date", columnDefinition = "date")
  private LocalDateTime date;


}
