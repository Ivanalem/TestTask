package com.example.testtask.dto;

import com.example.testtask.model.Address;
import com.example.testtask.model.ArrivalTime;
import com.example.testtask.model.Contacts;

import java.util.Optional;
import java.util.Set;

public record HotelExtendedDTO(Long id, String name, String description,String brand, Address address, Contacts contacts, ArrivalTime arrivalTime,
                               Optional<Set<String>> amenities) {

}
