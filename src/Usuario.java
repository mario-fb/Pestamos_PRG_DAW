import java.time.*;

public class Usuario {
    private String nombre;
    private String email;
    private String numeroSocio;
    private LocalDate fechaRegistro;
    private boolean sancionado;
    private LocalDate fechaFinSancion;


    public void validarEmail()throws  UsuarioInvalidoException{
        if(email.matches("\\w+@\\w+\\.\\w+")){
            this.email=email;
        }
        else{
            throw new UsuarioInvalidoException("Usuario invalido");
        }
    }

    public Usuario(String nombre, String email, String numeroSocio, LocalDate fechaRegistro)throws UsuarioInvalidoException{
        this.nombre=nombre;
        try{
            this.validarEmail();
        }
        catch (UsuarioInvalidoException uie){
            System.out.println(uie.getMessage());
        }

    }
}
