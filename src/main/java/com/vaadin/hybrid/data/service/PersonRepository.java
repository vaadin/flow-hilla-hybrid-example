package com.vaadin.hybrid.data.service;

import com.vaadin.hybrid.data.entity.Person;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, UUID> {

}