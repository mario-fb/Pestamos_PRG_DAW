import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void mostrarMenu(){
        System.out.println("=== SISTEMA GESTIÓN BIBLIOTECA ===");
        System.out.println("1) Registrar nuevo ususario");
        System.out.println("2) Realizar prestamo libro");
        System.out.println("3) Devolver libro");
        System.out.println("4) Consultar estado de usuario");
        System.out.println("5) Mostrar préstamos activos");
        System.out.println("6) Mostrar usuarios sancionados");
        System.out.println("7) Actualizar sanciones");
        System.out.println("8) Salir");
    }
    public static int seleccionarOpcion(Scanner in){
        System.out.println("Selecciona una opción");
        return Integer.parseInt(in.nextLine());
    }
    public static Usuario registrarNuevoUsuario(Scanner in) throws UsuarioInvalidoException{
        System.out.println("Nombre: ");
        String nombre=in.nextLine();
        System.out.println("Email: ");
        String email=in.nextLine();
        System.out.println("Número de socio: ");
        String numeroSocio=in.nextLine();
        System.out.println("Fecha registro dd/mm/aaaa");
        String fechaRegistro=in.nextLine();
        String[] fechaSplit=fechaRegistro.split("/");
        int dia=Integer.parseInt(fechaSplit[0]);
        int mes=Integer.parseInt(fechaSplit[1]);
        int anyo=Integer.parseInt(fechaSplit[2]);
        Usuario usuario=new Usuario(nombre, email, numeroSocio, LocalDate.of(anyo, mes, dia));
        return usuario;
    }

    public static void hacerPrestamo(Scanner in, GestorBiblioteca gestor) throws PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException{
        Usuario usuario =null;
        System.out.println("Escribe el codigo del libro: ");
        String codigoLibro=in.nextLine();
        System.out.println("Escribe el titulo: ");
        String titulo=in.nextLine();
        System.out.println("Escribe el numero de socio: ");
        String numSocio=in.nextLine();
        for(int i=0;i< gestor.numeroUsuarios;i++){
            if(gestor.getUsuarios()[i].getNumeroSocio().equals(numSocio)){
                usuario= gestor.getUsuarios()[i];
            }
        }
        System.out.println("Escribe la fecha de prestamo en dd/mm/aaaa: ");
        String fecha=in.nextLine();
        String[] fechaSplit=fecha.split("/");
        int dia=Integer.parseInt(fechaSplit[0]);
        int mes=Integer.parseInt(fechaSplit[1]);
        int anyo=Integer.parseInt(fechaSplit[2]);
        if(usuario==null){
            throw new PrestamoInvalidoException("El usuario es nulo");
        }
        gestor.realizarPrestamo(usuario, codigoLibro, titulo, LocalDate.of(anyo, mes, dia));
    }

    public static void devolverLibro(Scanner in, GestorBiblioteca gestor) throws PrestamoInvalidoException{
        System.out.println("Código libro: ");
        String codigoLibro=in.nextLine();
        System.out.println("Fecha devolucion dd/mm/aaaa");
        String fechaDevolucion=in.nextLine();
        String[] fechaSplit=fechaDevolucion.split("/");
        int dia=Integer.parseInt(fechaSplit[0]);
        int mes=Integer.parseInt(fechaSplit[1]);
        int anyo=Integer.parseInt(fechaSplit[2]);
        if(gestor.devolverLibro(codigoLibro, LocalDate.of(anyo,mes,dia))) {
            gestor.devolverLibro(codigoLibro, LocalDate.of(anyo, mes, dia));
        }
      //  *******TERMINAR*******

    }
        public static void consultarEstado(Scanner in, GestorBiblioteca gestor){
            System.out.println("Introduce el nombre de usuario");
            String nombre=in.nextLine();
            System.out.println("Introduce el codigo de socio");
            String codigoSocio=in.nextLine();
            Usuario usuario=gestor.buscarUsuario(codigoSocio);

        }

    public static void main(String[] args) {
        GestorBiblioteca gestor=new GestorBiblioteca();
        Scanner in=new Scanner(System.in);
        int respuesta=0;
        do{

                mostrarMenu();
            try{

                respuesta = seleccionarOpcion(in);
            }
            catch (NumberFormatException nfe){
                System.out.println("Debes seleccionar una opcion con un numero");
                System.out.println("Presiona intro para continuar");
            }
            switch (respuesta){
                case 1:
                   try{
                       gestor.registrarUsuario(registrarNuevoUsuario(in));
                       System.out.println("Usuario correctamente registrado");
                       System.out.println("Presiona intro para continuar");
                   }
                   catch (UsuarioInvalidoException uie){
                       System.out.println("Error "+uie.getMessage());
                   }
                   catch (UsuarioRepetidoException ure){
                       System.out.println("Error "+ure.getMessage());
                   }
                   catch (NumberFormatException nfe){
                       System.out.println("Tienes que poner fecha");
                   }
                   break;
                case 2:
                    try {
                        hacerPrestamo(in, gestor);
                        System.out.println("Prestamos realizado correctamente");
                    }
                    catch (PrestamoInvalidoException pie){
                        System.out.println("Error "+pie.getMessage());
                    }
                    catch (UsuarioSancionadoException use){
                        System.out.println("Error "+use.getMessage());
                    }
                    catch (LibroNoDisponibleException lnd){
                        System.out.println("Error "+lnd.getMessage());
                    }
                    break;

                case 3:
                    try {
                        devolverLibro(in, gestor);
                        System.out.println("Devolucion realizada");
                    }
                    catch (PrestamoInvalidoException pie){
                        System.out.println("Error "+pie.getMessage());
                    }
                    break;
                case 4:

            }
            in.nextLine();
        }while(respuesta!=8);
    }
}
