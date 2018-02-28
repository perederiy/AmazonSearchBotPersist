package org.itstep.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import org.itstep.model.Account;
import org.itstep.model.AddGood;
import org.itstep.model.Good;
import org.itstep.service.CreateAccount;
import org.itstep.service.CreateEntityManager;
import org.itstep.service.ImitatorAmazon;
import org.itstep.service.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class WindowsView extends JFrame {
	private JTextField inputTF;
	private JTextField deleteTF;

	public WindowsView() {
		setTitle("Amazon Bot");
		setBounds(200, 200, 340, 340);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel inputLabel = new JLabel("Enter ASIN");
		inputLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		inputLabel.setBounds(10, 34, 85, 20);
		getContentPane().add(inputLabel);
		
		JLabel deleteLabel = new JLabel("Enter ASIN");
		deleteLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		deleteLabel.setBounds(10, 100, 85, 20);
		getContentPane().add(deleteLabel);
		
		inputTF = new JTextField();
		inputTF.setBounds(93, 35, 113, 20);
		getContentPane().add(inputTF);
		inputTF.setColumns(10);
		
		deleteTF = new JTextField();
		deleteTF.setBounds(93, 101, 113, 20);
		getContentPane().add(deleteTF);
		deleteTF.setColumns(10);
		
		JLabel inputInfoLabel = new JLabel("");
		inputInfoLabel.setBounds(10, 65, 307, 24);
		getContentPane().add(inputInfoLabel);
		
		JLabel deleteInfoLabel = new JLabel("");
		deleteInfoLabel.setBounds(10, 132, 307, 24);
		getContentPane().add(deleteInfoLabel);
		
		JButton inputButton = new JButton("SAVE");
		inputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImitatorAmazon input = new ImitatorAmazon();
				EntityManager em = CreateEntityManager.getSession();
				em.getTransaction().begin();
				em.persist(input.searchGood(inputTF.getText()));
				em.getTransaction().commit();
				CreateEntityManager.closeSession();
			}
		});
		inputButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		inputButton.setBounds(216, 33, 89, 23);
		getContentPane().add(inputButton);
		
		JButton deleteButton = new JButton("DELETE");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EntityManager em = CreateEntityManager.getSession();
				Query query = em.createNativeQuery("SELECT g FROM good g WHERE g.asin = " + deleteTF.getText(), Good.class);
				em.getTransaction().begin();
				Good good = (Good)query.getSingleResult();
				em.remove(good);
				em.getTransaction().commit();
				CreateEntityManager.closeSession();
				//deleteInfoLabel.setText("Good was deleted");
			}
		});
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		deleteButton.setBounds(216, 99, 89, 23);
		getContentPane().add(deleteButton);
		
		JLabel head1Label = new JLabel("Save or delete good");
		head1Label.setFont(new Font("Tahoma", Font.BOLD, 13));
		head1Label.setBounds(47, 3, 219, 20);
		getContentPane().add(head1Label);
		
		JLabel registerInfoLabel = new JLabel("");
		registerInfoLabel.setBounds(47, 220, 234, 24);
		getContentPane().add(registerInfoLabel);
		
		JButton startButton = new JButton("START PROGRAM");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ImitatorAmazon imitator = new ImitatorAmazon();
				
				WebDriver driver = null;
				EntityManager em = CreateEntityManager.getSession();
				for(int i=0; i<3; i++) {
					Account account = new Account();	
					boolean registerIsNotTrue = true;
						do {
							account = CreateAccount.createAccountAmazon();
							driver = imitator.registerAccount(account);
								if (driver.getPageSource().contains("Hello, " + account.getName())) {	
									em.getTransaction().begin();
									em.persist(account);
									em.getTransaction().commit();
									registerIsNotTrue = false;
								} else {
									Timer.getTimeInSeconds(5);
									driver.quit();
									//registerInfoLabel.setText("User is not registered");
					
								}
				
						}while(registerIsNotTrue);
						
				Query query = em.createNativeQuery("SELECT g.asin FROM good g");	
				em.getTransaction().begin();
				ArrayList<String> asin = (ArrayList)query.getResultList();	
				em.getTransaction().commit();
				
				if(asin != null) {
					int counter = 1;
						for (String asinItem : asin) {
							//driver = imitator.getAddToWishList(driver, asinItem);
							//long timeAddToWishList = System.currentTimeMillis();
							//daoVLC.addWishList(asinItem, account, timeAddToWishList);
							//	if (driver == null) {
							//		registerInfoLabel.setText("Good isn't added to wishlist");
							//		break;
							//	}
							if(counter%2 == 0) {
								driver = imitator.getAddToCart(driver, asinItem);
								long timeAddToCart = System.currentTimeMillis();
								AddGood addGood = new AddGood();
								addGood.setAsin(asinItem);
								addGood.setEmail(account.getEmailUser());
								addGood.setAddCard("Yes");
								addGood.setTimeCard(timeAddToCart);
								em.getTransaction().begin();
								em.persist(addGood);
								em.getTransaction().commit();
									if (driver == null) {
										//registerInfoLabel.setText("Good isn't added to cart");
										break;
									}
							}
							counter++;
						}
				} else {
					//registerInfoLabel.setText("Database has no goods");
				}
				
				Timer.getTimeInSeconds(5);
				driver.quit();
				}
				CreateEntityManager.closeSession();
				//registerInfoLabel.setText("Program has ended");
			}
		});
		startButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		startButton.setBounds(93, 166, 154, 39);
		getContentPane().add(startButton);
		setVisible(true);
	}
}