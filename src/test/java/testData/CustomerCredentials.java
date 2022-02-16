package testData;

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

    public static CustomerCredentials getCustomerCredentialsIncorrectEmail(Customer customer) {
        return new CustomerCredentials("wrong" + customer.email, customer.password);
    }

    public static CustomerCredentials getCustomerCredentialsIncorrectPassword(Customer customer) {
        return new CustomerCredentials(customer.email, "wrong" + customer.password);
    }

    public static CustomerCredentials getCustomerCredentialsNewEmail(Customer customer) {
        return new CustomerCredentials("new" + customer.email, customer.password, customer.name);
    }

    public static CustomerCredentials getCustomerCredentialsNewName(Customer customer) {
        return new CustomerCredentials(customer.email, customer.password, "new" + customer.name);
    }

}
