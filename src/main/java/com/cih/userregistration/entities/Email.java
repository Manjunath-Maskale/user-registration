package com.cih.userregistration.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Email {

    public String houseAndStreetAddress;
    public String apartmentDetails;
    public String city;
    public String state;
    public String zipCode;
}
