package models.test;

import models.connector.ImprovedConnector;
import models.dao.DBReceptDAO;
import models.dto.ReceptDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by Frederik on 15-06-2017.
 */
public class ReceptDTOUnitTest {

    private ImprovedConnector conn = new ImprovedConnector();
    private ReceptDTO testReceptDTO = new ReceptDTO(5, "Meat");
    private DBReceptDAO testReceptDAO = new DBReceptDAO();

    @Before
    public void setUp() throws Exception {
        testReceptDAO.createRecept(testReceptDTO);

    }

    @After
    public void tearDown() throws Exception {
        testReceptDAO.deleteRecept(testReceptDTO);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void notNull() {
        assertNotNull(testReceptDAO);
        assertNotNull(testReceptDTO);
        assertNotNull(conn);
    }

    @Test
    public void testCreateAndGetRecept() throws Exception {
        ReceptDTO testReceptDTO2 = testReceptDAO.getRecept(5);
        assertEquals(testReceptDTO2, testReceptDTO);
        testReceptDAO.deleteRecept(testReceptDTO2);
    }

    @Test
    public void testUpdateRecept() throws Exception {
        assertEquals(testReceptDTO.getReceptNavn(), "Meat");
        System.out.println(testReceptDAO.getReceptList());

        testReceptDTO.setReceptNavn("MoreMeat");

        testReceptDAO.updateRecept(5, testReceptDTO);

        assertEquals(testReceptDTO.getReceptNavn(), "MoreMeat");
        System.out.println(testReceptDAO.getReceptList());

    }
}