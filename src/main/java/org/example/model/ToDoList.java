package org.example.model;
import lombok.Data;
//import org.jboss.logging.Logger;
@Data
public class ToDoList {
    //private static final Logger LOG = Logger.getLogger(ToDoListService.class);
    private int id;
    private String title;
    private String description;
    private String status;

    public ToDoList(int id,String title, String description, String status) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;

    }
    public ToDoList() {

    }
}
