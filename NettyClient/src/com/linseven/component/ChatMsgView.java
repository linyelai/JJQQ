package com.linseven.component;

import java.awt.Font;
import java.awt.FontMetrics;
import java.io.IOException;

import com.linseven.model.Message;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ChatMsgView extends Pane {
	
	@FXML
	private TextArea msgTextArea;
	private Pane page;
	
	public ChatMsgView()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/linseven/component/ChatMsgView.fxml"));
		fxmlLoader.setController(this);
		try 
	    {
	    	 page =fxmlLoader.load();
	       
	    } 
	    catch (IOException exception) 
	    {
	        throw new RuntimeException(exception);
	    }
	}
	
	public void addMsg(Message msg)
	{
		if(msg!=null)
		{
			String msgStr = (String) msg.getBody();
			int len = msgStr.length();
			StringBuffer buffer = new StringBuffer();
			int row = getMsgStr(msgStr,buffer);
			/*Text text = new Text();
			text.setText(msgStr);
			text.setLayoutX(45);
			text.setLayoutY(30);
			text.setWrappingWidth(290);
			text.setAccessibleText("hhh");
			text.setCursor(Cursor.TEXT);*/
			msgTextArea.setText(msgStr);
			msgTextArea.setWrapText(true);
			Font f = new Font("ËÎÌו",Font.BOLD, 12);  
			FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(f);
			double height =page.getPrefHeight();
			msgTextArea.setPrefHeight((row+1)*fm.getHeight()+10);
			if(row>1)
			{
				page.setPrefHeight(height+(row-1)*fm.getHeight()+15);
			}
			
			
		}
	}

	private int getMsgStr(String msgStr, StringBuffer buffer) 
	{
		// TODO Auto-generated method stub
		int len = msgStr.length();
		int i=0;
		while(len-22>0)
		{
			
			buffer.append(msgStr.substring(i*22, (i+1)*22)+"\r\n");
			i++;
			len = len-22;
		}
		buffer.append(msgStr.substring(i*22,msgStr.length()));
		
		
		return i;
	}

	public  Parent getPage() {
		return page;
	}
	

}
