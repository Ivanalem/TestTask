package com.example.testtask.service.impl;

import com.example.testtask.dto.HotelSummaryDTO;
import com.example.testtask.model.Hotel;
import com.example.testtask.repository.HotelRepository;
import com.example.testtask.service.HotelService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> findAllHotels() {

        return hotelRepository.findAll();
    }
        @Override
        public Hotel findHotelById (Long id){
            return hotelRepository.findHotelsById(id);
        }
        @Override
        public Hotel saveHotel(Hotel hotel){
            if (hotel == null) {
                throw new IllegalArgumentException("hotel cannot be null");
            }
        return hotelRepository.save(hotel);
        }

    }

