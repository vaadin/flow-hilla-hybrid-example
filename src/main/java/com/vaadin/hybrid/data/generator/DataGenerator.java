package com.vaadin.hybrid.data.generator;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.hybrid.data.entity.Person;
import com.vaadin.hybrid.data.service.PersonRepository;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PersonRepository personRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (personRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Person entities...");
            ExampleDataGenerator<Person> personRepositoryGenerator = new ExampleDataGenerator<>(Person.class,
                    LocalDateTime.of(2022, 2, 2, 0, 0, 0));
            personRepositoryGenerator.setData(Person::setFirstName, DataType.FIRST_NAME);
            personRepositoryGenerator.setData(Person::setLastName, DataType.LAST_NAME);
            personRepositoryGenerator.setData(Person::setEmail, DataType.EMAIL);
            personRepositoryGenerator.setData(Person::setPhone, DataType.PHONE_NUMBER);
            personRepositoryGenerator.setData(Person::setDateOfBirth, DataType.DATE_OF_BIRTH);
            personRepositoryGenerator.setData(Person::setOccupation, DataType.OCCUPATION);
            personRepositoryGenerator.setData(Person::setImportant, DataType.BOOLEAN_10_90);
            personRepository.saveAll(personRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}