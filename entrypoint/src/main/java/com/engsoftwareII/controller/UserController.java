package com.engsoftwareII.controller;

import com.engsoftwareII.converter.UserRequestConverter;
import com.engsoftwareII.request.UserRequest;
import com.engsoftwareII.response.Response;
import com.engsoftwareII.user.entity.Usuario;
import com.engsoftwareII.user.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Api("Api de Usuários")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRequestConverter requestConverter;


    @PostMapping
    @ApiOperation("Salva um novo usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário salvado com sucesso."),
            @ApiResponse(code = 400, message = "Requisição feita de maneira incorreta, se atente ao formato json.")
    })
    public ResponseEntity<Response<UserRequest>> save(@RequestBody UserRequest request, BindingResult result){

        Response<UserRequest> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Usuario usuario = this.userService.save(requestConverter.toUser(request));

        response.setData(requestConverter.toDTO(usuario));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{id}")
    @ApiOperation("Obter usuário via ID")
    @ApiResponses({
            @ApiResponse(code = 200,message = "Usuário encontrado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado para o ID informado")
    })
    public ResponseEntity<Response<Usuario>> getById(@PathVariable @ApiParam("ID do usuário") Long id){

        Response<Usuario> response = new Response<>();

        Optional<Usuario> usuario = userService.getById(id);

        if(usuario.isEmpty()){
            response.getErrors().add("ID de usuário inexistente.");
            return ResponseEntity.notFound().build();
        }

        response.setData(usuario.get());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
