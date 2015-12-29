package controller; /**
 * Created by Alex on 01.12.2015.
 */

import model.Task;
import model.TaskList;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskIO  {
private static  SimpleDateFormat dateFormat = new SimpleDateFormat("'['yyyy-MM-dd HH:mm:ss.SSS']'");
    static void write(TaskList tasks, OutputStream out){
        try(DataOutputStream dataOutputStream  = new DataOutputStream (out)){
            dataOutputStream.writeInt(tasks.size());
            for(Task t:tasks) {
                dataOutputStream.writeUTF(t.getTitle());
                dataOutputStream.writeInt(t.getTitle().length());
                dataOutputStream.writeBoolean(t.isActive());
                dataOutputStream.writeBoolean(t.isRepeated());
                if(t.isRepeated()) {
                    dataOutputStream.writeLong(t.getRepeatInterval().getTime());
                    dataOutputStream.writeLong(t.getStartTime().getTime());
                    dataOutputStream.writeLong(t.getEndTime().getTime());
                }
                else {
                    dataOutputStream.writeLong(t.getTime().getTime());
                }
            }
        }
        catch (IOException e){
            System.out.println(e.getStackTrace());
        }
    }
    static void read(TaskList tasks, InputStream in){
        try(DataInputStream dataInputStream = new DataInputStream(in)){
         int size;
            size=dataInputStream.readInt();
            System.out.println(size);
            for(int i=0;i<size;i++) {
                Task task=new Task("t",new Date(1));
                task.setTitle(dataInputStream.readUTF());
                dataInputStream.readInt();
                task.setActive(dataInputStream.readBoolean());
                boolean rep= dataInputStream.readBoolean();
               if(rep ) {
                    long interval=dataInputStream.readLong();
                    long start= dataInputStream.readLong();
                    long end=  dataInputStream.readLong();
                   task.setTime(new Date(start),new Date(end),new Date(interval));
                } else {
                   task.setTime(new Date(dataInputStream.readLong()));
                }
                tasks.add(task);
            }
        }
        catch (IOException e){
            System.out.println(e.getStackTrace());
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }
    static void writeBinary(TaskList tasks, File file) {
        try(DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream( file))) {
            dataOutputStream.writeInt(tasks.size());
            for(Task t:tasks) {
                dataOutputStream.writeUTF(t.getTitle());
                dataOutputStream.writeInt(t.getTitle().length());
                dataOutputStream.writeBoolean(t.isActive());
                dataOutputStream.writeBoolean(t.isRepeated());
                if(t.isRepeated()) {
                    dataOutputStream.writeLong(t.getRepeatInterval().getTime());
                    dataOutputStream.writeLong(t.getStartTime().getTime());
                    dataOutputStream.writeLong(t.getEndTime().getTime());
                }
                else {
                    dataOutputStream.writeLong(t.getTime().getTime());
                }
            }
        }
        catch (IOException e){
            System.out.println(e.getStackTrace());
        }

    }

    static void readBinary(TaskList tasks, File file){
        try(DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))){
            int size;
            size=dataInputStream.readInt();

            for(int i=0;i<size;i++) {
                Task task=new Task("t",new Date(1));
                task.setTitle(dataInputStream.readUTF());
                dataInputStream.readInt();
                task.setActive(dataInputStream.readBoolean());
                boolean rep= dataInputStream.readBoolean();
                if(rep ) {
                    long interval=dataInputStream.readLong();
                    long start= dataInputStream.readLong();
                    long end=  dataInputStream.readLong();
                    task.setTime(new Date(start),new Date(end),new Date(interval));
                } else {
                    task.setTime(new Date(dataInputStream.readLong()));
                }
                tasks.add(task);
            }
        }
        catch (IOException e){
            System.out.println(e.getStackTrace());
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
        }

    }





   public static void write(TaskList tasks, Writer out){

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("'[' d 'day' H 'hour', m  'minute', ss ,'second]'");
        try(FileWriter FileWriter = (FileWriter) out) {
            StringBuffer sb = new StringBuffer();
            int counter=0;
            for (Task t : tasks) {
                sb.append(t.getTitle());
                for(int i=0;i<sb.length();i++){
                   int ii=-1;
                    ii=sb.indexOf("\"",i);
                   if(ii!=-1){
                       sb.replace(ii, ii, "\"\"");
                       i=ii+1;
                   }

                }

                    out.write(sb.indexOf("\"")==0?String.valueOf(sb):"\""+String.valueOf(sb)+(sb.lastIndexOf("\"")!=sb.length()-1?"\" ":" "));
                sb.delete(0,sb.length());
                    if(t.isRepeated()){
                  //     System.out.println("ень "+t.getRepeatInterval().getHours()+" "+t.getRepeatInterval().getMinutes()+" "+t.getRepeatInterval().getTimezoneOffset()+" ");
                        out.write("from " + dateFormat.format(t.getStartTime()) + " to ");
                        out.write(dateFormat.format(t.getEndTime())+" ");
                        long second=t.getRepeatInterval().getTime()/1000;
                        long minute=second/60;
                        long hours=minute/60;
                        long day=hours/24;




                        out.write(String.format("every [ " +(day!=0?day+" day ":"") +(hours!=0?hours+" hours ":"")+(minute!=0?minute+" minutes ":"")+(second!=0?second+" seconds ":"")+"]" ));

                    }
                    else {
                        out.write("at "+dateFormat.format(t.getTime()));
                    }

                counter++;
                if(!t.isActive()) out.write(" inactive");
                out.write(counter!=tasks.count?";\n":".\n");

          }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    static void read(TaskList tasks, Reader in)  {
        try(BufferedReader FileReader = new BufferedReader(in)){
            String st;
            while ((st = FileReader.readLine()) != null) {

                Task task = new Task("t",new Date(455));
                int index;
                int index2;
                if((index=st.indexOf("\" at ["))>=0) {
                   String name=st.substring(1,index);
                    task.setTitle(name);

                    index+=5;
                    index2=st.indexOf("]",index)+1;
                    String date=st.substring(index,index2);
                     System.out.println("date: " + date + " parse" + (dateFormat.parse(date)));
                   task.setTime( (dateFormat.parse(date)));
                    if(st.indexOf("inactive")>=0){
                        task.setActive(false);
                        System.out.println("false");
                    }

                }else
                if((index=st.indexOf("\" from ["))>=0) {
                    String name=st.substring(1,index);
                    task.setTitle(name);

                    index+=7;
                    index2=st.indexOf("]",index)+1;
                    String date=st.substring(index,index2);

                    index=index2+4;
                    index2=st.indexOf("]",index)+1;
                    String dateTo=st.substring(index, index2);




                    if(st.indexOf("inactive")>=0){
                        task.setActive(false);

                    }
                    st=st.substring(st.indexOf("[",index2)+2,st.indexOf("]",index2));
                    System.out.println(st);
                    String[] arr = st.split(" ");
                    long time=0;
                    for (int i=0;i<arr.length-1;i++){
                        if(arr[i+1].equals("seconds")) time+=Long.valueOf(arr[i])*1000;
                        else
                        if(arr[i+1].equals("minutes")) time+=Long.valueOf(arr[i])*60*1000;
                        else
                        if(arr[i+1].equals("hours")) time+=Long.valueOf(arr[i])*60*60*1000;
                        else
                        if(arr[i+1].equals("hours")) time+=Long.valueOf(arr[i])*24*60*60*1000;

                    }
                    task.setTime( (dateFormat.parse(date)),dateFormat.parse(dateTo),new Date(time));

                    System.out.println("time: "+time);
                }



                tasks.add(task);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writeText(TaskList tasks, File file){
        SimpleDateFormat dateFormat = new SimpleDateFormat("'['yyyy-MM-dd HH:mm:ss.SSS']'");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("'[' d 'day' H 'hour', m  'minute', ss ,'second]'");

        try(FileWriter out = new FileWriter(file)) {
            StringBuffer sb = new StringBuffer();
            int counter=0;
            for (Task t : tasks) {
                sb.append(t.getTitle());
                for(int i=0;i<sb.length();i++){
                    int ii=-1;
                    ii=sb.indexOf("\"",i);
                    if(ii!=-1){
                        sb.replace(ii, ii, "\"\"");
                        i=ii+1;
                    }

                }

                out.write(sb.indexOf("\"")==0?String.valueOf(sb):"\""+String.valueOf(sb)+(sb.lastIndexOf("\"")!=sb.length()-1?"\" ":" "));
                sb.delete(0,sb.length());
                if(t.isRepeated()){
                    //     System.out.println("ень "+t.getRepeatInterval().getHours()+" "+t.getRepeatInterval().getMinutes()+" "+t.getRepeatInterval().getTimezoneOffset()+" ");
                    out.write("from " + dateFormat.format(t.getStartTime()) + " to ");
                    out.write(dateFormat.format(t.getEndTime())+" ");
                    long second=t.getRepeatInterval().getTime()/1000;
                    long minute=second/60;
                    long hours=minute/60;
                    long day=hours/24;




                    out.write(String.format("every [ " +(day!=0?day+" day ":"") +(hours!=0?hours+" hours ":"")+(minute!=0?minute+" minutes ":"")+(second!=0?second+" seconds ":"")+"]" ));

                }
                else {
                    out.write("at "+dateFormat.format(t.getTime()));
                }

                counter++;
                if(!t.isActive()) out.write(" inactive");
                out.write(counter!=tasks.count?";\n":".\n");

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    static void readText(TaskList tasks, File file) {
        try(BufferedReader FileReader = new BufferedReader(new FileReader(file))){
            String st;
            while ((st = FileReader.readLine()) != null) {
                System.out.println(st);
                Task task = new Task("t",new Date(455));
                int index;
                int index2;
                if((index=st.indexOf("\" at ["))>=0) {
                    String name=st.substring(1,index);

                    task.setTitle(name);
                    index+=5;
                    index2=st.indexOf("]",index)+1;
                    String date=st.substring(index,index2);

                    task.setTime( (dateFormat.parse(date)));
                    if(st.indexOf("inactive")>=0){
                        task.setActive(false);
                        System.out.println("false");
                    }

                }else
                if((index=st.indexOf("\" from ["))>=0) {
                    String name=st.substring(1,index);

                    task.setTitle(name);
                    index+=7;
                    index2=st.indexOf("]",index)+1;
                    String date=st.substring(index,index2);

                    index=index2+4;
                    index2=st.indexOf("]",index)+1;
                    String dateTo=st.substring(index, index2);




                    if(st.indexOf("inactive")>=0){
                        task.setActive(false);
                        System.out.println("false");
                    }
                    st=st.substring(st.indexOf("[",index2)+2,st.indexOf("]",index2));

                    String[] arr = st.split(" ");
                    long time=0;
                    for (int i=0;i<arr.length-1;i++){
                        if(arr[i+1].equals("seconds")) time+=Long.valueOf(arr[i])*1000;
                        else
                        if(arr[i+1].equals("minutes")) time+=Long.valueOf(arr[i])*60*1000;
                        else
                        if(arr[i+1].equals("hours")) time+=Long.valueOf(arr[i])*60*60*1000;
                        else
                        if(arr[i+1].equals("hours")) time+=Long.valueOf(arr[i])*24*60*60*1000;


                    }

                    task.setTime( (dateFormat.parse(date)),dateFormat.parse(dateTo),new Date(time));


                }



                tasks.add(task);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
