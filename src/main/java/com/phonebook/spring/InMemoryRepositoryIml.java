package com.phonebook.spring;

import com.phonebook.main.InMemoryRepository;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Keeps phoneBook data in memory in ordered in accordance to addition.
 */
@Repository
public class InMemoryRepositoryIml implements InMemoryRepository {

    private Map<String, Set<String>> data;

    /**
     * no args constructor
     */
    public InMemoryRepositoryIml() {
        // LinkedHashMap is chosen because usually iteration order matters
        this(new LinkedHashMap<>());
    }

    /**
     * this constructor allows to inject initial data to the repository
     *
     * @param data
     */
    public InMemoryRepositoryIml(Map<String, Set<String>> data) {
        this.data = new LinkedHashMap<>(data);
    }

    @Override
    public Map<String, Set<String>> findAll() {
        return new LinkedHashMap<>(this.data);
    }

    @Override
    public Set<String> findAllPhonesByName(String name) {
        throw new UnsupportedOperationException("Implement it!");
    }

    @Override
    public String findNameByPhone(String phone) {
        throw new UnsupportedOperationException("Implement it!");
    }

    @Override
    public void addPhone(String name, String[] phones) {
        Set<String> phonesSet = new HashSet<String>(Arrays.asList(phones));
        this.data.put(name, phonesSet);
    }

    @Override
    public void removePhone(String phone) {
        for (HashMap.Entry<String, Set<String>> pair : this.data.entrySet()){
            pair.getValue().removeIf(number -> number.equals(phone));
        }
    }
}
