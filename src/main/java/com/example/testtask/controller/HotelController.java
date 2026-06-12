package com.example.testtask.controller;

import com.example.testtask.dto.HotelExtendedDTO;
import com.example.testtask.model.Address;
import com.example.testtask.model.ArrivalTime;
import com.example.testtask.model.Contacts;
import com.example.testtask.model.Hotel;
import com.example.testtask.service.HotelService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/property-view/hotels")
    public List<Hotel> getAllHotels() {
        return hotelService.findAllHotels();
    }

    @GetMapping("/property-view/hotels/{id}")
    public Hotel getHotelById(@PathVariable Long id){
        return hotelService.findHotelById(id);
    }

    @PostMapping("/property-view/hotels")
    @Transactional
    public Hotel saveHotel(@RequestBody HotelExtendedDTO hotelEx){
        Hotel hotel = new Hotel();
        hotel.setName(hotelEx.name());
        hotel.setDescription(hotelEx.description());
        hotel.setAddress(hotelEx.address());
        hotel.setBrand(hotelEx.brand());
        hotel.setContacts(hotelEx.contacts());
        hotel.setArrivalTime(hotelEx.arrivalTime());
        hotel.setAmenities(hotelEx.amenities());
        return hotelService.saveHotel(hotel);
    }
    }
