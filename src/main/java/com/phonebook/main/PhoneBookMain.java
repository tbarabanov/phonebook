package com.phonebook.main;

import com.phonebook.spring.ApplicationConfig;
import com.phonebook.spring.PhoneBook;
import com.phonebook.spring.PhoneBookFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * PhoneBook entry point
 */
public class PhoneBookMain {

    public static void main(String[] args) {
        ApplicationContext context = newApplicationContext(args);

        Scanner sc = new Scanner(System.in);
        sc.useDelimiter(System.getProperty("line.separator"));

        PhoneBook phoneBook = context.getBean("phoneBook", PhoneBook.class);
        PhoneBookFormatter renderer = (PhoneBookFormatter) context.getBean("phoneBookFormatter");

        renderer.info("type 'exit' to quit.");
        while (sc.hasNext()) {
            String line = sc.nextLine();
            System.out.println(line);
            try {
                if ("exit".contains(line.toLowerCase())) {
                    renderer.info("Have a good day...");
                    break;
                }
                if (line.toLowerCase().startsWith("add")) {
                    String[] consoleArguments = line.split(" ");
                    String[] phones = consoleArguments[2].split(",");
                    phoneBook.addNumber(consoleArguments[1], phones);
                    renderer.info(phoneBook.findAll().toString());
                    break;
                }
                if ("show".equals(line.toLowerCase())) {
                    renderer.info(phoneBook.findAll().entrySet().stream()
                            .map(e -> e.getKey() + " " + e.getValue())
                            .collect(Collectors.joining("\n")));

                    break;
                }
                if (line.toLowerCase().startsWith("remove_phone")) {
                    String[] consoleArguments = line.split(" ");
                    try {
                        phoneBook.deleteNumber(consoleArguments[1]);
                        renderer.info(phoneBook.findAll().toString());
                    } catch (IllegalArgumentException e){
                        renderer.error("There is no such phone");
                    }
                    break;
                }
                throw new UnsupportedOperationException("Unsupported operation!");
            } catch (Exception e) {
                renderer.error(e);
            }
        }
    }

    static ApplicationContext newApplicationContext(String... args) {
        return args.length > 0 && args[0].equals("classPath")
                ? new ClassPathXmlApplicationContext("application-config.xml")
                : new AnnotationConfigApplicationContext(ApplicationConfig.class);
    }

}
