import java.time.*;
import java.util.Scanner;

public class MainPrueba {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        try {
            Usuario u1 = new Usuario("Pablo", "oscar@gmail.com", "SOC12345", LocalDate.of(2020, 02, 03));


            System.out.println(u1.estaSancionado());
            System.out.println(u1.toString());
            u1.levantarSancion();
            System.out.println(u1.toString());
        }

        catch (UsuarioInvalidoException uie){
            System.out.println(uie.getMessage());
        }
        try {
            Usuario u2 = new Usuario("Jesus", "Jesus@gmail", "SOC12323", LocalDate.of(2006, 10, 24));
        }
        catch (UsuarioInvalidoException uie){
            System.out.println(uie.getMessage());
        }
        try {
            Usuario u3 = new Usuario("Pablo", "Pablo@gmail.com", "SOC1223", LocalDate.of(2006, 10, 24));
        }
        catch (UsuarioInvalidoException uie){
            System.out.println(uie.getMessage());
        }
    }
}
