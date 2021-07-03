package com.phonebook.spring;

import com.phonebook.main.InMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * PhoneBook service implementation
 */
@Service
@Scope(scopeName = "singleton")
public class PhoneBook {
    //    @Autowired
    private InMemoryRepository repository;

    public PhoneBook() {
        // be careful this.repository will not be initialised if injection on setter is chosen
    }

    /**
     * injection is supported on constructor level.
     *
     * @param repository
     */
    @Autowired
    public PhoneBook(InMemoryRepository repository) {
        this.repository = repository;
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

    public void deleteNumber(String phone) {
        if (repository.findNameByPhone(phone) == null){
            throw new IllegalArgumentException("There is no person with such name in phonebook");
        } else {
            repository.removePhone(phone);
        }
    }

    public void addNumbers(String name, String[] phones) {
        repository.addPhones(name, phones);
    }
}
