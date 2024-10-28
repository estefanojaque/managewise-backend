package pe.edu.upc.managewise.backend.backlog.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.DeleteUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.UpdateUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.domain.services.UserStoryCommandService;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.UserStoryRepository;

import java.util.Optional;

@Service
public class UserStoryCommandServiceImpl implements UserStoryCommandService {
    private final UserStoryRepository userStoryRepository;

    public UserStoryCommandServiceImpl(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    @Override
    public Long handle(CreateUserStoryCommand command){
        var title = command.title();
        if(this.userStoryRepository.existsByTitle(title)){
            throw new IllegalArgumentException("UserStory with " + title + " as title already exists");
        }
        var userStory = new UserStory(command);
        try{
            this.userStoryRepository.save(userStory);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving userStory: " + e.getMessage());
        }
        return userStory.getId();
    }

    @Override
    public Optional<UserStory>handle(UpdateUserStoryCommand command){
        var userStoryId = command.id();
        var title = command.title();
        if (this.userStoryRepository.existsByTitleAndIdIsNot(title, userStoryId)){
            throw new IllegalArgumentException("UserStory with " + title + " as title already exists");
        }

        if (!this.userStoryRepository.existsById(userStoryId)){
            throw new IllegalArgumentException("UserStory with id " + userStoryId + " does not exist");
        }

        var userStoryTopUpdate = this.userStoryRepository.findById(userStoryId).get();
        userStoryTopUpdate.updateInformation(command.title(), command.description());

        try{
            var updatedUserStory = this.userStoryRepository.save(userStoryTopUpdate);
            return Optional.of(updatedUserStory);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating userStory: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteUserStoryCommand command){
        if(!this.userStoryRepository.existsById(command.userStoryId())){
            throw new IllegalArgumentException("UserStory with id " + command.userStoryId() + " does not exist");
        }

        try{
            this.userStoryRepository.deleteById(command.userStoryId());
        } catch (Exception e){
            throw new IllegalArgumentException("Error while deleting userStory: " + e.getMessage());
        }
    }
}
