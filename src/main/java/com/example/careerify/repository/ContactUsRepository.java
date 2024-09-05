package com.example.careerify.repository;

import com.example.careerify.model.ContactUs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactUsRepository extends MongoRepository<ContactUs, String> {
}
