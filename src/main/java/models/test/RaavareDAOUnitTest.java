package models.test;

import models.connector.ImprovedConnector;
import models.dao.DBRaavareDAO;
import models.dto.RaavareDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Frederik on 13-06-2017.
 */

public class RaavareDAOUnitTest {
    private RaavareDTO testRaavareDTO;
    private ImprovedConnector conn = new ImprovedConnector();
    private DBRaavareDAO testRaavareDAO;

    @Before
    public void setUp() throws Exception {
        testRaavareDAO = new DBRaavareDAO();
        testRaavareDTO = new RaavareDTO(99,"TestMeat", "Testers");
        testRaavareDAO.createRaavare(testRaavareDTO);
    }

    @After
    public void tearDown() throws Exception {
        this.testRaavareDAO.deleteRaavare(testRaavareDTO);
    }

    @Test
    public void notNull(){
        assertNotNull(this.conn);
        assertNotNull(this.testRaavareDAO);
        assertNotNull(this.testRaavareDTO);
    }

    @Test
    public void testGetRaavareAndCreateRaavare() throws Exception{
        RaavareDTO testRaavareDAO2 = testRaavareDAO.getRaavare(99);
        assertEquals(testRaavareDAO2,testRaavareDTO);
        testRaavareDAO.deleteRaavare(testRaavareDAO2);
    }

    @Test
    public void testUpdateRaavare() throws Exception{
        System.out.println(testRaavareDAO.getRaavareList());
        assertEquals(testRaavareDTO.getRaavareNavn(),"TestMeat");

        testRaavareDTO.setLeverandoer("NahFam");
        testRaavareDTO.setRaavareNavn("Meat");

        testRaavareDAO.updateRaavare(testRaavareDTO.getRaavareId(), testRaavareDTO);

        assertEquals(testRaavareDTO.getLeverandoer(), "NahFam");
        assertEquals(testRaavareDTO.getRaavareNavn(),"Meat");

        System.out.println(testRaavareDAO.getRaavareList());


    }
}