package controllers.Weight;

import models.*;
import models.dao.*;
import models.dao.interfaces.*;
import models.dto.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan on 12-06-2017.
 */
public class WeightController implements Runnable {
    private SocketMessage sim = new SocketMessage();
    private SocketMessage som = new SocketMessage();
    private Client c = new Client(sim, som);
    private int temp_ra_id, bruger_id, pb_id;
    private String temp;
    private IProduktBatchDAO produktBatchDao = new DBProduktBatchDAO();
    private IReceptDAO receptDao = new DBReceptDAO();
    private IBrugerDAO brugerDao = new DBBrugerDAO();
    private IReceptKompDAO receptKompDao = new DBReceptKompDAO();
    private IRaavareDAO raavareDao = new DBRaavareDAO();
    private IRaavareBatchDAO raavareBatchDao = new DBRaavareBatchDAO();
    private IProduktBatchKompDAO produktBatchKompDao = new DBProduktBatchKompDAO();
    private ProduktBatchDTO pbdto;
    private ReceptDTO rdto;
    private BrugerDTO bdto;


    @Override
    public void run() {
        while (true) {
            try {
                //Bruger ID og OK
                bruger_id = askForId("Indtast Bruger ID");
                bdto = brugerDao.getBruger(bruger_id);
                awaitOk(bdto.getOprNavn() + "?");

                //Produktbatch ID og OK
                pb_id = askForId("Indtast Produktbatch ID");
                pbdto = produktBatchDao.getProduktBatch(pb_id);
                rdto = receptDao.getRecept(pbdto.getReceptId());
                awaitOk(rdto.getReceptNavn() + "?");

                //Tarrering
                awaitOk("Toem Vaeten for Tarra");
                tara();

                //Loop over alle ting der skal vejes
                pbdto.setStatus(1);
                List<ReceptKompDTO> receptKomponenter = null;
                List<ProduktBatchKompDTO> produktbatchKomponenter = null;

                receptKomponenter = receptKompDao.getReceptKompList(rdto.getReceptId());
                List<RaavareDTO> raavarer = new ArrayList<RaavareDTO>();
                for (ReceptKompDTO receptKompDTO : receptKomponenter) {
                    raavarer.add(raavareDao.getRaavare(receptKompDTO.getRaavareId()));

                    int rbid;
                    double tara;
                    double netto;
                    ProduktBatchKompDTO afvejning;
                    boolean placed;
                    for (ReceptKompDTO receptKompDTOto : receptKomponenter) {
                        awaitOk("Stil beholder");
                        Thread.sleep(100);

                        tara = sCommand(); //Vægten af beholderen
                        tara();
                        Thread.sleep(100);

                        rbid = askForId("Indtast RBID");
                        //double min = receptKompDTO.getNomNetto()-(receptKompDTOto.getTolerance()*receptKompDTO.getNomNetto());
                        //double max = receptKompDTO.getNomNetto()+(receptKompDTOto.getTolerance()*receptKompDTO.getNomNetto());
                        //For at gøre testing nemmere har jeg lavet min og max til det her istedet.
                        double min = 0.0;
                        double max = 5.0;
                        String raavarenavn = "" + raavareDao.getRaavare(receptKompDTO.getRaavareId()).getRaavareNavn();
                        awaitOk(receptKompDTO.getNomNetto()+"g af " + raavarenavn); //TODO Hvad og hvormeget skal tilføjes
                        do {
                            som.setMessage("K 3");
                            Thread.sleep(100);
                            await4K();
                            Thread.sleep(100);
                            som.setMessage("K 1");
                            Thread.sleep(100);

                            netto = sCommand();

                            if (min > netto || max < netto) {
                                awaitOk("Proev igen");
                            }
                        } while (min > netto || max < netto);

                        //Opretter PBK'en
                        Thread.sleep(100);
                        afvejning = new ProduktBatchKompDTO(pbdto.getPbId(), rbid, tara, netto, bruger_id);
                        Thread.sleep(100);
                        //TODO Der skal være noget der holde styr på om der ligger noget i forvejen, for ikke at få DALexception
                        produktBatchKompDao.createProduktBatchKomp(afvejning);

                        awaitOk("PBK Oprettet");
                    }
                    awaitOk("PB Faerdigt");
                    break;
                }
            } catch (DALException e) {
                errorMSG("Data fejl, proev igen");
                som.setMessage("K 1");
            } catch (InterruptedException e) {
                errorMSG("Fejl, proev igen");
                som.setMessage("K 1");
            } catch (CancelException e) {
                errorMSG(e.getMessage());
                som.setMessage("K 1");
            }
        }
    }

    private void awaitOk(String txt) throws CancelException {
        boolean accepted;
        som.setMessage("RM20 3 \"" +txt+ "\" \"\" \"\"");
        accepted = false;

        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {/*Denne er tom med vilje */}
            if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("C")) {
                throw new CancelException("Du afbroed afvejningen");
            }
            if (sim.getMessage().split(" ")[0].equals("RM20") && (sim.getMessage().split(" ")[1].equals("L") || sim.getMessage().split(" ")[1].equals("I"))) {
                throw new CancelException("Proev igen");
            }
            if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("A")) {
                accepted = true;
                sim.setMessage("");
            }
        } while (!accepted);
    }

    private int askForId(String txt) throws CancelException {
        //TODO Hvis man ikke for et tal skal det håndteres
        int id =0;
        boolean accepted;
        som.setMessage("RM20 3 \"" + txt + "\" \"\" \"\"");
        accepted = false;
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {/*Denne er tom med vilje */}
            if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("C")) {
                throw new CancelException("Du afbroed afvejningen");
            }
            if (sim.getMessage().split(" ")[0].equals("RM20") && (sim.getMessage().split(" ")[1].equals("L") || sim.getMessage().split(" ")[1].equals("I"))) {
                throw new CancelException("Proev igen");
            }
            if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("A")) {
                id = Integer.parseInt(sim.getMessage().substring(8, sim.getMessage().length() - 1));
                accepted = true;
                sim.setMessage("");
            }
        } while (!accepted);
        return id;
    }

    private void await4K() throws CancelException {
        boolean fourk = false;
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {/*Denne er tom med vilje */}
            if (sim.getMessage().equals("K C 4")){
                fourk = true;
            }
        } while (!fourk);
    }

    private void tara() throws CancelException {
        som.setMessage("T");
        boolean confirmation = false;
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {/*Denne er tom med vilje */}
            if (sim.getMessage().split(" ")[0].equals("T") && sim.getMessage().split(" ")[1].equals("S")) {
                confirmation = true;
                sim.setMessage("");
            }
        } while (!confirmation);
    }

    private double sCommand() throws CancelException {
        som.setMessage("S");
        boolean weightRegistered = false;
        double temp = 0;
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {/*Denne er tom med vilje */}
            if (sim.getMessage().split(" ")[0].equals("S") && sim.getMessage().split(" ")[1].equals("S")) {
                temp = Double.parseDouble(sim.getMessage().substring(9, 14));
                sim.setMessage("");
                weightRegistered = true;}
        } while (!weightRegistered);
        return temp;
    }

    private void errorMSG(String txt) {
        boolean accepted;
        System.out.println(txt);
        som.setMessage("RM20 3 \"" +txt+ "\" \"\" \"\"");
        accepted = false;

        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {/*Denne er tom med vilje */}
            if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("C")) {
            }
            if (sim.getMessage().split(" ")[0].equals("RM20") && (sim.getMessage().split(" ")[1].equals("L") || sim.getMessage().split(" ")[1].equals("I"))) {
                som.setMessage("RM20 8 \"" + txt + "\" \"\" \"&0\"");
            }
            if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("A")) {
                accepted = true;
                sim.setMessage("");
            }
        } while (!accepted);
    }
}


