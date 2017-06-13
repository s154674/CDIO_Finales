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


    @Override
    public void run() {
        while (true) {
            som.setMessage("RM20 8 \"Indtast Bruger ID\" \"\" \"&4\"");
            boolean ib = true;
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("A")) try {
                    sim.setMessage(sim.getMessage().replace("\"", ""));
                    bruger_id = Integer.parseInt(sim.getMessage().split(" ")[2]);
                    temp = (brugerDao.getBruger(bruger_id).getOprNavn());
                    sim.setMessage("");
                    ib = false;
                } catch (DALException e) {
                }  /*
                else if (sim.getMessage().split(" ")[1].equals("C")) {
                    //crash to desktop
                    ib = false;
                } */
            } while (ib);

            som.setMessage("RM20 8 \"Er du " + temp + "?\" \"\" \"&0\"");
            boolean bo = true;
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("A")) {
                    bo = false;
                    sim.setMessage("");
                } /* else if (sim.getMessage().split(" ")[1].equals("C")) {
                    //Crash to desktop
                    bo = false;
                    sim.setMessage("");
                }*/
            } while (bo);

            som.setMessage("RM20 8 \"Indtast Produktbatch ID\" \"\" \"&4\"");
            boolean ub = true;
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("A")) try {
                    pbdto = produktBatchDao.getProduktBatch(Integer.parseInt(sim.getMessage().substring(8, sim.getMessage().length() - 1)));
                    rdto = receptDao.getRecept(pbdto.getReceptId());
                    temp = rdto.getReceptNavn();
                    ub = false;
                    sim.setMessage("");
                } catch (DALException e) {
                } /* else if (sim.getMessage().split(" ")[1].equals("C")) {
                    //Crash to desktop
                    ub = false;
                    sim.setMessage("");
                }*/
            } while (ub);

            som.setMessage("RM20 8 \"Recepten " + temp + "?\" \"\" \"&0\"");
            boolean bu = true;
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("A")) {
                    bu = false;
                    sim.setMessage("");
                }
                 /* else if (sim.getMessage().split(" ")[1].equals("C")) {
                    //Crash to desktop
                    bu = false;
                    sim.setMessage("");
                }*/
            } while (bu);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

            som.setMessage("RM20 8 \"Toem vaegten for tarra\"  \"\" \"&0\"");
            boolean pu = true;
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("A")) {
                    pu = false;
                    sim.setMessage("");
                }
                 /* else if (sim.getMessage().split(" ")[1].equals("C")) {
                    //Crash to desktop
                    pu = false;
                    sim.setMessage("");
                }*/
            } while (pu);

            som.setMessage("T");
            boolean pi = true;
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                if (sim.getMessage().split(" ")[0].equals("T") && sim.getMessage().split(" ")[1].equals("S")) {
                    pi = false;
                    sim.setMessage("");
                }
                 /* else if (sim.getMessage().split(" ")[1].equals("C")) {
                    //Crash to desktop
                    pi = false;
                    sim.setMessage("");
                }*/
            } while (pi);

            pbdto.setStatus(1);
            List<ReceptKompDTO> receptKomponenter = null;
            List<ProduktBatchKompDTO> produktbatchKomponenter = null;
            try {
                receptKomponenter = receptKompDao.getReceptKompList(rdto.getReceptId());
                List<RaavareDTO> raavarer = new ArrayList<RaavareDTO>();
                for (ReceptKompDTO receptKompDTO : receptKomponenter) {
                    raavarer.add(raavareDao.getRaavare(receptKompDTO.getRaavareId()));
                }

            } catch (DALException e) {
            }
            int rbid;
            double tara;
            double netto;
            ProduktBatchKompDTO afvejning;
            boolean placed;
            for (ReceptKompDTO receptKompDTO : receptKomponenter) {
                awaitOk("Stil beholder");
                som.setMessage("S");
                try { Thread.sleep(1000);} catch (InterruptedException e) {}
                netto = Double.parseDouble(sim.getMessage().substring(8, 12));
                System.out.println(netto);
                sim.setMessage("");
                som.setMessage("T");
                sim.setMessage("");
                try { Thread.sleep(1000);} catch (InterruptedException e) {}
                rbid = askForId("Indtast RBID");
                netto = 100;
                double min = receptKompDTO.getNomNetto()-receptKompDTO.getTolerance()*receptKompDTO.getNomNetto();
                double max = receptKompDTO.getNomNetto()+receptKompDTO.getTolerance()*receptKompDTO.getNomNetto();
                do {
                    som.setMessage("K 3");
                    await4K();
                    som.setMessage("K 1");
                    try { Thread.sleep(1000);} catch (InterruptedException e) {}
                    som.setMessage("S");
                    try { Thread.sleep(1000);} catch (InterruptedException e) {}
                    netto = Double.parseDouble(sim.getMessage().substring(8, 12));

                    if (min > netto || max < netto){
                        awaitOk("Udenfor graense");
                    }
                } while (min > netto || max < netto);

                //Tar´rer, afje , whatever
                tara = receptKompDTO.getTolerance(); // sættes altis til tolerancen
                afvejning = new ProduktBatchKompDTO(pbdto.getPbId(), rbid, tara, netto, bruger_id);
                try{produktBatchKompDao.createProduktBatchKomp(afvejning);} catch (DALException e) {}
                awaitOk("PBK Oprettet");
            }
            awaitOk("PB Faerdigt");
            break;


            //while(sim.getMessage()==null){
            //try { wait(100); } catch (InterruptedException e) {};
        }
    }

    private void awaitOk(String txt) {
        boolean accepted;
        som.setMessage("RM20 8 \"" + txt + "\" \"\" \"&0\"");
        accepted = false;
        do {
            if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("A")) {
                accepted = true;
                sim.setMessage("");
            }
        } while (!accepted);
    }

    private int askForId(String txt) {
        int id =0;
        boolean accepted;
        som.setMessage("RM20 8 \"" + txt + "\" \"\" \"&4\"");
        accepted = false;
        do {
            if (sim.getMessage().split(" ")[0].equals("RM20") && sim.getMessage().split(" ")[1].equals("A")) {
                id = Integer.parseInt(sim.getMessage().substring(8, sim.getMessage().length() - 1));
                accepted = true;
                sim.setMessage("");
            }
        } while (!accepted);
        return id;
    }

    private void await4K(){
        boolean gotem = false;
        do {
            if (sim.getMessage().equals("K C 4")){
                gotem = true;
            }
        } while (!gotem);
    }
}


