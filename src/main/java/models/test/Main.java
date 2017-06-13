package models.test;

import models.DALException;
import models.connector.OldConnector;
import models.dao.*;
import models.dto.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Johan on 08-06-2017.
 */
public class Main {
    private static int ec = 0; //Exceptions thrown counter

    public static void main(String[] args) {
        try {new OldConnector();}
        catch (InstantiationException e) {e.printStackTrace();}
        catch (IllegalAccessException e) {e.printStackTrace();}
        catch (ClassNotFoundException e) {e.printStackTrace();}
        catch (SQLException e) {e.printStackTrace();}

        testDBBrugerDAO();
        testDBProduktBatchDAO();
        testDBProduktBatchKompDAO();
        testDBRaavareDAO();
        testDBRaavareBatchDAO();
        testDBReceptDAO();
        testDBReceptKompDAO();

        System.out.println("\nForventet antal exceptions kastet = 7 \nAntal exceptions kastet: " + ec);
    }

    private static void testDBBrugerDAO() {
        DBBrugerDAO opr = new DBBrugerDAO();

        System.out.println("Henter operatoer nummer 3");
        try { System.out.println(opr.getBruger(3));}
        catch (DALException e) {System.out.println(e.getMessage()); ec++;}

        System.out.println("Indsætter ny operatoer");
        List<String> roller = Arrays.asList("Pharmaseut", "Værkfører", "Laborant");
        BrugerDTO oprDTO = new BrugerDTO(4, "Don Juan", "DJ", "000000-0000", "iloveyou", roller);
        try { opr.createBruger(oprDTO);}
        catch (DALException e) { System.out.println(e.getMessage()); ec++;}

        System.out.println("Henter operatoer nummer 4");
        try { System.out.println(opr.getBruger(4));}
        catch (DALException e) { System.out.println(e.getMessage()); ec++;}

        System.out.println("Opdaterer initialer for operatoer nummer 4");
        oprDTO.setIni("DoJu");
        try { opr.updateBruger(4, oprDTO);}
        catch (DALException e) { System.out.println(e.getMessage()); ec++;}

        System.out.println("Henter operatoer nummer 4");
        try { System.out.println(opr.getBruger(4));}
        catch (DALException e) {System.out.println(e.getMessage());ec++;}

        System.out.println("Henter liste af operatoerer");
        try { System.out.println(opr.getBrugerList());}
        catch (DALException e) {System.out.println(e.getMessage()); ec++;}

        System.out.println("Henter operatoer nummer 5");
        try { System.out.println(opr.getBruger(5));}
        catch (DALException e) {System.out.println(e.getMessage()); ec++;}

        System.out.println("Sletter operator 4 igen");
        try { opr.deleteBruger(oprDTO);}
        catch (DALException e) {System.out.println(e.getMessage()); ec++;}

        System.out.println("Henter liste af operatoerer");
        try { System.out.println(opr.getBrugerList());}
        catch (DALException e) {System.out.println(e.getMessage()); ec++;}
    }

    private static void testDBProduktBatchDAO() {
        DBProduktBatchDAO pb = new DBProduktBatchDAO();

        System.out.println("Henter produktbatch nummer 3");
        try { System.out.println(pb.getProduktBatch(3)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Indsætter nyt produktbatch");
        ProduktBatchDTO pbDTO = new ProduktBatchDTO(6,2,3);
        try { pb.createProduktBatch(pbDTO); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter produktbatch nummer 6");
        try { System.out.println(pb.getProduktBatch(6)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Opdaterer status på produktbatch nummer 6");
        pbDTO.setStatus(1);
        try { pb.updateProduktBatch(pbDTO.getPbId(),pbDTO); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter produktbatch nummer 6");
        try { System.out.println(pb.getProduktBatch(6)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter liste af produktbatches");
        try { System.out.println(pb.getProduktBatchList()); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Produktbatch nummer 7");
        try { System.out.println(pb.getProduktBatch(7)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Sletter produktbatch 6");
        try { pb.deleteProduktBatch(pbDTO); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter liste af produktbatches");
        try { System.out.println(pb.getProduktBatchList()); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }
    }

    private static void testDBProduktBatchKompDAO () {
        DBProduktBatchKompDAO pbk = new DBProduktBatchKompDAO();

        System.out.println("Henter produktbatch komponent med pb_id 5 og rb_id 2");
        try { System.out.println(pbk.getProduktBatchKomp(1,4)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Indsætter ny produktbatch komponent");
        ProduktBatchKompDTO pbkd = new ProduktBatchKompDTO(5, 2, 0.5, 1.99, 2);
        try { pbk.createProduktBatchKomp(pbkd); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter produktbatch komponent med pb_id 5 og rb_id 2");
        try { System.out.println(pbk.getProduktBatchKomp(5,2)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Opdaterer operatoer ID på produktbatch komponent med pb_id 5 og rb_id 2");
        pbkd.setOprId(1);
        try { pbk.updateProduktBatchKomp(pbkd.getPbId(), pbkd.getRbId(), pbkd); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter produktbatch komponent med pb_id 5 og rb_id 2");
        try { System.out.println(pbk.getProduktBatchKomp(5,2)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter liste af produktbatch komponenter");
        try { System.out.println(pbk.getProduktBatchKompList()); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter liste af produktbatch komponenter med id 3");
        try { System.out.println(pbk.getProduktBatchKompList(3)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter produktbatch komponent med pb_id 6 og rb_id 4");
        try { System.out.println(pbk.getProduktBatchKomp(6,4)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Sletter produktbatchkomponent");
        try { pbk.deleteProduktBatchKomp(pbkd); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter liste af produktbatch komponenter");
        try { System.out.println(pbk.getProduktBatchKompList()); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }
    }

    private static void testDBRaavareDAO () {
        DBRaavareDAO rad = new DBRaavareDAO();

        System.out.println("Henter raavare nummer 3");
        try { System.out.println(rad.getRaavare(3)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Indsaettelse af ny raavare");
        RaavareDTO radDTO = new RaavareDTO(8,"Ananas","Pen Pineapple ltd.");
        try { rad.createRaavare(radDTO); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++;}

        System.out.println("Henter raavare nummer 8");
        try { System.out.println(rad.getRaavare(8)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Opdaterer leverandoer af raavare nummer 8");
        radDTO.setLeverandoer("Condiments by Satan");
        try { rad.updateRaavare(radDTO.getRaavareId(),radDTO); }
        catch (DALException e) { System.out.println(e.getMessage());ec++; }

        System.out.println("Henter raavare nummer 8");
        try { System.out.println(rad.getRaavare(4)); }
        catch (DALException e) { System.out.println(e.getMessage());ec++; }

        System.out.println("Henter liste af Raavarer");
        try { System.out.println(rad.getRaavareList()); }
        catch (DALException e) { System.out.println(e.getMessage());ec++; }

        System.out.println("Henter raavare nummer 9");
        try { System.out.println(rad.getRaavare(9)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }
    }

    private static void testDBRaavareBatchDAO () {
        DBRaavareBatchDAO rabd = new DBRaavareBatchDAO();

        System.out.println("Henter raavarebatch nummer 3");
        try { System.out.println(rabd.getRaavareBatch(3)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Indsaetter nyt raavarebatch");
        RaavareBatchDTO rbDTO = new RaavareBatchDTO(8,2,3);
        try { rabd.createRaavareBatch(rbDTO); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter raavarebatch nummer 8");
        try { System.out.println(rabd.getRaavareBatch(8)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Opdaterer maengde på raavarebatch 8");
        rbDTO.setMaengde(500);
        try { rabd.updateRaavareBatch(rbDTO.getRaavareId(),rbDTO); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter raavarebatch nummer 8");
        try { System.out.println(rabd.getRaavareBatch(8)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter liste af raavarebatches");
        try { System.out.println(rabd.getRaavareBatchList()); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter raavarebatch nummer 9");
        try { System.out.println(rabd.getRaavareBatch(9)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }
    }

    private static void testDBReceptDAO () {
        DBReceptDAO red = new DBReceptDAO();

        System.out.println("Henter recept nummer 3");
        try { System.out.println(red.getRecept(3)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Indsaetter ny recept");
        ReceptDTO redDTO = new ReceptDTO(4,"Hawaii");
        try { red.createRecept(redDTO); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++;}

        System.out.println("Henter recept nummer 4");
        try { System.out.println(red.getRecept(4)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Opdaterer receptnavn for recept nummer 4");
        redDTO.setReceptNavn("misfoster");
        try { red.updateRecept(redDTO.getReceptId(),redDTO); }
        catch (DALException e) { System.out.println(e.getMessage());ec++; }

        System.out.println("Henter recept nummer 4");
        try { System.out.println(red.getRecept(4)); }
        catch (DALException e) { System.out.println(e.getMessage());ec++; }

        System.out.println("Henter liste af recepter");
        try { System.out.println(red.getReceptList()); }
        catch (DALException e) { System.out.println(e.getMessage());ec++; }

        System.out.println("Henter recept nummer 5");
        try { System.out.println(red.getRecept(5)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }
    }

    private static void testDBReceptKompDAO () {
        DBReceptKompDAO rekd = new DBReceptKompDAO();

        System.out.println("Henter recept komponent med recept id 1 og raavare id 5");
        try { System.out.println(rekd.getReceptKomp(1,5)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Opretter ny recept komponent");
        ReceptKompDTO rekDTO = new ReceptKompDTO(4, 2, 6.0, 0.1);
        try { rekd.createReceptKomp(rekDTO); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter recept komponent med recept id 4 og raavare id 2");
        try { System.out.println(rekd.getReceptKomp(4,2)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Opdaterer netto vaegt på recept komponent med recept id 4");
        rekDTO.setNomNetto(4);
        try { rekd.updateReceptKomp(rekDTO.getReceptId(), rekDTO.getRaavareId(), rekDTO); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter recept komponent med recept id 4 og raavare id 2");
        try { System.out.println(rekd.getReceptKomp(4,2)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter liste af recept komponenter");
        try { System.out.println(rekd.getReceptKompList()); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter liste af recept komponenter med id 3");
        try { System.out.println(rekd.getReceptKompList(3)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }

        System.out.println("Henter recept komponent med recept id 5 og raavare id 9");
        try { System.out.println(rekd.getReceptKomp(5,9)); }
        catch (DALException e) { System.out.println(e.getMessage()); ec++; }
    }
}