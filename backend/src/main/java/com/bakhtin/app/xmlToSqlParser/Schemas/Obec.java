package com.bakhtin.app.xmlToSqlParser.Schemas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Obec {

    @Id
    @Column(unique = true)
    private long kod;

    @Column
    private String nazev;

    public long getKod() {
        return kod;
    }

    public void setKod(long kod) {
        this.kod = kod;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }
}
