package models.connector;


// erstat konstanterne nedenfor

public abstract class Constant
{
    public static final String
            // mysql://bmln0et36dmu9x76:v5xu4jayr68jyd7w@e7qyahb3d90mletd.chr7pe7iynqr.eu-west-1.rds.amazonaws.com:3306/bj52demyb9qowt4b
            server					= "e7qyahb3d90mletd.chr7pe7iynqr.eu-west-1.rds.amazonaws.com",  // database-serveren
            database				= "bj52demyb9qowt4b",  //"jdbcdatabase", // navnet paa din database = dit studienummer
            username				= "bmln0et36dmu9x76", // dit brugernavn = dit studienummer
            password				= "v5xu4jayr68jyd7w"; // dit password som du har valgt til din database

    public static final int
            port					= 3306;
}
