package interfazusuario;


import conexiondb.ConexionDB;
import naves.CrearNaves;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InterfazUsuario {
     JPanel interfazUsuario;
    private JComboBox listaTiposNave;
    private JTable información;
    private JTextField campoNombre;
    private JTextField campoDesplazamiento;
    private JTextField campoVelocidad;
    private JTextField campoMisión;
    private JButton crearNaveButton;
    private JButton activarNaveButton;
    private JButton borrarNaveButton;
    private JButton limpiarCamposButton;
    private JButton buscarTodasLasNavesButton;
    private JButton buscarButton;
    private static ConexionDB postgres;

    String [][] data ={{"null", "null", "null", "null", "null", "null", "null"}};
    String [] columnas = {"nombre", "desplazamiento", "velocidad", "mision", "pesoNave", "destinoCercano", "tipoNave"};
    CrearNaves nave = new CrearNaves();


    public InterfazUsuario(){


        activarNaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JComboBox<String> cbox_nombres = new JComboBox();
                String [] nombre = new String[4];
                int c =1;

                ArrayList<ArrayList> nomb = new ArrayList();
                cbox_nombres.addItem("Seleccionar Nave");
                postgres.obtenerSQL("SELECT  * FROM " +
                        "\"Naves\"", nomb, "nombre", true,520);

                for (int i = 0; i < nombre.length; i++) {
                    nombre [i] = String.valueOf(nomb.get(c));
                    c++;

                    listaTiposNave.addItem(nombre[i]);
                }
            }
        });

        limpiarCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoNombre.setText("");
                campoDesplazamiento.setText("");
                campoMisión.setText("");
                campoVelocidad.setText("");

            }
        });

        crearNaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (campoNombre.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Campo nombre en blanco");
                }else if (campoDesplazamiento.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campo desplazamiento en blanco");
                }else if (campoMisión.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campo mision en blanco");
                }else if (campoVelocidad.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campo velocidad en blanco");
                }else{
                    try {
                        int c = (Integer.parseInt(campoDesplazamiento.getText()));
                        int c1 = (Integer.parseInt(campoVelocidad.getText()));


                        if (c >= 0  && c1 >= 0){
                            int idNave = Integer.parseInt(nave.idNaves(listaTiposNave.getSelectedItem()).toString());

                                    if(nave.destinoNave((Integer.parseInt(campoDesplazamiento.getText()))).equals("No hay destinos")) {

                                        JOptionPane.showMessageDialog(null, "Ingresar una distancia entre 0 y 5999");

                                    } else {

                                        for (int i = 0; i < 4; i++) {

                                            if ((i + 1) == idNave) {
                                                postgres.ejecutarSQL("INSERT INTO \"CaracteristicasNaves\" ( nombre, desplazamiento," +
                                                                "velocidad, mision, \"pesoNave\",  \"destinoCercano\", \"tipoNave\")" +
                                                                "VALUES ('" +
                                                                campoNombre.getText() + "', " +
                                                                Integer.parseInt(campoDesplazamiento.getText()) + ", " +
                                                                Integer.parseInt(campoVelocidad.getText()) + ", '" +
                                                                campoMisión.getText() + "', " +
                                                                nave.pesoNave(listaTiposNave.getSelectedItem().toString()) + ", '" +
                                                                nave.destinoNave((Integer.parseInt(campoDesplazamiento.getText()))) + "', '" +
                                                                nave.nombreNave(idNave) + " ');"
                                                        , "Crear nave en su respectiva tabla");

                                                String[][] newData = new String[data.length][8];

                                                newData[0][0] = campoNombre.getText();
                                                newData[0][1] = String.valueOf(campoDesplazamiento.getText());
                                                newData[0][2] = campoVelocidad.getText();
                                                newData[0][3] = campoMisión.getText();
                                                newData[0][4] = String.valueOf(nave.pesoNave(listaTiposNave.getSelectedItem().toString()));
                                                newData[0][5] = nave.destinoNave((Integer.parseInt(campoDesplazamiento.getText())));
                                                newData[0][6] = nave.nombreNave(idNave);

                                                enviarDatos(newData, columnas);
                                            }

                                        }
                                    }

                        }
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null,
                                "El dato ingresado en el campo desplazamiento o velocidad no es valido o debes seleccionar nave");

                    }
                }
            }
        });

        buscarTodasLasNavesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ArrayList> dataTotal = new ArrayList();

                postgres.obtenerSQL("SELECT * FROM \"CaracteristicasNaves\"", dataTotal, "Traer informacion base de datos", true, 500);

                String [][] newData = new String[dataTotal.size()-1][7];

                for (int i = 1; i <dataTotal.size(); i++) {


                    newData[i - 1][0] = String.valueOf(dataTotal.get(i).get(0));
                    newData[i - 1][1] = String.valueOf(dataTotal.get(i).get(1));
                    newData[i - 1][2] = String.valueOf(dataTotal.get(i).get(2));
                    newData[i - 1][3] = String.valueOf(dataTotal.get(i).get(3));
                    newData[i - 1][4] = String.valueOf(dataTotal.get(i).get(4));
                    newData[i - 1][5] = String.valueOf(dataTotal.get(i).get(5));
                    newData[i - 1][6] = String.valueOf(dataTotal.get(i).get(6));

                }
                enviarDatos(newData,columnas);
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ArrayList> dataTotal = new ArrayList();

                int idNave = Integer.parseInt(nave.idNaves(listaTiposNave.getSelectedItem()).toString());

                postgres.obtenerSQL("SELECT * FROM \"CaracteristicasNaves\" WHERE \"tipoNave\" = '" + nave.nombreNave(idNave) + " '", dataTotal, "Traer naves de base de datos", true, 500);

                String [][] newData = new String[dataTotal.size()-1][7];

                String naves = nave.nombreNave(idNave);

                for (int i = 1; i <dataTotal.size(); i++) {
                    if (naves.equals(listaTiposNave.getSelectedItem())) {
                        newData[i - 1][0] = String.valueOf(dataTotal.get(i).get(0));
                        newData[i - 1][1] = String.valueOf(dataTotal.get(i).get(1));
                        newData[i - 1][2] = String.valueOf(dataTotal.get(i).get(2));
                        newData[i - 1][3] = String.valueOf(dataTotal.get(i).get(3));
                        newData[i - 1][4] = String.valueOf(dataTotal.get(i).get(4));
                        newData[i - 1][5] = String.valueOf(dataTotal.get(i).get(5));
                        newData[i - 1][6] = String.valueOf(dataTotal.get(i).get(6));

                    }
                }
                enviarDatos(newData,columnas);
            }
        });
        borrarNaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (campoNombre.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Ingresar nombre de dato a eliminar");
                }else {postgres.ejecutarSQL("DELETE  FROM \"CaracteristicasNaves\" WHERE \"nombre\" = '" + campoNombre.getText() + "'", "Eliminar registro");
                }


            }
        });

    }
    public static void conexionPostgres(){
        try {
            if (postgres != null){
                postgres.cerrar();
                postgres = null;
            }
            postgres = new ConexionDB("jdbc:postgresql://localhost/Naves", "", "postgres","12345");
            postgres.abierto();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void enviarDatos (String [][] data, String [] columnas){
        información.setModel(new DefaultTableModel(data,columnas));
    }




}
