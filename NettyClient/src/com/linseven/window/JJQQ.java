package com.linseven.window;

import com.linseven.client.NettyClient;
import com.linseven.constant.NettyConstant;
import com.linseven.controller.LoginController;
import com.linseven.controller.MainController;
import com.linseven.sender.MessageSender;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JJQQ extends Application {

	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		// TODO Auto-generated method stub
		try
		{       stage = primaryStage;  
			 	Pane root = FXMLLoader.load(getClass() .getResource("/com/linseven/window/Login.fxml"));
			 	stage.setTitle("JJQQ");
			 	stage.initStyle(StageStyle.UNDECORATED);
			 	stage.setScene(new Scene(root));
			 	stage.show();
			 	Thread sendThread = new Thread(new MessageSender());
				sendThread.start();
	            
        }
		catch(Exception e)
		{
            e.printStackTrace();
        }

	}
	
	 public static void main(String[] args)
	 {
		launch(args);
	 }

}
