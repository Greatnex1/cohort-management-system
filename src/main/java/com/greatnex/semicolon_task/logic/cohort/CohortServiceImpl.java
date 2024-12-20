package com.greatnex.semicolon_task.logic.cohort;

import com.greatnex.semicolon_task.dtos.request.CohortRequest;
import com.greatnex.semicolon_task.entity.dtos.CohortDto;
import com.greatnex.semicolon_task.entity.dtos.InstructorDto;
import com.greatnex.semicolon_task.entity.dtos.LearnerDto;
import com.greatnex.semicolon_task.entity.models.Cohort;
//import com.greatnex.semicolon_task.entity.models.InvitationToken;
import com.greatnex.semicolon_task.entity.models.users.Instructor;
import com.greatnex.semicolon_task.entity.models.users.Learner;
import com.greatnex.semicolon_task.exception.CohortAlreadyExistException;
import com.greatnex.semicolon_task.exception.CohortNotFoundException;
import com.greatnex.semicolon_task.exception.LearnerAlreadyExistException;
import com.greatnex.semicolon_task.logic.invitation.EmailService;
import com.greatnex.semicolon_task.repository.CohortRepository;
import com.greatnex.semicolon_task.repository.LearnerRepository;
//import com.greatnex.semicolon_task.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CohortServiceImpl implements CohortService{

    private final CohortRepository cohortRepository;

   private final LearnerRepository learnerRepository;

    private ModelMapper cohortMapper;

   private EmailService emailService;

   //private final TokenRepository tokenRepository;

    @Override
    public Cohort createNewCohort(CohortDto cohortDto) throws CohortAlreadyExistException {

        if(cohortRepository.findByCohortName(cohortDto.getCohortName()).isPresent()) {
            log.info("this cohort name {} is already taken, please use another name for the cohort",cohortDto.getCohortName());
            throw new CohortAlreadyExistException("There is a cohort account with  this detail");
        }
     Cohort cohort = new Cohort();
     cohort.setCohortName(cohortDto.getCohortName());
     cohort.setDescription(cohortDto.getDescription());
     cohort.setDateCohortStarted(Instant.now().toString());
     cohort.setCohortAvatar(cohortDto.getCohortAvatar());

     log.info("Cohort Created successfully");
     return cohortRepository.save(cohort);

    }

    @Override
    public Cohort saveCohort(Cohort cohort) throws CohortAlreadyExistException {

        if(cohortRepository.findByCohortName(cohort.getCohortName()).isPresent()) {
            log.info("this cohort name {} is already taken, please use another name to create cohort",cohort.getCohortName());
            throw new CohortAlreadyExistException("There is a cohort account with  this detail");
        }
        Cohort saveCohort = new Cohort();
        saveCohort.setCohortName(cohort.getCohortName());
        saveCohort.setDescription(cohort.getDescription());
        saveCohort.setDateCohortStarted(cohort.getDateCohortStarted());

       return cohortRepository.save(saveCohort);


    }

    @Override
    public Optional<Cohort> findCohortById(Long id)  {

       // cohortRepository.findCohortById(id);//log.warn("cohort does not exist {}" , id);
        return cohortRepository.findById(id);

    }

    @Override
    public List<Cohort> findAllCohorts() {
     var noCohort =  cohortRepository.findAll();
            if(noCohort.isEmpty()){
            log.warn("No cohort exists {}" , noCohort);
        }
        return cohortRepository.findAll();
    }

    @Override
    public Page<Cohort> findAllCohortByPagination(Pageable pageable) {
        var noCohortFound =  cohortRepository.findAll();
        if(noCohortFound.isEmpty()){
            log.warn("No cohort exists {}" , noCohortFound);
        }
        return cohortRepository.findAll(pageable);
    }


        @Override
        public void editCohort (Long id, CohortDto cohortDto){
            if (cohortDto == null) {
                throw new NullPointerException("Cohort object cannot be null");
            }
            Cohort cohort = cohortRepository.findById(id).orElseThrow(() -> new RuntimeException("Cohort not found"));
            log.info("cohort id -> {}", cohort.getId());
            cohortMapper.map(cohortDto, cohort);
                cohort.setCohortAvatar( cohortDto.getCohortAvatar());
                cohort.setDescription(cohortDto.getDescription());
                cohort.setDateCohortStarted(cohortDto.getDateCohortStarted());
                log.info("cohort update-->{}", cohort);
                log.info("Update successful");
                cohortRepository.save(cohort);

        }


    @Override
    public Cohort addInstructorToCohort(Long id)  {
        Cohort cohort = cohortRepository.findById(id)
                .orElseThrow(() -> new CohortNotFoundException("Cohort does not exist"));
        var instructor = cohort.getListOfInstructors();
        instructor.add(cohort.getCohortName());

        return cohortRepository.save(cohort);
    }

    @Override
    public  Cohort viewCohort(Long id, CohortDto cohortDto) {
        Cohort savedCohort = cohortRepository.findById(id).orElseThrow(() -> new RuntimeException("Cohort not found"));
        log.info(" view saved cohort -> {}", savedCohort);
        if (savedCohort != null) {
            cohortMapper.map(cohortDto, savedCohort);
        }
        return savedCohort;
    }

//    @Override
//    public void deleteCohortById(Long id) {
//        var  cohort =cohortRepository.findCohortById(id);
//        cohortRepository.deleteById(id);
//        log.info("cohort is removed");
//        cohortRepository.deleteById(id);
//    }

    @Override
    public boolean inviteLearnerToCohort(Long learnerId, Long cohortId) {
        Optional<Learner> learner = learnerRepository.findById(learnerId);
        Optional<Cohort> cohort = cohortRepository.findById(cohortId);

        if (learner.isPresent() && cohort.isPresent()) {
            Cohort addLearner = cohort.get();
            addLearner.getListOfLearners().add(learner.get().getEmail());
            cohortRepository.save(addLearner);

           return true;
        }
        return false;
    }
    @Override
    public void deleteCohortByName(String cohort_name) {
        Cohort cohort =cohortRepository.findByCohortName(cohort_name).orElseThrow(
                ()-> new CohortNotFoundException("Cohort with name " + cohort_name +"does not exist" ));
        cohortRepository.deleteById(cohort.getId());
        log.info("cohort removed");

    }

    @Override
    public void deleteCohortById(Long id) {
//        Cohort cohort =cohortRepository.findById(id).orElseThrow(
//                ()-> new CohortNotFoundException("Cohort with id " + id +" does not exist" ));
        cohortRepository.deleteById(id);
        log.info("cohort with id -> {} deleted",id);

    }
//            String token = UUID.randomUUID().toString();
//
//       Cohort cohort = cohortRepository.fetchByNameOfCohort(cohortDto.getCohortName());
//        Learner learner = learnerRepository.findLearnerByEmail(learnerDto.getEmail());
//    public Cohort inviteLearnerToCohort(LearnerDto learnerDto, CohortDto cohortDto) throws MessagingException {
//    @Override
//    @Override
//    public Cohort addLearnerToCohort(String cohortName, LearnerDto learnerDto) {
////        Cohort cohort = cohortRepository.findByCohortName(cohortName).orElseThrow(()-> new CohortNotFoundException("Cohort Not Found"));
//////
////
////        Learner student = cohort.getLearner();
////        student.getListOfCohort().add(learnerDto.getEmail());
//
////        learner.setEmail(learnerDto.getEmail());
////        learner.setFirstName(learnerDto.getLastname());
////        learner.setLastName(learnerDto.getLastname());
////        learner.setDateCreated(Instant.now().toString());
////        learner.setLearnerAbout(learnerDto.getLearnerAbout());
////
////        learner.setCohort(cohort);
////        learnerRepository.save(learner);
////        cohort.getListOfLearners().add(learner);
//
//        return cohortRepository.save(cohort);
//    }
//
//            InvitationToken invitationToken = new InvitationToken(token,learner.getEmail());
//
//            tokenRepository.save(invitationToken);
//
//            //send email
//
//            emailService.sendInvitationMessage(learnerDto,token);
//
//            learnerRepository.save(learner);
//

//return cohort;
//        }
//    private void sendInvitationEmail(Learner learner, Cohort cohort) {
//        String subject = "Invitation to Join Cohort: " + cohort.getCohortName();
//        String body = "Dear " + learner.getFirstName() + ",\n\n" +
//                "You have been invited to join the cohort: " + cohort.getCohortName() +
//
//                ". Please log in to your account to accept this invitation.\n\n" +
//                "Best regards,\nThe Enum Team";
//     emailService.sendEmail(instructor.getEmail(), subject, body);
//
//     sendInvitationEmail(learner.getEmail(),subject, body);
//
//    }
}
