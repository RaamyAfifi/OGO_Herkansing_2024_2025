package com.Jabberpoint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;


/** <p>SlideViewerComponent is a graphical component that ca display Slides.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent implements PresentationObserver {

	private Slide slide; //The current slide
	private Font labelFont; //The font for labels
	private Presentation presentation; //The presentation
	private JFrame frame;

	private final Color BGCOLOR = Color.white;
	private final Color COLOR = Color.black;
	private final String FONTNAME = "Dialog";
	private final int FONTSTYLE = Font.BOLD;
	private final int FONTHEIGHT = 10;
	private final int XPOS = 1100;
	private final int YPOS = 20;

	public SlideViewerComponent(Presentation pres, JFrame frame)
	{
		setBackground(BGCOLOR);
		presentation = pres;
		labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
		this.frame = frame;
		pres.addObserver(this);
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(Slide.WIDTH, Slide.HEIGHT);
	}

	//Draw the slide
	public void paintComponent(Graphics g)
	{
		g.setColor(BGCOLOR);
		g.fillRect(0, 0, getSize().width, getSize().height);
		if (presentation.getSlideNumber() < 0 || slide == null)
		{
			return;
		}
		g.setFont(labelFont);
		g.setColor(COLOR);
		g.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " + presentation.getSize(), XPOS, YPOS);
		Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
		slide.draw(g, area, this);
	}

	@Override
	public void onSlideChanged(Presentation presentation, Slide currentSlide)
	{
		this.presentation = presentation;
		this.slide = currentSlide;
		repaint();
		frame.setTitle(presentation.getTitle());
	}
}
