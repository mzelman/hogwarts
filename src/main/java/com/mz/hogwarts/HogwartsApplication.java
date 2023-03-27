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
			new House("Gryffindor"),
			new House("Slytherin"),
			new House("Ravenclaw"),
			new House("Hufflepuff"),
		};

		Student[] students = new Student[] {
			new Student("Harry", "Potter"),
			new Student("Ron", "Wesley"),
			new Student( "Draco", "Malfoy"),
			new Student( "Vincent", "Crabbe"),
			new Student( "Luna", "Lovegood"),
			new Student( "Padma", "Patil"),
			new Student("Cedric", "Digory"),
			new Student("Ernie", "Macmillan")
		};

		students[0].setHouse(houses[0]);
		students[1].setHouse(houses[0]);
		students[2].setHouse(houses[1]);
		students[3].setHouse(houses[1]);
		students[4].setHouse(houses[2]);
		students[5].setHouse(houses[2]);
		students[6].setHouse(houses[3]);
		students[7].setHouse(houses[3]);
		

		Course[] courses = new Course[] {
			new Course("CHA", "Charms", "Charms comprise very wide range of different spells concerned with giving a target new and unexpected properties."),
			new Course("DEF", "Defence Against the Dark Arts", "Defence Against the Dark Arts is a the class that teaches students how to protect themselves against the Dark Arts and how to handle dark creatures."),
			new Course("HER", "Herbology", "Herbology is the study of magical plants and fungi. Students learn how to properly care for and utilise different plants, as well as about their magical properties and what they are used for."),
			new Course("POT", "Potions", "Potions is the exact art and subtle science of creating various liquid mixtures with different magical effects, many of which could not be achieved through spells."),
			new Course("CRE", "Care of Magical Creatures", "Care of Magical Creatures is a class which instructes students about the proper identification, feeding, grooming, riding, breeding, and treatment of various magical beasts."),
			new Course("DIV", "Divination", "Divination is the art of predicting the future. Various methods are taught, including tea leaves,] fire-omens, crystal balls, palmistry, cartomancy, astrology, and dream interpretation."),
		};

		houses[0].setStudents(Arrays.asList(students[0], students[1]));
		houses[1].setStudents(Arrays.asList(students[2], students[3]));
		houses[2].setStudents(Arrays.asList(students[4], students[5]));
		houses[3].setStudents(Arrays.asList(students[6], students[7]));

		for (int i = 0; i < houses.length; i++) {
			houseRepository.save(houses[i]);
		}

		for (int i = 0; i < students.length; i++) {
			studentRepository.save(students[i]);
		}

		for (int i = 0; i < courses.length; i++) {
			courseRepository.save(courses[i]);
		}

	}
}

