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

      public static void desconectar(){
        con = null;
        idSesion = 0;
        nameSesion = null;
        LoginWindow login = new LoginWindow();

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



	public static int insertarPlantilla(String nombre,int i) throws SQLException {
	
		String sentencia;
		Statement st = con.createStatement();
		int r;
		int id = 0;
	
		sentencia = "insert into excels(IDCreador, NombrePlantilla) values ('"+i+"','"+nombre+"')";
		r = st.executeUpdate(sentencia);
		JOptionPane.showMessageDialog(null, "Plantilla Creada");
		
		Statement s = con.createStatement();
		ResultSet rs = con.createStatement().executeQuery("SELECT * FROM excels WHERE NombrePlantilla = '" + nombre +"' ");
	
		if(rs.next()){
			
			id = rs.getInt("IDPlantilla");
		}
		
		
		return id;
	}
	
	public static int devolverIDRelativo (String nombrePlantilla) throws SQLException{
		int id = 0;
		Statement s = con.createStatement();
		ResultSet rs = con.createStatement().executeQuery("SELECT * FROM excels WHERE NombrePlantilla = '" + nombrePlantilla +"' ");
	
		if(rs.next()){
			
			id = rs.getInt("IDPlantilla");
		}
		
		
		return id;
	}

	public static String cambiarValor (int idPlantilla, String valorViejo ) throws SQLException{
		String valorNuevo = valorViejo;
		
		Statement s = con.createStatement();
		ResultSet rs = con.createStatement().executeQuery("SELECT * FROM valores WHERE ValorAntiguo = '" + valorViejo + "' AND IDRelativo = '" + idPlantilla + "' ");
	
		if(rs.next()){
			
			valorNuevo = rs.getString("ValorNuevo");
		}
		
		return valorNuevo;
	}
	
	
	


	public static void insertarValoresNuevos(String[] valoresAntiguos,String[] valoresNuevos, int idPlantilla) throws SQLException {
	
		String sentencia;
		Statement st = con.createStatement();
		int r;
		int i = 0;
		String [] antiguos = valoresAntiguos;
		
		
		for( i = 0 ; i< antiguos.length; i++){
			sentencia = "insert into valores(ValorAntiguo, ValorNuevo, IDRelativo) values ('"+valoresAntiguos[i]+"','"+valoresNuevos[i]+"','"+idPlantilla+"')";
			r = st.executeUpdate(sentencia);
			
		}
		
	}



	public static String[] llenarComboPatrones(int idUsuarioActual) {
		
		String [] combo = null;
		if(getSesionRol() == false){
			String sentencia;
			sentencia = "SELECT * FROM excels";
			
			int i = 0;
			try {
				ResultSet rs =  con.createStatement().executeQuery(sentencia);
				
				
				while (rs.next()){
					i= i+1;
				}
				rs.beforeFirst();
				combo = new String [i];
				i = 0;
				while (rs.next()){	
					
					combo[i] = rs.getString("NombrePlantilla");
					i = i+1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
		
		String sentencia;
		sentencia = "SELECT * FROM excels WHERE IDCreador='"+idUsuarioActual+"' ";
		int i = 0;
		try {
			ResultSet rs =  con.createStatement().executeQuery(sentencia);
			
			
			while (rs.next()){
				i= i+1;
			}
			rs.beforeFirst();
			combo = new String [i];
			i = 0;
			while (rs.next()){	
				
				combo[i] = rs.getString("NombrePlantilla");
				i = i+1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
		return combo;
	}



	public static void eliminarPlantilla(String nombre) throws SQLException {
		
		int idPlantilla = 0;
		
		String sentencia;
		sentencia = "SELECT * FROM excels WHERE NombrePlantilla='"+nombre+"' ";
		ResultSet rs =  con.createStatement().executeQuery(sentencia);
		while (rs.next()){	
			idPlantilla = rs.getInt("IDPlantilla");
		}
		
		try {
			Statement st = con.createStatement();
			int r = st.executeUpdate("delete from excels where NombrePlantilla='"+nombre+"'");
			UserMainWindow.vaciarCombo();
			UserMainWindow.llenarCombo(idSesion);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		try {
			Statement st = con.createStatement();
			int r = st.executeUpdate("delete from valores where IDRelativo='"+idPlantilla+"'");

		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
	



