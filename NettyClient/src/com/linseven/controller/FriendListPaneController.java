package com.linseven.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.linseven.model.UserInfo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class FriendListPaneController implements Initializable{

	@FXML
	private AnchorPane friendListPane;
	@FXML
	private VBox friendListBox;
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		
	}
	public void addFriend(UserInfo user)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/linseven/component/friendInfoView.fxml"));
		try 
		{
			AnchorPane friendItem = fxmlLoader.load();
			friendListBox.getChildren().add(friendItem);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
