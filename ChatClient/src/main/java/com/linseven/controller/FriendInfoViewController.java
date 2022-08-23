package com.linseven.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.linseven.cache.CacheCenter;
import com.linseven.component.ChatDialog;
import com.linseven.window.JJQQ;
import com.linseven.window.MainWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class FriendInfoViewController implements Initializable 
{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub

	}
 
	public void chatWithFriend(MouseEvent event)
	{
		HBox friendItem = (HBox) event.getSource();
		ObservableMap<Object, Object> properties = friendItem.getProperties();
		Long userId = (Long) properties.get("userId");
		ChatDialog dialog = CacheCenter.getInstance().getDialogByUserId(userId);
		if(dialog==null){
			dialog = new ChatDialog(userId);
			CacheCenter.getInstance().addDialog(userId, dialog);
		}
		else
		{
			dialog.show();
		}
		
	}
}
