package com.kassandra.repository.config;

public class RepositoryConfiguration {
    private String host;
    private int port;

    public RepositoryConfiguration() {
        this.host = "";
        this.port = 0;
    }

    public RepositoryConfiguration(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
