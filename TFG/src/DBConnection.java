import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class DBConnection {

	public static Connection con ;
	Statement statement;
	
	public void conectar(){
        System.out.println("INICIO DE EJECUCIÓN.");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/tfg", "root", "");
            JOptionPane.showMessageDialog(null, "Se ha iniciado la conexión con el servidor de forma exitosa");
        }
        catch(SQLException s)
        {
            System.out.println("Error: SQL.");
            System.out.println("SQLException: " + s.getMessage());
        }
        catch(Exception s)
        {
            System.out.println("Error: Varios.");
            System.out.println("SQLException: " + s.getMessage());
        }
	}



    public Connection conectado(){
        return con;
  }

      public void desconectar(){
        con = null;
        System.out.println("conexion terminada");

      }



	public boolean systemEntry(String text, String valueOf) throws SQLException {
		
		String user = text;
		String password = valueOf;
		boolean correcto = false ;
		String datoSesion = "";
		Statement s = con.createStatement();
	
		ResultSet rs = con.createStatement().executeQuery("SELECT * FROM usuarios WHERE Passname = '" + user + "' AND Password = '" + password + "' ");
		
		if(rs.next()){
			datoSesion = rs.getString("ID");
			correcto = true;
		}
		
		
		return correcto;
	}





	
}
	



