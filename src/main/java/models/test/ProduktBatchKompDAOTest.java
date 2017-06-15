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
public class ProduktBatchKompDAOTest {

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
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void notNull() throws Exception{
        assertNotNull(conn);
        assertNotNull(testPBK_DAO);
        assertNotNull(testPBK_DTO);
    }

    @Test
    public void testCreatePBK() throws Exception{
    }

    @Test
    public void testGetPBK() throws Exception {
       System.out.println(testPBK_DAO.getProduktBatchKomp(2,5));
       System.out.println(testPBK_DAO.getProduktBatchKompList());
       System.out.println(testPBK_DAO.getProduktBatchKompList(2));

    }



}