import java.time.LocalDate;

public class GestorBiblioteca {
    private final static int MAX_USUARIOS=50;
    private final static int MAX_PRESTAMOS=200;
    Usuario[] usuarios=new Usuario[MAX_USUARIOS];
    Prestamo[] prestamos=new Prestamo[MAX_PRESTAMOS];
    int numeroUsuarios;
    int numeroPrestamos;

    public GestorBiblioteca(){
        numeroUsuarios=0;
        numeroPrestamos=0;
    }

    public void registrarUsuario(Usuario u1) throws UsuarioRepetidoException{
        boolean estaenlista=false;
        for(int i=0;i<usuarios.length;i++){
            if(usuarios[i]==u1){
                estaenlista=true;
            }
        }
        if(estaenlista){
            throw new UsuarioRepetidoException("El usuario esta repetido en la lista");
        }
        else{
            usuarios[numeroUsuarios]=u1;
        }
    }

    public Prestamo realizarPrestamo(Usuario u1, String codigoLibro, String titulo, LocalDate fechaPrestamo) throws PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException{
        boolean libroDisponlibe=true;
        for (int i=0;i<prestamos.length;i++){
            if(!(prestamos[i].getFechaDevolucionReal()==null)|| prestamos[i].getCodigoLibro().equals(codigoLibro)){
                libroDisponlibe=false;
            }
        }
        if(u1.estaSancionado()){
            throw new UsuarioSancionadoException("El usuario esta sancionado");
        }
        else if (!(codigoLibro.matches("[A-Z]{3}[0-9]{4}"))){
            throw new PrestamoInvalidoException("El codigo del libro no es correcto")
        }
        else if (titulo==null) {
            throw new PrestamoInvalidoException("El titulo del libro es invalido");
        } else if (fechaPrestamo.isAfter(LocalDate.now())) {
            throw new PrestamoInvalidoException("La fecha de prestamo no puede ser posterior a hoy");
        }

        else if(libroDisponlibe==false){
            throw new LibroNoDisponibleException("El libro no esta disponible");
        }
        else{
            Prestamo p1=new Prestamo(codigoLibro, u1, titulo, fechaPrestamo);
            return p1;
        }
    }

    public boolean devolverLibro() throws PrestamoInvalidoException{
        return false;
    }
    public Usuario buscarUsuario(){
        return null;
    }
}
