package com.example.testtask.dto;

import com.example.testtask.model.Address;
import com.example.testtask.model.ArrivalTime;
import com.example.testtask.model.Contacts;

import java.util.Set;

public record HotelExtendedDTO(Long id, String name, String description, Address address, Contacts contacts,ArrivalTime arrivalTime,
Set<String> amenities, String brand) {

}
