package com.example.careerify.service;

import com.example.careerify.model.ContactUs;
import com.example.careerify.repository.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ContactUsService {

    private final ContactUsRepository repository;

    @Autowired
    public ContactUsService(ContactUsRepository repository) {
        this.repository = repository;
    }

    public List<ContactUs> getAllMessages() {
        return repository.findAll();
    }

    public Optional<ContactUs> getMessageById(String id) {
        return repository.findById(id);
    }

    public ContactUs createMessage(@Valid ContactUs message) {
        return repository.save(message);
    }

    public void deleteMessage(String id) {
        repository.deleteById(id);
    }
}
