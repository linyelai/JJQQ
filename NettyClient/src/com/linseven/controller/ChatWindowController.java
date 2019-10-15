package com.linseven.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;







import netscape.javascript.JSObject;

import com.linseven.cache.CacheCenter;
import com.linseven.component.ChatMsgView;
import com.linseven.factory.MessageFactory;
import com.linseven.message.SendMessage;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

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
	private TextArea msgText;
	private Parent root;
	@FXML
	private Label userIdLabel;
	@FXML
	private Label friendNameLabel;
	@FXML
	private WebView sendPic;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub
		this.msgText.setWrapText(true);
		msgText.setOnKeyPressed(keyEvent->{
			if(keyEvent.getCode().equals(KeyCode.ENTER)){
				long dstUserId =Long.valueOf(userIdLabel.getText()) ;
				ChatMsgView chatMsgView = new ChatMsgView();
				String text = msgText.getText();
				if(text!=null&&text.trim().length()>0){
				Message msg = MessageFactory.buildTextMsg(dstUserId, text);
				CacheCenter.getInstance().putMessage(msg);
				SendMessage sendMessage = new SendMessage();
				sendMessage.setContent(text);
				sendMessage.setType(1);
				chatMsgView.addMsg(sendMessage);
				addMsgChild(chatMsgView.getPage());
				chatSroollPane.vvalueProperty().bind(messageWindowView.heightProperty());
				msgText.clear();
				}
			}
		});
		//发送图片
		String url = ChatWindowController.class.getResource("sendPic.html").toExternalForm();
		WebEngine engine  = sendPic.getEngine();
		engine.load(url);
		JSObject window = (JSObject) engine.executeScript("window");  
		window.setMember("chatWindow", this);

	}

	public void addMsgChild(Node child)
	{
		messageWindowView.getChildren().add(child);
		
	}
	
	public void sendMsg(MouseEvent event)
	{
		long dstUserId =Long.valueOf(userIdLabel.getText()) ;
		ChatMsgView chatMsgView = new ChatMsgView();
		String text = msgText.getText();
		if(text!=null&&text.trim().length()>0){
		Message msg = MessageFactory.buildTextMsg(dstUserId, text);
		CacheCenter.getInstance().putMessage(msg);
		SendMessage sendMessage = new SendMessage();
		sendMessage.setContent(text);
		sendMessage.setType(1);
		chatMsgView.addMsg(sendMessage);
		addMsgChild(chatMsgView.getPage());
		chatSroollPane.vvalueProperty().bind(messageWindowView.heightProperty());
		msgText.clear();
		}
		
	}
	
	/**
	 * 发送表情包
	 */
	public void sendEmo(MouseEvent event){
		long dstUserId =Long.valueOf(userIdLabel.getText()) ;
		ChatMsgView chatMsgView = new ChatMsgView();
		String text = "/:hj";
		if(text!=null&&text.trim().length()>0){
		Message msg = MessageFactory.buildTextMsg(dstUserId, text);
		CacheCenter.getInstance().putMessage(msg);
		SendMessage sendMessage = new SendMessage();
		sendMessage.setContent(text);
		sendMessage.setType(2);
		chatMsgView.addMsg(sendMessage);
		addMsgChild(chatMsgView.getPage());
		chatSroollPane.vvalueProperty().bind(messageWindowView.heightProperty());
		msgText.clear();
		
	}
	}
	
	public void sendPicture(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
			    new FileChooser.ExtensionFilter("All Images", "*.*"),
			    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
			    new FileChooser.ExtensionFilter("GIF", "*.gif"),
			    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
			    new FileChooser.ExtensionFilter("PNG", "*.png")
			);


		File file = fileChooser.showOpenDialog(null);
		long dstUserId =Long.valueOf(userIdLabel.getText()) ;
		ChatMsgView chatMsgView = new ChatMsgView();
		String text = file.getAbsolutePath();
		if(text!=null&&text.trim().length()>0){
		Message msg = MessageFactory.buildTextMsg(dstUserId, text);
		CacheCenter.getInstance().putMessage(msg);
		SendMessage sendMessage = new SendMessage();
		sendMessage.setContent(text);
		sendMessage.setType(3);
		chatMsgView.addMsg(sendMessage);
		addMsgChild(chatMsgView.getPage());
		chatSroollPane.vvalueProperty().bind(messageWindowView.heightProperty());
		msgText.clear();

		}
	}
	
	
	public void sendFile(){
		
		FileChooser fileChooser = new FileChooser();
       //上传文件到文件系统，
	}
	
}
