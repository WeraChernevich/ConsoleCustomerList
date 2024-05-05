import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private final Map<String, Customer> storage;
    private static final Logger queryLogger = (Logger) LogManager.getLogger("QueriesLogger");
    private static final Logger errorLogger = (Logger) LogManager.getLogger("ErrorsLogger");

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) throws IncorrectDataFormatException, PhoneNumberFormatException, EmailFormatException {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        String[] components = data.split("\\s+");
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];

        try {
            storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
            queryLogger.info("Added customer: " + name);
        } catch (Exception e) {
            errorLogger.error("Error adding customer: " + e.getMessage(), e);
            System.out.println("An error occurred while adding a customer: " + e.getMessage());
        }


        if (components.length != 4) {
            throw new IncorrectDataFormatException("Incorrect data format. Expected: name surname email phone");
        }
        // проверка формата номера телефона
        if (!components[3].matches("\\+\\d{11}")) {
            throw new PhoneNumberFormatException("Incorrect phone number format. Expected: +11234567890");
        }
        // проверка формата email
        if (!components[2].matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            throw new EmailFormatException("Incorrect email format");
        }


    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }

    public class IncorrectDataFormatException extends Exception {
        public IncorrectDataFormatException(String message) {
            super(message);
        }
    }
    public class PhoneNumberFormatException extends Exception {
        public PhoneNumberFormatException(String message) {
            super(message);
        }
    }
    public class EmailFormatException extends Exception {
        public EmailFormatException(String message) {
            super(message);
        }
    }
}