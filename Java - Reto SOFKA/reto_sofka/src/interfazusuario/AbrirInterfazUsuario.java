package interfazusuario;

import javax.swing.*;

public class AbrirInterfazUsuario {
    static JFrame form = new JFrame("Interfaz de naves");
    public static void abrirInterfazUsuario() {
        form.setContentPane(new InterfazUsuario().interfazUsuario);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.pack();
        form.setSize(1200,800);
        form.setVisible(true);
    }
}
