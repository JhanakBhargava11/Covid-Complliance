package com.xebia.innovationportal.services.impl;

import static com.xebia.innovationportal.constants.CommonConstants.getNextStatusList;
import static com.xebia.innovationportal.services.IdeaService.getUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xebia.innovationportal.constants.CommonConstants;
import com.xebia.innovationportal.entities.Authority;
import com.xebia.innovationportal.entities.Idea;
import com.xebia.innovationportal.entities.IdeaLikeDetail;
import com.xebia.innovationportal.entities.IdeaStatus;
import com.xebia.innovationportal.entities.IdeaStatusHistory;
import com.xebia.innovationportal.entities.Idea_;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.enums.Role;
import com.xebia.innovationportal.exceptions.GenericException;
import com.xebia.innovationportal.models.IdeaDataRequest;
import com.xebia.innovationportal.models.IdeaDataResponse;
import com.xebia.innovationportal.models.IdeaStatusUpdateRequest;
import com.xebia.innovationportal.repositories.AbstractJPA;
import com.xebia.innovationportal.repositories.CategoryRepository;
import com.xebia.innovationportal.repositories.IdeaRepository;
import com.xebia.innovationportal.repositories.IdeaStatusHistoryRepository;
import com.xebia.innovationportal.repositories.IdeaStatusRespository;
import com.xebia.innovationportal.repositories.LikeIdeaRepository;
import com.xebia.innovationportal.repositories.SubCategoryRepository;
import com.xebia.innovationportal.repositories.UserPredicate;
import com.xebia.innovationportal.repositories.UserRepository;
import com.xebia.innovationportal.services.IdeaService;

@Service("IdeaService")
public class IdeaServiceImpl extends AbstractJPA implements IdeaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdeaServiceImpl.class);

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private IdeaStatusRespository ideaStatusRepository;

    @Autowired
    private LikeIdeaRepository likeIdeaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdeaStatusHistoryRepository ideaStatusHistoryRepository;

    @Override
    public Page<IdeaDataResponse> findAllIdeas(Pageable pageable) {
        LOGGER.info("*********** Start Fetching All Ideas *************");
        long totalRecords = findAllIdeaCount(null);
        if (totalRecords == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, totalRecords);
        } else {

            List<IdeaDataResponse> ideas = ideaRepository.findAll(pageable).stream()
                    .map(obj -> new IdeaDataResponse(obj)).collect(Collectors.toList());
            return new PageImpl<IdeaDataResponse>(ideas, pageable, totalRecords);

        }

    }

    @Override
    public List<Idea> findAllIdeaByStatus(String status) throws GenericException {

        List<Idea> ideaList = ideaRepository
                .findByIdeaStatusOrderBySubmissionDateDesc(ideaStatusRepository.findByStatus(status));
        return ideaList;
    }

    @Override
    public Integer saveIdea(IdeaDataRequest data) throws GenericException {
        LOGGER.info("************ Start mapping the request Data with Idea Object *********");
        Idea idea = new Idea();
        User user = IdeaService.getUser();
        idea.setUser(user);
        idea.setLocation(user.getLocation());
        idea.setCreationTime(LocalDateTime.now());
        updateIdeaDetail(idea, data);
        LOGGER.info("************ Create Idea Successfully !!! ");
        return ideaRepository.save(idea).getId();

    }

    @Override
    public void updateIdea(Integer id, IdeaDataRequest data) throws GenericException {
        LOGGER.info("************ Start Updating Idea Object *********");
        Idea idea = ideaRepository.getOne(id);
        if (idea == null)
            throw new GenericException(HttpStatus.BAD_REQUEST);
        updateIdeaDetail(idea, data);
        ideaRepository.save(idea);
        LOGGER.info("************ Update Idea Successfully !!! *********");
    }

    @Transactional
    @Override
    public Integer likeIdea(Integer ideaId, Long userId) throws GenericException {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow((Supplier<GenericException>) () -> new GenericException(HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow((Supplier<GenericException>) () -> new GenericException(HttpStatus.NOT_FOUND));
        IdeaLikeDetail like = likeIdeaRepository.findByIdeaAndUser(idea, user);
        int likeCount = idea.getLikeCount();
        if (like == null) {
            like = new IdeaLikeDetail();
            like.setIdea(idea);
            like.setUser(user);
            idea.getLikeIdeaList().add(like);
            idea.setLikeCount(++likeCount);
            ideaRepository.save(idea);
        }
        return like.getId();
    }

    @Transactional
    @Override
    public Integer dislikeIdea(Integer ideaId, Long userId) throws GenericException {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow((Supplier<GenericException>) () -> new GenericException(HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow((Supplier<GenericException>) () -> new GenericException(HttpStatus.NOT_FOUND));
        int likeCount = idea.getLikeCount();
        idea.setLikeCount(--likeCount);
        IdeaLikeDetail likes = likeIdeaRepository.findByIdeaAndUser(idea, user);
        likeIdeaRepository.delete(likes);
        ideaRepository.save(idea);
        return likes.getId();
    }

    private void updateIdeaDetail(Idea idea, IdeaDataRequest data) throws GenericException {
        LOGGER.info("************ Start Validating the request Data with Idea Object ");
        IdeaStatus ideaStatus = null;
        IdeaStatusHistory ideahistory = null;
        idea.setTitle(data.getTitle());
        if (data.getIdeaDescription() != null)
            idea.setIdeaDescription(data.getIdeaDescription());

        if (data.getBusinessImpact() != null)
            idea.setBusinessImpact(data.getBusinessImpact());

        if (data.getAzureUrl() != null)
            idea.setAzureUrl(data.getAzureUrl());

        if (data.getFileName() != null)
            idea.setFileName(data.getFileName());

        idea.setCategory(categoryRepository.findById(data.getCategoryId())
                .orElseThrow((Supplier<GenericException>) () -> new GenericException(HttpStatus.NOT_FOUND)));

        idea.setSubCategory(subCategoryRepository.findById(data.getSubCategoryId())
                .orElseThrow((Supplier<GenericException>) () -> new GenericException(HttpStatus.NOT_FOUND)));

        ideaStatus = ideaStatusRepository.findById(data.getIdeaStatusId())
                .orElseThrow((Supplier<GenericException>) () -> new GenericException(HttpStatus.NOT_FOUND));

        List<IdeaStatusHistory> statusHistory = idea.getStatusHistory();

        if (idea.getIdeaStatus() == null) {
            ideahistory = new IdeaStatusHistory();
            ideahistory.setIdea(idea);
            ideahistory.setIdeaStatus(ideaStatus);
            ideahistory.setModificationTime(LocalDateTime.now());
            ideahistory.setUser(getUser());

            statusHistory.add(ideahistory);

            LOGGER.info("*************** Idea Status History mapped with Idea Object successfully!!");
        } else if (idea.getIdeaStatus().getId() == data.getIdeaStatusId()) {
            ideahistory = ideaStatusHistoryRepository.findByIdeaAndIdeaStatus(idea, idea.getIdeaStatus());
            ideahistory.setModificationTime(LocalDateTime.now());
            ideahistory.setUser(getUser());
        } else {
            List<String> nextStatusList = getNextStatusList(idea.getIdeaStatus().getStatus());
            if (nextStatusList.contains(ideaStatus.getStatus())) {
                ideahistory = new IdeaStatusHistory();
                ideahistory.setIdea(idea);
                ideahistory.setIdeaStatus(ideaStatus);
                ideahistory.setModificationTime(LocalDateTime.now());
                ideahistory.setUser(getUser());
                statusHistory.add(ideahistory);
            } else
                throw new GenericException(HttpStatus.BAD_REQUEST);
        }

        idea.setIdeaStatus(ideaStatus);
        idea.setSubmissionDate(idea.getSubmissionDate());
        if (idea.getIdeaStatus().getStatus().equalsIgnoreCase("submitted"))
            idea.setSubmissionDate(LocalDate.now());

        LOGGER.info("*********** Validate all Reqeust Data Successfully ***********");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IdeaDataResponse> getIdeaByUserId(Long userId, Pageable pageable) throws GenericException {
        LOGGER.info("*************** Fetching user by ID *********");

        User ideaCreator = userRepository.findById(userId)
                .orElseThrow((Supplier<GenericException>) () -> new GenericException(HttpStatus.NOT_FOUND));
        long totalRecords = findAllIdeaCount(ideaCreator);

        if (totalRecords == 0)
            return new PageImpl<>(Collections.emptyList(), pageable, totalRecords);
        else {
            List<IdeaDataResponse> ideaAllData = fetchAllIdeaByUserId(ideaCreator, pageable).stream()
                    .map(obj -> new IdeaDataResponse(obj)).collect(Collectors.toList());
            LOGGER.info("*************** Successfully get all ideas for user *********");
            return new PageImpl<IdeaDataResponse>(ideaAllData, pageable, totalRecords);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IdeaDataResponse findIdeaById(Integer ideaId) throws GenericException {
        LOGGER.info("*************** finding ideas by ID *********");
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow((Supplier<GenericException>) () -> new GenericException(HttpStatus.NOT_FOUND));
        return new IdeaDataResponse(idea, true);
    }

    private long findAllIdeaCount(User user) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Long> query = criteriaQuery(Long.class);
        Root<Idea> root = query.from(Idea.class);
        query.select(criteriaBuilder.countDistinct(root));
        if (user != null)
            query.where(criteriaBuilder.equal(root.get(Idea_.user).get("id"), user.getId()));
        return typedQuery(query).getSingleResult().longValue();

    }

    @Override
    public List<Idea> findAllIdeaRequestByStatus(String status) throws GenericException {
        Integer statusId = ideaStatusRepository.findByStatus(status).getId();
        return ideaRepository.findByIdeaStatusIdNotInOrderBySubmissionDateDesc(Collections.singletonList(statusId));
    }

    @Override
    public Page<IdeaDataResponse> findAllIdeasRequest(Pageable pageable, String status) {
        List<Authority> roles = getUser().getAuthorities().stream().collect(Collectors.toList());
        Role role = roles.get(0).getRole();
        long totalRecords = countAllIdeaRequestCount(status, role);
        if (totalRecords == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, totalRecords);
        } else {

            List<IdeaDataResponse> ideas = findAllIdeaWithStatus(status, role, pageable).stream()
                    .map(obj -> new IdeaDataResponse(obj)).collect(Collectors.toList());
            return new PageImpl<IdeaDataResponse>(ideas, pageable, totalRecords);

        }
    }

    private List<Idea> findAllIdeaWithStatus(String status, Role role, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Idea> query = criteriaQuery(Idea.class);
        Root<Idea> root = query.from(Idea.class);
        query.where(UserPredicate.getUserPredicateByRole(criteriaBuilder, root, status, role));
        query.orderBy(criteriaBuilder.asc(root.get("title")));
        query.select(root);
        TypedQuery<Idea> typedQuery = typedQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());
        return typedQuery.getResultList();
    }

    private Long countAllIdeaRequestCount(String status, Role role) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Long> query = criteriaQuery(Long.class);
        Root<Idea> root = query.from(Idea.class);
        query.select(criteriaBuilder.count(root));
        query.where(UserPredicate.getUserPredicateByRole(criteriaBuilder, root, status, role));
        return typedQuery(query).getSingleResult().longValue();
    }

    private List<Idea> fetchAllIdeaByUserId(User userId, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Idea> query = criteriaQuery(Idea.class);
        Root<Idea> root = query.from(Idea.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get("user"), userId));
        TypedQuery<Idea> typedQuery = typedQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());
        return typedQuery.getResultList();
    }

    @Override
    public Integer updateIdeaByStatus(IdeaStatusUpdateRequest request) {
        Idea idea = ideaRepository.findById(request.getIdeaId())
                .orElseThrow((Supplier<GenericException>) () -> new GenericException(HttpStatus.NOT_FOUND));
        IdeaStatus ideaStatus = ideaStatusRepository.findById(request.getStatusId())
                .orElseThrow((Supplier<GenericException>) () -> new GenericException(HttpStatus.NOT_FOUND));
        String comment = request.getComment();
        IdeaStatusHistory ideahistory = null;
        List<IdeaStatusHistory> statusHistory = idea.getStatusHistory();

        if (idea.getIdeaStatus().getId() == request.getStatusId()) {
            ideahistory = ideaStatusHistoryRepository.findByIdeaAndIdeaStatus(idea, idea.getIdeaStatus());
            ideahistory.setModificationTime(LocalDateTime.now());
            ideahistory.setUser(getUser());
            ideahistory.setComment(comment);
        } else {
            List<String> nextStatusList = getNextStatusList(idea.getIdeaStatus().getStatus());
            if (nextStatusList.contains(ideaStatus.getStatus())) {
                ideahistory = new IdeaStatusHistory();
                ideahistory.setIdea(idea);
                ideahistory.setIdeaStatus(ideaStatus);
                ideahistory.setComment(comment);
                ideahistory.setModificationTime(LocalDateTime.now());
                ideahistory.setUser(getUser());
                statusHistory.add(ideahistory);
            } else
                throw new GenericException(HttpStatus.BAD_REQUEST);
        }
        idea.setIdeaStatus(ideaStatus);
        ideaRepository.save(idea);
        return idea.getId();
    }

}
