package testData;

import io.restassured.response.Response;

public class CustomerCredentials {

    public String email;
    public String password;
    public String name;


    public CustomerCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public CustomerCredentials(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static CustomerCredentials getCustomerCredentials (Customer customer) {
       return new CustomerCredentials(customer.email, customer.password);
    }

    public static CustomerCredentials getCustomerCredentialsWrongEmail(Customer customer) {
        return new CustomerCredentials("wrong" + customer.email, customer.password);
    }

    public static CustomerCredentials getCustomerCredentialsWrongPassword(Customer customer) {
        return new CustomerCredentials(customer.email, "wrong" + customer.password);
    }

    public static CustomerCredentials getCustomerCredentialsNewEmail(Customer customer) {
        return new CustomerCredentials("new" + customer.email, customer.password, customer.name);
    }

    public static CustomerCredentials getCustomerCredentialsNewName(Customer customer) {
        return new CustomerCredentials(customer.email, customer.password, "new" + customer.name);
    }

    public String getEmail() {
        return email;
    }
}
