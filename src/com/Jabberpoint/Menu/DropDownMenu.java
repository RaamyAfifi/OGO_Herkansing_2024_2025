package com.Jabberpoint.Menu;

import com.Jabberpoint.Presentation;

import java.awt.*;

public abstract class DropDownMenu
{
    protected Menu menu;
    protected Presentation presentation;
    protected Frame parent;

    public DropDownMenu(Frame parent, Presentation presentation)
    {
        this.presentation = presentation;
        this.parent = parent;
    }

    public Menu getMenu()
    {
        return menu;
    }

    public void setMenu(Menu menu)
    {
        this.menu = menu;
    }

    public abstract void addMenuItems();

    public MenuItem mkMenuItem(String name)
    {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
