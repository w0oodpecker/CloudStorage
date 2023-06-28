package com.example.cloudstorage.model;

import com.example.cloudstorage.component.CloudTools;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CloudError {

    private Integer id;
    private String message;

    public CloudError(String message) {
        this.id = Integer.parseInt(CloudTools.getRandomCode());
        this.message = message;
    }
}
