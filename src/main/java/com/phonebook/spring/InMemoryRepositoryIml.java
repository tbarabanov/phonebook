package com.phonebook.spring;

import com.phonebook.main.InMemoryRepository;
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
        Set<String> numbersForName = new HashSet<>();
        for (Map.Entry<String, Set<String>> pair : this.data.entrySet()) {
            if (name.equals(pair.getKey())) {
                 numbersForName = pair.getValue();
            }
        }
        return numbersForName;
    }


    @Override
    public String findNameByPhone(String phone) {
        String name = null;
        for (Map.Entry<String, Set<String>> pair : this.data.entrySet()){
            for (String number: pair.getValue()) {
                if (number.equals(phone)){
                    name = pair.getKey();
                }
            }
        }
        return name;
    }

    @Override
    public void addPhones(String name, String[] phones) {
        this.data.put(name, new HashSet<>(Arrays.asList(phones)));
        findAllPhonesByName(name).addAll(Arrays.asList(phones));
    }

    @Override
    public void removePhone(String phone) {
        for (Map.Entry<String, Set<String>> pair : this.data.entrySet()){
            pair.getValue().removeIf(number -> number.equals(phone));
            if (pair.getValue().isEmpty()) {
                this.data.remove(pair.getKey());
            }
        }
    }
}
