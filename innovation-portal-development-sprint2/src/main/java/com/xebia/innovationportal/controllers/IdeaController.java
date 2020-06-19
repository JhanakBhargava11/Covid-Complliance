package com.xebia.innovationportal.controllers;

import static com.xebia.innovationportal.constants.RestConstants.INNOVATION_PORTAL_API;
import static com.xebia.innovationportal.constants.RestConstants.ideaDetails.ALL_IDEAS_REQUEST;
import static com.xebia.innovationportal.constants.RestConstants.ideaDetails.IDEAS;
import static com.xebia.innovationportal.constants.RestConstants.ideaDetails.IDEAS_BY_ID;
import static com.xebia.innovationportal.constants.RestConstants.ideaDetails.IDEAS_LIKE_BY_ID;
import static com.xebia.innovationportal.constants.RestConstants.ideaDetails.IDEAS_UPDATE_STATUS;
import static com.xebia.innovationportal.constants.RestConstants.ideaDetails.IDEA_DISLIKE_BY_ID;
import static com.xebia.innovationportal.constants.RestConstants.ideaDetails.RECENT_IDEA_REQUEST;
import static com.xebia.innovationportal.constants.RestConstants.ideaDetails.RECENT_SUBMITTED_IDEAS;
import static com.xebia.innovationportal.constants.RestConstants.ideaDetails.USER_BY_ID_IDEAS;
import static com.xebia.innovationportal.services.IdeaService.getUser;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xebia.innovationportal.constants.CommonConstants;
import com.xebia.innovationportal.exceptions.GenericException;
import com.xebia.innovationportal.models.BaseResponse;
import com.xebia.innovationportal.models.IdeaDataRequest;
import com.xebia.innovationportal.models.IdeaDataResponse;
import com.xebia.innovationportal.models.IdeaStatusUpdateRequest;
import com.xebia.innovationportal.services.IdeaService;
import com.xebia.innovationportal.utils.PaginatedResource;
import com.xebia.innovationportal.utils.PaginatedResourceAssembler;

@RestController
@RequestMapping(value = INNOVATION_PORTAL_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class IdeaController {

    @Autowired
    private IdeaService ideaService;

    private static final Logger LOGGER = LoggerFactory.getLogger(IdeaController.class);

    @Autowired
    private PaginatedResourceAssembler<IdeaDataResponse> pagedResourcesAssembler;

    @GetMapping(IDEAS)
    public ResponseEntity<BaseResponse<PaginatedResource<IdeaDataResponse>>> getAllIdeas(
            @RequestParam(defaultValue = "50") Integer pageNumber, @RequestParam(defaultValue = "15") Integer pageSize)
            throws GenericException {
        LOGGER.info("*********** Inside getIdeas method *************");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "title"));

        Page<IdeaDataResponse> ideaAllData = ideaService.findAllIdeas(pageable);
        return new ResponseEntity<>(
                new BaseResponse<PaginatedResource<IdeaDataResponse>>(pagedResourcesAssembler.assemble(ideaAllData)),
                HttpStatus.OK);
    }

    @PostMapping(IDEAS)
    public ResponseEntity<BaseResponse<Integer>> createIdea(@Valid @RequestBody IdeaDataRequest data)
            throws GenericException, IOException {
        LOGGER.info("*********** Inside createIdea method *************");
        return new ResponseEntity<>(new BaseResponse<>(ideaService.saveIdea(data)), HttpStatus.CREATED);
    }

    @PutMapping(IDEAS_BY_ID)
    public ResponseEntity<BaseResponse<Integer>> updateIdea(@PathVariable(value = "id") Integer id,
                                                            @Valid @RequestBody IdeaDataRequest data) throws GenericException, IOException {
        ideaService.updateIdea(id, data);
        return new ResponseEntity<>(new BaseResponse<>(id), HttpStatus.OK);
    }

    @PostMapping(IDEAS_LIKE_BY_ID)
    public ResponseEntity<BaseResponse<Integer>> likeIdea(@PathVariable(value = "id") Integer id)
            throws GenericException {
        LOGGER.info("*************** Like an Idea method inside *********");
        return new ResponseEntity<>(new BaseResponse<>(ideaService.likeIdea(id, getUser().getId())), HttpStatus.OK);
    }

    @PutMapping(IDEAS_UPDATE_STATUS)
    public ResponseEntity<BaseResponse<Integer>> updateIdeaByStatus(
            @Valid @RequestBody IdeaStatusUpdateRequest requestData) {
        return new ResponseEntity<>(new BaseResponse<>(ideaService.updateIdeaByStatus(requestData)), HttpStatus.OK);
    }

    @PostMapping(IDEA_DISLIKE_BY_ID)
    public ResponseEntity<BaseResponse<Integer>> dislikeIdea(@PathVariable(value = "id") Integer id)
            throws GenericException {
        LOGGER.info("*************** Like an Idea method inside *********");
        return new ResponseEntity<>(new BaseResponse<>(ideaService.dislikeIdea(id, getUser().getId())), HttpStatus.OK);

    }

    @GetMapping(USER_BY_ID_IDEAS)
    public ResponseEntity<BaseResponse<PaginatedResource<IdeaDataResponse>>> fetchIdeabyUserId(@PathVariable Long id,
                                                                                               @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "15") Integer pageSize)
            throws GenericException {
        LOGGER.info("*************** Fetch all Idea by user Id method inside *********");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<IdeaDataResponse> ideaAllData = ideaService.getIdeaByUserId(id, pageable);

        return new ResponseEntity<>(
                new BaseResponse<PaginatedResource<IdeaDataResponse>>(pagedResourcesAssembler.assemble(ideaAllData)),
                HttpStatus.OK);

    }

    @GetMapping(RECENT_SUBMITTED_IDEAS)
    public ResponseEntity<BaseResponse<List<IdeaDataResponse>>> getRecentSubmittedIdeas() throws GenericException {

        LOGGER.info("*********** Inside recent submitted Ideas method *************");
        List<IdeaDataResponse> collect = ideaService.findAllIdeaByStatus("Submitted").stream()
                .map(obj -> new IdeaDataResponse(obj)).collect(Collectors.toList());
        return new ResponseEntity<>(new BaseResponse<List<IdeaDataResponse>>(collect), HttpStatus.OK);
    }

    @GetMapping(IDEAS_BY_ID)
    public ResponseEntity<BaseResponse<IdeaDataResponse>> getIdeaById(@PathVariable(value = "id") Integer id)
            throws GenericException, IOException {
        LOGGER.info("*********** Inside get Idea by Id method *************");
        return new ResponseEntity<>(new BaseResponse<>(ideaService.findIdeaById(id)), HttpStatus.OK);
    }

    @GetMapping(RECENT_IDEA_REQUEST)
    @PreAuthorize(value = "hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER')")
    public ResponseEntity<BaseResponse<List<IdeaDataResponse>>> getRecentSubmittedIdeasRequest()
            throws GenericException {

        LOGGER.info("*********** Inside recent submitted Ideas request method *************");
        List<IdeaDataResponse> collect = ideaService.findAllIdeaRequestByStatus(CommonConstants.STATUS_DRAFT).stream()
                .map(obj -> new IdeaDataResponse(obj)).collect(Collectors.toList());
        return new ResponseEntity<>(new BaseResponse<List<IdeaDataResponse>>(collect), HttpStatus.OK);
    }

    @GetMapping(ALL_IDEAS_REQUEST)
    @PreAuthorize(value = "hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER')")
    public ResponseEntity<BaseResponse<PaginatedResource<IdeaDataResponse>>> getAllIdeasRequest(
            @PathVariable String status, @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "15") Integer pageSize) throws GenericException {
        LOGGER.info("*********** Inside getIdeas method *************");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<IdeaDataResponse> ideaAllData = ideaService.findAllIdeasRequest(pageable, status);
        return new ResponseEntity<>(
                new BaseResponse<PaginatedResource<IdeaDataResponse>>(pagedResourcesAssembler.assemble(ideaAllData)),
                HttpStatus.OK);
    }

}
