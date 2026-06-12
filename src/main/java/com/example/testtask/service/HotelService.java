package com.example.testtask.service;

import com.example.testtask.dto.HotelExtendedDTO;
import com.example.testtask.dto.HotelSummaryDTO;
import com.example.testtask.model.Hotel;

import java.util.List;

public interface HotelService {

    List<HotelSummaryDTO> findAllHotels();
    HotelExtendedDTO findHotelById(Long id);
    Hotel saveHotel(Hotel hotel);
    void addAmenities(Long id, List<String> amenities);



}
