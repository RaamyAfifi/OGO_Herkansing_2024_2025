package com.Jabberpoint;

import java.util.ArrayList;
import java.util.List;

public class Presentation {
    private String showTitle; //The title of the presentation
    private ArrayList<Slide> showList; //An ArrayList with slides
    private int currentSlideNumber = 0; //The number of the current slide

    private List<PresentationObserver> observers = new ArrayList<>();

    // notify when navigation has happened
    public void addObserver(PresentationObserver observer)
    {
        observers.add(observer);
    }
    public void removeObserver(PresentationObserver observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for(PresentationObserver observer : observers){
            observer.onSlideChanged(this, getCurrentSlide());
        }
    }

    // Constructor for dependency injection
    public Presentation(ArrayList<Slide> slides) {
        if(slides == null){
            this.showList = new ArrayList<>();
            clear();
        }
        else{
            this.showList = slides;
            this.currentSlideNumber = 0;
        }
    }

    // Default Constructor initializes an empty list
    public Presentation() {
        this(new ArrayList<>());
    }

    public ArrayList<Slide> getShowList()
    {
        return showList;
    }

    public int getSize()
    {
        return showList.size();
    }

    public String getTitle()
    {
        return showTitle;
    }

    public void setTitle(String nt)
    {
        showTitle = nt;
    }


    //Returns the number of the current slide
    public int getSlideNumber()
    {
        return currentSlideNumber;
    }

    //Change the current slide number and report it the the window
    public void setSlideNumber(int number)
    {
        if( number >= 0 && number < showList.size() ){
            currentSlideNumber = number;
            notifyObservers();
        }
    }

    //Navigate to the previous slide unless we are at the first slide
    public void prevSlide()
    {
        if (currentSlideNumber > 0)
        {
            setSlideNumber(currentSlideNumber - 1);
        }
    }

    //Navigate to the next slide unless we are at the last slide
    public void nextSlide()
    {
        if (currentSlideNumber < (showList.size()-1))
        {
            setSlideNumber(currentSlideNumber + 1);
        }
    }

    //Remove the presentation
    public void clear()
    {
        showList = new ArrayList<Slide>();
        currentSlideNumber = -1;
    }

    //Add a slide to the presentation
    public void append(Slide slide)
    {
        showList.add(slide);
    }

    //Return a slide with a specific number
    public Slide getSlide(int number)
    {
        if (number < 0 || number >= getSize()){
            return null;
        }
        return (Slide)showList.get(number);
    }

    //Return the current slide
    public Slide getCurrentSlide()
    {
        return getSlide(currentSlideNumber);
    }

}