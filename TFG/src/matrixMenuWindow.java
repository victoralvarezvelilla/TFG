import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class matrixMenuWindow implements ActionListener {
	
	JFrame frame;
	private static JTable tablaMatrices;
	private static DefaultTableModel modeloMatrices;
	
	private FileInputStream file;
	private Workbook wb;
	private Sheet pagina;
	
	private JButton botonGenerar;
	private JTextField rutaField;

	
	private Set<Integer> indicesV;
	private String[] anonimizadosV;
	private String [] titulosV;


	
	public matrixMenuWindow(String[] anonimizados, String rutaExcell, Set<Integer> indices, String[] titulos, int idPlantilla) throws SQLException {
		frame = new JFrame();
		frame.setBounds(300, 200, 470, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Menu Matrices");
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);	
		
		indicesV = indices;
		anonimizadosV = anonimizados;
		titulosV = titulos;
		
		 botonGenerar = new JButton("Generar Excel");
		 botonGenerar.setBounds(310, 140, 150, 30);
		 frame.getContentPane().add(botonGenerar);
		 botonGenerar.addActionListener(this);
		 
		 rutaField = new JTextField();
		 rutaField.setBounds(310, 60, 150, 30);
		 frame.getContentPane().add(rutaField);
		 rutaField.setColumns(20);
		 rutaField.setVisible(true);
		
		
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(""+rutaExcell+""));
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
		 modeloMatrices = new DefaultTableModel();
		 tablaMatrices = new JTable(modeloMatrices);
		 JScrollPane scroll = new JScrollPane(tablaMatrices,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		 tablaMatrices.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		 
		 modeloMatrices.addColumn("Alumno");
		 for (int i = 0 ; i<titulos.length ; i++){
			 if(indices.contains(i)){
			 modeloMatrices.addColumn(titulos[i]);
			 }
		 }
		 System.out.println(rutaExcell);
		 
		 for (int i = 1; i < pagina.getLastRowNum(); i++){
			 Row fila = pagina.getRow(i);
			 Vector<String> v = new Vector<String>();
			 v.add(anonimizados[i-1]);
			 for( int j= 1; j< fila.getLastCellNum(); j++){
				 String celda = fila.getCell(j,Row.CREATE_NULL_AS_BLANK).toString();
				 if (indices.contains(j)){
					String valor = DBConnection.cambiarValor(idPlantilla, celda);
					v.add(valor);
				 }
			 }
			modeloMatrices.addRow(v);
		 }
		
		 scroll.setBounds(0, 0, 300, 250);
		 frame.getContentPane().add(scroll);
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getSource() == botonGenerar){

			String ruta = rutaField.getText();
			if ( ruta.length() == 0){
				JOptionPane.showMessageDialog(null, "Introduzca un nombre a la plantilla");
			}else {	

				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet hoja = workbook.createSheet();

				XSSFRow fila = hoja.createRow(0);
				int indiceColumnas = 0;
				/*Creamos la fila 0 */
			
				for ( int i = 0; i < modeloMatrices.getColumnCount(); i++ ){
					fila.createCell(i).setCellValue(modeloMatrices.getColumnName(i));
									
				}
				XSSFRow filas;

				for (int j = 0; j<tablaMatrices.getRowCount(); j++){

					tablaMatrices.setRowSelectionInterval(j, j);

					filas = hoja.createRow(j+1);

					for (int k = 0; k< tablaMatrices.getColumnCount(); k++){
						filas.createCell(k).setCellValue(tablaMatrices.getValueAt(j, k).toString());
					}
				}

				try {
					workbook.write(new FileOutputStream(new File("C:/Users/Usuario/Desktop/TFG/"+ruta+".xlsx")));
							Desktop.getDesktop().open(new File("C:/Users/Usuario/Desktop/TFG/"+ruta+".xlsx"));
									JOptionPane.showMessageDialog(null, "Excel generado correctamente");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				frame.dispose();
			}
		}
	}

}
