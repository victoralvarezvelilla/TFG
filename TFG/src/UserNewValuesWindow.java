import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class UserNewValuesWindow extends JFrame {

	
	JFrame frame;
	private static JTable table;
	private static DefaultTableModel modelo;
	private Sheet pagina;
	private Workbook workb;
	
	UserNewValuesWindow(Workbook wb, String[] anonimizados, Set<Integer> indices) {

		frame = new JFrame();
		frame.setBounds(300, 200, 870, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Asignador de valores ");
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);

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
			 scroll.setBounds(100, 300, 700, 200);
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
		
	}
}
