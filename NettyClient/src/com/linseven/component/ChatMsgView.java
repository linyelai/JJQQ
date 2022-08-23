package com.linseven.component;

import java.awt.Font;
import java.awt.FontMetrics;
import java.io.IOException;

import com.linseven.message.SendMessage;
import com.linseven.model.Message;





import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ChatMsgView extends Pane {


	private Text msgItem;
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

	public void addMsg(SendMessage msg)
	{
		if(msg!=null)
		{
			int type = msg.getType();
			String msgStr = msg.getContent();
			ImageView imageView = new ImageView();
			Image image = null;
			double height =page.getPrefHeight();
			switch(type){

				case 1://
					int len = msgStr.length();
					StringBuffer buffer = new StringBuffer();
					int row = getMsgStr(msgStr,buffer);
					msgItem = new Text();
					msgItem.setLayoutX(77);
					msgItem.setLayoutY(29);
					msgItem.setText(msgStr);
					msgItem.setWrappingWidth(300);
					Font f = new Font("ËÎÌו",Font.BOLD, 12);
					FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(f);

					if(row>1)
					{
						page.setPrefHeight(height+(row-1)*fm.getHeight()+15);
					}
					page.getChildren().add(msgItem);
					break;
				case 2://
					imageView.setLayoutX(77);
					imageView.setLayoutY(20);
					imageView.setFitWidth(22);
					imageView.setFitHeight(22);
					image = new Image("/resource/img/hj.png");
					imageView.setImage(image);
					page.getChildren().add(imageView);
					break;
				case 3:
					imageView.setLayoutX(77);
					imageView.setLayoutY(20);
					imageView.setFitWidth(200);
					imageView.setFitHeight(200);
					image = new Image("file:"+msgStr);
					imageView.setImage(image);
					page.getChildren().add(imageView);
					page.setPrefHeight(height+200);
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
