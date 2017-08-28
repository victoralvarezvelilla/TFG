import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class UserMainWindow extends JFrame implements ActionListener  {

	JFrame frame;
	private JButton botonSeleccionar;
	private JButton botonCrearExcell;
	private JButton botonAdministrar;
	private JButton botonEliminar;
	private JButton botonCerrar;
	private JTextField textField;
	private JTextField fieldUno;
	private JTextField fieldDos;
	private static DefaultComboBoxModel comboPatrones;
	private static JComboBox combo;
	
	private FileInputStream file;
	private Workbook wb;
	private static JTable table;
	private static DefaultTableModel modelo;
	private static JTable tableSelecionados;
	private static DefaultTableModel modeloSelecionados;
	private HSSFWorkbook oWB;
	private HSSFSheet hoja1;
	private FileOutputStream archivoSalida;
	private int contadorColumna = 0;
	private int contador = 0;

	private String [] columnaAniadir;
	private Set<Integer> indices;
	private String [] titulos;
	private String [] nombres;
	private String[] anonimizados;
	private Sheet pagina;
	
	String rutaExcell = "";
	
	UserMainWindow() throws FileNotFoundException{
		
		 frame = new JFrame();
		 frame.setBounds(300, 200, 870, 650);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setLocationRelativeTo(null);
		 frame.setTitle("Menu ");
		 frame.getContentPane().setLayout(null);
		 frame.setResizable(false);
		 frame.setVisible(true);
		 
		 botonSeleccionar = new JButton ("Cambiar Fuente");
		 botonSeleccionar.setBounds(150, 20, 145, 20);
		 frame.getContentPane().add(botonSeleccionar);
		 botonSeleccionar.addActionListener(this);
		 
		 botonCrearExcell = new JButton("Componer");
		 botonCrearExcell.setBounds(420, 250 , 105, 20);
		 frame.getContentPane().add(botonCrearExcell);
		 botonCrearExcell.addActionListener(this);
		 botonCrearExcell.setVisible(true);
		 
		 botonAdministrar = new JButton("Administrar");
		 botonAdministrar.setBounds(20, 20 , 105 ,20);
		 frame.getContentPane().add(botonAdministrar);
		 botonAdministrar.addActionListener(this);
		 botonAdministrar.setVisible(false);
		 
		 botonCerrar = new JButton("Salir");
		 botonCerrar.setBounds(700, 20, 100, 30);
		 frame.getContentPane().add(botonCerrar);
		 botonCerrar.addActionListener(this);
		 
		 if (DBConnection.getSesionRol() == false){
			 botonAdministrar.setVisible(true);
		 }
		 
	/*	 textField = new JTextField();
		 textField.setToolTipText("Ruta");
		 textField.setBounds(15, 155, 200, 20);
		 frame.getContentPane().add(textField);
		 textField.setColumns(10);*/
		 fieldUno = new JTextField();
		 fieldUno.setToolTipText("Desde");
		 fieldUno.setBounds(360, 520, 30, 30);
		 frame.getContentPane().add(fieldUno);
		 
		 fieldUno.addKeyListener(new KeyAdapter()
		 {
		    public void keyTyped(KeyEvent e)
		    {
		       char caracter = e.getKeyChar();

		       if(((caracter < '0') ||
		          (caracter > '9')) &&
		          (caracter != '\b' ))
		       {
		          e.consume();  
		       }
		    }
		 });
		 
		 fieldDos = new JTextField();
		 fieldDos.setToolTipText("Hasta");
		 fieldDos.setBounds(450, 520, 30, 30);
		 frame.getContentPane().add(fieldDos);
		 
		 fieldDos.addKeyListener(new KeyAdapter()
		 {
		    public void keyTyped(KeyEvent e)
		    {
		       char caracter = e.getKeyChar();

		       if(((caracter < '0') ||
		          (caracter > '9')) &&
		          (caracter != '\b' ))
		       {
		          e.consume();  
		       }
		    }
		 });
		 
		 JLabel hasta = new JLabel("hasta ");
		 hasta.setBounds(400, 520, 50, 30);
		 frame.getContentPane().add(hasta);
		 
		 JButton rango = new JButton("Añadir");
		 rango.setBounds(370, 570, 100, 30);
		 frame.getContentPane().add(rango);
		 rango.addActionListener(new ActionListener() {
			
			 @Override
			 public void actionPerformed(ActionEvent e) {

				 if(fieldUno.getText().length() > 4){
					 JOptionPane.showMessageDialog(null, "No hay tantas columnas");
				 }else if (fieldDos.getText().length() > 4){
					 JOptionPane.showMessageDialog(null, "No hay tantas columnas");
				 }else if (fieldUno.getText().equals("") || fieldDos.getText().equals("") ){
					 JOptionPane.showMessageDialog(null, "Introduce ambos valores");
				 }else{
					 
					 int rango1 = Integer.parseInt(fieldUno.getText()) ;
					 int rango2 = Integer.parseInt(fieldDos.getText()) ;

					 if (rango1 > rango2){
						 JOptionPane.showMessageDialog(null, "Error de rangos");

					 }else if(rango2>pagina.getRow(0).getLastCellNum()){
						 JOptionPane.showMessageDialog(null, "Error de rangos");
					 }else{

						 for (int i = rango1; i<rango2; i++ ){
							 indices.add(i);
							 Vector<Integer> v = new Vector<Integer>();
							 v.add(i+1);
							 modeloSelecionados.addRow(v);
						 }
					 }
				 }
				 fieldUno.setText("");
				 fieldDos.setText("");
			 }
		 });
		 
		
		 
		 int idUsuarioActual = DBConnection.getSesionID();
		 String nombreUsuarioActual = DBConnection.getSesionName();
		 
		 oWB = new HSSFWorkbook();
		 hoja1 = oWB.createSheet("hoja 1");
		 
		 JLabel usuario = new JLabel("Bienvenido " + nombreUsuarioActual);
		 usuario.setBounds(700, 60, 200, 20);
		 frame.getContentPane().add(usuario);
		 
	
	
		 
		 modelo = new DefaultTableModel() {
			   @Override
			   public boolean isCellEditable(int fila, int columna) {
			       return false; //Con esto conseguimos que la tabla no se pueda editar
			   }
			};
			 
			 JScrollPane scroll = new JScrollPane(); 
			 table = new JTable(modelo); //Metemos el modelo dentro de la tabla
			 table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			 modelo.addColumn("Preguntas ");
			 scroll.setViewportView(table);
			 scroll.setBounds(100, 300, 700, 200);
			 frame.getContentPane().add(scroll);
			 
			 
			 modeloSelecionados = new DefaultTableModel() {
				   @Override
				   public boolean isCellEditable(int fila, int columna) {
				       return false; //Con esto conseguimos que la tabla no se pueda editar
				   }
				};
				 
				 JScrollPane scroll2 = new JScrollPane(); 
				 tableSelecionados = new JTable(modeloSelecionados); //Metemos el modelo dentro de la tabla
				 tableSelecionados.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				 modeloSelecionados.addColumn("Columnas selecionadas ");
				 scroll2.setViewportView(tableSelecionados);
				 scroll2.setBounds(100, 100, 200, 150);
				 frame.getContentPane().add(scroll2);
			 
			 
			 comboPatrones = new DefaultComboBoxModel();
			 combo = new JComboBox();
			 combo.setBounds(320, 20, 175, 30);
			 frame.getContentPane().add(combo);
			 combo.setModel(comboPatrones);
			 
			 botonEliminar = new JButton("Eliminar Plantilla");
			 botonEliminar.setBounds(500, 20, 150, 30);
			 frame.getContentPane().add(botonEliminar);
			 botonEliminar.addActionListener(this);
			 
			 llenarCombo(idUsuarioActual);
		 
			 indices = new HashSet<Integer>();
			 
		
		
				
				
				FileInputStream file = null;
				try {
					file = new FileInputStream(new File("C:/Users/Usuario/Desktop/TFG/EX1.xlsx"));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//@SuppressWarnings("resource")
				wb = null;
				try {
					wb = new XSSFWorkbook(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				pagina = wb.getSheetAt(0);
				titulos = new String[pagina.getRow(0).getLastCellNum()-3];
				columnaAniadir = new String[pagina.getRow(0).getLastCellNum()+1];
			
				DataFormatter formatter = new DataFormatter();
				
				for (int i = 1; i<pagina.getRow(0).getLastCellNum()-3; i++ ){

					String celda = pagina.getRow(0).getCell(i-1).toString();
					titulos[i-1] = celda;

				}
				
			
				nombres = new String[pagina.getLastRowNum()+1];
				for (int j = 1; j< pagina.getLastRowNum()+1; j++){
					Row fila = pagina.getRow(j);
					nombres[j-1] = fila.getCell(0).getStringCellValue(); 
				}
				anonimizados =	anonimizar(nombres);
				try {
					crearNuevoExcell(anonimizados);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				for ( int k = 1; k < titulos.length; k++){
					Vector<String> v = new Vector<String>();
					v.add(titulos[k]);
					modelo.addRow(v);
				}
			
			
				table.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
					
						int columna = table.rowAtPoint(e.getPoint());
						int opcion = JOptionPane.showConfirmDialog(null, "¿Añadir columna a la matriz?");
						
						aniadirColumna(columna);
						//indices.add(columna);
						/* SI = 0 NO = 1 CANCEL = 2 */
						if (opcion == 0){
							
							Vector<Integer> v = new Vector<Integer>();
							v.add(columna+1);
							modeloSelecionados.addRow(v);
							
							for (int i = 1; i< pagina.getLastRowNum()+1; i++){
								Row fila = pagina.getRow(i);
								String celda = fila.getCell(columna+1).toString();
								columnaAniadir[i-1] = celda; 
								
								
								//System.out.println(columnaAniadir[i-1]);
							
							}
							try {
								crearNuevoExcell(columnaAniadir);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

					private void aniadirColumna(int columna) {
						// TODO Auto-generated method stub
						columna = columna +1;
						indices.add(columna);
					}
				});
				
			}
			 
		 
	static void llenarCombo(int idUsuarioActual){
		
		 comboPatrones.addElement("---Plantillas Disponibles---");
		 String [] listaPatrones = DBConnection.llenarComboPatrones(idUsuarioActual);
		 for (int i= 0; i< listaPatrones.length; i++){
			 comboPatrones.addElement(listaPatrones[i]);
		 }
	}
	static void vaciarCombo(){
		combo.removeAllItems();
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
				
				while(modelo.getRowCount() > 0) modelo.removeRow(0);
				File fichero = fc.getSelectedFile();
				rutaExcell = fichero.getAbsolutePath();
			
				
				FileInputStream file = null;
				try {
					file = new FileInputStream(new File(rutaExcell));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//@SuppressWarnings("resource")
				wb = null;
				try {
					wb = new XSSFWorkbook(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				pagina = wb.getSheetAt(0);
				titulos = new String[pagina.getRow(0).getLastCellNum()-3];
				columnaAniadir = new String[pagina.getRow(0).getLastCellNum()+1];
			
				DataFormatter formatter = new DataFormatter();
				
				for (int i = 1; i<pagina.getRow(0).getLastCellNum()-3; i++ ){

					String celda = pagina.getRow(0).getCell(i-1).toString();
					titulos[i-1] = celda;

				}
				
			
				nombres = new String[pagina.getLastRowNum()+1];
				for (int j = 1; j< pagina.getLastRowNum()+1; j++){
					Row fila = pagina.getRow(j);
					nombres[j-1] = fila.getCell(0).getStringCellValue(); 
				}
				String[] anonimizados =	anonimizar(nombres);
				try {
					crearNuevoExcell(anonimizados);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				for ( int k = 1; k < titulos.length; k++){
					Vector<String> v = new Vector<String>();
					v.add(titulos[k]);
					modelo.addRow(v);
				}
			
			
			}
			
		}
		if(e.getSource() == botonAdministrar) {
			AdminMainMenu menu = new AdminMainMenu();
			frame.dispose();
			
		}
		if(e.getSource() == botonEliminar){
			String nombre = (String) combo.getSelectedItem();
			if( nombre.equals("---Plantillas Disponibles---")){
				JOptionPane.showMessageDialog(null, "Elija un patron");
				return;
			}
			try {
				DBConnection.eliminarPlantilla(nombre);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource() == botonCerrar) {
			DBConnection.desconectar();
			frame.dispose();
		}
		if(e.getSource() == botonCrearExcell) {
		
			UserNewValuesWindow menuValores = new UserNewValuesWindow(wb, anonimizados,indices );
			
		}
	
		
	}



	private void crearNuevoExcell(String[] columna) throws IOException {
	
		for( int i = 0; i< columna.length; i++){
			HSSFRow fila = hoja1.createRow(i);
			HSSFCell celda = fila.createCell(contadorColumna);
			celda.setCellValue(columna[i]);
			
		}
		contadorColumna = contadorColumna +1;

	}



	private String[] anonimizar(String[] nombres) {
		
		String[] codigos = new String[nombres.length];
		for (int i = 0; i < nombres.length-1; i++){
			String codigo = "";
			String zero = "0";
			int primerNumero = nombres[i].codePointCount(0, nombres[i].length());
			double random = Math.floor(Math.random()*(1-999+1)+999);
			System.out.println(random);
			double multiplicador = primerNumero * random;
			double modulo = multiplicador%9999;
			String stringModulo = Double.toString(modulo);
			if (stringModulo.length()<5){
				for (int j = 0; stringModulo.length()<5; j++){
					stringModulo = zero.concat(stringModulo);
				}
			}
			codigo = Integer.toString(primerNumero).concat(stringModulo).substring(0, 4);
			codigos[i] = codigo;
			System.out.println("Anonimizando "+ codigo);
			
		}
		return codigos;
	}
}
