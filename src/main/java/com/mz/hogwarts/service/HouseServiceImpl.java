package com.mz.hogwarts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mz.hogwarts.pojo.House;
import com.mz.hogwarts.pojo.Student;
import com.mz.hogwarts.repository.HouseRepository;
import com.mz.hogwarts.repository.StudentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HouseServiceImpl implements HouseService {

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    StudentRepository studentRepository;

    public List<House> getHouses() {
        return (List<House>) houseRepository.findAll(Sort.by("id"));
    }

    public House getHouse(Long id) {
        return unwrapHouse(houseRepository.findById(id), id);
    }

    public List<Student> getStudents(Long houseId) {
        return studentRepository.findByHouseId(houseId, Sort.by("lastName").and(Sort.by("firstName")));
    }

    public void addPoints(Long houseId, String points) {
        House house = getHouse(houseId);
        house.setPoints(house.getPoints() + Integer.parseInt(points));
        houseRepository.save(house);
    }

    public void substractPoints(Long houseId, String points) {
        House house = getHouse(houseId);
        house.setPoints(house.getPoints() - Integer.parseInt(points));
        houseRepository.save(house);
    }

    static House unwrapHouse(Optional<House> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new IndexOutOfBoundsException(id);
}
}