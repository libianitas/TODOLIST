package org.example.service;
import org.example.model.ToDoList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

import org.jboss.logging.Logger;

@Path("/to-do-list")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ToDoListService {
    private static final Logger LOGGER = Logger.getLogger(ToDoListService.class);
    private Set<ToDoList> toDoLists = new HashSet<>();

    public ToDoListService() {
        toDoLists.add(new ToDoList(1,"Test1", "desc test1","DONE"));
        toDoLists.add(new ToDoList(2,"Test1", "desc test1","INCOMPLETE"));
        toDoLists.add(new ToDoList(3,"Test2", "desc test2","DONE"));
        toDoLists.add(new ToDoList(4,"Test3", "desc test3","INCOMPLETE"));
    }
    @GET
    public Set<ToDoList> list() {
        LOGGER.info("Obtiene la lista de todas las tareas");
        return toDoLists;
    }
    @POST
    @Path("/add")
    public long  add(ToDoList task) {
        task.setId(generateId() + 1);
        LOGGER.info("Añade una tarea a la lista");
        toDoLists.add(task);
        return  task.getId();
    }
   private int  generateId(){
      int max = -1;
        for (ToDoList task : toDoLists) {
           if (task.getId() > max)
              max = task.getId();
       }
       LOGGER.info("Genera un id único" + max);
       return max;
   }
   @DELETE
   @Path("/delete/{id}")
   public  Set<ToDoList> delete(Long id) {
       toDoLists.removeIf(value -> value.getId()==(id));
       LOGGER.info("Elimina la tarea de ID " + id);
       return toDoLists;
   }

    @PUT
    @Path("/update")
    public Set<ToDoList> update(ToDoList task) {
         toDoLists.forEach(value -> {
            if (value.getId()==(task.getId())) {
                value.setTitle(task.getTitle());
                value.setDescription(task.getDescription());
                value.setStatus(task.getStatus());
                LOGGER.info("La tarea con ID " + task.getId()+ " ha sido editada" );
            }
        });
         return toDoLists;
    }
    @PUT
    @Path("/updateStatus/{newStatus}")
    public Set<ToDoList> updateStatus( String newStatus, ToDoList task) {
        toDoLists.forEach(value -> {
            if (value.getId()==(task.getId())) {
                value.setStatus(newStatus);
                LOGGER.info("El estado de la tarea con ID " + task.getId()+ " ha sido cambiada a " +newStatus );
            }
        });
        return toDoLists;
    }
     @GET
     @Path("/task/{id}")
     public ToDoList task(Long id) {
         ToDoList taskTemp = new ToDoList();
         for (ToDoList task : toDoLists) {
             if(task.getId()==(id)){
                 taskTemp =  task;
                 LOGGER.info("Obtiene tarea por el ID " + id);
             }
         }
         return taskTemp;
     }

    @GET
    @Path("/taskTitle/{title}")
    public Set<ToDoList> task(String title) {
         if (title ==  null) {
             LOGGER.info("Obtiene la lista de tareas completa, cuando no tiene un texto en la búsqueda por título");
             return toDoLists;
         }else {
             Set<ToDoList> toDoListsTemp = new HashSet<>();
             toDoLists.forEach(value -> {
                 if (value.getTitle().contains(title))
                     toDoListsTemp.add(value);
                 LOGGER.info("Obtiene la tarea que tiene como título "+title);
             });
             return toDoListsTemp;
         }
     }

}