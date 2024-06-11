package br.com.springboot.curso_spring.controllers;

import br.com.springboot.curso_spring.model.Usuario;
import br.com.springboot.curso_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GreetingsController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {

        Usuario usuario = new Usuario();
        usuario.setNome(name);
        usuarioRepository.save(usuario);

        return "Hello " + name + "!";
    }

    @GetMapping(value = "listatodos")
    @ResponseBody //Retorna dos dados para o corpo da resposta
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        List<Usuario> usuarios = usuarioRepository.findAll();
        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); //retorna a lista em JSON
    }

    @PostMapping(value = "salvar")
    @ResponseBody //Faz a descrição da resposta
    //RequestBody recebe os dados para salvar.
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {

        Usuario user = usuarioRepository.save(usuario);
        return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);

    }

    @DeleteMapping(value = "deletar")
    @ResponseBody
    public ResponseEntity<String> deletar(@RequestParam Long iduser) {

         usuarioRepository.deleteById(iduser);
         return new ResponseEntity<String>("Deletado com sucesso!", HttpStatus.OK);

    }

    @GetMapping(value = "buscaruserid")
    @ResponseBody
    public ResponseEntity<Usuario> buscarUserId(@RequestParam(name = "iduser") Long iduser) {

        Usuario user = usuarioRepository.findById(iduser).get();
        return new ResponseEntity<Usuario>(user, HttpStatus.OK);

    }

    @PutMapping(value = "atualizar")
    @ResponseBody //Faz a descrição da resposta
    //RequestBody recebe os dados para salvar.
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {

        if (usuario.getId() == null) {
            return new ResponseEntity<String>("ID não informado.", HttpStatus.OK);
        }

        Usuario user = usuarioRepository.saveAndFlush(usuario);
        return new ResponseEntity<Usuario>(user, HttpStatus.OK);

    }

    @GetMapping(value = "buscarpornome")
    @ResponseBody
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) {

        List<Usuario> user = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
        return new ResponseEntity<List<Usuario>>(user, HttpStatus.OK);

    }
}
