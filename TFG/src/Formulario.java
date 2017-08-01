import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class Formulario extends JFrame  {
	
	JFrame frame;
	private JButton botonAceptar;

	Formulario(){
		
		frame = new JFrame();
		 frame.setBounds(300, 200, 470, 400);
		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 frame.setLocationRelativeTo(null);
		 frame.setTitle("Formulario");
		 frame.getContentPane().setLayout(null);
		 frame.setResizable(false);
		 frame.setVisible(true);
		 
		 JLabel etiqueta1 = new JLabel("Nombre ");
		 etiqueta1.setBounds(50, 50, 100, 14);
		 frame.getContentPane().add(etiqueta1);
		 
		 JTextField nombreTF = new JTextField();
		 nombreTF.setBounds(160, 50, 150, 20);
		 frame.getContentPane().add(nombreTF);
		 nombreTF.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (nombreTF.getText().length() == 20){
					e.consume();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		 
		 JLabel etiqueta2 = new JLabel("Apellidos ");
		 etiqueta2.setBounds(50, 100, 100, 14);
		 frame.getContentPane().add(etiqueta2);
		 
		 JTextField apellidosTF = new JTextField();
		 apellidosTF.setBounds(160, 100, 150, 20);
		 frame.getContentPane().add(apellidosTF);
		 apellidosTF.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					if (apellidosTF.getText().length() == 50){
						e.consume();
					}
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
		 
		 JLabel etiqueta3 = new JLabel("Usuario ");
		 etiqueta3.setBounds(50, 150, 100, 14);
		 frame.getContentPane().add(etiqueta3);
		 
		 JTextField passnameTF = new JTextField();
		 passnameTF.setBounds(160, 150, 150, 20);
		 frame.getContentPane().add(passnameTF);
		 passnameTF.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					if (passnameTF.getText().length() == 20){
						e.consume();
					}
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
		 
		 JLabel etiqueta4 = new JLabel("Contraseña ");
		 etiqueta4.setBounds(50, 200, 100, 14);
		 frame.getContentPane().add(etiqueta4);
		 
		 JTextField passwordTF = new JTextField();
		 passwordTF.setBounds(160, 200, 150, 20);
		 frame.getContentPane().add(passwordTF);
		 passwordTF.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					if (passwordTF.getText().length() == 20){
						e.consume();
					}
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
		 
		 JLabel etiqueta5 = new JLabel("Nivel: ");
		 etiqueta5.setBounds(50, 250, 100, 14);
		 frame.getContentPane().add(etiqueta5);
		
		 JRadioButton radio = new JRadioButton("Administrador");
		 radio.setBounds(160, 250, 150, 20);
		 frame.getContentPane().add(radio);
		 
		 botonAceptar = new JButton("Aceptar");
		 botonAceptar.setBounds(160, 280, 100, 50);
		 frame.getContentPane().add(botonAceptar);
		 botonAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton funciona");
				
			}
		});
		 
	}


	

}
