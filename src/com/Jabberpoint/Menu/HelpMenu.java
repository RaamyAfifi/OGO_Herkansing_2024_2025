package com.Jabberpoint.Menu;

import com.Jabberpoint.AboutBox;
import com.Jabberpoint.Presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpMenu extends DropDownMenu
{
    public HelpMenu(Frame parent, Presentation presentation)
    {
        super(parent, presentation);
        this.menu = new Menu("Help");
        addMenuItems();
    }
    @Override
    public void addMenuItems()
    {
        MenuItem menuItem;
        this.menu.add(menuItem = mkMenuItem("About"));
        menuItem.addActionListener(actionEvent -> AboutBox.show(parent));
    }
}
