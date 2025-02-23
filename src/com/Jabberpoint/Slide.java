package com.Jabberpoint;

import com.Jabberpoint.SlideItems.SlideItem;
import com.Jabberpoint.SlideItems.TextItem;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/** <p>A slide. This class has drawing functionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide {
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;
	private String title; //The title is kept separately
	private ArrayList<SlideItem> items; //The SlideItems are kept in a ArrayList

	// Constructor for dependency injection
	public Slide(String title, ArrayList<SlideItem> items)
	{
		if(title != null) {
			this.title = title;
		}
		if(items != null) {
			this.items = items;
		}
	}

	public Slide() {
		this(null, new ArrayList<>());
	}

	//Add a SlideItem
	public void append(SlideItem anItem) {
		if(anItem != null){
			items.add(anItem);
		}
	}

	//Return the title of a slide
	public String getTitle() {
		return title;
	}

	//Change the title of a slide
	public void setTitle(String newTitle) {
		title = newTitle;
	}

	//Create a Jabberpoint.Menu.HelpMenu.TextItem out of a String and add the Jabberpoint.Menu.HelpMenu.TextItem
	public void append(int level, String message) {
		append(new TextItem(level, message));
	}

	//Returns the SlideItem
	public SlideItem getSlideItem(int number) {
		if(number < 0 || number >= items.size()) {
			throw new IndexOutOfBoundsException();
		}
		return items.get(number);
	}

	//Return all the SlideItems in a ArrayList
	public ArrayList<SlideItem> getSlideItems() {
		return items;
	}

	//Draws the slide
	public void draw(Graphics g, Rectangle area, ImageObserver view)
	{
		int y = area.y;
		float scale = getScale(area);
		SlideItem slideItem = new TextItem(0, getTitle());
		Style style = Style.getStyle(slideItem.getLevel());
		slideItem.draw(area.x, area.y, scale, g, style, view);
		y+= slideItem.getBoundingBox(g, view, scale, style).height;
		drawItems(y,g, area, scale, view);
	}

	//Returns the size of a slide
	public int getSize() {
		return items.size();
	}

	// draws the items
	public void drawItems(int y,Graphics g, Rectangle area, float scale, ImageObserver view){
		for (SlideItem slideItem : items)
		{
			Style style = Style.getStyle(slideItem.getLevel());
			slideItem.draw(area.x, y, scale, g, style, view);
			y += slideItem.getBoundingBox(g, view, scale, style).height;
		}
	}

	//Returns the scale to draw a slide
	private float getScale(Rectangle area) {
		return Math.min(((float)area.width) / ((float)WIDTH), ((float)area.height) / ((float)HEIGHT));
	}
}
