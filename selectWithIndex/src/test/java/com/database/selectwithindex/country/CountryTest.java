package com.database.selectwithindex.country;

import com.database.selectwithindex.country.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CountryTest {
    @Autowired
    private CountryRepository countryRepository;

    @Test
    void unique10() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 1000; j++) {
                countryRepository.save(new Country("A" + i, "A" + i + "_city" + j, j % 100 + 1));
            }
        }
    }

    @Test
    void unique1000() {
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 10; j++) {
                countryRepository.save(new Country("B" + i, "B" + i + "_city" + j, i % 100 + 1));
            }
        }
    }

    @Test
    void unique100() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                countryRepository.save(new Country("C" + i, "C" + i + "_city" + j, j % 100 + 1));
            }
        }
    }
}