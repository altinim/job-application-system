package com.example.careerify.controller;

import com.example.careerify.model.ContactUs;
import com.example.careerify.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/contact-us")
public class ContactUsController {

    private final ContactUsService service;

    @Autowired
    public ContactUsController(ContactUsService service) {
        this.service = service;
    }

    @GetMapping
    public List<ContactUs> getAllMessages() {
        return service.getAllMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactUs> getMessageById(@PathVariable String id) {
        return service.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ContactUs> createMessage(@Valid @RequestBody ContactUs message) {
        ContactUs createdMessage = service.createMessage(message);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String id) {
        if (service.getMessageById(id).isPresent()) {
            service.deleteMessage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
