package com.example.testtask.service.impl;

import com.example.testtask.dto.HotelExtendedDTO;
import com.example.testtask.dto.HotelSummaryDTO;
import com.example.testtask.model.Hotel;
import com.example.testtask.repository.HotelRepository;
import com.example.testtask.service.HotelService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    @Transactional
    public Hotel saveHotel(HotelExtendedDTO hotelEx) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelEx.name());
        hotel.setDescription(hotelEx.description());
        hotel.setAddress(hotelEx.address());
        hotel.setBrand(hotelEx.brand());
        hotel.setContacts(hotelEx.contacts());
        hotel.setArrivalTime(hotelEx.arrivalTime());

        Set<String> amenities = hotelEx.amenities()
                .orElseGet(HashSet::new);
        hotel.setAmenities(amenities);

        return hotelRepository.save(hotel);
    }

    @Override
    @Transactional
    public Set<String> addAmenities(Long id, List<String> amenities) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        hotel.getAmenities().addAll(amenities);
        hotelRepository.save(hotel);
        return hotel.getAmenities();
    }

    @Override
    public Map<String, Long> getHistogram(String param) {

        List<Hotel> hotels = hotelRepository.findAll();

        return switch (param.toLowerCase()) {
            case "brand" -> hotels.stream()
                    .filter(hotel -> hotel.getBrand() != null)
                    .collect(Collectors.groupingBy(Hotel::getBrand, Collectors.counting()));
            case "city" -> hotels.stream()
                    .filter(hotel -> hotel.getAddress().getCity() != null)
                    .collect(Collectors.groupingBy(hotel -> hotel.getAddress().getCity(),Collectors.counting()));
            case "country" -> hotels.stream()
                    .filter(hotel -> hotel.getAddress().getCountry() != null)
                    .collect(Collectors.groupingBy(hotel -> hotel.getAddress().getCountry(),Collectors.counting()));
            case "amenities" -> hotels.stream()
                    .filter(hotel -> hotel.getAmenities() != null)
                    .flatMap(hotel -> hotel.getAmenities().stream())
                    .collect(Collectors.groupingBy(amenity -> amenity, Collectors.counting()));

            default -> throw new IllegalArgumentException("Invalid param: " + param);
        };
    }

    public List<HotelSummaryDTO> findHotelByParam(String name, String brand, String city, String country, String amenities) {
        return hotelRepository.findAll().stream()
                .filter(hotel -> matchField(hotel.getName(), name))
                .filter(hotel -> matchField(hotel.getBrand(), brand))
                .filter(hotel -> hotel.getAddress() != null && matchField(hotel.getAddress().getCity(), city))
                .filter(hotel -> hotel.getAddress() != null && matchField(hotel.getAddress().getCountry(), country))
                .filter(hotel -> hotel.getAmenities() == null || amenities == null || amenities.isBlank() ||
                        hotel.getAmenities().stream().anyMatch(a -> a.equalsIgnoreCase(amenities)))
                .map(this::convertToSummaryDTO)
                .collect(Collectors.toList());
    }
    //Check null and convert param to lower case
    private boolean matchField(String fieldValue, String searchValue) {
        return searchValue == null || searchValue.isBlank() ||
                (fieldValue != null && fieldValue.toLowerCase().contains(searchValue.toLowerCase()));
    }


}

