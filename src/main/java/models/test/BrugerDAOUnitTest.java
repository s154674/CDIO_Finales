package models.test;

import models.connector.ImprovedConnector;
import models.dao.DBBrugerDAO;
import models.dto.BrugerDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Frederik on 14-06-2017.
 */
public class BrugerDAOUnitTest {
    private ImprovedConnector conn = new ImprovedConnector();
    private DBBrugerDAO testOprDAO;
    private List<String> roller = Arrays.asList("Pharmaseut","Værkfører", "Laborant");
    private BrugerDTO testOprDTO;


    @Before
    public void setUp() throws Exception {
    testOprDAO  = new DBBrugerDAO();
    testOprDTO  = new BrugerDTO(50, "Don Juan", "DJ", "000000-0000", "iloveyou", roller);
    testOprDAO.createBruger(testOprDTO);
    }

    @After
    public void tearDown() throws Exception {
    testOprDAO.deleteBruger(testOprDTO);
    }

    @Test
    public void notNull() throws Exception{
        assertNotNull(conn);
        assertNotNull(testOprDTO);
        assertNotNull(testOprDAO);
    }


    @Test
    public void testGetOperator() throws Exception{
        BrugerDTO testBrugerDTO2 = testOprDAO.getBruger(50);
        assertEquals(testBrugerDTO2,testOprDTO);
        testOprDAO.deleteBruger(testBrugerDTO2);

    }

    @Test
    public void testUpdateOperator() throws Exception{
        assertEquals(testOprDTO.getIni(),"DJ");
        System.out.println(testOprDAO.getBrugerList());

        List<String> roller2 = Arrays.asList("Pharmaseut","Laborant");

        testOprDTO.setIni("DoJu");
        testOprDTO.setPassword("password123");
        testOprDTO.setCpr("012345-6789");
        testOprDTO.setRoller(roller2);

        testOprDAO.updateBruger(testOprDTO.getOprId(),testOprDTO);

        System.out.println(testOprDAO.getBrugerList());

        assertEquals(testOprDTO.getPassword(), "password123");
        assertEquals(testOprDTO.getIni(), "DoJu");
        assertEquals(testOprDTO.getCpr(), "012345-6789");
        assertThat(testOprDTO.getRoller(),is(roller2));
        assertThat(testOprDTO.getRoller(),is(not(roller2)));

    }



}