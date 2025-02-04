package com.Jabberpoint;

import java.awt.Color;
import java.awt.Font;

/** <p>Style stands for Indent, Color, Font and Leading.</p>
 * <p>The link between a style number and a item level is hard-linked:
 * in Slide the style is grabbed for an item
 * with a style number the same as the item level.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Style {
	private static Style[] styles = new Style[]{
			new Style(0, Color.RED, 48, 20),
			new Style(20, Color.BLUE, 40, 10),
			new Style(50, Color.BLACK, 36, 10),
			new Style(70, Color.BLACK, 30, 10),
			new Style(90, Color.BLACK, 24, 10)}; // de styles

	private static final String FONTNAME = "Helvetica";
	public int indent;
	public Color color;
	public Font font;
	public int fontSize;
	public int leading;
//	static {
//		createDefaultStyles();
//	}

	public static void createDefaultStyles() {
		styles = new Style[]{
				new Style(0, Color.RED, 48, 20),
				new Style(20, Color.BLUE, 40, 10),
				new Style(50, Color.BLACK, 36, 10),
				new Style(70, Color.BLACK, 30, 10),
				new Style(90, Color.BLACK, 24, 10)
		};
	}

	public static Style getStyle(int level) {
		if (level >= styles.length) {
			level = styles.length - 1;
		}
		return styles[level];
	}

	public Style(int indent, Color color, int points, int leading) {
		this.indent = indent;
		this.color = color;
		font = new Font(FONTNAME, Font.BOLD, fontSize=points);
		this.leading = leading;
	}

	public String toString() {
		return "["+ indent + "," + color + "; " + fontSize + " on " + leading +"]";
	}

	public Font getFont(float scale) {
		return font.deriveFont(fontSize * scale);
	}

	public int getIndent()
	{
		return indent;
	}

	public int getFontSize()
	{
		return fontSize;
	}

	public Color getColor()
	{
		return color;
	}

	public int getLeading()
	{
		return leading;
	}
}
