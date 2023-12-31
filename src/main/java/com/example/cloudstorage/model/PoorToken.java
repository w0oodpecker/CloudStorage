package com.example.cloudstorage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Repository
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_tokenblacklist")
public class  PoorToken{
    @Id
    @Column(nullable = false)
    @JsonProperty("auth-token")
    private String accessToken;
}



