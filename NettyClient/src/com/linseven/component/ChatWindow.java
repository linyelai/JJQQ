package com.linseven.component;

import java.io.IOException;
import java.util.List;

import com.linseven.model.Message;
import com.linseven.model.UserInfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ChatWindow extends VBox 
{
	/*@FXML
	private VBox messageWindowView;
	@FXML
	private TextArea sendMsgTextArea;
	@FXML
	private Button sendMsgBtn;
	@FXML
	private ScrollPane chatSroollPane;*/
	
	private Parent root;
	public ChatWindow()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/linseven/component/chatWindow.fxml"));
	    //fxmlLoader.setRoot(this);
	  //  fxmlLoader.setController(this);
	    ObservableList<String> items =FXCollections.observableArrayList ();
	    try 
	    {
	        root =fxmlLoader.load();
	       
	    } 
	    catch (IOException exception) 
	    {
	        throw new RuntimeException(exception);
	    }
	}
	
	public final Parent getRoot() {
		return root;
	}
	
	
}
