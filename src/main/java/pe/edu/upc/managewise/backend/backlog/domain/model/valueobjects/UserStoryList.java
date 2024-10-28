package pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.Epic;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class UserStoryList {

    @OneToMany(mappedBy = "epic", cascade = CascadeType.ALL)
    private List<UserStory> userStories;

    public UserStoryList(){
        userStories = new ArrayList<>();
    }

    public void addUserStory(Epic epic, String title, String description){
        userStories.add(new UserStory(epic, title, description));
    }

    public void updateUserStory(Long userStoryId, String title, String description){
        userStories.stream()
                .filter(userStory -> userStory.getId().equals(userStoryId))
                .findFirst()
                .ifPresent(userStory -> userStory.update(title, description));
    }

    public void removeUserStory(Long userStoryId){
        userStories.removeIf(userStory -> userStory.getId().equals(userStoryId));
    }



}
