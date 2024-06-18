package ch.coll.ctf.adapter.repository.jpa.ctf;

import ch.coll.ctf.adapter.repository.jpa.ctf.mapper.CtfEntityMapper;
import ch.coll.ctf.adapter.repository.jpa.ctf.service.JpaCtfEntityRepository;
import ch.coll.ctf.domain.ctf.model.Ctf;
import ch.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaCtfRepository implements CtfRepositoryPort{
    private final JpaCtfEntityRepository ctfRepository;
    private final CtfEntityMapper ctfMapper;

    public List<Ctf> findAll() {
        log.info("Getting all Ctfs");

        return ctfRepository.findAll().stream().map(ctfMapper::mapEntityToModel).collect(Collectors.toList());
    }

    public Optional<Ctf> findByName(String name) {
        log.info("Getting Ctf by id - id={}", name);

        return ctfRepository.findByName(name).map(ctfMapper::mapEntityToModel);
    }

    public Ctf save(Ctf ctf) {
        log.info("Creating Ctf - Ctf={}", ctf);

        return ctfMapper.mapEntityToModel(ctfRepository.save(ctfMapper.mapModelToEntity(ctf)));
    }

    public void updateCtf(Ctf ctf) {
        log.info("Updating Ctf - Ctf={}", ctf);
        if( ctfRepository.findByName(ctf.getName()).isEmpty()){
            throw new IllegalArgumentException("Ctf does not exist");
        }else{
            ctfRepository.save(ctfMapper.mapModelToEntity(ctf));
        }
    }

    public void deleteByName(String name) {
        log.info("Deleting Ctf by name - name={}", name);

        ctfRepository.deleteByName(name);
    }
}
