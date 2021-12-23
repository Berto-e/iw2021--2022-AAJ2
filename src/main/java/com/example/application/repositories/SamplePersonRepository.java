package com.example.application.repositories;

import com.example.application.classes.SamplePerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SamplePersonRepository extends JpaRepository<SamplePerson, Integer> {

}