package com.mz.hogwarts.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mz.hogwarts.pojo.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByCoursesId(Long courseId, Sort sort);
    List<Student> findByCoursesId(Long courseId);
    List<Student> findByHouseId(Long houseId, Sort sort);
    List<Student> findByHouseId(Long houseId);
    Student findByFirstNameAndLastName(String firstName, String LastName);
}
