import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Prestamo {
    private String codigoLibro;
    private String tituloLibro;
    private Usuario socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;

    public void validarCodigo()throws PrestamoInvalidoException{
        if(!(codigoLibro.matches("[A-Z]{3}[0-9]{4}"))){
            throw new PrestamoInvalidoException("Codigo del libro invalido");
        }
    }

    public Prestamo(String codigoLibro, Usuario socio, String tituloLibro, LocalDate fechaPrestamo) throws PrestamoInvalidoException{
        this.codigoLibro=codigoLibro;
        validarCodigo();
        this.socio=socio;
        this.tituloLibro=tituloLibro;
        this.fechaPrestamo=fechaPrestamo;
        fechaDevolucionPrevista= fechaPrestamo.plusDays(14);
    }
    public void registrarDevolucion(LocalDate fecha) throws PrestamoInvalidoException{
        if(fecha.isBefore(fechaPrestamo)){
            throw new PrestamoInvalidoException("La fecha es anterior a la del prestamo");
        }
        else{
            fechaDevolucionReal=fecha;
        }
    }
    public int calcularDiasRetraso(){
        int retraso=(int)(ChronoUnit.DAYS.between(fechaDevolucionPrevista,fechaDevolucionReal));

        if (fechaDevolucionReal.isAfter(fechaDevolucionPrevista)) {
                return retraso;
        }

        else if(estaRetrasado()==false){
            return 0;
        }
    }
    public boolean estaRetrasado(){
        if(fechaDevolucionReal.isBefore(fechaDevolucionPrevista)){
            return false;
        }
        else if(fechaDevolucionReal.isEqual(fechaDevolucionPrevista)){
            return false;
        }
        else{
            return true;
        }
    }
    @Override
    public String toString(){
        String mensaje= "El codigo del libro es: "+codigoLibro+" el titulo del libro es: "+tituloLibro+" El usuario que ha realizado el prestamo es: "+socio.toString()+" La fecha del prestamo es "+
                fechaPrestamo+" La fecha de devolucion prevista es: "+fechaDevolucionPrevista;
        if(fechaDevolucionReal!=null){
            mensaje+=" La fecha de devolucion fue el: "+fechaDevolucionReal;
            if(estaRetrasado()){
                mensaje+=" La entrega fue con un retraso de:"+calcularDiasRetraso();
            }
            else{
                mensaje+=" La entrega no conto con retraso";
            }
        }
        return mensaje;
    }
}
