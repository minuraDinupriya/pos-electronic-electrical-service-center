package edu.icet.crm.dto;

public class CustomerBean {
    private String customer_id;
    private String customer_name;
    private String contact_number;
    private String email_address;

    public CustomerBean(String customer_id, String customer_name, String contact_number, String email_address) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.contact_number = contact_number;
        this.email_address = email_address;
    }
    public CustomerBean(){}

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }
}
