package ch.coll.ctf.domain.ctf.service;

import java.io.*;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import ch.coll.ctf.domain.ctf.model.Ctf;
import ch.coll.ctf.domain.ctf.port.in.CtfServicePort;
import ch.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import ch.coll.ctf.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class CtfService implements CtfServicePort {

    private final CtfRepositoryPort ctfRepositoryPort;

    @Override
    public List<Ctf> getAllCtfs() {
        return ctfRepositoryPort.findAll();
    }

    @Override
    public Ctf getCtfByName(String name) {
        return ctfRepositoryPort.findByName(name).orElseThrow(() -> new IllegalArgumentException("Ctf with name " + name + " not found"));
    }

    @Override
    public Ctf createCtf(Ctf ctf, MultipartFile file) {
        System.out.println("created ctf");

        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ctf.setAuthor(author);
        // Save the file
        //todo: Virusscanner
        if (file != null) {
            String directoryPath = "uploads";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new RuntimeException("Failed to create directory: " + directoryPath);
                }
            }
            String originalFilename = file.getOriginalFilename();
            String filePath = directoryPath + originalFilename;
            File serverFile = new File(filePath);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(serverFile))) {
                writer.write(new String(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to write file: " + originalFilename, e);
            }

            // Set the file path in the Ctf entity
            ctf.setFilePath(filePath);
        }
        // Save the Ctf entity
        return ctfRepositoryPort.save(ctf);
    }

    @Override
    public Ctf updateCtf(String name, Ctf ctf) {
        if (ctfRepositoryPort.findByName(name).isEmpty()) {
            throw new IllegalArgumentException("Ctf with name " + name + " does not exist");
        }
        return ctfRepositoryPort.save(ctf);
    }

    @Override
    public void deleteCtf(String name) {
        ctfRepositoryPort.deleteByName(name);
    }
}
