package com.mz.hogwarts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mz.hogwarts.pojo.Course;
import com.mz.hogwarts.pojo.House;
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

		Course[] courses = new Course[] {
			new Course("CHA", "Charms", "Charms comprise very wide range of different spells concerned with giving a target new and unexpected properties."),
			new Course("DEF", "Defence Against the Dark Arts", "Defence Against the Dark Arts is a the class that teaches students how to protect themselves against the Dark Arts and how to handle dark creatures."),
			new Course("HER", "Herbology", "Herbology is the study of magical plants and fungi. Students learn how to properly care for and utilise different plants, as well as about their magical properties and what they are used for."),
			new Course("POT", "Potions", "Potions is the exact art and subtle science of creating various liquid mixtures with different magical effects, many of which could not be achieved through spells."),
			new Course("CRE", "Care of Magical Creatures", "Care of Magical Creatures is a class which instructes students about the proper identification, feeding, grooming, riding, breeding, and treatment of various magical beasts."),
			new Course("DIV", "Divination", "Divination is the art of predicting the future. Various methods are taught, including tea leaves,] fire-omens, crystal balls, palmistry, cartomancy, astrology, and dream interpretation."),
		};

		for (int i = 0; i < houses.length; i++) {
			houseRepository.save(houses[i]);
		}

		for (int i = 0; i < courses.length; i++) {
			courseRepository.save(courses[i]);
		}

	}
}

