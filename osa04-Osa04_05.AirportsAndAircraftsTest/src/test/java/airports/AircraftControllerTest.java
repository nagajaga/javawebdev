package airports;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AircraftControllerTest {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get("/aircrafts"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("aircrafts"))
                .andExpect(model().attributeExists("airports"));
    }

    @Test
    public void testAircraftIsAddedToDb() throws Exception {
        mockMvc.perform(post("/aircrafts")
                .param("name", "HA-LOL"))
                .andExpect(redirectedUrl("/aircrafts"));

        List<Aircraft> aircrafts = aircraftRepository.findAll();
        
        assertTrue(aircrafts.stream().filter(a -> a.getName().equals("HA-LOL")).count() == 1);
    }

    @Test
    public void testAircraftIsAddedToModel() throws Exception {
        mockMvc.perform(post("/aircrafts")
                .param("name", "XP-55"))
                .andExpect(redirectedUrl("/aircrafts"));

        MvcResult res = mockMvc.perform(get("/aircrafts"))
                .andExpect(status().isOk())
                .andReturn();

        List<Aircraft> aircrafts = (List<Aircraft>) res.getModelAndView().getModel().get("aircrafts");
        assertTrue(aircrafts.stream().filter(a -> a.getName().equals("XP-55")).count() == 1);
    }

}
