package com.bakhtin.app.xmlToSqlParser.Schemas.Repository;

import com.bakhtin.app.xmlToSqlParser.Schemas.Obec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ObecRepo extends JpaRepository<Obec, Long> {

    List<Obec> findByKod(long kod);
}
