import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;


public class AdminWindowMenu extends JFrame implements ActionListener {

	JFrame frame;
	private JButton botonAdministrar;
	private JButton botonAplicacion;
	
	AdminWindowMenu(){
		
		 frame = new JFrame();
		 frame.setBounds(300, 200, 350, 100);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setLocationRelativeTo(null);
		 frame.setTitle("Seleccione Opción");
		 frame.getContentPane().setLayout(null);
		 frame.setResizable(false);
		 frame.setVisible(true);
		 
		 botonAdministrar = new JButton ("Administrar Cuentas");
		 botonAdministrar.setBounds(0, 0, 175, 100);
		 frame.getContentPane().add(botonAdministrar);
		 botonAdministrar.addActionListener(this);
		 
		 botonAplicacion = new JButton ("Administrar Matrices");
		 botonAplicacion.setBounds(175, 0, 175, 100);
		 frame.getContentPane().add(botonAplicacion);
		 botonAplicacion.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		if(arg0.getSource() == botonAplicacion) {
			try {
				UserMainWindow menu = new UserMainWindow();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			frame.dispose();
		}
		if(arg0.getSource() == botonAdministrar) {
			AdminMainMenu menuAdministrador = new AdminMainMenu();	
			frame.dispose();
		}
	}

}
