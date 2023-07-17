package com.chernenkov.webservlet.util;

import com.chernenkov.webservlet.exception.ServiceException;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PropertiesReader {
    public Path getFileFromResource(String filename) throws ServiceException{
        URL resource;
        Path path = null;
        resource = getClass().getClassLoader().getResource(filename);
        if (resource!=null){
            try {
                path = Paths.get(resource.toURI());
            } catch (URISyntaxException e) {
                throw new ServiceException(e);
            }
        } else {
            throw new ServiceException("File not found " + filename);
        }
        return path;
    }

}
