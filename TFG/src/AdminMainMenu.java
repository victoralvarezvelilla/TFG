import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class AdminMainMenu extends JFrame implements ActionListener {

	JFrame frame;
	private static JTable table;
	private static DefaultTableModel modelo;
	private JButton botonAniadir;
	
	AdminMainMenu() {
		 JFrame frame = new JFrame();
		 frame.setBounds(300, 200, 870, 600);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setLocationRelativeTo(null);
		 frame.setTitle("Menu de Administración de usuarios ");
		 frame.getContentPane().setLayout(null);
		 frame.setResizable(false);
		 frame.setVisible(true);
		 
		 botonAniadir = new JButton ("Añadir Usuarios");
		 botonAniadir.setBounds(550, 100, 150, 50);
		 frame.getContentPane().add(botonAniadir);
		 botonAniadir.addActionListener(this);
		 
		 
		 String nombreUsuarioActual = DBConnection.getSesionName();

		 JLabel usuario = new JLabel("Bienvenido " + nombreUsuarioActual);
		 usuario.setBounds(700, 15, 200, 20);
		 frame.getContentPane().add(usuario);
		 
		 /*Tabla */
		 modelo = new DefaultTableModel() {
		   @Override
		   public boolean isCellEditable(int fila, int columna) {
		       return false; //Con esto conseguimos que la tabla no se pueda editar
		   }
		};
		 
		 JScrollPane scroll = new JScrollPane(); 
		 table = new JTable(modelo); //Metemos el modelo dentro de la tabla
		 
		 modelo.addColumn("ID");
		 modelo.addColumn("Nombre"); //Añadimos las columnas a la tabla (tantas como queramos)
		 modelo.addColumn("Apellidos");
		 modelo.addColumn("Usuario común");
		  
		 DBConnection.rellenarTabla(); //Llamamos al método que rellena la tabla con los datos de la base de datos
		  

		 scroll.setViewportView(table);
		 scroll.setBounds(100, 0, 400, 200);
		 frame.getContentPane().add(scroll);
	
	}
	
	static void aniadirFila(Object [] fila){
		modelo.addRow(fila);
	}
	static void actualizarTabla(){
		table.updateUI();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == botonAniadir) {
			Formulario formulario = new Formulario();
		}
	}

}
