package com.example.cinemaplanner.account.model;

import lombok.*;

/**
 * Created by Andreï on 15/04/2016 for ZKY.
 * Object clean returning only useful data.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AccountPublic {

    private int id;
    private String login;
    private boolean isAdmin;
    private String numDossier;
    private String nom;
    private String cp;
    private String ville;
    private String pays;
    private String enseigne;
    private String telephone1;
    private String telephone2;
    private String fax;
    private String administration;
    private String compte;
    private String email;
    private String siret;
    private String numVoie;
    private String nomdelavoie;
    private String complementAdresse;

    public AccountPublic( Account account )
    {
        this.id = account.getId();
        this.login = account.getLogin();
        this.isAdmin = account.isAdmin();
        //columns from client csv
        this.numDossier = account.getNumDossier();
        this.nom = account.getNom();
        this.cp = account.getCp();
        this.ville = account.getVille();
        this.pays = account.getPays();
        this.enseigne = account.getEnseigne();
        this.telephone1 = account.getTelephone1();
        this.telephone2 = account.getTelephone2();
        this.fax = account.getFax();
        this.administration = account.getAdministration();
        this.compte = account.getCompte();
        this.email = account.getEmail();
        this.siret = account.getSiret();
        this.numVoie = account.getNumVoie();
        this.nomdelavoie = account.getNomdelavoie();
        this.complementAdresse = account.getComplementAdresse();
    }
}
