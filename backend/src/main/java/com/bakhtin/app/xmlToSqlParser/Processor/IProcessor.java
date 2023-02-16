package com.bakhtin.app.xmlToSqlParser.Processor;

import com.bakhtin.app.xmlToSqlParser.Downloader.IDownloader;
import com.bakhtin.app.xmlToSqlParser.Parser.IParser;

public interface IProcessor {
    void process(String url, IDownloader downloader, IParser parser);
}
