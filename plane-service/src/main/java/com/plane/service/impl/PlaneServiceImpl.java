package com.plane.service.impl;

import com.plane.dto.PlaneDTO;
import com.plane.entity.Plane;
import com.plane.entity.PlaneType;
import com.plane.exception.PlaneNotFoundException;
import com.plane.mapper.PlaneMapper;
import com.plane.repository.PlaneRepository;
import com.plane.service.PlaneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaneServiceImpl implements PlaneService {

    private final PlaneRepository planeRepository;
    private final PlaneMapper planeMapper;

    @Override
    @Cacheable("planes")
    public List<PlaneDTO> findAllPlanes() {

        return planeRepository.findAll()
                .stream()
                .map(planeMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("planes")
    public List<PlaneDTO> findAllPlanesByType(String type) {

        PlaneType planeType = convertStringToPlaneType(type);

        return planeRepository.findAllByType(planeType)
                .stream()
                .map(planeMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("planes")
    public List<PlaneDTO> findAllPlanesByCapacity(int capacity) {
        return planeRepository.findAllByCapacityGreaterThan(capacity)
                .stream()
                .map(planeMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("planes")
    public List<PlaneDTO> findAllPlanesByManufacturer(String manufacturer) {

        StringBuilder sb = new StringBuilder();

        return planeRepository.findAllByModelLikeIgnoreCase(sb.append(manufacturer).append("%").toString())
                .stream()
                .map(planeMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlaneDTO createNewPlane(PlaneDTO planeDTO) {

        Plane plane = Plane.builder()
                .model(planeDTO.getModel())
                .capacity(planeDTO.getCapacity())
                .type(planeDTO.getType())
                .description(planeDTO.getDescription())
                .build();

        planeRepository.save(plane);
        return planeMapper.mapToDTO(plane);
    }

    @Override
    @CacheEvict(value = "planes", allEntries = true)
    public PlaneDTO updatePlane(Long id, PlaneDTO updatedPlane) {

        Plane plane = planeRepository.findById(id)
                .orElseThrow(() -> new PlaneNotFoundException(id));

        plane.setModel(updatedPlane.getModel());
        plane.setCapacity(updatedPlane.getCapacity());
        plane.setType(PlaneType.valueOf(updatedPlane.getType().toString().toUpperCase()));
        plane.setDescription(updatedPlane.getDescription());

        Plane updatedEntity = planeRepository.save(plane);
        return planeMapper.mapToDTO(updatedEntity);

    }

    @Override
    @CacheEvict(value = "planes", key = "#id")
    public String deletePlane(Long id) {

        Plane plane = planeRepository.findById(id)
                        .orElseThrow(() -> new PlaneNotFoundException(id));

        planeRepository.deleteById(id);
        return "Plane successfully deleted with ID: " + id;
    }

    public PlaneType convertStringToPlaneType(String type) {
        if (type != null) {
            try {
                return PlaneType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid PlaneType: " + type);
            }
        }
        return null;
    }
}
