package com.search.search.attraction.domain.repository;

import com.search.search.attraction.domain.Attractions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionsRepository extends JpaRepository<Attractions, Integer> {
}
