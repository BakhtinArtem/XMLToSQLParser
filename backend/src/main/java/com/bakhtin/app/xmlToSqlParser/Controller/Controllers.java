package com.bakhtin.app.xmlToSqlParser.Controller;

import com.bakhtin.app.xmlToSqlParser.Downloader.IDownloader;
import com.bakhtin.app.xmlToSqlParser.Downloader.ZipDownloader;
import com.bakhtin.app.xmlToSqlParser.Parser.IParser;
import com.bakhtin.app.xmlToSqlParser.Parser.XMLParser;
import com.bakhtin.app.xmlToSqlParser.Processor.IProcessor;
import com.bakhtin.app.xmlToSqlParser.Processor.XMLProcessor;
import com.bakhtin.app.xmlToSqlParser.Schemas.CastObce;
import com.bakhtin.app.xmlToSqlParser.Schemas.Obec;
import com.bakhtin.app.xmlToSqlParser.Schemas.Repository.CastObceRepo;
import com.bakhtin.app.xmlToSqlParser.Schemas.Repository.ObecRepo;
import com.bakhtin.app.xmlToSqlParser.Schemas.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
public class Controllers {

    public static void processXMLToSQLSave(IDownloader downloader, IParser parser, IProcessor processor, String url) {
        processor.process(url, downloader, parser);
    }

    @Autowired
    private XMLProcessor xmlProcessor;

    @Autowired
    private ObecRepo obecRepo;

    @Autowired
    private CastObceRepo castObceRepo;

    @PostMapping(value="/saveUrl")
    public ResponseEntity<String> saveUrl(@RequestBody Response response) {
        try {
            processXMLToSQLSave(
                    new ZipDownloader(),    //  downloader
                    new XMLParser(),        //  parser
                    xmlProcessor,           //  processor
                    response.getUrl()       //  url containing zipped xml
            );
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
        return ResponseEntity.ok("Extract successfully");
    }

    @GetMapping(value = "/getObec")
    public List<Obec> getObec() {
        return obecRepo.findAll();
    }

    @GetMapping(value = "/getCastObce")
    public List<CastObce> getCastObce() {
        return castObceRepo.findAll();
    }

    @GetMapping(value = "/deleteAll")
    public ResponseEntity<String> deleteAllData() {
        castObceRepo.deleteAll();
        obecRepo.deleteAll();
        return ResponseEntity.ok("Deleted");
    }
}
