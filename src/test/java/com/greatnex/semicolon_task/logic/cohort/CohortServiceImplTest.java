package com.greatnex.semicolon_task.logic.cohort;

import com.greatnex.semicolon_task.entity.models.users.Learner;
import com.greatnex.semicolon_task.entity.dtos.CohortDto;
import com.greatnex.semicolon_task.entity.models.Cohort;
//import com.greatnex.semicolon_task.entity.models.users.Learner;
import com.greatnex.semicolon_task.exception.CohortAlreadyExistException;
import com.greatnex.semicolon_task.repository.CohortRepository;
import com.greatnex.semicolon_task.repository.LearnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class CohortServiceImplTest {
    @InjectMocks
    private CohortServiceImpl cohortServiceImpl;

    @Mock
    private CohortRepository cohortRepository;

    @Mock
    private LearnerRepository learnerRepository;

    @MockBean
    private ModelMapper cohortMapper;

    @Mock
    private List<Learner> learnerList;

    private Cohort testCohort;

    @BeforeEach
    void up(){
        testCohort = Cohort.builder()
                .id(1L)
                .cohortName("Phoenix")
                .dateCohortStarted("25th May, 2025")
                .description("test intelligence of students in cohort")
                .build();




    }

    @Test
    void createNewCohort() throws CohortAlreadyExistException {
        Cohort cohort = new Cohort();
        when(cohortRepository.save(any())).thenReturn(cohort);
        assertThat(cohort).isNotNull();
        cohortServiceImpl.createNewCohort(new CohortDto());
    }

    @Test
    void  testCohortIsSaved() throws CohortAlreadyExistException {
        cohortServiceImpl.saveCohort(testCohort);

        log.info(" {} cohort created successfully ===>", testCohort.getCohortName());
       // assertThat(testCohort).isNotNull();
        assertNotNull(testCohort);
    }

    @Test
    void findCohortById() throws CohortAlreadyExistException {
        Cohort cohort = new Cohort();
        cohort.setId(1L);
        cohort.setCohortName("Phoenix");
        when(cohortRepository.save(any())).thenReturn(cohort);
        cohortServiceImpl.createNewCohort(new CohortDto());

        log.info("Cohort id ->{}", cohort.getId());

        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(List.of(cohort));
        assertThat(cohort).isNotNull();
        assertThat(cohort.getId()).isEqualTo(1L);
        cohortServiceImpl.findCohortById(cohort.getId());
    }

    @Test
    void testSavedCohortCanBeFoundWithId(){
        cohortServiceImpl.findCohortById(1L);
       assertEquals(1L,testCohort.getId());
    }

    @Test
    void testSavedCohortCanBeRemovedById(){
        log.info("Saved cohort ->{}",testCohort.getId());
        cohortServiceImpl.deleteCohortById(1L);
        assertTrue(cohortServiceImpl.findCohortById(1L).isEmpty());
    }


    @Test
    void cohortCanBeRemoved() {
        Cohort cohort = new Cohort();

        when(cohortRepository.save(any())).thenReturn(cohort);

        cohortRepository.save(cohort);

        cohortRepository.delete(cohort);


        verify(cohortRepository).delete(cohort);


    }

    @Test
    void inviteLearnerToCohort() {
        Cohort cohort = new Cohort();
        cohort.setId(1L);
        when(cohortRepository.save(any())).thenReturn(cohort);
        cohortRepository.save(cohort);
        var savedCohort = cohortRepository.findById(cohort.getId());
        savedCohort.ifPresent(value -> log.info("Cohort id -> {}" + String.valueOf(value.getId())));

//        Learner learner = new Learner();
//        when(learnerRepository.save(any())).thenReturn(learner);
//        learner.setId(1L);
//        learnerRepository.save(learner);
//        var savedLearner = learnerRepository.findById(learner.getId());
//
//        if (savedLearner.isPresent() && savedCohort.isPresent()) {
//            Cohort addLearner = savedCohort.get();
//            addLearner.getListOfLearners().add(savedLearner.get().getEmail());
//
//            cohortRepository.save(addLearner);
//            assertThat(cohortRepository).isNotNull();
//
//            assertThat(learnerRepository).isNotNull();
//
//            boolean result =cohort.getListOfLearners().add("Ade");
//
//            // Assert
//
//            assertTrue(result);
    //
        // }

        }

    @Test
    void updateCohort(){
        Cohort cohort = new Cohort();
        CohortDto cohortDto = new CohortDto();
        cohort.setId(1L);
        cohort.setCohortName("Phoenix");
        when(cohortRepository.save(any())).thenReturn(cohort);
        cohortRepository.save(cohort);

        cohortDto.setCohortName("Eagle");
        log.info("Cohort name has been updated successfully ->{}", cohortDto.getCohortName());

    }

    @Test
    void cohortCanBeRemovedById() {
        Cohort cohort = new Cohort();

        when(cohortRepository.save(any())).thenReturn(cohort);

        cohortRepository.save(cohort);


        cohortRepository.deleteById(cohort.getId());
        log.info("Cohort id ->{}", cohort.getId());
        assertThat(cohort.getId()).isNull();


        verify(cohortRepository).deleteById(cohort.getId());

        cohortServiceImpl.deleteCohortById(cohort.getId());
    }

    @Test
    void testThatALearnerCanBeAddedToACohort(){
        Cohort cohort = new Cohort();
        cohort.setId(1L);

        // Act
        boolean result =cohort.getListOfLearners().add("Ade");

        // Assert
        assertTrue(result);

        log.info("New learner added to cohort -> {}", true );
    }
}
