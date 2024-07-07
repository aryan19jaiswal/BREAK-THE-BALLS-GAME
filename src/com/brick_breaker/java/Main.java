package com.brick_breaker.java;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args)
	{
		JFrame obj = new JFrame();
		Gameplay gP = new Gameplay();
		
		//Sizing the Frame
		obj.setBounds(10, 10, 700,600);
		
		//Naming the Frame
		obj.setTitle("Break the Balls");
		
		//making frame of the fixed size
		obj.setResizable(false);
		
		//making frame visible
		obj.setVisible(true);
		
		//
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//
		obj.add(gP); 

	}

}
