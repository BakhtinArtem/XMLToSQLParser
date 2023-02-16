package com.bakhtin.app.xmlToSqlParser.Processor;

import com.bakhtin.app.xmlToSqlParser.Downloader.IDownloader;
import com.bakhtin.app.xmlToSqlParser.Parser.IParser;
import com.bakhtin.app.xmlToSqlParser.Schemas.CastObce;
import com.bakhtin.app.xmlToSqlParser.Schemas.Obec;
import com.bakhtin.app.xmlToSqlParser.Schemas.Repository.CastObceRepo;
import com.bakhtin.app.xmlToSqlParser.Schemas.Repository.ObecRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class XMLProcessor implements IProcessor {

    private final int requiredUnzippedFiles = 1;
    private final int indexOfFile = 0;
    private final String OBEC_TAG = "vf:Obec";

    private final String OBEC_KOD = "obi:Kod";

    private final String OBEC_NAZEV = "obi:Nazev";
    private final String CAST_OBCE_TAG = "vf:CastObce";
    private final String CAST_OBCE_KOD = "coi:Kod";
    private final String CAST_OBCE_NAZEV = "coi:Nazev";
    private final String CAST_OBCE_PARENT = "coi:Obec";
    @Autowired
    private ObecRepo obecRepo;

    @Autowired
    private CastObceRepo castObceRepo;

    private static Iterable<Node> iterable(NodeList nodes) {
        return () -> new Iterator<Node>() {

            private int index = 0;          //  starting index

            @Override
            public boolean hasNext() {
                return index < nodes.getLength();
            }

            @Override
            public Node next() {
                if (hasNext())
                    return nodes.item(index++);
                throw new NoSuchElementException();
            }
        };
    }

    @Override
    public void process(String url, IDownloader downloader, IParser parser) throws IllegalArgumentException {
        List<String> outPaths = downloader.download(url, IDownloader.downloadTo);           //  download and unzip file
        if (outPaths.size() != requiredUnzippedFiles)
            throw new IllegalArgumentException(
                    String.format("Zip should contain one file, but found %d", outPaths.size()));
        String fileToParse = outPaths.get(indexOfFile);
        parser.parse(fileToParse);                                          //  parse given XML file
        processObec((NodeList) parser.getTokensByName(OBEC_TAG));           //  process and save Obec to repo
        processCastObce((NodeList) parser.getTokensByName(CAST_OBCE_TAG));  //  process and save Cast Obce to repo
    }

    private boolean isElementNode(Node node) {
        return node.getNodeType() == Node.ELEMENT_NODE;
    }

    private void findCastObceParent(Node nodeProperty, CastObce newCastObce) {
        NodeList parentProperty = nodeProperty.getChildNodes();
        long parentKod = -1;
        for (Node parentPropertyNode : iterable(parentProperty)) {
            if(!isElementNode(parentPropertyNode))
                continue;
            Element parentPropertyElement = (Element) parentPropertyNode;
            if (Objects.equals(parentPropertyElement.getTagName(), OBEC_KOD)) {
                parentKod = Long.parseLong(parentPropertyElement.getTextContent());
                break;
            }
        }
        Obec parent = obecRepo.findByKod(parentKod).get(0);
        newCastObce.setObec(parent);
    }

    private void processCastObce(NodeList nodes) {
        for (Node node : iterable(nodes)) {
            if (!isElementNode(node))
                continue;
            CastObce newCastObce = new CastObce();
            for (Node nodeProperty : iterable(node.getChildNodes())) {
                if (!isElementNode(nodeProperty))
                    continue;
                Element nodePropertyElement = (Element) nodeProperty;
                switch (nodePropertyElement.getTagName()){
                    case CAST_OBCE_KOD -> newCastObce.setKod(Long.parseLong(nodePropertyElement.getTextContent()));
                    case CAST_OBCE_NAZEV -> newCastObce.setNazev(nodePropertyElement.getTextContent());
                    case CAST_OBCE_PARENT -> findCastObceParent(nodeProperty, newCastObce);
                }
            }
            castObceRepo.save(newCastObce);
        }
    }

    private void processObec(NodeList nodes) {
        for (Node node : iterable(nodes)) {
            if (!isElementNode(node))                //  skip not element node
                continue;
            Obec newObec = new Obec();
            for (Node nodeProperty : iterable(node.getChildNodes())) {
                if (!isElementNode(nodeProperty))
                    continue;
                Element nodePropertyElement = (Element) nodeProperty;
                switch (nodePropertyElement.getTagName()) {
                    case OBEC_KOD -> newObec.setKod(Long.parseLong(nodePropertyElement.getTextContent()));
                    case OBEC_NAZEV -> newObec.setNazev(nodePropertyElement.getTextContent());
                }
            }
            obecRepo.save(newObec);
        }
    }
}
