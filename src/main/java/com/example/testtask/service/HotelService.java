package com.example.testtask.service;

import com.example.testtask.model.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> findAllHotels();
    Hotel findHotelById(Long id);
    Hotel saveHotel(Hotel hotel);



}
