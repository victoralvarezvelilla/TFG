import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginWindow extends JFrame implements ActionListener {
	
	// Declaracion de la ventana de login
		JFrame frame;
		// Declaración de botons y campos de escritura
		private JTextField userField;
		private JPasswordField passField;
		private JButton entryButton;

		private boolean correctLogin = false;
		
		private DBConnection oDBConection;
	

	public LoginWindow(){
		
		runLogin();
	}

	 void runLogin() {
		
		 frame = new JFrame();
		 frame.setBounds(100, 100, 570, 400);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setLocationRelativeTo(null);
		 frame.setTitle("Login");
		 frame.getContentPane().setLayout(null);
		 frame.setResizable(false);
		 frame.setVisible(true);

		 JLabel userLabel = new JLabel("Usuario: ");
		 userLabel.setBounds(80, 120, 86, 14);
		 frame.getContentPane().add(userLabel);

		 JLabel passLabel = new JLabel("Contraseña: ");
		 passLabel.setBounds(59, 170, 86, 14);
		 frame.getContentPane().add(passLabel);
		 
		 userField = new JTextField();
		 userField.setBounds(150, 115, 176, 25);
		 frame.getContentPane().add(userField);
		 userField.setColumns(20);

		 passField = new JPasswordField();
		 passField.setBounds(150, 165, 176, 25);
		 frame.getContentPane().add(passField);
		 passField.setColumns(20);

		 entryButton = new JButton("Entrar");
		 entryButton.setBounds(170, 200, 89, 23);
		 frame.getContentPane().add(entryButton);
		 entryButton.addActionListener(this);
		 
		 oDBConection = new DBConnection();
		
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == entryButton) {
			 oDBConection.conectar();
			
			try {
				if(String.valueOf(passField.getPassword()).isEmpty() && userField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Introduzca los datos");
				}else if (String.valueOf(passField.getPassword()).isEmpty()){
					JOptionPane.showMessageDialog(null, "Introduzca contraseña");
				}else if (userField.getText().isEmpty()){	
					JOptionPane.showMessageDialog(null, "Introduzca nombre");
				}else{
					if( oDBConection.systemEntry(userField.getText(), String.valueOf(passField.getPassword()))){
						JOptionPane.showMessageDialog(null, "Usuario correcto");
						
						if (DBConnection.getSesionRol() == false){
							AdminWindowMenu menuAdministrador = new AdminWindowMenu();
							frame.dispose();
						}else {
							UserMainWindow menu = new UserMainWindow();
							frame.dispose();
						}
						

					} else {
						JOptionPane.showMessageDialog(null, "Usuario y/o incorrectos");
						userField.setText("");
						passField.setText("");
					}
				}
			} catch (SQLException | FileNotFoundException e1) {
				// TODO Auto-generated catch block
				
				e1.printStackTrace();
			}
		
		
		}
			
	}


	
}
