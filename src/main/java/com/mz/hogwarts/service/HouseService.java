package com.mz.hogwarts.service;

import java.util.List;

import com.mz.hogwarts.pojo.House;
import com.mz.hogwarts.pojo.Student;

public interface HouseService {
    public List<House> getHouses();
    public House getHouse(Long id);
    public List<Student> getStudents(Long houseId);
    public void addPoints(Long houseId, String points);
    public void substractPoints(Long houseId, String points);
}
