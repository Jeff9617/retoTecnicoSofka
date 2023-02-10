import interfazusuario.AbrirInterfazUsuario;
import interfazusuario.InterfazUsuario;

public class Main {
    public static void main(String[] args) {
        InterfazUsuario.conexionPostgres();
        AbrirInterfazUsuario.abrirInterfazUsuario();

    }


}