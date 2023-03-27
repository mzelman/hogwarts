package com.mz.hogwarts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mz.hogwarts.pojo.House;
import com.mz.hogwarts.pojo.Student;
import com.mz.hogwarts.repository.HouseRepository;
import com.mz.hogwarts.repository.StudentRepository;

@SpringBootApplication
public class HogwartsApplication implements CommandLineRunner {

	@Autowired
	HouseRepository houseRepository;

	@Autowired
	StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(HogwartsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// House[] houses = new House[] {
		// 	new House("Gryffindor"),
		// 	new House("Slytherin"),
		// 	new House("Ravenclaw"),
		// 	new House("Hufflepuff"),
		// };

		// List<Student> students = new ArrayList<Student>();
		// students.add(new Student(1L, "Harry", "Potter", houses[0]));
		// students.add(new Student(2L, "Ron", "Wesley", houses[0]));
		// students.add(new Student(3L, "Draco", "Malfoy", houses[1]));
		// students.add(new Student(4L, "Vincent", "Crabbe", houses[1]));
		// students.add(new Student(5L, "Luna", "Lovegood", houses[2]));
		// students.add(new Student(6L, "Padma", "Patil", houses[2]));
		// students.add(new Student(7L, "Cedric", "Digory", houses[3]));
		// students.add(new Student(8L, "Cho", "Chang", houses[3]));

		// List<Student> students = new ArrayList<Student>();
		// students.add(new Student("Harry", "Potter"));
		// students.add(new Student("Ron", "Wesley"));
		// students.add(new Student("Draco", "Malfoy"));
		// students.add(new Student("Vincent", "Crabbe"));
		// students.add(new Student("Luna", "Lovegood"));
		// students.add(new Student("Padma", "Patil"));
		// students.add(new Student("Cedric", "Digory"));
		// students.add(new Student("Cho", "Chang"));


		// for (int i = 0; i < students.size(); i++) {
		// 	studentRepository.save(students.get(i));
		// }


		// for (int i = 0; i < houses.length; i++) {
		// 	// houses[i].setStudents(students.subList(i, i + 2));
		// 	houseRepository.save(houses[i]);
		}

	}

