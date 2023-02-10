package conexiondb;

import java.sql.*;
import java.util.ArrayList;

public class ConexionDB {
    private String db_conexion = "";
    private String db_propietario = "";
    private String usuario = "";
    private String contrasena = "";
    private Connection conexion = null;

    public ConexionDB (String db_conexion, String db_propietario, String usuario, String contrasena) {
        this.db_conexion = db_conexion;
        this.db_propietario = db_propietario;
        this.usuario = usuario;
        this.contrasena = contrasena;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.out.println("error= " + e);
        }
    }

    //ingresa a la DB
    public boolean abierto (){
        try {
            if (conexion != null){
                if (!conexion.isClosed()){
                    conexion.close();
                }
                conexion = null;
            }

            conexion = DriverManager.getConnection(db_conexion, usuario, contrasena);
            System.out.println("se reestablecio conexion exitosamente");
        }catch (Exception e){
            System.out.println("error= " + e);
        }
        return false;
    }



    public boolean obtenerSQL(String SQLIn, ArrayList<ArrayList> registros, String accion, boolean error, long maximoFilas){
        synchronized (this){
            for (int y = 0; y < 2; y++) {
                try {
                    if (conexion == null){
                        abierto();
                    }
                    Statement statement = conexion.createStatement();

                    SQLIn = SQLIn.replace("#propiertario", db_propietario);
                    System.out.println(SQLIn);


                    ResultSet cursor = statement.executeQuery(SQLIn);
                    ResultSetMetaData resultado = cursor.getMetaData();

                    registros.clear();

                    int nr_columnas = resultado.getColumnCount();
                    int nr_filas = 0;

                    ArrayList Tupla = new ArrayList();

                    for (int x = 0; x < nr_columnas; x++) {
                        Tupla.add(x, resultado.getColumnName(x + 1));
                    }
                    registros.add(0, Tupla);

                    while (cursor.next()){
                        Tupla = new ArrayList();

                        for (int x = 0; x < nr_columnas; x++) {
                            Tupla.add(x, cursor.getObject(x + 1));
                        }
                        registros.add(Tupla);
                        if (nr_filas > maximoFilas){
                            break;
                        }
                        nr_filas++;
                    }
                    statement.close();
                    statement = null;
                    return true;
                }catch (SQLException e) {
                    try {
                        if (conexion.isClosed()) {
                            abierto();
                        }
                    } catch (SQLException ex) {}
                    if (y == 1) {
                        System.out.println("Error= " + e);
                    }
                }
            }
            return false;
        }
    }

    public boolean ejecutarSQL(String SQLIn, String a_accion){
        synchronized (this){
            try {
                if (conexion == null){
                    abierto();
                }
                Statement statement = conexion.createStatement();

                SQLIn = SQLIn.replace("#propietario#", db_propietario);
                System.out.println(SQLIn);
                boolean r = statement.execute(SQLIn);

                statement.close();
                statement = null;

                return r;
            }catch (SQLException e){
                System.out.println("Error= " + e);
            }

            return false;
        }
    }

    public void cerrar() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
            } catch (Exception e) {
                System.out.println("Error= " + e);
            }
        }
    }

}
