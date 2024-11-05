package ca.mcgill.ecse321.GameOn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import ca.mcgill.ecse321.GameOn.exception.reviewException;

import ca.mcgill.ecse321.GameOn.repository.ReviewRepository;
import ca.mcgill.ecse321.GameOn.model.Review;

//import ca.mcgill.ecse321.eventregistration.model.Event;
//import ca.mcgill.ecse321.eventregistration.model.Person;


@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;


    public Review findReviewById(int id){
        Review existingReview = reviewRepo.findReviewById(int id);
        if(existingReview == null){
            throw new IllegalArgumentException("There is no review with ID " + id + ".");
        }
        return existingReview;

    }

    public void likeReview(){}
    public void postReview(){}
    public int getStars(){}
    public void addReply(){}


    @Transactional
    public Person createPerson(String name, String email, String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password too short.");
        }

        Date now = Date.valueOf(LocalDate.now());
        Person personToCreate = new Person(name, email, password, now);
        return personRepo.save(personToCreate);
    }

    public Registration register(Person person, Event event) {
        Registration existingRegistration = findRegistration(person, event);
        if (existingRegistration != null) {
            // Already registered
            return existingRegistration;
        } else {
            Registration newRegistration = new Registration(new Registration.Key(person, event));
            return registrationRepo.save(newRegistration);
        }
    }


    @Transactional
    public void unregister(Person person, Event event) {
        Registration existingRegistration = findRegistration(person, event);
        registrationRepo.delete(existingRegistration);
    }

    public Registration findRegistration(Person person, Event event) {
        Registration reg = registrationRepo.findRegistrationByKey(new Registration.Key(person, event));
        if (reg == null) {
            throw new EventRegistrationException(HttpStatus.NOT_FOUND,
                    String.format("Person %d is not registered for event %d.", person.getId(), event.getId()));
        }
        return reg;
    }

    public int countRegistrantsForEvent(Event event) {
        return registrationRepo.countRegistrationsByKeyEvent(event);
    }
}