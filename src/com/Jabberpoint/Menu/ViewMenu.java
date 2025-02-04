package com.Jabberpoint.Menu;

import com.Jabberpoint.Presentation;

import javax.swing.*;
import java.awt.*;

public class ViewMenu extends DropDownMenu
{

    public ViewMenu(Frame parent, Presentation presentation)
    {
        super(parent, presentation);
        this.menu = new Menu("View");
        addMenuItems();
    }

    @Override
    public void addMenuItems()
    {
        MenuItem menuItem;
        this.menu.add(menuItem = mkMenuItem("Next"));
        menuItem.addActionListener(actionEvent -> presentation.nextSlide());

        this.menu.add(menuItem = mkMenuItem("Prev"));
        menuItem.addActionListener(actionEvent -> presentation.prevSlide());

        this.menu.add(menuItem = mkMenuItem("Go to"));
        menuItem.addActionListener(actionEvent ->
        {
            String pageNumberStr = JOptionPane.showInputDialog("Page number?");
            int pageNumber = Integer.parseInt(pageNumberStr);
            presentation.setSlideNumber(pageNumber - 1);
        });
    }
}
