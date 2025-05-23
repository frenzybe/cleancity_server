package ru.frenzybe.server.entities.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.*;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.entities.Urn;
import ru.frenzybe.server.entities.user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "urn_id")
    private Urn urn;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotion_id", unique = true)
    @JsonIdentityReference(alwaysAsId=true)
    private Promotion promotion;

    @Column(name = "date_time", nullable = false)
    private Instant dateTime;

}