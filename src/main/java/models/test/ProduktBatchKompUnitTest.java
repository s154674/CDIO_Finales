package models.test;

import models.connector.ImprovedConnector;
import models.dao.DBProduktBatchKompDAO;
import models.dto.ProduktBatchKompDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Frederik on 14-06-2017.
 */
public class ProduktBatchKompUnitTest {

    private DBProduktBatchKompDAO testPBK_DAO;
    private ProduktBatchKompDTO testPBK_DTO;
    private ImprovedConnector conn = new ImprovedConnector();

    @Before
    public void setUp() throws Exception {
        testPBK_DAO = new DBProduktBatchKompDAO();
        testPBK_DTO = new ProduktBatchKompDTO(5, 5, 0.9, 4, 4);
        testPBK_DAO.createProduktBatchKomp(testPBK_DTO);
    }

    @After
    public void tearDown() throws Exception {
        testPBK_DAO.deleteProduktBatchKomp(testPBK_DTO);

    }

    @Rule
    private ExpectedException thrown = ExpectedException.none();

    @Test
    public void notNull() throws Exception{
        assertNotNull(conn);
        assertNotNull(testPBK_DAO);
        assertNotNull(testPBK_DTO);
    }

    @Test
    public void testCreateAndGetPBK() throws Exception {
        ProduktBatchKompDTO testPBK_DTO2 = testPBK_DAO.getProduktBatchKomp(5,5);
        assertEquals(testPBK_DTO, testPBK_DTO2);
        testPBK_DAO.deleteProduktBatchKomp(testPBK_DTO2);
    }

    @Test
    public void testUpdatePBK() throws Exception{
        assertEquals(testPBK_DTO.getRbId(),5);

        System.out.println(testPBK_DAO.getProduktBatchKompList());

        testPBK_DTO.setOprId(3);
        testPBK_DTO.setNetto(10);
        testPBK_DTO.setTara(2);

        testPBK_DAO.updateProduktBatchKomp(testPBK_DTO.getPbId(),testPBK_DTO.getRbId(),testPBK_DTO);

        assertEquals(testPBK_DTO.getOprId(),3);
        assertEquals(testPBK_DTO.getNetto(),10, 0);
        assertEquals(testPBK_DTO.getTara(),2, 0);

        System.out.println(testPBK_DAO.getProduktBatchKompList());

    }

}