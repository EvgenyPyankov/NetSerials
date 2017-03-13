package com.project.communication;

import com.project.model.Episode;


public class EpisodeInformation {

    private String token;

    private Episode episode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    @Override
    public String toString() {
        return "AddEpisodeRequest{" +
                "token='" + token + '\'' +
                ", episode=" + episode +
                '}';
    }
}
