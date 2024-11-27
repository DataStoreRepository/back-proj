package com.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;

    public AddressDTO(String city, String state, String street, String number, String neighborhood) {
        this.city = city;
        this.state = state;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
    }
}
