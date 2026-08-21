package com.sportsbook.service.category;

import com.sportsbook.dto.category.CategoryRequestDTO;
import com.sportsbook.dto.category.CategoryResponseDTO;
import com.sportsbook.model.Category.Category;
import com.sportsbook.model.Sport.Sport;
import com.sportsbook.repository.category.CategoryRepository;
import com.sportsbook.repository.sport.SportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SportRepository sportRepository;

    public CategoryService(CategoryRepository categoryRepository, SportRepository sportRepository) {
        this.categoryRepository = categoryRepository;
        this.sportRepository = sportRepository;
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return toResponseDTO(category);
    }

    public List<CategoryResponseDTO> getCategoriesBySport(Long sportId) {
        return categoryRepository.findBySportId(sportId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {
        Sport sport = sportRepository.findById(request.getSportId())
                .orElseThrow(() -> new RuntimeException("Sport not found with id: " + request.getSportId()));

        Category category = new Category();
        category.setName(request.getName());
        category.setSport(sport);

        Category saved = categoryRepository.save(category);
        return toResponseDTO(saved);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setName(request.getName());

        if (request.getSportId() != null) {
            Sport sport = sportRepository.findById(request.getSportId())
                    .orElseThrow(() -> new RuntimeException("Sport not found with id: " + request.getSportId()));
            category.setSport(sport);
        }

        Category updated = categoryRepository.save(category);
        return toResponseDTO(updated);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryResponseDTO toResponseDTO(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getSport() != null ? category.getSport().getId() : null,
                category.getSport() != null ? category.getSport().getName() : null
        );
    }
}