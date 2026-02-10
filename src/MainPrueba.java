import java.time.*;
import java.util.Scanner;

public class MainPrueba {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        try {
            Usuario u1 = new Usuario("Pablo", "oscar@gmail.com", "SOC12345", LocalDate.of(2020, 02, 03));
            u1.sancionar(in);

            System.out.println(u1.estaSancionado());
            System.out.println(u1.toString());
            u1.levantarSancion();
            System.out.println(u1.toString());
        }
        catch (UsuarioInvalidoException uie){
            System.out.println("Usuario invalido");
        }
    }
}
