package model;

import java.util.Iterator;
public abstract class TaskList  implements  Iterable<Task>{


    public int count=0;
    public abstract void add(Task task);
    public abstract Boolean remove(Task task);
    public int size()
    {
        return count;
    }
    protected abstract Task getTask(int index);
    public Iterator<Task> iterator() {

        return new Iterator<Task>() {
            private int cursor = 0;
            public boolean hasNext() { return cursor < size(); }
            public Task next() {
                if (! hasNext()) throw new NullPointerException();
                return getTask(cursor++);

            }
            public void remove() {
                TaskList.this.remove(getTask(cursor));
            }
        };



}}
