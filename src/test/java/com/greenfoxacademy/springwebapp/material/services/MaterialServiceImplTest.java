package com.greenfoxacademy.springwebapp.material.services;

import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyProducedException;
import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.common.exceptions.NotEnoughMaterialException;
import com.greenfoxacademy.springwebapp.common.exceptions.QualityDifferenceException;
import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;
import com.greenfoxacademy.springwebapp.material.repositories.MaterialRepository;
import com.greenfoxacademy.springwebapp.product.models.Product;
import com.greenfoxacademy.springwebapp.product.repositories.ProductRepository;
import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;
import com.greenfoxacademy.springwebapp.warehouse.repositories.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.greenfoxacademy.springwebapp.TestFactory.*;
import static com.greenfoxacademy.springwebapp.product.models.ProductStatus.READY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class MaterialServiceImplTest {

  private MaterialService materialService;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private MaterialRepository materialRepository;
  @Mock
  private WarehouseRepository warehouseRepository;

  String quality = "25CrMo4";
  double size = 10.68;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    materialService = new MaterialServiceImpl(productRepository, materialRepository, warehouseRepository);
  }

  @Test
  public void saveMaterial_ValidMaterial_ReturnsMaterialResponseDTO()
          throws IdNotFoundException, QualityDifferenceException, NotEnoughMaterialException, AlreadyProducedException {
    MaterialRequestDTO requestDTO = materialRequestDtoBuilder();
    Warehouse external = externalWarehouseBuilder();
    when(warehouseRepository.findById(1)).thenReturn(Optional.of(external));

    MaterialResponseDTO responseDTO = materialService.saveMaterial(requestDTO);

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
    MaterialRequestDTO requestDTO = materialRequestDtoBuilder();

    when(warehouseRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(IdNotFoundException.class, () -> {
      materialService.saveMaterial(requestDTO);
    });
  }

  @Test
  public void convert_ReturnsCorrectMaterialResponseDTO() {
    Material material = materialBuilder();

    MaterialResponseDTO responseDTO = materialService.convert(material);

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
    MaterialResponseDTO responseDTO = materialService.convert(null);

    assertNull(responseDTO);
  }

  @Test
  void findMaterial_WithNoParameters_ReturnsEmptyList() {
    List<Material> materials = materialService.findMaterial(Optional.empty(), Optional.empty());

    assertThat(materials).isNotNull();
    assertThat(materials.size()).isEqualTo(0);
  }

  @Test
  void assignMaterialToProduct_InvalidProductId_ThrowsIdNotFoundException() {
    Integer invalidProductId = -1;
    Integer materialId = 1;

    assertThatThrownBy(() -> materialService.assignMaterialToProduct(invalidProductId, materialId))
            .isInstanceOf(IdNotFoundException.class);
  }

  @Test
  void requestValidation_ProductAlreadyProduced_ThrowsAlreadyProducedException() {
    Product product = new Product();
    product.setStatus(READY);
    Material material = new Material();

    assertThrows(AlreadyProducedException.class, () -> materialService.requestValidation(product, material));
  }

  @Test
  void requestValidation_QualityDifference_ThrowsQualityDifferenceException() {
    Product product = new Product();
    product.setQuality("high");
    Material material = new Material();
    material.setQuality("low");

    assertThrows(QualityDifferenceException.class, () -> materialService.requestValidation(product, material));
  }

  @Test
  void buildMaterial_WithInvalidWarehouseId_ShouldThrowException() {
    Material originalMaterial = new Material();
    originalMaterial.setQuality("Test Quality");
    originalMaterial.setSize(10.0);
    originalMaterial.setHitNumber(5L);
    originalMaterial.setUnitPrice(100L);
    originalMaterial.setUnitWeight(0.5);
    originalMaterial.setUnitLength(2.0);

    Double deltaLength = 5.0;
    Double deltaWeight = deltaLength * originalMaterial.getUnitWeight();

    assertThrows(IdNotFoundException.class,
        () -> materialService.buildMaterial(originalMaterial, deltaLength, deltaWeight));
  }

}
