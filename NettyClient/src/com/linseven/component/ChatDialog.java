package com.linseven.component;

import java.io.IOException;

import com.linseven.cache.CacheCenter;
import com.linseven.message.SendMessage;
import com.linseven.model.Message;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChatDialog {
	
	private long dstUserId;//���͵��û���
	private Stage stage;//����
	public ChatDialog(long dstUserId)
	{
		this.dstUserId = dstUserId;
		//��ʼ������Ի���
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/linseven/component/chatWindow.fxml"));
		try {
			
			AnchorPane root = fxmlLoader.load();
			ObservableList<Node> child = root.getChildren();
			HBox hbox = (HBox) child.get(0);
			child = hbox.getChildren();
			for(Node node:child){
				String nodeId = node.getId();
				if(nodeId!=null&&"userIdLabel".equals(nodeId)){
					Label userIdLabel = (Label) node;
					userIdLabel.setText(String.valueOf(dstUserId));
				}
				else if(nodeId!=null&&"friendNameLabel".equals(nodeId)){//friendNameLabel
					Label userIdLabel = (Label) node;
					String friendName = CacheCenter.getInstance().getFriendName(dstUserId);
					userIdLabel.setText(friendName);
					break;
				}
			}
			stage = new Stage();
			Scene chatScene = new Scene(root);
			stage.setScene(chatScene);
			stage.setTitle(String.valueOf(dstUserId));
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void show(){
		
		stage.sizeToScene();
		stage.toFront();
		stage.show();
	}
	
	public void reciveMsg(Message msg)
	{
		if(msg!=null){
			
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					ChatMsgView chatMsgView = new ChatMsgView();
					SendMessage sendMessage = new SendMessage();
					sendMessage.setContent((String)msg.getBody());
					sendMessage.setType(1);
					chatMsgView.addMsg(sendMessage);
					VBox messageWindowView =null;
					AnchorPane pane = (AnchorPane) stage.getScene().getRoot();
					ObservableList<Node> child = pane.getChildren();
					for(Node node:child){
						String nodeId = node.getId();
						if(nodeId!=null&&"chatSroollPane".equals(nodeId)){
							
							ScrollPane chatSroollPane=  (ScrollPane) node;
							messageWindowView = (VBox) chatSroollPane.getContent();
							ObservableList<Node> subChild = messageWindowView.getChildren();
							if(subChild!=null){
								subChild.add(chatMsgView.getPage());
								
							}
							break;
						}
					}
					
				}});
			
		}
	}

}
