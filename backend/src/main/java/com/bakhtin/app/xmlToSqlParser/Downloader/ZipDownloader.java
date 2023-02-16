package com.bakhtin.app.xmlToSqlParser.Downloader;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipDownloader implements IDownloader{

    final int BUFFER_SIZE = 2048;

    /**
     * Creates directory if no such exist
     * @param dirPath path to required directory
     */
    private void tryCreateDir(String dirPath) {
        try {
            Files.createDirectories(Paths.get(dirPath));
        } catch (Exception ex) {
            //  ignored
        }
    }
    @Override
    public List<String> download(String url, String downloadTo) {
        ArrayList<String> outPaths = new ArrayList<>();                 // contains path to unzipped files
        try {
            InputStream in = new BufferedInputStream(new URL(url).openStream(), BUFFER_SIZE);
            ZipInputStream zipIn = new ZipInputStream(in);
            byte[] buffer = new byte[BUFFER_SIZE];
            ZipEntry entry;
            while((entry = zipIn.getNextEntry()) != null) {
                if (entry.isDirectory())
                    continue;
                tryCreateDir(downloadTo);                               //  create directory where to save unzipped files
                String newPath = downloadTo + entry.getName();          //  path to unzipped file
                outPaths.add(newPath);                                  //  save all paths to container
                File outFile = new File(newPath);
                FileOutputStream out = new FileOutputStream(outFile);
                int len;
                while((len = zipIn.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.close();
            }
            zipIn.closeEntry();
            zipIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outPaths;
    }
}
