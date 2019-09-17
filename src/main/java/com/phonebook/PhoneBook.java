package com.phonebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * PhoneBook service implementation
 */
@Service
public class PhoneBook {

    // @Autowired - can be here
    private InMemoryRepository repository;

    public PhoneBook() {
        // be careful this.repository will not be initialised if injection on setter is chosen
    }

    /**
     * injection is supported on constructor level.
     *
     * @param inMemoryRepository
     */
    @Autowired
    public PhoneBook(InMemoryRepository inMemoryRepository) {
        this.repository = inMemoryRepository;
    }

    /**
     * injection is supported on setter level
     *
     * @param repository
     */
    public void setRepository(InMemoryRepository repository) {
        this.repository = repository;
    }

    /**
     * @return all pairs of type {name: [phone1, phone2]}
     */
    public Map<String, Set<String>> findAll() {
        return repository.findAll();
    }

    /**
     * please add required methods here
     */
}
