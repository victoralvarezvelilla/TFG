import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class DBConnection {

	public static Connection con ;
	Statement statement;
	private static int idSesion;
	private static String nameSesion;
	private static boolean rolSesion;
	
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
		
		Statement s = con.createStatement();
	
		ResultSet rs = con.createStatement().executeQuery("SELECT * FROM usuarios WHERE Passname = '" + user + "' AND Password = '" + password + "' ");
	
		if(rs.next()){
			
			
			correcto = true;
		}
		setSesionID( rs.getInt("ID"));
		setSesionName(rs.getString("Nombre"));
		setSesionRol(rs.getBoolean("Rol"));
		
		return correcto;
	}
	
	public void setSesionRol( boolean r){
		System.out.println("rol="+r);
		rolSesion = r;
		System.out.println(nameSesion);
	}
	
	public static boolean getSesionRol(){
		
		return rolSesion;
	}
	
	public void setSesionName( String nombre){
		System.out.println(nombre);
		nameSesion = nombre;
		System.out.println(nameSesion);
	}
	
	public static String getSesionName(){
		
		return nameSesion;
	}
	
	
	public void setSesionID( int i){
		System.out.println(i);
		idSesion = i;
		System.out.println(idSesion);
	}
	
	public static int getSesionID(){
		System.out.println(idSesion);
		return idSesion;
	}



	
}
	



