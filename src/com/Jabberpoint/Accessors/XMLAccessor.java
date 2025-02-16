package com.Jabberpoint.Accessors;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.Jabberpoint.Presentation;
import com.Jabberpoint.Slide;
import com.Jabberpoint.SlideItems.BitmapItem;
import com.Jabberpoint.SlideItems.SlideItem;
import com.Jabberpoint.SlideItems.TextItem;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


/** XMLAccessor, reads and writes XML files
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor extends Accessor
{
	public void loadFile(Presentation presentation, String filename) throws IOException
	{
		try
		{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(new File(filename)); //Create a JDOM document
			Element doc = document.getDocumentElement();
			presentation.setTitle(getTitle(doc, "showtitle"));
			loadSlides(doc, presentation);

		}
		catch (IOException iox) {
			System.err.println(iox.toString());
		}
		catch (SAXException sax) {
			System.err.println(sax.getMessage());
		}
		catch (ParserConfigurationException pcx) {
			System.err.println("Parser Configuration Exception");
		}
	}

    private String getTitle(Element element, String tagName)
	{
    	NodeList titles = element.getElementsByTagName(tagName);
    	return titles.item(0).getTextContent();

    }

	public void loadSlides(Element doc, Presentation presentation){
		NodeList slides = doc.getElementsByTagName("slide");
		for(int i = 0; i < slides.getLength();  i++ ){
			Element xmlSlide = (Element) slides.item(i);
			Slide slide = new Slide();
			slide.setTitle(getTitle(xmlSlide, "title"));
			loadSlideItems(xmlSlide, slide);
			presentation.append(slide);
		}
	}

	public void loadSlideItems (Element xmlSlide, Slide slide){
		NodeList slideItems = xmlSlide.getElementsByTagName("item");
		for(int i = 0; i < slideItems.getLength();  i++ ){
			Element item = (Element) slideItems.item(i);
			slide.append(createSlideItem(item));
		}
	}

	public SlideItem createSlideItem(Element item){
		int level = Integer.parseInt(item.getAttribute("level"));
		String type = item.getAttribute("kind");
		String content = item.getTextContent();
		if ("text".equals(type)) {
			return new TextItem(level, content);
		}
		else {
			if ("image".equals(type)) {
				return new BitmapItem(level, content);
			}
			else {
				throw new IllegalArgumentException("Unsupported type: " + type);
			}
		}

	}

	public void saveFile(Presentation presentation, String filename) throws IOException
	{
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println("<?xml version=\"1.0\"?>");
		out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
		out.println("<presentation>");
		out.print("<showtitle>");
		out.print(presentation.getTitle());
		out.println("</showtitle>");
		for (int slideNumber=0; slideNumber<presentation.getSize(); slideNumber++) {
			Slide slide = presentation.getSlide(slideNumber);
			out.println("<slide>");
			out.println("<title>" + slide.getTitle() + "</title>");
			ArrayList<SlideItem> slideItems = slide.getSlideItems();
			for (int itemNumber = 0; itemNumber<slideItems.size(); itemNumber++) {
				SlideItem slideItem = (SlideItem) slideItems.get(itemNumber);
				out.print("<item kind=");
				if (slideItem instanceof TextItem) {
					out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
					out.print( ( (TextItem) slideItem).getText());
				}
				else {
					if (slideItem instanceof BitmapItem) {
						out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
						out.print( ( (BitmapItem) slideItem).getName());
					}
					else {
						System.out.println("Ignoring " + slideItem);
					}
				}
				out.println("</item>");
			}
			out.println("</slide>");
		}
		out.println("</presentation>");
		out.close();
	}
}
