package models.test;

import models.connector.ImprovedConnector;
import models.dao.DBReceptKompDAO;
import models.dto.ReceptKompDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import static org.junit.Assert.*;

/**
 * Created by Frederik on 15-06-2017.
 */
public class ReceptKompUnitTest {

    private ImprovedConnector conn = new ImprovedConnector();
    private DBReceptKompDAO testRK_DAO = new DBReceptKompDAO();
    private ReceptKompDTO testRK_DTO = new ReceptKompDTO(1, 3, 9.0, 0.1);

    @Before
    public void setUp() throws Exception {
    testRK_DAO.createReceptKomp(testRK_DTO);

    }

    @After
    public void tearDown() throws Exception {
    testRK_DAO.deleteReceptKomp(testRK_DTO);

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void notNull() throws Exception{
        assertNotNull(conn);
        assertNotNull(testRK_DAO);
        assertNotNull(testRK_DTO);
    }

    @Test
    public void testCreateAndGetRK() throws Exception{
        ReceptKompDTO testRK_DTO2 = testRK_DAO.getReceptKomp(1,3);
        assertEquals(testRK_DTO2,testRK_DTO);
        testRK_DAO.deleteReceptKomp(testRK_DTO2);
    }

/*
    @Test
    public void testUpdateRK() throws Exception{
        assertEquals(testRK_DTO.getNomNetto(),9,0);

        testRK_DTO.setTolerance(0.2);
        testRK_DTO.setNomNetto(5);

        System.out.println(testRK_DAO.getReceptKompList(1));
        testRK_DAO.updateReceptKomp(1,3, testRK_DTO);
        System.out.println(testRK_DAO.getReceptKompList(1));

        assertEquals(testRK_DTO.getNomNetto(),5,0);
        assertEquals(testRK_DTO.getTolerance(),0.2,0);
    }
    */
}