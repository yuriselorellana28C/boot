package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.CategoriaRequest;
import com.ejemplo.demo.api.dto.CategoriaResponde;
import com.ejemplo.demo.domain.model.Categoria;
import com.ejemplo.demo.domain.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponde> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponde obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));
        return toResponse(categoria);
    }

    public CategoriaResponde crear(CategoriaRequest request) {
        validarNombreDuplicadoEnCrear(request.getNombre());

        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre().trim());
        categoria.setDescripcion(request.getDescripcion());

        Categoria guardada = categoriaRepository.save(categoria);
        return toResponse(guardada);
    }

    public CategoriaResponde actualizar(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));

        validarNombreDuplicadoEnActualizar(request.getNombre(), id);

        categoria.setNombre(request.getNombre().trim());
        categoria.setDescripcion(request.getDescripcion());

        Categoria actualizada = categoriaRepository.save(categoria);
        return toResponse(actualizada);
    }

    public void eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));

        categoriaRepository.delete(categoria);
    }

    private void validarNombreDuplicadoEnCrear(String nombre) {
        if (categoriaRepository.existsByNombreIgnoreCase(nombre.trim())) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + nombre.trim());
        }
    }

    private void validarNombreDuplicadoEnActualizar(String nombre, Long id) {
        if (categoriaRepository.existsByNombreIgnoreCaseAndIdNot(nombre.trim(), id)) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + nombre.trim());
        }
    }

    private CategoriaResponde toResponse(Categoria categoria) {
        return new CategoriaResponde(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}
