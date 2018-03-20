package com.example.springforum.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SiteDetail {
    @Id
    private
    String key;
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
    private String description;

    public SiteDetail() {
    }
    public SiteDetail(String key, String value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return getValue();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
