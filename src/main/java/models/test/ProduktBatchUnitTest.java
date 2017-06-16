package models.test;

import models.connector.ImprovedConnector;
import models.dao.DBProduktBatchDAO;
import models.dto.ProduktBatchDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Frederik on 14-06-2017.
 */
public class ProduktBatchUnitTest {

    private ImprovedConnector conn = new ImprovedConnector();
    private ProduktBatchDTO testPB_DTO;
    private DBProduktBatchDAO testPB_DAO;

    @Before
    public void setUp() throws Exception {
        testPB_DTO = new ProduktBatchDTO(6,2,3);
        testPB_DAO = new DBProduktBatchDAO();
        testPB_DAO.createProduktBatch(testPB_DTO);
    }

    @After
    public void tearDown() throws Exception {
        testPB_DAO.deleteProduktBatch(testPB_DTO);

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void notNull(){
        assertNotNull(conn);
        assertNotNull(testPB_DAO);
        assertNotNull(testPB_DTO);
    }

    @Test
    public void testCreateAndGetPB() throws Exception{
       ProduktBatchDTO testPB_DTO2 = testPB_DAO.getProduktBatch(6);
       assertEquals(testPB_DTO2,testPB_DTO);
       testPB_DAO.deleteProduktBatch(testPB_DTO2);
    }

    @Test
    public void testUpdatePB() throws Exception{
        assertEquals(testPB_DTO.getPbId(),6);
        System.out.println(testPB_DAO.getProduktBatchList());

        testPB_DTO.setReceptId(3);
        testPB_DTO.setStatus(2);

        testPB_DAO.updateProduktBatch(testPB_DTO.getPbId(), testPB_DTO);

        assertEquals(testPB_DTO.getReceptId(), 3);
        assertEquals(testPB_DTO.getStatus(),2);
    }


}
