package com.example.testtask.repository;


import com.example.testtask.model.Address;
import com.example.testtask.model.ArrivalTime;
import com.example.testtask.model.Contacts;
import com.example.testtask.model.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;


import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class HotelRepositoryTest {

    @Autowired
    private  HotelRepository hotelRepository;
    @Autowired
    private TestEntityManager entityManager;

    private  Hotel testHotel;

    @BeforeEach
    public void setUp() {
        testHotel = new Hotel();
        testHotel.setName("Hotel Eleon");
        testHotel.setDescription("Hotel Eleon - good");
        testHotel.setBrand("Eleon");

        Address address = new Address();
        address.setHouseNumber("11");
        address.setStreet("Bulochka");
        address.setCity("City");
        address.setCountry("Country");
        address.setPostCode("12345");
        testHotel.setAddress(address);

        Contacts contacts = new Contacts();
        contacts.setPhone("+375 29 123 23 32");
        contacts.setEmail("eleon@mail.ru");
        testHotel.setContacts(contacts);

        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn("12:00");
        arrivalTime.setCheckOut("14:00");
        testHotel.setArrivalTime(arrivalTime);

        testHotel.setAmenities(Set.of("Free wifi", "Free Parking"));

        entityManager.persist(testHotel);
        entityManager.flush();
    }

    @Test
    void findHotelById() {
        Hotel found = hotelRepository.findById(testHotel.getId()).orElse(null);

        assertNotNull(found, "Hotel should be found");
        assertEquals("Hotel Eleon", found.getName());
        assertEquals("Hotel Eleon - good", found.getDescription());
        assertEquals("Eleon", found.getBrand());
        assertEquals("City", found.getAddress().getCity());
        assertEquals("+375 29 123 23 32", found.getContacts().getPhone());
        assertEquals("12:00", found.getArrivalTime().getCheckIn());
        assertTrue(found.getAmenities().contains("Free wifi"));
    }
    @Test
    void findHotelByIdNotFound() {
        Hotel found = hotelRepository.findById(12L).orElse(null);
        assertNull(found);
    }

    @Test
    void findAllHotels() {
        List<Hotel> found = hotelRepository.findAll();

        assertNotNull(found);
        assertEquals(1, found.size());
    }
}
