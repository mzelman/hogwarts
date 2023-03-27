package com.mz.hogwarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mz.hogwarts.pojo.House;

public interface HouseRepository extends JpaRepository<House, Long> {
    
}
