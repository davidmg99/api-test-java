package com.dmartinez.api.service.urlgenerator.impl;

import com.dmartinez.api.service.urlgenerator.FileUrlGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class LocalFileUrlGenerator implements FileUrlGenerator {
    @Override
    public String generateUrl(String filename) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/invoices/")
                .path(filename)
                .toUriString();
    }
}
