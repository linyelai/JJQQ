package com.linseven.component;

import java.io.IOException;

import com.linseven.model.Message;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChatDialog {
	
	private long dstUserId;//发送的用户名
	private Stage stage;//窗口
	public ChatDialog(long dstUserId)
	{
		this.dstUserId = dstUserId;
		//初始化聊天对话框
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/linseven/component/chatWindow.fxml"));
		try {
			
			AnchorPane root = fxmlLoader.load();
			ObservableList<Node> child = root.getChildren();
			for(Node node:child){
				String nodeId = node.getId();
				if(nodeId!=null&&"userIdLabel".equals(nodeId)){
					
					Label userIdLabel = (Label) node;
					userIdLabel.setText(String.valueOf(dstUserId));
					break;
				}
			}
			stage = new Stage();
			//stage.setWidth(600);
			//stage.setHeight(400);
			Scene chatScene = new Scene(root);
			stage.setScene(chatScene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle(String.valueOf(dstUserId));
			//设置界面
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
					chatMsgView.addMsg(msg);
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
