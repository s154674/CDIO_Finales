package models.test;

import models.connector.ImprovedConnector;
import models.dao.DBRaavareBatchDAO;
import models.dto.RaavareBatchDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by Frederik on 15-06-2017.
 */
public class RaavareBatchUnitTest {
    private ImprovedConnector conn = new ImprovedConnector();
    private DBRaavareBatchDAO testRB_DAO = new DBRaavareBatchDAO();
    private RaavareBatchDTO testRB_DTO = new RaavareBatchDTO(8,2,3);

    @Before
    public void setUp() throws Exception {
    testRB_DAO.createRaavareBatch(testRB_DTO);
    }

    @After
    public void tearDown() throws Exception {
    testRB_DAO.deleteRaavareBatch(testRB_DTO);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void notNull() throws Exception{
        assertNotNull(testRB_DAO);
        assertNotNull(testRB_DTO);
        assertNotNull(conn);
    }

    @Test
    public void testCreateAndGetRB() throws Exception{
        RaavareBatchDTO testTB_DTO2 = testRB_DAO.getRaavareBatch(8);
        assertEquals(testTB_DTO2,testRB_DTO);
        testRB_DAO.deleteRaavareBatch(testTB_DTO2);
    }

    @Test
    public void testUpdateRB() throws Exception{
        System.out.println(testRB_DAO.getRaavareBatchList());
        assertEquals(testRB_DTO.getRaavareId(),2);

        testRB_DTO.setMaengde(50);
        testRB_DTO.setRaavareId(4);

        testRB_DAO.updateRaavareBatch(8,testRB_DTO);

        assertEquals(testRB_DTO.getMaengde(),50,0);
        assertEquals(testRB_DTO.getRaavareId(),4);
        System.out.println(testRB_DAO.getRaavareBatchList());


    }

}