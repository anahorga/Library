package view.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserDTO {

    private StringProperty username;


    public void setUsername(String username) {
        usernameProperty().set(username);
    }

    public StringProperty usernameProperty() {
        if(username==null)
            username=new SimpleStringProperty(this,"username");
        return username;
    }
    public String getUsername()
    {
        return usernameProperty().get();
    }

    private StringProperty role;

    public void setRole(String role) {
        roleProperty().set(role);
    }

    public StringProperty roleProperty() {
        if(role==null)
            role=new SimpleStringProperty(this,"role");
        return role;
    }
    public String getRole()
    {
        return roleProperty().get();
    }

    private LongProperty id;
    public void setId(Long id)
    {
        idProperty().set(id);
    }
    public LongProperty idProperty()
    {
        if(id==null)
            id=new SimpleLongProperty(this,"id");
        return id;
    }
    public Long getId()
    {
        return idProperty().get();
    }
}
