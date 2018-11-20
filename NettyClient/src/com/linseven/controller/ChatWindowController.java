package com.linseven.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.linseven.cache.CacheCenter;
import com.linseven.component.ChatMsgView;
import com.linseven.factory.MessageFactory;
import com.linseven.model.Message;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ChatWindowController implements Initializable 
{
	@FXML
	private VBox messageWindowView;
	@FXML
	private Pane sendMsgPane;
	@FXML
	private Button sendMsgBtn;
	@FXML
	private ScrollPane chatSroollPane;
	@FXML
	private TextField msgText;
	private Parent root;
	@FXML
	private Label userIdLabel;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub

	}

	public void addMsgChild(Node child)
	{
		messageWindowView.getChildren().add(child);
		
	}
	
	public void sendMsg(MouseEvent event)
	{
		long dstUserId =Long.valueOf(userIdLabel.getText()) ;
		ChatMsgView chatMsgView = new ChatMsgView();
		//Message msg = new Message();
		String text = msgText.getText();
		Message msg = MessageFactory.buildTextMsg(dstUserId, text);
		CacheCenter.getInstance().putMessage(msg);
		chatMsgView.addMsg(msg);
		addMsgChild(chatMsgView.getPage());
		chatSroollPane.vvalueProperty().bind(messageWindowView.heightProperty());
		//ImageView image = new ImageView();
		
		//sendMsgTextArea.
		//sendMsgTextArea.getChildrenUnmodifiable().add(image);
		//Text t = null;
		//t.accessibleHelpProperty().
		
		
	}
}
