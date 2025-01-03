package com.search.search.attraction.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(
        name = "attractions"
        // CREATE FULLTEXT INDEX idx_title_addr1 ON attractions (title, addr1);
)
public class Attractions {

    @Column(name = "no")
    @Id @GeneratedValue(strategy = IDENTITY)
    private Integer no;

    @Column(name = "title", length = 500)
    private String title;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "addr1", length = 100)
    private String addr1;

    private Attractions(
            String title,
            BigDecimal latitude,
            BigDecimal longitude,
            String addr1
    ) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addr1 = addr1;
    }
}
