package com.xebia.innovationportal.controllers;

import static com.xebia.innovationportal.constants.RestConstants.INNOVATION_PORTAL_API;
import static com.xebia.innovationportal.constants.RestConstants.dashboardDetails.IDEA_STATS;
import static com.xebia.innovationportal.constants.RestConstants.dashboardDetails.TOP_CONTRIBUTOR;
import static com.xebia.innovationportal.constants.RestConstants.dashboardDetails.TRENDING;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xebia.innovationportal.models.BaseResponse;
import com.xebia.innovationportal.models.IdeaStatsResponse;
import com.xebia.innovationportal.models.TopContributorResponse;
import com.xebia.innovationportal.models.TopTrendingIdeasResponse;
import com.xebia.innovationportal.services.DashboardService;
import com.xebia.innovationportal.utils.ImmutableCollectors;

@RestController
@RequestMapping(value = INNOVATION_PORTAL_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class DashboardController {
	private static final Logger LOGGER = LoggerFactory.getLogger(IdeaController.class);

	@Autowired
	private DashboardService dashboardService;

	@GetMapping(TOP_CONTRIBUTOR)
	public ResponseEntity<List<TopContributorResponse>> getTopContributor() {
		LOGGER.info("*************** Top Contributor method inside *********");
		List<TopContributorResponse> topContributor = dashboardService.getTopContributor();
		return ResponseEntity.status(HttpStatus.OK).body(topContributor);
	}

	@GetMapping(TRENDING)
	public ResponseEntity<List<TopTrendingIdeasResponse>> getTopTrendingIdeas() {
		LOGGER.info("*************** Top Trending idea inside *********");

		List<TopTrendingIdeasResponse> topTrendingIdeas = this.dashboardService.getTrendingIdeas().stream()
				.map(TopTrendingIdeasResponse::of).collect(ImmutableCollectors.toImmutableList());
		return ResponseEntity.status(HttpStatus.OK).body(topTrendingIdeas);

	}

	@GetMapping(IDEA_STATS)
	public ResponseEntity<BaseResponse<HashSet<IdeaStatsResponse>>> getIdeaStats(
			@RequestParam(defaultValue = "all") String duration) {

		return new ResponseEntity<BaseResponse<HashSet<IdeaStatsResponse>>>(
				new BaseResponse<>(dashboardService.getAllIdeaStats(duration)), HttpStatus.OK);
	}
}
