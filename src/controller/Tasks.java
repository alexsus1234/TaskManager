package controller; /**
 * Created by Alex on 28.11.2015.
 */

import model.ArrayTaskList;
import model.Task;
import model.TaskList;

import java.util.*;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end){
        ArrayTaskList result ;
        result = new ArrayTaskList();
        for(Task task:tasks){
            if ((task.getTime().compareTo(start)>0 && task.getTime().compareTo(end)<0)){
                result.add(task);
            }
        }
        return result;
    }
    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        SortedMap<Date, Set<Task>> result = new TreeMap<>();

        for(Task task:tasks){
            if ((task.getTime().compareTo(start)>=0 && task.getTime().compareTo(end)<=0)){
                if(result.containsKey(task.getTime())){
                    Set s = result.get(task.getTime());
                    s.add(task);

                }
                else {
                    HashSet<Task> res = new HashSet<>();
                    res.add(task);
                    result.put(task.getTime(),res);
                }
            }
        }





            return result;
        }


}
