package com.rkind.splity.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(name = "dp_url", columnDefinition = "TEXT")
    private String dpUrl;

    private Long createdBy;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime createdAt;

public LocalDateTime getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
}

    public Group(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("dpUri")
    public String getDpUrl() {
    return dpUrl;
}

    public void setDpUrl(String dpUrl) {
        this.dpUrl = dpUrl;
    }

    public Long getCreatedBy() {
    return createdBy;
}

public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
}

public String getDescription() {
    return description;
}
public void setDescription(String description) {
    this.description = description;
}

}
