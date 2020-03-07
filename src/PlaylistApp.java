/*
 * Class: CISC 3130
 * Section: TY2
 * EmplId: 23809622
 * Name: Liyu Lin
 */

import java.io.*;
import java.util.*;
/**
 *
 * @author linliyu
 */
 
//A node represents a song
class Song{
    String track;
    Song next;
  //no-arg constructor
    Song(){
      track = null; next = null;
    }
  //constructors with arguements
     Song(String track){
       this.track = track;
       next = null;
     }
     Song(String track, Song next){
       this.track = track;
       this.next = next;
     }
}//end of class Song

//The List Playlist is composed of a series of songs
class Playlist{
    // constructor for creating a new list
    // queue implemented linked list
    // double ended linked list
    public void Playlist(){
        this.front = this.rear = null;
    }
    public boolean isEmpty(){
        return((front == null)&&(rear == null));
    }
    public void addSong(String str){
        Song s = new Song(str);
        //first see if the list is empty
        if(this.rear == null){
            this.front = this.rear = s;
        }
        //there is something in the list
        //now check to see if it belongs in front
        else if(s.track.trim().substring(0, 2).compareToIgnoreCase(front.track.trim().substring(0, 2))<0){
            s.next = front;
            front = s;
        }
        //otherwise, step down the list
        else{
            Song after = front.next;
            Song before = front;
            while(after != null){
                if(s.track.trim().substring(0, 2).compareToIgnoreCase(after.track.trim().substring(0, 2))<0)
                    break;
                before = after;
                after = after.next;
            }
            //insert between before & after
            s.next = before.next;
            before.next = s;
        }
    }
    public void displayList(){
            System.out.println("\nTrack Names(in alphabetical order):");
        while(front != null){
            System.out.println(front.track + " ");
            front = front.next;
        }
    }
    public Song listenToSong(){
        return front.next;// retrieves the next song to listen to
    }
    private Song front;
    private Song rear;
}//end of class Playlist

/* The SongHistoryList implementation */
class SongHistoryList {
  private Song first;
  Stack<Song> songHistoryList = new Stack<>();
  public void SongHistoryList(){
    first = null;// constructor for creating a new list
  }
  public void addSong(Song s){
    songHistoryList.push(s);
  }
  public Song lastListened(){
    return songHistoryList.peek();// retrieves the last song listened
  }
}


class MyQueue extends LinkedList {
    //no-arg constructor
    public MyQueue(){
      Queue<String> qu = new LinkedList<>();
      Queue<String> qu1 = new LinkedList<>();
      Queue<String> qu2 = new LinkedList<>();
    }
    // constructor creates a linked list that stores songs from one text file
    public MyQueue(String csvFile){
        String line = "";
        String cvsSplitBy = ",";
        Queue<String> qu = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] track = line.split(cvsSplitBy);
                
                //System.out.println(track[1].replaceAll("^\"|\"$", ""));
                
                qu.add(track[1].replaceAll("^\"|\"$", ""));
            }
           qu.remove();
           qu.remove();
           sortQueue(qu);
           //show the values of queue
           System.out.println("Track Names(in ascending order):");
           for(String s : qu) { 
             System.out.println(s.toString()); 
           }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }//end of one-arg constructor
    
    //constructor with two arguements for merging two queues into one sorted queue
     public MyQueue(String csvFile1, String csvFile2){
        String line = "";
        String cvsSplitBy = ",";
        Queue<String> qu1 = new LinkedList<>();
        Queue<String> qu2 = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile1))) {
            
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] track = line.split(cvsSplitBy);
                
                //System.out.println(track[1].replaceAll("^\"|\"$", ""));
                
                qu1.add(track[1].replaceAll("^\"|\"$", ""));
            }
           qu1.remove();
           qu1.remove();
           sortQueue(qu1);
     
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile2))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] track = line.split(cvsSplitBy);
                
                //System.out.println(track[1].replaceAll("^\"|\"$", ""));
                
                qu2.add(track[1].replaceAll("^\"|\"$", ""));
            }
           qu2.remove();
           qu2.remove();
           sortQueue(qu2);
           Queue result = MyQueue.mergingFunction(qu1, qu2);
           //show the values of queue
           System.out.println("Track Names(in ascending order):");
           result.forEach((s) -> {
               System.out.println(s);
            }); //System.out.println(result.toString());
             
          
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }//end of two-args constructor
     
    
    /* This function takes two queues and returns one sorted queue */
    public static Queue mergingFunction(Queue q1, Queue q2){
         Queue<String> mergedQueue = new LinkedList<>();
         
         // If both queues are not empty.
         while (!q1.isEmpty() && !q2.isEmpty()) {
            String left = (String) q1.peek();
            String right = (String) q2.peek();
  
            if (left.trim().substring(0, 2).compareToIgnoreCase(right.trim().substring(0, 2)) < 0) {
                mergedQueue.add((String) q1.poll());
            } else {
                mergedQueue.add((String) q2.poll());
            }
        }

        // If there are remaining items in one of the queue.
        while (!q1.isEmpty()) {
            mergedQueue.add((String) q1.poll());
        }

        while (!q2.isEmpty()) {
            mergedQueue.add((String) q2.poll());
        }

        return mergedQueue;
    }

/* The following functions aim to sort the Queue using Recursion */
// Function to push element in last by 
// popping from front until size becomes 0 
static void FrontToLast(Queue<String> q, int qsize){ 
    // Base condition 
    if (qsize <= 0) 
        return; 
  
    // pop front element and push 
    // this last in a queue 
    q.add(q.peek()); 
    q.remove(); 
  
    // Recursive call for pushing element 
    FrontToLast(q, qsize - 1); 
} 
  
// Function to push an element in the queue 
// while maintaining the sorted order 
static void pushInQueue(Queue<String> q, String temp, int qsize){ 
  
    // Base condition 
    if (q.isEmpty() || qsize == 0){ 
        q.add(temp); 
        return; 
    } 
  
    // If current element is less than 
    // the element at the front 
    //
    else if (temp.trim().substring(0, 2).compareToIgnoreCase(q.peek().trim().substring(0, 2))<=0) { 
  
        // Call stack with front of queue 
        q.add(temp); 
  
        // Recursive call for inserting a front 
        // element of the queue to the last 
        FrontToLast(q, qsize); 
    } 
    else { 
  
        // Push front element into 
        // last in a queue 
        q.add(q.peek()); 
        q.remove(); 
  
        // Recursive call for pushing 
        // element in a queue 
        pushInQueue(q, temp, qsize - 1); 
    } 
} 
  
// Function to sort the given 
// queue using recursion 
static void sortQueue(Queue<String> q) { 
  
    // Return if queue is empty 
    if (q.isEmpty()) 
        return; 
  
    // Get the front element which will 
    // be stored in this variable 
    // throughout the recursion stack 
    String temp = q.peek(); 
  
    // Remove the front element 
    q.remove(); 
  
    // Recursive call 
    sortQueue(q); 
  
    // Push the current element into the queue 
    // according to the sorting order 
    pushInQueue(q, temp, q.size()); 
} 
    private Queue<String> qu;
    private Queue<String> qu1;
    private Queue<String> qu2;
}//end of class MyQueue

/* the class PlaylistApp contains the main class for testing functons */
public class PlaylistApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        String[] myFiles = {"week1.csv","week2.csv","week3.csv","week4.csv"};

// The files you'll be reading stored in a data structure to make it iterable
//       ArrayList<LinkedList> allTheWeeks = new ArrayList<>();
//        for (int i=0; i < myFiles.length; i++){
//             MyQueue dataExtract = new MyQueue(myFiles[i]);
//             allTheWeeks.add(dataExtract);
//          }
         
         //an example of reading any number of files in a directory.
         System.out.println("Choose one of the following lists: week1 week2 week3 week4 (i.e. enter 1 if you would like to listen week 1's list):");
         Scanner sc = new Scanner(System.in);
         MyQueue dataExtract = new MyQueue(myFiles[sc.nextInt()-1]);
         
         //an example of merging 2 sorted lists into one.
         MyQueue mergedQueue = new MyQueue(myFiles[0],myFiles[3]);
         
    }
    
}//end of class PlaylistApp
