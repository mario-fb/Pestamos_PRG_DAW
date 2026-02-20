import javax.sound.midi.Soundbank;
import java.time.DateTimeException;
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
    public static Usuario registrarNuevoUsuario(Scanner in, GestorBiblioteca gestor) throws UsuarioInvalidoException, UsuarioRepetidoException{
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
        for(int i=0;i< gestor.getNumeroUsuarios();i++){
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
            System.out.println("Libro devuelto");
        }
        else{
            System.out.println("Libro no esta en lista");
        }

    }

    public static void consultarEstado(Scanner in, GestorBiblioteca gestor){
            System.out.println("Introduce el codigo de socio");
            String codigoSocio=in.nextLine();
            Usuario usuario=gestor.buscarUsuario(codigoSocio);
            if(usuario!=null) {
                System.out.println(usuario.toString());
            }
            else{
                System.out.println("Usuario no encontrado");
            }
    }

    public static void mostrarPrestamos(GestorBiblioteca gestor){
            for (int i=0;i<gestor.getNumeroPrestamos();i++){
                if (gestor.getPrestamos()[i]!=null && !gestor.getPrestamos()[i].getDevuelto()){
                    System.out.println(gestor.getPrestamos()[i].toString());
                }
            }
    }

    public static void mostrarUsuariosSancionados(GestorBiblioteca gestor){
            for(int i=0;i<gestor.getNumeroUsuarios();i++){
                if(gestor.getUsuarios()[i]!=null && gestor.getUsuarios()[i].estaSancionado()){
                    System.out.println(gestor.getUsuarios()[i].toString());
                }
            }
    }

    public static void actualizarSanciones(GestorBiblioteca gestor){
        for (int i=0;i<gestor.getNumeroUsuarios();i++){
            if(gestor.getUsuarios()[i]!=null && gestor.getUsuarios()[i].estaSancionado()){
                if (gestor.getUsuarios()[i].getFechaFinSancion() != null &&
                        gestor.getUsuarios()[i].getFechaFinSancion().isBefore(LocalDate.now())) {
                    gestor.getUsuarios()[i].levantarSancion();
                }
            }
        }

    }

    public static void main(String[] args) {
        GestorBiblioteca gestor=new GestorBiblioteca();
        Scanner in=new Scanner(System.in);
        int respuesta=0;
        do{
                respuesta=0;
                mostrarMenu();
                try{
            try{

                respuesta = seleccionarOpcion(in);
            }
            catch (NumberFormatException nfe){
                System.out.println("Debes seleccionar una opcion con un numero");

            }
            switch (respuesta) {
                case 1:
                    try {
                        gestor.registrarUsuario(registrarNuevoUsuario(in, gestor));
                        System.out.println("Usuario correctamente registrado");
                    } catch (UsuarioInvalidoException uie) {
                        System.out.println("Error " + uie.getMessage());
                    } catch (UsuarioRepetidoException ure) {
                        System.out.println("Error " + ure.getMessage());
                    }
                    catch (DateTimeException dte){
                        System.out.println("Has puesto una fecha que no es valida");
                    }
                    catch (ArrayIndexOutOfBoundsException aiob){
                        System.out.println("No has puesto la fecha en el formato indicado");
                    }
                    break;
                case 2:
                    try {
                        hacerPrestamo(in, gestor);
                        System.out.println("Prestamo realizado correctamente");
                    } catch (PrestamoInvalidoException pie) {
                        System.out.println("Error " + pie.getMessage());
                    } catch (UsuarioSancionadoException use) {
                        System.out.println("Error " + use.getMessage());
                    } catch (LibroNoDisponibleException lnd) {
                        System.out.println("Error " + lnd.getMessage());
                    }
                    catch (DateTimeException dte){
                        System.out.println("Has puesto una fecha que no es valida");
                    }catch (ArrayIndexOutOfBoundsException aiob){
                        System.out.println("No has puesto la fecha en el formato indicado");
                    }
                    break;

                case 3:
                    try {
                        devolverLibro(in, gestor);

                    } catch (PrestamoInvalidoException pie) {
                        System.out.println("Error " + pie.getMessage());

                    }catch (ArrayIndexOutOfBoundsException aiob){
                        System.out.println("No has puesto la fecha en el formato indicado");
                    }
                    break;
                case 4:
                    consultarEstado(in, gestor);

                    break;
                case 5:
                    mostrarPrestamos(gestor);

                    break;
                case 6:
                    mostrarUsuariosSancionados(gestor);

                    break;
                case 7:
                    actualizarSanciones(gestor);
                    System.out.println("Sanciones actualizadas");

                    break;


            }

            }
                catch (NumberFormatException nfe){
                    System.out.println("No has escrito correctamente lo que se pedia");
                }
                if(respuesta!=8) {
                    System.out.println("Presiona intro para continuar");
                    in.nextLine();
                }
        }while(respuesta!=8);
        System.out.println("Saliendo del programa......");

    }
}
