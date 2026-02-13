import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
            numeroUsuarios++;
        }
    }

    public Prestamo realizarPrestamo(Usuario u1, String codigoLibro, String titulo, LocalDate fechaPrestamo) throws PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException{
        boolean libroDisponlibe=true;
        for (int i=0;i<prestamos.length;i++){
            if(prestamos[i]!=null) {
                if (!(prestamos[i].getFechaDevolucionReal() == null) || prestamos[i].getCodigoLibro().equals(codigoLibro)) {
                    libroDisponlibe = false;
                }
            }
        }
        if(u1.estaSancionado()){
            throw new UsuarioSancionadoException("El usuario esta sancionado");
        }
        else if (!(codigoLibro.matches("[A-Z]{3}[0-9]{4}"))){
            throw new PrestamoInvalidoException("El codigo del libro no es correcto");
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
            prestamos[numeroPrestamos]=p1;
            numeroPrestamos++;
            return p1;
        }
    }

    public boolean devolverLibro(String codigoLibro, LocalDate fechaDevolucion) throws PrestamoInvalidoException{
        boolean libroEnLista=false;
        for(int i=0;i<prestamos.length;i++){
            if(prestamos[i]!=null){
                if(prestamos[i].getCodigoLibro().equals(codigoLibro)){
                    libroEnLista=true;
                    if(fechaDevolucion.isBefore(prestamos[i].getFechaDevolucionPrevista())){
                        throw new PrestamoInvalidoException("La fecha de devolucion es anterior a la de prestamo");
                    }
                    else if(fechaDevolucion.isAfter(prestamos[i].getFechaDevolucionPrevista())){
                        long retraso= ChronoUnit.DAYS.between(prestamos[i].getFechaDevolucionPrevista(),fechaDevolucion);
                        System.out.println("Dias de retraso: "+retraso);
                        prestamos[i].getSocio().aniadirDiasSancion(retraso);
                    }
                }
            }
        }
        if(libroEnLista){
            return true;
        }
        else{
            return false;
        }
    }
    public Usuario buscarUsuario(String codigoSocio){
        Usuario aux =new Usuario();
        boolean encontrado=false;
        if(codigoSocio.matches("SOC[0-9]{5}")){
            for (int i=0;i<usuarios.length;i++){
                if(usuarios!=null) {
                    if (usuarios[i].getNumeroSocio().matches(codigoSocio)) {
                        aux = usuarios[i];
                        encontrado=true;
                    }
                }
            }
        }
        if(encontrado){
            return aux;
        }
        else{
            return null;
        }
    }
    public Usuario[] getUsuarios(){
        return usuarios;
    }
    public Prestamo[] getPrestamos(){
        return prestamos;
    }
    @Override
    public String toString(){
        String mensaje="Los prestamos y usuarios son: ";
        for(int i=0;i<prestamos.length;i++){
            if(prestamos[i]!=null){
                mensaje+= i+prestamos[i].toString();
            }
        }
        return mensaje;
    }
}
