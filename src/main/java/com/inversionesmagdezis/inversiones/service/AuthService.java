package main.java.com.inversionesmagdezis.inversiones.service;

import main.java.com.inversionesmagdezis.inversiones.dto.request.LoginDTORequest;
import main.java.com.inversionesmagdezis.inversiones.dto.response.LoginDTOResponse;
import main.java.com.inversionesmagdezis.inversiones.repository.AuthRepository;
import main.java.com.inversionesmagdezis.inversiones.security.BCrypt;

public class AuthService {
     //atributos
    private final AuthRepository authRepository;

    //constructor
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    
    //metodo
    public LoginDTOResponse login(LoginDTORequest loginDTORequest){
      if(loginDTORequest == null){
          throw new RuntimeException("los datos estan vacios");
      } else if (loginDTORequest.getEmail() == null ||loginDTORequest.getPassword() == null){
        throw new RuntimeException ("uno o los dos campos estan vacios");
      }else if(loginDTORequest.getEmail().isEmpty()||loginDTORequest.getPassword().isEmpty()) {
       throw new RuntimeException("No puedes dejar campos en blanco");
      }
      
    LoginDTOResponse response  = authRepository.findUserbyEmail(loginDTORequest);
    
    if(response == null){
        throw new RuntimeException("Usuario no encontrado");
    }
    
    
   if(response.getContrasena()==null){
          throw new RuntimeException("no se ha podido concretar la operacion");
      }else{
    if (BCrypt.checkpw(loginDTORequest.getPassword(),response.getContrasena())) {
        return response;         
        }
   }
    return null;
}
}


