package com.example.andorinhas2.service;

import com.example.andorinhas2.dto.ChildDto;
import com.example.andorinhas2.dto.UserDto;
import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.model.ERegistration;
import com.example.andorinhas2.model.UserTable;
import com.example.andorinhas2.repository.ChildRepository;
import com.example.andorinhas2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChildService {
    private ChildRepository childRepository;

    public ChildService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    public List<ChildTable> listarTodos() {
        return childRepository.findAll();
    }

    @Transactional
    public void cadastrar(ChildDto childDto) {
        ChildTable crianca = new ChildTable();
        crianca.setNome(childDto.nome());
        crianca.setId(childDto.id());
        crianca.setTurma(null);
        crianca.setNomePai(childDto.nomePai());
        crianca.setDataNascimento(childDto.dataNascimento());
        crianca.setAvatarId(childDto.avatarId());
        crianca.setTelefonePai(childDto.telefonePai());
        crianca.setERegistration(ERegistration.SAIDA);
        crianca.setAtiva(true);
        childRepository.save(crianca);
    }


    public ChildTable listarCrianca(Long id) {
        return childRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criança não encontrada"));
    }

    public Long totalCriancas() {
        Long total;
        total = childRepository.count();
        return total;
    }

    public void excluir(Long id) {

        ChildTable child = childRepository.getReferenceById(id);
        child.setAtiva(false);

        childRepository.save(child);
    }

    public void reativar(Long id) {

        ChildTable child = childRepository.getReferenceById(id);
        child.setAtiva(true);

        childRepository.save(child);
    }

    public void atualizar(ChildDto newData) {

        ChildTable oldData = childRepository.getReferenceById(newData.id());

        if (newData.nome() != oldData.getNome() && newData.nome() != null) {
            oldData.setNome(newData.nome());
        }
        if (newData.turma() != oldData.getTurma() && newData.turma() != null) {
            oldData.setTurma(newData.turma());
        }
        if (newData.avatarId() != oldData.getAvatarId() && newData.avatarId() != null) {
            oldData.setAvatarId(newData.avatarId());
        }
        if (newData.nomePai() != oldData.getNomePai() && newData.nomePai() != null) {
            oldData.setNomePai(newData.nomePai());
        }
        if (newData.telefonePai() != oldData.getTelefonePai() && newData.telefonePai() != null) {
            oldData.setTelefonePai(newData.telefonePai());
        }
        if (newData.dataNascimento() != oldData.getDataNascimento() && newData.dataNascimento() != null) {
            oldData.setDataNascimento(newData.dataNascimento());
        }childRepository.save(oldData);
    }

    public List<ChildTable> acharAtivas(boolean isAtiva){
        List<ChildTable> lista = childRepository.findByAtiva(isAtiva);
        return lista;
    }

}
