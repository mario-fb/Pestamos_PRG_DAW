import java.time.*;
import java.util.Scanner;

public class MainPrueba {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);

        try{
            Usuario u5 = new Usuario("Pablo", "oscar@gmail.com", "SOC12345", LocalDate.of(2020, 02, 03));

            GestorBiblioteca g1=new GestorBiblioteca();
            g1.realizarPrestamo(u5, "LAS1234", "Los tres cerditos", LocalDate.of(2024,10,20));
            System.out.println(g1.toString());
        }
        catch (UsuarioInvalidoException uie){
            System.out.println(uie.getMessage());
        }
        catch (PrestamoInvalidoException pie){
            System.out.println(pie.getMessage());
        }
        catch (UsuarioSancionadoException use){
            System.out.println(use.getMessage());
        }
        catch (LibroNoDisponibleException lnd){
            System.out.println(lnd.getMessage());
        }
    }
}
