package model;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Alex
 */
public class ArrayTaskList extends TaskList implements Cloneable {
    private Task item[];

    {
        item=new Task[10];
        count=0;
    }
    public void add(Task task) throws NullPointerException{

        if(task==null) throw new NullPointerException("Task cannot be null");
        if(count<item.length) {
            item[count]=task;
            count++;
        }
        else{
            Task[] bufArr;
            bufArr=item;
            item= null;
            item=new Task[bufArr.length+(int)Math.round(bufArr.length*0.15)];
            for(int i=0;i<bufArr.length;i++)
            {
                item[i]=bufArr[i];
            }
            item[count]=task;
            count++;

        }
    }



    public Boolean remove(Task task){
        int index=-1;
        for(int i=0;i<this.count;i++)
        {
            if(task.equals(item[i])) {index=i;break;}
        }
        if(index==-1) return false;
        else{
            if((this.item.length*0.8)<count){
                count--;
                if(index!=count)
                    for(int i=index;i<count;i++)
                    {
                        item[i]=item[i+1];
                    }
            }else{

                Task[] bufArr;
                bufArr=item;
                item= null;
                count--;
                item=new Task[count];
                int k=0;
                for(int i=0;i<count;i++)
                {
                    if(k!=index)  item[i]=bufArr[k];else {
                        k++;
                        item[i]=bufArr[k];
                    }
                    k++;
                }

            }
            return true;
        }
    }
    public Task getTask(int index) throws NullPointerException{
        if(index>count||index<0) throw new NullPointerException("index cannot be >count and <0");
        return item[index];

    }

  /*  ArrayTaskList incoming(int from, int to) throws Exception{
        if(from>to) throw new Exception("from should be less than to");
        ArrayTaskList BuffOb=new ArrayTaskList();
        for(int i=0;i<count;i++)
        {
            if(item[i].nextTimeAfter(from)!=-1&&(to-from-item[i].nextTimeAfter(from)>=0)) BuffOb.add(item[i]);
        }
        return BuffOb;
    }*/


    public Iterator<Task> iterator() {
        return new Itr();
    }
    private class Itr implements Iterator<Task>{
        private int index = 0;

        @Override
        public boolean hasNext() {
            if(index < count) return true;
            return false;
        }


        @Override
        public Task next() {

            if(count == index)
                try {
                    throw new NullPointerException();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            index++;
            return item[index-1];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public String toString(){
        String res= " ArrayTaskList ";
        for (int i =0; i < this.size(); i++) {
            try {
                res += this.getTask(i) + ", ";
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        return res;

    }
    public int hashCode() {
        return item != null ? Arrays.hashCode(item) : 0;



    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if(this.hashCode()==o.hashCode()) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList list = (ArrayTaskList) o;


        return Arrays.equals(item, list.item);
    }
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}


