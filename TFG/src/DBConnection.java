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
			
			setSesionID( rs.getInt("ID"));
			setSesionName(rs.getString("Nombre"));
			setSesionRol(rs.getBoolean("Rol"));
		}
		

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

	static void rellenarTabla(){
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM usuarios");
            while(rs.next()){
                Object[] fila = new Object[4];//Creamos un Objeto con tantos parámetros como datos retorne cada fila 
                                              // de la consulta
                fila[0] = rs.getString("ID"); //Lo que hay entre comillas son los campos de la base de datos
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getString("Apellidos");
                if( rs.getInt("Rol") == 0){
                	fila[3] = "Administrador";
                }else {
                	fila[3] = "Usuario";
                }
                
                AdminMainMenu.aniadirFila(fila);// Añade una fila al final del modelo de la tabla
            }
 
         //   
 
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
    }



	public static void aniadirUsuario(String nombre, String apellidos, String passname, String password, int rol) throws SQLException {
		// TODO Auto-generated method stub
		String sentencia;
		Statement st = con.createStatement();
		int r;
		
	
		sentencia = "insert into usuarios(Nombre, Apellidos, Rol, Passname, Password) values ('"+nombre+"','"+apellidos+"','"+rol+"','"+passname+"','"+password+"')";
		r = st.executeUpdate(sentencia);
		JOptionPane.showMessageDialog(null, "Usuario Creado");
		
	}
	
	public static String[] llenar_combo() {
		String sentencia;
		sentencia = "SELECT * FROM usuarios WHERE rol='1' ";
		String [] combo = null;
		int i = 0;
		try {
			ResultSet rs =  con.createStatement().executeQuery(sentencia);
			
			
			while (rs.next()){
				i= i+1;
			//	System.out.println(rs.getString("Nombre")+"While1");
			}
			rs.beforeFirst();
			combo = new String [i];
			i = 0;
			while (rs.next()){	
		//		System.out.println(rs.getString("Nombre") +"While2");
				
				combo[i] = rs.getString("Nombre");
				i = i+1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return combo;
		
		
		
		
	}



	public static void eliminarUsuario(String nombre) {
		
		try {
			Statement st = con.createStatement();
			int r = st.executeUpdate("delete from usuarios where Nombre='"+nombre+"'");
			AdminMainMenu.vaciarCombo();
			AdminMainMenu.llenarCombo();
			AdminMainMenu.actualizarTabla();
			rellenarTabla();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
	



