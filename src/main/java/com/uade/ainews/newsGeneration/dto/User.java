package com.uade.ainews.newsGeneration.dto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "USER")
public class User {
    public static final double DEFAULT_LECTURE_TIME = 1.00;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'N'")
    private String newsletter;
    @Column(columnDefinition = "INT DEFAULT 100")
    private Integer politicsInterest;
    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 1.00")
    private BigDecimal politicsTime;
    @Column(columnDefinition = "INT DEFAULT 100")
    private Integer economyInterest;
    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 1.00")
    private BigDecimal economyTime;
    @Column(columnDefinition = "INT DEFAULT 100")
    private Integer sportsInterest;
    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 1.00")
    private BigDecimal sportsTime;
    @Column(columnDefinition = "INT DEFAULT 100")
    private Integer socialInterest;
    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 1.00")
    private BigDecimal socialTime;
    @Column(columnDefinition = "INT DEFAULT 100")
    private Integer internationalInterest;
    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 1.00")
    private BigDecimal internationalTime;
    @Column(columnDefinition = "INT DEFAULT 100")
    private Integer policeInterest;
    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 1.00")
    private BigDecimal policeTime;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        // Establece los valores predeterminados
        this.newsletter = "N";
        this.politicsInterest = 100;
        this.politicsTime = BigDecimal.valueOf(DEFAULT_LECTURE_TIME);

        this.politicsInterest = 100;
        this.politicsTime = BigDecimal.valueOf(DEFAULT_LECTURE_TIME);

        this.economyInterest = 100;
        this.economyTime = BigDecimal.valueOf(DEFAULT_LECTURE_TIME);

        this.sportsInterest = 100;
        this.sportsTime = BigDecimal.valueOf(DEFAULT_LECTURE_TIME);

        this.socialInterest = 100;
        this.socialTime = BigDecimal.valueOf(DEFAULT_LECTURE_TIME);

        this.internationalInterest = 100;
        this.internationalTime = BigDecimal.valueOf(DEFAULT_LECTURE_TIME);

        this.policeInterest = 100;
        this.policeTime = BigDecimal.valueOf(DEFAULT_LECTURE_TIME);

    }
}
