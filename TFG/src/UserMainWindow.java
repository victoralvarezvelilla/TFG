import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class UserMainWindow extends JFrame implements ActionListener  {

	JFrame frame;
	private JButton botonSeleccionar;
	private JTextField textField;


	String rutaExcell = "";
	
	UserMainWindow(){
		
		 JFrame frame = new JFrame();
		 frame.setBounds(300, 200, 870, 600);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setLocationRelativeTo(null);
		 frame.setTitle("Menu ");
		 frame.getContentPane().setLayout(null);
		 frame.setResizable(false);
		 frame.setVisible(true);
		 
		 botonSeleccionar = new JButton ("Seleccionar");
		 botonSeleccionar.setBounds(220, 155, 105, 20);
		 frame.getContentPane().add(botonSeleccionar);
		 botonSeleccionar.addActionListener(this);
		 
		 textField = new JTextField();
		 textField.setToolTipText("Ruta");
		 textField.setBounds(15, 155, 200, 20);
		 frame.getContentPane().add(textField);
		 textField.setColumns(10);
		 
		 
		 int idUsuarioActual = DBConnection.getSesionID();
		 String nombreUsuarioActual = DBConnection.getSesionName();
		 
		 JLabel usuario = new JLabel("Bienvenido " + nombreUsuarioActual);
		 usuario.setBounds(700, 15, 200, 20);
		 frame.getContentPane().add(usuario);
	
		 
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == botonSeleccionar) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.xlsx", "xlsx");
			fc.setFileFilter(filtro);
			
			int seleccion = fc.showOpenDialog(this);
			
			if (seleccion == JFileChooser.APPROVE_OPTION){
				
				File fichero = fc.getSelectedFile();
				rutaExcell = fichero.getAbsolutePath();
				textField.setText(rutaExcell);
			}
			
		}
		
	}
}
