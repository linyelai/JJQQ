package com.linseven.window;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.linseven.component.ChatWindow;
import com.linseven.model.Friend;
import com.linseven.model.UserInfo;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainWindow extends VBox 
{
	
	private static AnchorPane friendListPane;
	
	private static VBox friendListBox;
	
	private static AnchorPane mainWin;
	public MainWindow(UserInfo user)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/linseven/window/MainWindow.fxml"));
        FXMLLoader friendsListViewLoader = new FXMLLoader(getClass().getResource("/com/linseven/component/friendsListView.fxml"));
      //  fxmlLoader.setController(this);
        try 
        {
        	mainWin =fxmlLoader.load();
            friendListPane = friendsListViewLoader.load();
           List<Node> child=  mainWin.getChildren();
           System.out.println(child.size());
           child.add(friendListPane);
           ObservableList<Node> nodelist = friendListPane.getChildren();
           TabPane tabPane = (TabPane) nodelist.get(0);
           ObservableList<Tab> tabs = tabPane.getTabs();
          Tab tab = tabs.get(0);
          AnchorPane  anchroPane = (AnchorPane) tab.getContent();
          ScrollPane srollPane =  (ScrollPane) anchroPane.getChildren().get(0);
          friendListBox = (VBox) srollPane.getContent();
        } 
        catch (IOException exception) 
        {
            throw new RuntimeException(exception);
        }
	}
	public final Parent getRoot() {
		return mainWin;
	}
	
	public void addFriend(List<Friend> friends)
	{
		try 
		{
			for(Friend friend:friends){
				
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/linseven/component/friendInfoView.fxml"));
				AnchorPane friendItem = fxmlLoader.load();
				ObservableList<Node> child = friendItem.getChildren();
				Label friendLabel = (Label) child.get(1);
				friendLabel.setText(friend.getName());
				HBox child1 = (HBox) child.get(0);
				ObservableMap<Object, Object> properties = child1.getProperties();
				properties.put("userId", friend.getFriendId());
				friendListBox.getChildren().add(friendItem);
				
				}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
