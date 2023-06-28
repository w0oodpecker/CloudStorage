package com.example.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CloudFile {

    private String filename;
    private long size;

}


