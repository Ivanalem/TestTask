package com.example.testtask.controller;

import com.example.testtask.dto.HotelExtendedDTO;
import com.example.testtask.dto.HotelSummaryDTO;
import com.example.testtask.model.Hotel;
import com.example.testtask.service.HotelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("hotels")
    public List<HotelSummaryDTO> getAllHotels() {
        return hotelService.findAllHotels();
    }

    @GetMapping("hotels/{id}")
    public HotelExtendedDTO getHotelById(@PathVariable Long id) {
        return hotelService.findHotelById(id);
    }

    @PostMapping("hotels")
    public Hotel saveHotel(@RequestBody HotelExtendedDTO hotelEx) {
        return hotelService.saveHotel(hotelEx);
    }

    @PostMapping("hotels/{id}/amenities")
    public void addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        hotelService.addAmenities(id, amenities);
    }

    @GetMapping("histogram/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelService.getHistogram(param);
    }

    @GetMapping("search")
    public List<HotelSummaryDTO> findHotelByParam(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String brand,
                                                  @RequestParam(required = false) String city,
                                                  @RequestParam(required = false) String country,
                                                  @RequestParam(required = false) String amenities) {
        return hotelService.findHotelByParam(name, brand, city, country, amenities);
    }
}
