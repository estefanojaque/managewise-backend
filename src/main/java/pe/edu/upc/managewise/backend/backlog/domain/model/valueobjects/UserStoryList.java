package pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.UserStory;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class UserStoryList {

    @OneToMany(mappedBy = "epic", cascade = CascadeType.ALL)
    private List<UserStory> userStories;

    public UserStoryList(){
        userStories = new ArrayList<>();
    }
}
