package com.linseven.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.linseven.cache.CacheCenter;
import com.linseven.client.NettyClient;
import com.linseven.constant.NettyConstant;
import com.linseven.factory.MessageFactory;
import com.linseven.model.Message;
import com.linseven.model.UserInfo;
import com.linseven.sender.MessageSender;
import com.linseven.window.JJQQ;
import com.linseven.window.MainWindow;
import com.sun.javafx.geom.Rectangle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController implements Initializable 
{
	@FXML
	private Button loginBtn;
	@FXML
	private TextField userIdField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label errorMsgLabel;
	@FXML
	private Label userIdLabel;
	@FXML
	private Label passwordLabel;
	@FXML
	private Rectangle logo;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub

	}
	public void login(MouseEvent even)
	{
		String userIdStr = userIdField.getText();
		String passwordStr = passwordField.getText();
		if(!MessageSender.isConnected){
			
				Thread clientThread  = new Thread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try
						{
							new NettyClient().connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}});
				clientThread.start();
		}
		Message loginMsg = MessageFactory.buildLoginMsg(Long.parseLong(userIdStr), passwordStr);
		CacheCenter.getInstance().putMessage(loginMsg);
	}

}
