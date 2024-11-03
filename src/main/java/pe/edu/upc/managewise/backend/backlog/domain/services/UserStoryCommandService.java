package pe.edu.upc.managewise.backend.backlog.domain.services;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.AddTaskITemToUserStoryTaskListCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.DeleteUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.UpdateUserStoryCommand;

import java.util.Optional;

public interface UserStoryCommandService {
    Long handle(CreateUserStoryCommand command);
    Optional<UserStory> handle(UpdateUserStoryCommand command);
    void handle(DeleteUserStoryCommand command);


    void handle(AddTaskITemToUserStoryTaskListCommand command);
}
