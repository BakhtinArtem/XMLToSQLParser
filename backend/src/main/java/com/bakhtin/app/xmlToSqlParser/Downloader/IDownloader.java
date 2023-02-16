package com.bakhtin.app.xmlToSqlParser.Downloader;

import java.util.List;

public interface IDownloader {
    String downloadTo = "downloads/";
    public List<String> download(String url, String downloadTo);
}
