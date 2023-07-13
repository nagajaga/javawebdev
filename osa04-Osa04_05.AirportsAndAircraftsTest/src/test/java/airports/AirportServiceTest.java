package airports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirportServiceTest {
    @Autowired
    private AirportService airportService;

    @Autowired
    AirportRepository airportRepository;

    @Test
    public void testDbHasAirport() throws Exception {
        Airport airport = new Airport();
        airport.setIdentifier("HEL");
        airport.setName("Helsinki-Vantaa");
        airportService.create(airport.getIdentifier(), airport.getName());
        assertTrue(airportRepository.findAll().stream().filter(a -> a.getIdentifier().equals("HEL")).count() == 1);

    }

    @Test
    public void dbHasAllAirports() throws Exception{
        Airport airport = new Airport();
        airport.setIdentifier("VAA");
        airport.setName("Vaasa");
        airportService.create(airport.getIdentifier(), airport.getName());

        List<Airport> airports = airportService.list();
        assertEquals(airports.size(), airportRepository.findAll().size());
    }

    @Test
    public void testAirportIsNotAddedTwice() throws Exception {
        Airport airport = new Airport();
        airport.setIdentifier("HEL");
        airport.setName("Helsinki-Vantaa");
        airportService.create(airport.getIdentifier(), airport.getName());
        airportService.create(airport.getIdentifier(), airport.getName());
        assertEquals(1,airportRepository.findAll().size());
    }
    
}
