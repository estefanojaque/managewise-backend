package pe.edu.upc.managewise.backend.backlog.domain.services;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.*;

import java.util.Optional;

public interface UserStoryCommandService {
    Long handle(CreateUserStoryCommand command);
    Optional<UserStory> handle(UpdateUserStoryCommand command);
    void handle(DeleteUserStoryCommand command);


    Long handle(CreateTaskITemByUserStoryIdCommand command);
    void handle(DeleteTaskCommand command);
}
