package com.greenfoxacademy.springwebapp.material.services;

import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyProducedException;
import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.common.exceptions.NotEnoughMaterialException;
import com.greenfoxacademy.springwebapp.common.exceptions.QualityDifferenceException;
import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;
import com.greenfoxacademy.springwebapp.material.repositories.MaterialRepository;
import com.greenfoxacademy.springwebapp.product.repositories.ProductRepository;
import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;
import com.greenfoxacademy.springwebapp.warehouse.repositories.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.greenfoxacademy.springwebapp.TestFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class MaterialServiceImplTest {

  private MaterialService materialService;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private MaterialRepository materialRepository;
  @Mock
  private WarehouseRepository warehouseRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    materialService = new MaterialServiceImpl(productRepository, materialRepository, warehouseRepository);
  }

  @Test
  public void saveMaterial_ValidMaterial_ReturnsMaterialResponseDTO()
          throws IdNotFoundException, QualityDifferenceException, NotEnoughMaterialException, AlreadyProducedException {
    // Arrange
    MaterialRequestDTO requestDTO = materialRequestDtoBuilder();
    Warehouse external = warehouseBuilder();
    Mockito.when(warehouseRepository.findById(1)).thenReturn(Optional.of(external));

    // Act
    MaterialResponseDTO responseDTO = materialService.saveMaterial(requestDTO);

    // Assert
    assertNotNull(responseDTO);
    assertEquals(requestDTO.getQuality(), responseDTO.getQuality());
    assertEquals(requestDTO.getSize(), responseDTO.getSize());
    assertEquals(requestDTO.getHitNumber(), responseDTO.getHitNumber());
    assertEquals(requestDTO.getUnitWeight(), responseDTO.getUnitWeight());
    assertEquals(requestDTO.getUnitLength(), responseDTO.getUnitLength());
    assertEquals(requestDTO.getOriginalWeight(), responseDTO.getOriginalWeight());
    assertEquals(requestDTO.getOriginalLength(), responseDTO.getOriginalLength());
  }

  @Test
  public void saveMaterial_InvalidWarehouseId_ThrowsIdNotFoundException()
          throws IdNotFoundException, QualityDifferenceException, NotEnoughMaterialException, AlreadyProducedException {
    // Arrange
    MaterialRequestDTO requestDTO = new MaterialRequestDTO();
    requestDTO.setQuality("A");
    requestDTO.setSize(20.0);
    requestDTO.setHitNumber(10L);
    requestDTO.setUnitPrice(20L);
    requestDTO.setUnitWeight(2.5);
    requestDTO.setUnitLength(3.0);
    requestDTO.setOriginalWeight(25.0);

    Mockito.when(warehouseRepository.findById(1)).thenReturn(Optional.empty());

    // Act/Assert
    assertThrows(IdNotFoundException.class, () -> {
      materialService.saveMaterial(requestDTO);
    });
  }

  @Test
  public void convert_ReturnsCorrectMaterialResponseDTO() {
    // arrange
    Material material = materialBuilder();

    // act
    MaterialResponseDTO responseDTO = materialService.convert(material);

    // assert
    assertEquals(1, responseDTO.getId());
    assertEquals("quality", responseDTO.getQuality());
    assertEquals(20.0, responseDTO.getSize());
    assertEquals(5L, responseDTO.getHitNumber());
    assertEquals(10.0, responseDTO.getUnitLength());
    assertEquals(5.0, responseDTO.getUnitWeight());
    assertEquals(500.0, responseDTO.getOriginalLength());
    assertEquals(250.0, responseDTO.getOriginalWeight());
  }

  @Test
  public void convert_ReturnsNull_WhenMaterialIsNull() {
    // act
    MaterialResponseDTO responseDTO = materialService.convert(null);

    // assert
    assertNull(responseDTO);
  }


}
