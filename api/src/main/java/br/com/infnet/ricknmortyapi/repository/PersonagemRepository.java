package br.com.infnet.ricknmortyapi.repository;

import br.com.infnet.ricknmortyapi.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
    List<Personagem> findByNameContainingIgnoreCase(String name);
}
