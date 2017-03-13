package com.project.communication;

import java.util.Set;


public class AddEpisodesRequest {

    private Set<EpisodeInformation> episodesInformation;

    public Set<EpisodeInformation> getEpisodesInformation() {
        return episodesInformation;
    }

    public void setEpisodeInformations(Set<EpisodeInformation> episodesInformation) {
        this.episodesInformation = episodesInformation;
    }

    @Override
    public String toString() {
        return "AddEpisodesRequest{" +
                "addEpisodeRequests=" + episodesInformation +
                '}';
    }
}
