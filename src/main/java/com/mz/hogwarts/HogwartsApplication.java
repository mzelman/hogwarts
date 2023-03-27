package com.mz.hogwarts;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mz.hogwarts.pojo.Course;
import com.mz.hogwarts.pojo.House;
import com.mz.hogwarts.pojo.Student;
import com.mz.hogwarts.repository.CourseRepository;
import com.mz.hogwarts.repository.GradeRepository;
import com.mz.hogwarts.repository.HouseRepository;
import com.mz.hogwarts.repository.StudentRepository;

@SpringBootApplication
public class HogwartsApplication implements CommandLineRunner {

	@Autowired
	HouseRepository houseRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	GradeRepository gradeRepository;

	public static void main(String[] args) {
		SpringApplication.run(HogwartsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		House[] houses = new House[] {
			new House(1L, "Gryffindor", 0, null),
			new House(2L, "Slytherin", 0, null),
			new House(3L, "Ravenclaw", 0, null),
			new House(4L, "Hufflepuff", 0, null),
		};

		Student[] students = new Student[] {
			new Student(1L, "Harry", "Potter", houses[0], null, null),
			new Student(2L, "Ron", "Wesley", houses[0], null, null),
			new Student(3L, "Draco", "Malfoy", houses[1], null, null),
			new Student(4L, "Vincent", "Crabbe", houses[1], null, null),
			new Student(5L, "Luna", "Lovegood", houses[2], null, null),
			new Student(6L, "Padma", "Patil", houses[2], null, null),
			new Student(7L, "Cedric", "Digory", houses[3], null, null),
			new Student(8L, "Ernie", "Macmillan", houses[3], null, null)
		};
		// students.add(new Student(1L, "Harry", "Potter", houses[0], null, null));
		// students.add(new Student(2L, "Ron", "Wesley", houses[0], null, null));
		// students.add(new Student(3L, "Draco", "Malfoy", houses[1], null, null));
		// students.add(new Student(4L, "Vincent", "Crabbe", houses[1], null, null));
		// students.add(new Student(5L, "Luna", "Lovegood", houses[2], null, null));
		// students.add(new Student(6L, "Padma", "Patil", houses[2], null, null));
		// students.add(new Student(7L, "Cedric", "Digory", houses[3], null, null));
		// students.add(new Student(8L, "Cho", "Chang", houses[3], null, null));

		Course[] courses = new Course[] {
			new Course(1L, "CHA", "Charms", "Charms comprise very wide range of different spells concerned with giving a target (be it an object or an individual) new and unexpected properties and/or making the target perform certain actions, among other possible effects.", null, null),
			new Course(2L, "DEF", "Defence Against the Dark Arts", "Defence Against the Dark Arts is a the class that teaches students how to protect themselves against the Dark Arts and how to handle dark creatures.", null, null),
			new Course(3L, "HER", "Herbology", "Herbology is the study of magical plants and fungi. Students learn how to properly care for and utilise different plants, as well as about their magical properties and what they are used for.", null, null),
			new Course(4L, "POT", "Potions", "Potions is the exact art and subtle science of creating various liquid mixtures with different magical effects, many of which could not be achieved through spells.", null, null),
			new Course(5L, "CRE", "Care of Magical Creatures", "Care of Magical Creatures is a class which instructes students about the proper identification, feeding, grooming, riding, breeding, and treatment of various magical beasts.", null, null),
			new Course(5L, "DIV", "Divination", "Divination is the art of predicting the future. Various methods are taught, including tea leaves,] fire-omens, crystal balls, palmistry, cartomancy, astrology, and dream interpretation.", null, null),
		};

		// Grade[] grades = new Grade[] {
		// 	new Grade(1L, "5", students[3], courses[1]),
		// 	new Grade(2L, "3", students[7], courses[2]),
		// 	new Grade(3L, "1", students[2], courses[1]),
		// 	new Grade(4L, "5", students[1], courses[0]),
		// 	new Grade(5L, "5", students[6], courses[5]),
		// };


		for (int i = 0; i < students.length; i++) {
			studentRepository.save(students[i]);
		}

		houses[0].setStudents(Arrays.asList(students[0], students[1]));
		houses[1].setStudents(Arrays.asList(students[2], students[3]));
		houses[2].setStudents(Arrays.asList(students[4], students[5]));
		houses[3].setStudents(Arrays.asList(students[6], students[7]));

		for (int i = 0; i < houses.length; i++) {
			houseRepository.save(houses[i]);
		}

		for (int i = 0; i < courses.length; i++) {
			courseRepository.save(courses[i]);
		}

	}
}

