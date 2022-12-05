import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
//import java.util.Random;
//import java.io.*;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  //private static final String SPACE = " ";
  
  public static void main(String[] args) {
    //System.out.println(totalSentiment("/workspace/ApcsThings/ConsumerLabCode/SimpleReview.txt"));
    //System.out.println(starRating("/workspace/ApcsThings/ConsumerLabCode/SimpleReview.txt"));
    //System.out.println(totalSentiment("/workspace/ApcsThings/ConsumerLabCode/GoodReview.txt"));
    //System.out.println(starRating("/workspace/ApcsThings/ConsumerLabCode/GoodReview.txt"));
    System.out.println(fakeReview("/workspace/ApcsThings/ConsumerLabCode/SimpleReview.txt"));
  }

  public static double totalSentiment(String filename) {
    String thingy = textToString(filename);
    double totalSentiment = 0.0;
    while (!(thingy.equals(""))) {//loops until there are no words left
      int index = thingy.indexOf(" "); //gets the index of the first space
      if (index == -1) { //if there isn't a space, just pretend that there is at the end of the word
        index = thingy.length()-1;
      }
      totalSentiment += sentimentVal(removePunctuation(thingy.substring(0, index))); //add the sentiment value of the first word to the total
      thingy = thingy.substring(index+1); //remove that word from the list of words
    }
    return totalSentiment;
  }

  public static int starRating(String filename) {
    double sentiment = totalSentiment(filename);
    if (sentiment < -2) {
      return 1;
    } else if (sentiment <-0.5) {
      return 2;
    } else if (sentiment < 0.5) {
      return 3;
    } else if (sentiment < 2) {
      return 4;
    } else {
      return 5;
    }
  }

  public static String fakeReview(String filename) {
    String thingy = textToString(filename) + " ";
    while (!(thingy.indexOf("*") == -1)) {
      int indexStar = thingy.indexOf("*");
      int indexSpc = thingy.substring(indexStar).indexOf(" ") + thingy.substring(0, indexStar).length();
      thingy = thingy.substring(0, indexStar) + randomAdjective() + thingy.substring(indexSpc);
    }
    return thingy;
  }

  static{
    try {
      Scanner input = new Scanner(new File("/workspace/ApcsThings/ConsumerLabCode/cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("/workspace/ApcsThings/ConsumerLabCode/positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        //System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("/workspace/ApcsThings/ConsumerLabCode/negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }

      /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
 
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }
}
