package com.mz.hogwarts.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Code cannot be blank.")
    @NonNull
    @Size(min = 3, max = 3, message = "Code must be 3 chatacters.")
    @Pattern(regexp = "^[A-Z0-9]*$", message = "Code must consist of only uppercase letters and numbers.")
    @Column(name = "code", nullable = false)
    private String code;
    
    @NotBlank(message = "Subject cannot be blank.")
    @NonNull
    @Size(min = 2, message = "Subject must be at least 2 characters.")
    @Size(max = 30, message = "Subject cannot be longer than 30 characters.")
    @Column(name = "subject", nullable = false)
    private String subject;

    @NotBlank(message = "Description cannot be blank.")
    @NonNull
    @Size(min = 10, message = "Description must be at least 10 characters.")
    @Size(max = 200, message = "Description cannot be longer than 200 characters.")
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany
    @JoinTable(
        name = "course_student",
        joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id")
    )
    private List<Student> students;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Grade> grades;
}
