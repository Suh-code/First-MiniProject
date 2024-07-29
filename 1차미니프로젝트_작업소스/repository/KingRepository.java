package com.eureka.king.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eureka.king.entity.King;

public interface KingRepository extends JpaRepository<King, Integer> {

}
