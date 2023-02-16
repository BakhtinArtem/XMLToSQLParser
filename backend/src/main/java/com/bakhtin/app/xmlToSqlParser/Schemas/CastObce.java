package com.bakhtin.app.xmlToSqlParser.Schemas;

import jakarta.persistence.*;

@Entity
public class CastObce {

    @ManyToOne(targetEntity = Obec.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "CastObce_Obce_FK")
    private Obec obec;

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

    public Obec getObec() {
        return obec;
    }

    public void setObec(Obec obec) {
        this.obec = obec;
    }
}
