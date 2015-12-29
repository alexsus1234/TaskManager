package model;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.lang.Exception;
import java.util.Date;
/**
 *
 * @author klass
 */
public  class Task implements Cloneable{
    private String title;
    private Boolean active,repeat;
    private Date time,start,end,interval;

    public  Task(String title,Date time) throws Exception{

        if(title==null||title.isEmpty())
            throw new NullPointerException("Title should be not empty");
        this.title=title;
        this.time=time;
        this.repeat=false;
        this.active=true;
    }
    public  Task(String title,Date start,Date end,Date interval) throws Exception{



        if(title==null||title.isEmpty())
            throw new NullPointerException("Title should be not empty");

        this.title=title;
        this.start=start;
        this.end=end;
        this.interval=interval;
        this.repeat=true;
        this.active=true;
    }
    private Task(){

    }
    public String getTitle() {
       return title;

    }

    public void setTitle(String title) throws NullPointerException{
        if(title==null||title.isEmpty())
            throw new NullPointerException("Title should be not empty");

        this.title=title;
    }

    public Date getTime(){
        if(repeat)
        {
            return start;
        }else return time;
    }

    public void setTime(Date time) throws Exception{

            if(repeat)
            {
                repeat=false;

            }
            this.time=time;

    }


    public Boolean isActive(){
        return active;
    }


    public void setActive(Boolean active){
        this.active=active;
    }


    public Date getStartTime(){
        if(repeat) return start;else return time;
    }

    public Date getEndTime(){
        if(repeat) return end;else return time;
    }

    public Date getRepeatInterval(){
        if(!repeat) return new Date(0);else return interval;

    }

    public void setTime(Date start,Date end,Date interval) throws Exception{

        this.start=start;
        this.end=end;
        this.interval=interval;
        this.repeat=true;
    }

    public Boolean isRepeated(){
        return repeat;
    }

    public Date nextTimeAfter(Date current) throws Exception
    {

        if(this.isActive()){


            if(this.isRepeated()){
                if(current.compareTo(this.end)==0) return end;
                if(current.compareTo(this.end)<0){
                    long i=0;
                    Date next=new Date(start.getTime());
                    for ( i=next.getTime();i<=current.getTime();i+=interval.getTime()){
                        next.setTime(next.getTime()+interval.getTime());
                    }
                    return next;

                }
                else return null;
            }
            else if(current.compareTo(this.time)<=0) return this.time ;else
                return null;
        } else return null;
    }

    public int hashCode(){
        int k=5;
        int n=4;
        if(time!=null)
        k+=n*this.time.hashCode();
        if(start!=null&&end!=null){
            k+=n*start.hashCode();
            k+=n*end.hashCode();
        }
        k+=(title!=null?title.hashCode():1)*n/8;
        k+=n*(interval==null?1:interval.getTime());
        k+=(this.active?2:1)*n/2;
        k+=(this.repeat?2:1)*n/2;
        return k;



    }
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                ", active=" + repeat +
                '}';
    }
    public boolean equals(Object o){
        if(this==o) return true;
        if(this.hashCode()==o.hashCode()) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;

        if (time.getTime() != task.time.getTime()) return false;
        if (start.getTime() != task.start.getTime()) return false;
        if (end.getTime() != task.end.getTime()) return false;
        if (interval.getTime() != task.interval.getTime()) return false;
        if (active != task.active) return false;
        return !(title != null ? !title.equals(task.title) : task.title != null);

    }
    protected Object clone() throws CloneNotSupportedException{
        return super.clone();

    }





}
