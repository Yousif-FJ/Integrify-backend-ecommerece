package com.backend.ecommerce.infastructure.jpaRepositories;

import com.backend.ecommerce.application.dto.dtoInterfaces.IOrderDetailsDto;
import com.backend.ecommerce.domain.entities.Order;
import com.backend.ecommerce.application.dto.dtoInterfaces.IOrderDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, UUID> {
  @Query(value = """
          SELECT o.id as Id, u.id as UserId, u.name as UserName, o.status as OrderStatus,
                 p.payment_status as paymentStatus, o.date as orderDate,  p.amount as Amount
          FROM ecommerce.order AS o
          INNER JOIN ecommerce.user AS u ON u.id = o.user_id
          INNER JOIN ecommerce.payment AS p ON p.id = o.payment_id;""", nativeQuery = true)
  public List<IOrderDto> getAllOrders();

  @Query(value = "SELECT o.id as Id, u.id as UserId, u.name as UserName, u.email as UserEmail, " +
          "p.id as PaymentId, o.status as OrderStatus, " +
          "o.city as ShipmentCity, o.street as ShipmentStreet, o.post_number as ShipmentPostNumber, " +
          "o.date as OrderDate, p.amount as Amount, p.payment_status as PaymentStatus, " +
          "p.city as BillingCity,p.street as BillingStreet, p.post_number as BillingPostNumber " +
          "FROM ecommerce.order AS o " +
          "INNER JOIN ecommerce.user AS u ON u.id = o.user_id " +
          "INNER JOIN ecommerce.payment AS p ON p.id = o.payment_id " +
          "WHERE o.id = ?1;",
          nativeQuery = true)
  public Optional<IOrderDetailsDto> getOrderDetails(UUID id);


  @Query(value = "SELECT o.id as Id, u.id as UserId, u.name as UserName, " +
          "o.status as OrderStatus, p.payment_status as paymentStatus, p.amount as Amount\n" +
          "FROM ecommerce.order AS o\n" +
          "INNER JOIN ecommerce.user AS u ON u.id = o.user_id\n" +
          "INNER JOIN ecommerce.payment AS p ON p.id = o.payment_id\n" +
          "WHERE p.payment_status = ?1;",
          nativeQuery = true)
  public List<IOrderDto> getAllOrdersByPaymentStatus(boolean status);

  @Query(value = "SELECT o.id as Id, u.id as UserId, u.name as UserName, " +
          "o.status as OrderStatus, p.payment_status as paymentStatus, p.amount as Amount\n" +
          "FROM ecommerce.order AS o\n" +
          "INNER JOIN ecommerce.user AS u ON u.id = o.user_id\n" +
          "INNER JOIN ecommerce.payment AS p ON p.id = o.payment_id\n" +
          "WHERE u.id = ?1;",
          nativeQuery = true)
  public List<IOrderDto> getUserOrders(UUID id);

  @Transactional
  @Modifying
  @Query(value= "UPDATE ecommerce.order " +
          "SET status = :#{#order.status}, city = :#{#order.city}, street = :#{#order.street}, post_number = :#{#order.postNumber} " +
          "WHERE id = :#{#order.id};",
          nativeQuery = true)
  public void updateOrder(@Param("order") Order order);

}
