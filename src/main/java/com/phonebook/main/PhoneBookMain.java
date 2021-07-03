package com.phonebook.main;

import com.phonebook.spring.ApplicationConfig;
import com.phonebook.spring.PhoneBook;
import com.phonebook.spring.PhoneBookFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.*;

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
            try {
                if ("exit".contains(line.toLowerCase())) {
                    renderer.info("Have a good day!...");
                    break;
                }
                if (line.toLowerCase().startsWith("add")) {
                    String[] consoleArguments = line.split(" ");
                    if (consoleArguments.length != 3) {
                        renderer.error("Invalid amount of arguments");
                    }
                    String[] phones = consoleArguments[2].split(",");
                    phoneBook.addNumbers(consoleArguments[1], phones);
                    renderer.show(phoneBook.findAll());

                }
                if ("show".equals(line.toLowerCase())) {
                    renderer.show(phoneBook.findAll());

                }
                if (line.toLowerCase().startsWith("remove_phone")) {
                    String[] consoleArguments = line.split(" ");
                    try {
                        phoneBook.deleteNumber(consoleArguments[1]);
                        renderer.show(phoneBook.findAll());
                    } catch (IllegalArgumentException e){
                        renderer.error("There is no such phone");
                    }
                    break;
                }

            } catch (UnsupportedOperationException e) {
                renderer.error("Unsupported command");
            }
        }
    }

    static ApplicationContext newApplicationContext(String... args) {
        return args.length > 0 && args[0].equals("classPath")
                ? new ClassPathXmlApplicationContext("application-config.xml")
                : new AnnotationConfigApplicationContext(ApplicationConfig.class);
    }

}
