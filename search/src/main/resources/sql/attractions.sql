CREATE TABLE IF NOT EXISTS `attractions` (
    `no` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(500) NULL DEFAULT NULL,
    `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
    `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
    `addr1` VARCHAR(100) NULL DEFAULT NULL,
    PRIMARY KEY (`no`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE FULLTEXT INDEX idx_title_addr1 ON attractions (title, addr1) WITH PARSER ngram;

CREATE TABLE IF NOT EXISTS `houseinfos` (
                                            `apt_seq` VARCHAR(20) NOT NULL,
                                            `sgg_cd` VARCHAR(5) NULL DEFAULT NULL,
                                            `umd_cd` VARCHAR(5) NULL DEFAULT NULL,
                                            `umd_nm` VARCHAR(20) NULL DEFAULT NULL,
                                            `jibun` VARCHAR(10) NULL DEFAULT NULL,
                                            `road_nm_sgg_cd` VARCHAR(5) NULL DEFAULT NULL,
                                            `road_nm` VARCHAR(20) NULL DEFAULT NULL,
                                            `road_nm_bonbun` VARCHAR(10) NULL DEFAULT NULL,
                                            `road_nm_bubun` VARCHAR(10) NULL DEFAULT NULL,
                                            `apt_nm` VARCHAR(40) NULL DEFAULT NULL,
                                            `build_year` INT NULL DEFAULT NULL,
                                            `latitude` VARCHAR(45) NULL DEFAULT NULL,
                                            `longitude` VARCHAR(45) NULL DEFAULT NULL,
                                            PRIMARY KEY (`apt_seq`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE FULLTEXT INDEX idx_road_nm_apt_nm ON houseinfos (road_nm, apt_nm) WITH PARSER ngram;

CREATE TABLE IF NOT EXISTS `housedeals` (
                                            `no` INT NOT NULL AUTO_INCREMENT,
                                            `apt_seq` VARCHAR(20) NULL DEFAULT NULL,
                                            `apt_dong` VARCHAR(40) NULL DEFAULT NULL,
                                            `floor` VARCHAR(3) NULL DEFAULT NULL,
                                            `deal_year` INT NULL DEFAULT NULL,
                                            `deal_month` INT NULL DEFAULT NULL,
                                            `deal_day` INT NULL DEFAULT NULL,
                                            `exclu_use_ar` DECIMAL(7,2) NULL DEFAULT NULL,
                                            `deal_amount` VARCHAR(10) NULL DEFAULT NULL,
                                            PRIMARY KEY (`no`),
                                            INDEX `apt_seq_to_house_info_idx` (`apt_seq` ASC) VISIBLE,
                                            CONSTRAINT `apt_seq_to_house_info`
                                                FOREIGN KEY (`apt_seq`)
                                                    REFERENCES `houseinfos` (`apt_seq`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 7084512
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE FULLTEXT INDEX idx_apt_seq ON housedeals(apt_seq) WITH PARSER ngram;