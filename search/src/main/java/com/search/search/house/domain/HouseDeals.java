package com.search.search.house.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(
        name = "housedeals"
        // CREATE FULLTEXT INDEX idx_road_nm_apt_nm ON houseinfos (road_nm, apt_nm);
)
public class HouseDeals {
    @Column(name = "no")
    @Id @GeneratedValue(strategy = IDENTITY)
    private Integer no;

    @Column(name = "apt_seq", length = 20)
    private String aptSeq;

    @Column(name = "apt_dong", length = 40)
    private String aptDong;

    @Column(name = "floor", length = 3)
    private String floor;

    @Column(name = "deal_year")
    private Integer dealYear;

    @Column(name = "deal_month")
    private Integer dealMonth;

    @Column(name = "deal_day")
    private Integer dealDay;

    @Column(name = "exclu_use_ar")
    private BigDecimal excluUseAr;

    @Column(name = "deal_amount", length = 10)
    private String dealAmount;

    @Builder
    private HouseDeals(
            String aptSeq,
            String aptDong,
            String floor,
            Integer dealYear,
            Integer dealMonth,
            Integer dealDay,
            BigDecimal excluUseAr,
            String dealAmount
    ) {
        this.aptSeq = aptSeq;
        this.aptDong = aptDong;
        this.floor = floor;
        this.dealYear = dealYear;
        this.dealMonth = dealMonth;
        this.dealDay = dealDay;
        this.excluUseAr = excluUseAr;
        this.dealAmount = dealAmount;
    }
}
