package models.connector;


// erstat konstanterne nedenfor

public abstract class Constant
{
    public static final String
            server					= "localhost",  // database-serveren
            database				= "medstuff",  //"jdbcdatabase", // navnet paa din database = dit studienummer
            username				= "root", // dit brugernavn = dit studienummer
            password				= ""; // dit password som du har valgt til din database

    public static final int
            port					= 3306;
}
