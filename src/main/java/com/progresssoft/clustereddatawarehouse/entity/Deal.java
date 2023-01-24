package com.progresssoft.clustereddatawarehouse.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deals")
@Entity
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100, unique = true, name = "deal_id")
    private String dealUniqueId;

    @Column(nullable = false, length = 3, unique = true, name = "from_currency")
    private Currency fromCurrencyISOCode;

    @Column(nullable = false, length = 3, unique = true, name = "to_currency")
    private Currency toCurrencyISOCode;

    @CreationTimestamp
    private LocalDateTime timePlaced;

    @Column(nullable = false, name = "deal_amount")
    private BigDecimal dealAmount;

}
