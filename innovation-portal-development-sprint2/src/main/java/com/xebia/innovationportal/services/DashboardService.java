package com.xebia.innovationportal.services;

import java.util.HashSet;
import java.util.List;

import com.xebia.innovationportal.entities.Idea;
import com.xebia.innovationportal.models.IdeaStatsResponse;
import com.xebia.innovationportal.models.TopContributorResponse;

public interface DashboardService {
    List<TopContributorResponse> getTopContributor();

    List<Idea> getTrendingIdeas();

    HashSet<IdeaStatsResponse> getAllIdeaStats(String duration);
}
