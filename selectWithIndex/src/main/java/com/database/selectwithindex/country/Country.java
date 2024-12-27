package com.database.selectwithindex.country;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(
        name = "country",
        indexes = {
                @Index(name = "idx_country", columnList = "country")
        }
)
public class Country {

    @Column(name = "country_id")
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 30)
    private String country;

    @Column(length = 20)
    private String city;

    public Country(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
