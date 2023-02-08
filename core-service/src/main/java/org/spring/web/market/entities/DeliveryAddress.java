package org.spring.web.market.entities;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;

@Entity
@Table(name = "delivery_addresses")
@Data
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long user;

    @Column(name = "address")
    private String address;
}
