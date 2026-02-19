import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Usuario {
    private String nombre;
    private String email;
    private String numeroSocio;
    private LocalDate fechaRegistro;
    private boolean sancionado;
    private LocalDate fechaFinSancion;

    public void aniadirDiasSancion(long dias){

        if(!sancionado){
            fechaFinSancion = LocalDate.now().plusDays(dias);
        }
        else{
            fechaFinSancion = fechaFinSancion.plusDays(dias);
        }
        sancionado = true;
    }
    public String getNumeroSocio(){
        return numeroSocio;
    }
    public void validarEmail()throws  UsuarioInvalidoException{
        if(email.matches("\\w+@\\w+\\.\\w+")){

        }
        else{
            throw new UsuarioInvalidoException("Email invalido");
        }
    }

    public void validarNumeroSocio()throws UsuarioInvalidoException{
        if(numeroSocio.matches("SOC[0-9]{5}")){

        }
        else{
            throw new UsuarioInvalidoException("Numero de socio invalido");
        }
    }
    public Usuario(){

    }
    public Usuario(String nombre, String email, String numeroSocio, LocalDate fechaRegistro)throws UsuarioInvalidoException{
        if(nombre==null){
            throw new UsuarioInvalidoException("El nombre no puede ser nulo");
        }
        else {
            this.nombre = nombre;
            this.email = email;
            this.validarEmail();

            this.numeroSocio = numeroSocio;
            this.validarNumeroSocio();

            this.fechaRegistro = fechaRegistro;
        }
    }
    public void sancionar(Scanner in){
        LocalDate fechaInicioSancion;
        System.out.println("Indica los dias a sancionar");
        int diasSancion=Integer.parseInt(in.nextLine());
        System.out.println("Indica la fecha que inicia la sancion en dd/mm/yyyy");
        String fecha=in.nextLine();
        if(fecha.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}")){
            String []array=fecha.split("/");
            fechaInicioSancion=LocalDate.of(Integer.parseInt(array[2]),Integer.parseInt(array[1]),Integer.parseInt(array[0]));
            fechaFinSancion=fechaInicioSancion.plusDays(diasSancion);
            sancionado=true;
        }
        else{
            System.out.println("El formato de fecha no es correcto");
        }
    }

    public void sancionarAMano(int dias, LocalDate inicioSancion){

        fechaFinSancion=inicioSancion.plusDays(dias);
        sancionado=true;

    }

    public void levantarSancion(){
        sancionado=false;
    }
    public boolean estaSancionado(){
        return sancionado;
    }

    @Override
    public String toString() {
        String mensaje= "Nombre: "+nombre+" email: "+email+" numero de socio: "+numeroSocio+" fecha de registro: "+fechaRegistro+" estado de la sancion: ";
        if(estaSancionado()){
            mensaje+=" Su sancion acaba en la fecha: "+fechaFinSancion;
        }
        else{
            mensaje+="El usuario no esta sancionado";
        }
        return mensaje;
    }
}
