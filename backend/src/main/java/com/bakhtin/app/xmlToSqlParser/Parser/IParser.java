package com.bakhtin.app.xmlToSqlParser.Parser;

public interface IParser {

    public void parse(String filePath);
    public Object getTokensByName(String name);

}
