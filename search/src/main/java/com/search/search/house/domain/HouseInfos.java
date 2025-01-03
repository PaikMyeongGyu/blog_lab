package com.search.search.house.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "houseinfos")
public class HouseInfos {

    @Id
    @Column(name = "apt_seq", length = 20, nullable = false)
    private String aptSeq;

    @Column(name = "sgg_cd", length = 5)
    private String sggCd;

    @Column(name = "umd_cd", length = 5)
    private String umdCd;

    @Column(name = "umd_nm", length = 20)
    private String umdNm;

    @Column(name = "jibun", length = 10)
    private String jibun;

    @Column(name = "road_nm_sgg_cd", length = 5)
    private String roadNmSggCd;

    @Column(name = "road_nm", length = 20)
    private String roadNm;

    @Column(name = "road_nm_bonbun", length = 10)
    private String roadNmBonbun;

    @Column(name = "road_nm_bubun", length = 10)
    private String roadNmBubun;

    @Column(name = "apt_nm", length = 40)
    private String aptNm;

    @Column(name = "build_year")
    private Integer buildYear;

    @Column(name = "latitude", length = 45)
    private String latitude;

    @Column(name = "longitude", length = 45)
    private String longitude;

    @Builder
    public HouseInfos(
            String aptSeq,
            String sggCd,
            String umdCd,
            String umdNm,
            String jibun,
            String roadNmSggCd,
            String roadNm,
            String roadNmBonbun,
            String roadNmBubun,
            String aptNm,
            Integer buildYear,
            String latitude,
            String longitude
    ) {
        this.aptSeq = aptSeq;
        this.sggCd = sggCd;
        this.umdCd = umdCd;
        this.umdNm = umdNm;
        this.jibun = jibun;
        this.roadNmSggCd = roadNmSggCd;
        this.roadNm = roadNm;
        this.roadNmBonbun = roadNmBonbun;
        this.roadNmBubun = roadNmBubun;
        this.aptNm = aptNm;
        this.buildYear = buildYear;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
