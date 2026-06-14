package com.example.testtask.service;

import com.example.testtask.dto.HotelExtendedDTO;
import com.example.testtask.dto.HotelSummaryDTO;
import com.example.testtask.model.Hotel;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HotelService {

    List<HotelSummaryDTO> findAllHotels();
    HotelExtendedDTO findHotelById(Long id);
    Hotel saveHotel(HotelExtendedDTO hotelEx);
    Set<String> addAmenities(Long id, List<String> amenities);
    Map<String, Long> getHistogram(String param);
    List<HotelSummaryDTO> findHotelByParam(String name, String brand, String city, String country, String amenity);



}
