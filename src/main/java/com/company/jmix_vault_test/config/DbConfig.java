package com.company.jmix_vault_test.config;


import java.io.Serializable;

public class DbConfig implements Serializable {

    private String url;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
