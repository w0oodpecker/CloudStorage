package com.example.cloudstorage.model;

import com.example.cloudstorage.component.CloudTools;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CloudError {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("message")
    private String message;

    public CloudError(String message) {
        this.id = Integer.parseInt(CloudTools.getRandomCode());
        this.message = message;
    }

}
