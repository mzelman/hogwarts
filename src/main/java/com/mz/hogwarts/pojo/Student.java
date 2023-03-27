package com.mz.hogwarts.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student", uniqueConstraints = {@UniqueConstraint (columnNames = {"house_id"})})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Pattern(regexp="^[\\p{L}-]*$", message = "First name cannot consist of characters other than letters and \"-\".")
    @Size(max = 20, message = "First name cannot be longer than 20 characters.")
    @NotBlank(message = "First name cannot be blank.")
    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Pattern(regexp="^[\\p{L}-]*$", message = "Last name cannot consist of characters other than letters and \"-\".")
    @Size(max = 20, message = "Last name cannot be longer than 20 characters.")
    @NotBlank(message = "Last name cannot be blank.")
    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "house_id", referencedColumnName = "id")
    private House house;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Grade> grades;

}
