import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class UserMainWindow extends JFrame implements ActionListener  {

	JFrame frame;
	private JButton botonSeleccionar;
	private JTextField textField;
	private FileInputStream file;
	private Workbook wb;
	private static JTable table;
	private static DefaultTableModel modelo;

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
				
				
				FileInputStream file = null;
				try {
					file = new FileInputStream(new File(rutaExcell));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				@SuppressWarnings("resource")
				Workbook wb = null;
				try {
					wb = new XSSFWorkbook(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Sheet pagina = wb.getSheetAt(0);
				String [] titulos = new String[pagina.getRow(0).getLastCellNum()+1];
				
			
				DataFormatter formatter = new DataFormatter();
				
				for (int i = 1; i<pagina.getRow(0).getLastCellNum()+1; i++ ){

					String celda = pagina.getRow(0).getCell(i-1).toString();
					titulos[i-1] = celda;

				}
				
			
				String [] nombres = new String[pagina.getLastRowNum()+1];
				for (int j = 1; j< pagina.getLastRowNum()+1; j++){
					Row fila = pagina.getRow(j);
					nombres[j-1] = fila.getCell(0).getStringCellValue(); 
				}
				String[] anonimizados =	anonimizar(nombres);
				for ( int k = 1; k < titulos.length; k++){
					Vector<String> v = new Vector<String>();
					v.add(titulos[k]);
					modelo.addRow(v);
				}
				String[] seleciones = String.valueOf(table.getSelectedRow());
				
			}
			
		}
		
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
