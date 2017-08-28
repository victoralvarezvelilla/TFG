import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class UserNewValuesWindow extends JFrame implements ActionListener {

	
	JFrame frame;
	private static JTable table;
	private static DefaultTableModel modelo;
	private Sheet pagina;
	private Workbook workb;
	private JButton botonGuardar;
	private JButton botonLimpiar;
	private String [] valoresAntiguos;
	private String [] valoresNuevos;
	
	private JTextField textField;
	
	UserNewValuesWindow(Workbook wb, String[] anonimizados, Set<Integer> indices) {

		frame = new JFrame();
		frame.setBounds(300, 200, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Asignador de valores ");
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		botonGuardar = new JButton ("Guardar plantilla");
		botonGuardar.setBounds(220, 155, 145, 20);
		frame.getContentPane().add(botonGuardar);
		botonGuardar.addActionListener(this);
		
		botonLimpiar = new JButton ("Limpiar");
		botonLimpiar.setBounds(420, 155, 145, 20);
		frame.getContentPane().add(botonLimpiar);
		botonLimpiar.addActionListener(this);
		
		textField = new JTextField();
		textField.setToolTipText("Nombre de la plantilla");
		textField.setBounds(250, 255, 200, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (textField.getText().length() == 20){
					e.consume();
				}
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		workb = wb; 
		pagina = workb.getSheetAt(0);
		
		 modelo = new DefaultTableModel() {
			   @Override
			   public boolean isCellEditable(int fila, int columna) {
				   if( columna == 1){
					   return true;
				   }else{
					   return false; 
				   }
			       
			   }
			};
		
			 
			 JScrollPane scroll = new JScrollPane(); 
			 table = new JTable(modelo); //Metemos el modelo dentro de la tabla
			 table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			 modelo.addColumn("Valores Iniciales");
			 modelo.addColumn("Nuevos Valores");
			 scroll.setViewportView(table);
			 scroll.setBounds(50, 300, 700, 250);
			 frame.getContentPane().add(scroll);
			 
			 	Set<String> set = new HashSet<String>();
				for (int i = 1; i< pagina.getLastRowNum()+1; i++){
					Row fila = pagina.getRow(i);
					for (int j = 0; j < fila.getLastCellNum(); j++){
						for(Iterator ite = indices.iterator(); ite.hasNext();){
							int columna = (Integer)ite.next();
							if(columna == j){
								String valor = fila.getCell(j).toString();
								//	System.out.println(valor);
								set.add(valor);
							}
						
						}
					}
				}
			
				for (Iterator it = set.iterator(); it.hasNext();){
					String respuesta = (String)it.next();
					Vector<String> v = new Vector<String>();
					v.add(respuesta);
					modelo.addRow(v);
					
				}
				for (int k = 0; k<modelo.getRowCount(); k++){
					modelo.setValueAt("", k, 1);
				}
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == botonGuardar) {
			
			valoresAntiguos = new String [modelo.getRowCount()];
			valoresNuevos = new String [ modelo.getRowCount()];
			String nombre = textField.getText();
			
			if ( nombre == ""){
					JOptionPane.showMessageDialog(null, "Introduzca un nombre a la plantilla");
				
			}else{ 
				int idPlantilla = 0;
				try {
					idPlantilla = DBConnection.insertarPlantilla(nombre, DBConnection.getSesionID());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

				for( int i = 0; i< modelo.getRowCount(); i++){
					if (modelo.getValueAt(i, 1).equals("")){
							/*Mejor no guardar los valores en blanco */
					/*	valoresAntiguos[i] = (String) modelo.getValueAt(i, 0);
						valoresNuevos[i] = valoresAntiguos[i]; */
					} else {
						valoresAntiguos[i] = (String) modelo.getValueAt(i, 0);
						valoresNuevos[i] = (String) modelo.getValueAt(i, 1);

					}
				}

				try {
					DBConnection.insertarValoresNuevos(valoresAntiguos,valoresNuevos, idPlantilla);
					UserMainWindow.vaciarCombo();
					UserMainWindow.llenarCombo(DBConnection.getSesionID());
					frame.dispose();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(arg0.getSource() == botonLimpiar) {
			
			for (int i = 0; i < modelo.getRowCount(); i++){
				modelo.setValueAt("", i, 1);
			}
			
		}
		
	}
}
