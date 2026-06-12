package com.example.testtask.service.impl;

import com.example.testtask.dto.HotelExtendedDTO;
import com.example.testtask.dto.HotelSummaryDTO;
import com.example.testtask.model.Hotel;
import com.example.testtask.repository.HotelRepository;
import com.example.testtask.service.HotelService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<HotelSummaryDTO> findAllHotels() {

        return hotelRepository.findAll().stream()
                .map(this::convertToSummaryDTO)
                .collect(Collectors.toList());
    }

    //To SummaryDTO
    private HotelSummaryDTO convertToSummaryDTO(Hotel hotel) {
        String fullAddress = String.format("%s %s, %s, %s, %s",
                hotel.getAddress().getHouseNumber(),
                hotel.getAddress().getStreet(),
                hotel.getAddress().getCity(),
                hotel.getAddress().getPostCode(),
                hotel.getAddress().getCountry());

        return new HotelSummaryDTO(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                fullAddress,
                hotel.getContacts().getPhone());
    }

    @Override
    public HotelExtendedDTO findHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        return convertToDetailsDTO(hotel);
    }

    // TO ExtendedDTO
    private HotelExtendedDTO convertToDetailsDTO(Hotel hotel) {
        return new HotelExtendedDTO(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getBrand(),
                hotel.getAddress(),
                hotel.getContacts(),
                hotel.getArrivalTime(),
                Optional.ofNullable(hotel.getAmenities()));
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        if (hotel == null) {
            throw new IllegalArgumentException("hotel cannot be null");
        }
        return hotelRepository.save(hotel);
    }

    @Override
    @Transactional
    public void addAmenities(Long id, List<String> amenities) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        hotel.getAmenities().addAll(amenities);
        hotelRepository.save(hotel);
    }

}

