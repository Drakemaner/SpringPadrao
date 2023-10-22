package com.springpadrao.services;

import com.springpadrao.model.Endereco;
import com.springpadrao.model.Usuario;
import com.springpadrao.repository.EnderecoRepository;
import com.springpadrao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private cepService viaCepService;

    public Iterable<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.get();
    }


    public Usuario buscarPorEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.get();
    }

    public void inserir(Usuario usuario) {
        salvarUsuarioComCep(usuario);
    }


    public void atualizar(Long id, Usuario cliente) {
        Optional<Usuario> usuarioBd = usuarioRepository.findById(id);
        if (usuarioBd.isPresent()) {
            salvarUsuarioComCep(cliente);
        }
        else{
            throw new RuntimeException("Usuario não encontrado");
        }
    }


    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    private void salvarUsuarioComCep(Usuario usuario) {
        String cep = usuario.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        usuario.setEndereco(endereco);
        usuarioRepository.save(usuario);
    }
}
