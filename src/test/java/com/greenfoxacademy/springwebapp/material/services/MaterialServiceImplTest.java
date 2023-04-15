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

  //  @Test
  //  void findMaterial_WithQualityAndSize_ReturnsListOfMaterials() {
  //    Material material = Material.builder().quality(quality).size(size).build();
  //    materialRepository.save(material);
  //    List<Material> materialList = materialRepository.findAllByQualityAndSize(quality, size);
  //    List<Material> materials = new ArrayList<>();
  //    for (Material m : materialList) {
  //      materials.add(m);
  //    }
  //
  //    assertThat(materials).isNotNull();
  //    assertThat(materials.size()).isEqualTo(1);
  //    assertThat(materials.get(0)).isEqualTo(material);
  //  }


  //  @Test
  //  void findMaterial_WithQualityAndSize_ReturnsListOfMaterialss() {
  //    String quality = "quality";
  //    double size = 20.0;
  //    Material material = Material.builder().quality(quality).size(size).build();
  //    materialRepository.save(material);
  //    List<Material> materialList = materialRepository.findAllByQualityAndSize(quality, size);
  //    List<Material> materials = materialService.findMaterial(Optional.of(quality), Optional.of(size));
  //
  //    assertThat(materials).isNotNull();
  //    assertThat(materials.size()).isEqualTo(1);
  //    assertThat(materials.get(0)).isEqualTo(material);
  //  }

  //    @Test
  //    void findMaterial_WithQuality_ReturnsListOfMaterials() {
  //      String quality = "B";
  //      Material material = new Material();
  //      material.setQuality(quality);
  //      materialRepository.save(material);
  //
  //      List<Material> materials = materialService.findMaterial(Optional.of(quality), Optional.empty());
  //
  //      assertThat(materials).isNotNull();
  //      assertThat(materials.size()).isEqualTo(1);
  //      assertThat(materials.get(0)).isEqualTo(material);
  //    }

  @Test
  void findMaterial_WithNoParameters_ReturnsEmptyList() {
    List<Material> materials = materialService.findMaterial(Optional.empty(), Optional.empty());

    assertThat(materials).isNotNull();
    assertThat(materials.size()).isEqualTo(0);
  }

  //  @Test
  //  void transferMaterial_WhenEnoughMaterial_ReturnsTransferredMaterial() throws NotEnoughMaterialException {
  //    // arrange
  //    String quality = "quality";
  //    double size = 20.0;
  //    int quantity = 3;
  //    double unitLength = 2.0;
  //    double unitWeight = 1.0;
  //    double originalLength = 6.0;
  //    double originalWeight = originalLength * unitWeight;
  //    double remainingLength = originalLength - (quantity * unitLength);
  //    double remainingWeight = remainingLength * unitWeight;
  //
  //    Material material = new Material();
  //    material.setQuality(quality);
  //    material.setSize(size);
  //    material.setUnitLength(unitLength);
  //    material.setUnitWeight(unitWeight);
  //    material.setOriginalLength(originalLength);
  //    material.setOriginalWeight(originalWeight);
  //    material.setRemainingLength(originalLength);
  //    material.setRemainingWeight(originalWeight);
  //    material.setWarehouse(new Warehouse());
  //    materialRepository.save(material);
  //
  //    // act
  //    Material transferredMaterial = materialService.transferMaterial(quality, size, quantity);
  //
  //    // assert
  //    assertThat(transferredMaterial).isNotNull();
  //    assertThat(transferredMaterial.getId()).isNotNull();
  //    assertThat(transferredMaterial.getQuality()).isEqualTo(quality);
  //    assertThat(transferredMaterial.getSize()).isEqualTo(size);
  //    assertThat(transferredMaterial.getQuantity()).isEqualTo(quantity);
  //    assertThat(transferredMaterial.getUnitLength()).isEqualTo(unitLength);
  //    assertThat(transferredMaterial.getUnitWeight()).isEqualTo(unitWeight);
  //    assertThat(transferredMaterial.getOriginalLength()).isEqualTo(quantity * unitLength);
  //    assertThat(transferredMaterial.getOriginalWeight()).isEqualTo(quantity * unitLength * unitWeight);
  //    assertThat(transferredMaterial.getRemainingLength()).isEqualTo(remainingLength);
  //    assertThat(transferredMaterial.getRemainingWeight()).isEqualTo(remainingWeight);
  //  }

  //  @Test
  //  void transferMaterial_WhenNotEnoughMaterial_ThrowsNotEnoughMaterialException() {
  //    // arrange
  //    String quality = "quality";
  //    double size = 20.0;
  //    int quantity = 3;
  //    double unitLength = 2.0;
  //    double unitWeight = 1.0;
  //    double originalLength = 4.0;
  //    double originalWeight = originalLength * unitWeight;
  //
  //    Material material = new Material();
  //    material.setQuality(quality);
  //    material.setSize(size);
  //    material.setUnitLength(unitLength);
  //    material.setUnitWeight(unitWeight);
  //    material.setOriginalLength(originalLength);
  //    material.setOriginalWeight(originalWeight);
  //    material.setRemainingLength(originalLength);
  //    material.setRemainingWeight(originalWeight);
  //    material.setWarehouse(new Warehouse());
  //    materialRepository.save(material);
  //
  //    // act and assert
  //    assertThatThrownBy(() -> materialService.transferMaterial(quality, size, quantity))
  //            .isInstanceOf(NotEnoughMaterialException.class);
  //  }

  //  @Test
  //  void updateMaterial_UpdatesMaterialCorrectly() {
  //    // arrange
  //    Material material = new Material();
  //    material.setRemainingLength(10.0);
  //    material.setRemainingWeight(20.0);
  //    material.setUpdatedAt(LocalDate.now());
  //    materialRepository.save(material);
  //
  //    Integer quantity = 5;
  //    Double remainingLength = 7.5;
  //    Double remainingWeight = 15.0;
  //
  //    // act
  //    materialService.updateMaterial(material, quantity, remainingLength, remainingWeight);
  //
  //    // assert
  //    Material updatedMaterial = materialRepository.findById(material.getId()).orElse(null);
  //    assertThat(updatedMaterial).isNotNull();
  //    assertThat(updatedMaterial.getRemainingLength()).isEqualTo(remainingLength);
  //    assertThat(updatedMaterial.getRemainingWeight()).isEqualTo(remainingWeight);
  //    assertThat(updatedMaterial.getUpdatedAt()).isAfterOrEqualTo(material.getUpdatedAt());
  //  }

  //  @Test
  //  void updateMaterial_WithNullRemainingMaterial_DoesNotUpdateMaterial() {
  //    // arrange
  //    Material material = new Material();
  //    material.setRemainingLength(10.0);
  //    material.setRemainingWeight(20.0);
  //    material.setUpdatedAt(LocalDate.now());
  //    materialRepository.save(material);
  //
  //    Integer quantity = 5;
  //    Double remainingLength = 7.5;
  //    Double remainingWeight = 15.0;
  //
  //    // act
  //    materialService.updateMaterial(null, quantity, remainingLength, remainingWeight);
  //
  //    // assert
  //    Material updatedMaterial = materialRepository.findById(material.getId()).orElse(null);
  //    assertThat(updatedMaterial).isNotNull();
  //    assertThat(updatedMaterial.getRemainingLength()).isEqualTo(material.getRemainingLength());
  //    assertThat(updatedMaterial.getRemainingWeight()).isEqualTo(material.getRemainingWeight());
  //    assertThat(updatedMaterial.getUpdatedAt()).isEqualTo(material.getUpdatedAt());
  //  }

  //  @Test
  //  void assignMaterialToProduct_ValidInputs_ReturnsMaterial() throws Exception {
  //    // arrange
  //    Product product = new Product();
  //    product.setStatus(NEW);
  //    productRepository.save(product);
  //
  //    Material material = new Material();
  //    material.setOriginalLength(100.0);
  //    material.setRemainingLength(100.0);
  //    material.setUnitWeight(2.0);
  //    materialRepository.save(material);
  //
  //    // act
  //    Material assignedMaterial = materialService.assignMaterialToProduct(product.getId(), material.getId());
  //
  //    // assert
  //    assertThat(assignedMaterial).isNotNull();
  //    assertThat(assignedMaterial.getRemainingLength()).isEqualTo(0.0);
  //    assertThat(assignedMaterial.getRemainingWeight()).isEqualTo(assignedMaterial.getOriginalLength() *
  //    assignedMaterial.getUnitWeight());
  //    assertThat(product.getMaterialProducts()).equals(assignedMaterial);
  //    assertThat(product.getStatus()).isEqualTo(READY);
  //  }

  @Test
  void assignMaterialToProduct_InvalidProductId_ThrowsIdNotFoundException() {
    Integer invalidProductId = -1;
    Integer materialId = 1;

    assertThatThrownBy(() -> materialService.assignMaterialToProduct(invalidProductId, materialId))
            .isInstanceOf(IdNotFoundException.class);
  }

  //  @Test
  //  void assignMaterialToProduct_ProductAlreadyHasMaterials_ThrowsAlreadyProducedException() throws Exception {
  //    // arrange
  //    Product product = new Product();
  //    product.setStatus(READY);
  //    productRepository.save(product);
  //
  //    Material material = new Material();
  //    material.setOriginalLength(100.0);
  //    material.setRemainingLength(100.0);
  //    material.setUnitWeight(2.0);
  //    materialRepository.save(material);
  //
  //    // act & assert
  //    assertThatThrownBy(() -> materialService.assignMaterialToProduct(product.getId(), material.getId()))
  //            .isInstanceOf(AlreadyProducedException.class);
  //  }

  //  @Test
  //  void assignMaterialToProduct_NotEnoughMaterial_ThrowsNotEnoughMaterialException() throws Exception {
  //    // arrange
  //    Product product = new Product();
  //    product.setStatus(NEW);
  //    productRepository.save(product);
  //
  //    Material material = new Material();
  //    material.setOriginalLength(100.0);
  //    material.setRemainingLength(0.0);
  //    material.setUnitWeight(2.0);
  //    materialRepository.save(material);
  //
  //    // act & assert
  //    assertThatThrownBy(() -> materialService.assignMaterialToProduct(product.getId(), material.getId()))
  //            .isInstanceOf(NotEnoughMaterialException.class);
  //  }

  //  @Test
  //  void assignMaterialToProduct_QualityMismatch_ThrowsQualityDifferenceException() throws Exception {
  //    // arrange
  //    Product product = new Product();
  //    product.setStatus(NEW);
  //    productRepository.save(product);
  //
  //    Material material = new Material();
  //    material.setOriginalLength(100.0);
  //    material.setRemainingLength(100.0);
  //    material.setUnitWeight(2.0);
  //    material.setQuality("high");
  //    materialRepository.save(material);
  //
  //    product.setQuality("low");
  //    productRepository.save(product);
  //
  //    // act & assert
  //    assertThatThrownBy(() -> materialService.assignMaterialToProduct(product.getId(), material.getId()))
  //            .isInstanceOf(QualityDifferenceException.class);
  //  }

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

  //  @Test
  //  void requestValidation_NotEnoughMaterial_ThrowsNotEnoughMaterialException() {
  //    // arrange
  //    Product product = new Product();
  //    product.setQuantity(10);
  //    product.setLength(10.0);
  //    Material material = new Material();
  //    material.setRemainingLength(19.0);
  //
  //    // act & assert
  //    assertThrows(NotEnoughMaterialException.class, () -> materialService.requestValidation(product, material));
  //  }

  //  @Test
  //  void requestValidation_ValidInput_ReturnsAssignedLength() {
  //    // arrange
  //    Product product = new Product();
  //    product.setQuantity(10);
  //    product.setLength(10.0);
  //    product.setQuality("high");
  //    Material material = new Material();
  //    material.setRemainingLength(20.0);
  //    material.setQuality("high");
  //
  //    // act
  //    Double assignedLength = materialService.requestValidation(product, material);
  //
  //    // assert
  //    assertThat(assignedLength).isEqualTo(0.101);
  //  }

  //  @Test
  //  void addRemainingMaterial_ReturnsNewMaterialWithCorrectProperties() {
  //    // arrange
  //    Material material = new Material();
  //    material.setId(1);
  //    material.setOriginalLength(100.0);
  //    material.setOriginalWeight(10.0);
  //    material.setRemainingLength(50.0);
  //    material.setRemainingWeight(5.0);
  //    Double assignedLength = 20.0;
  //    Double assignedWeight = 2.0;
  //
  //    // act
  //    Material newMaterial = materialService.addRemainingMaterial(material, assignedLength, assignedWeight);
  //
  //    // assert
  //    assertThat(newMaterial).isNotNull();
  //    assertThat(newMaterial.getId()).isEqualTo(material.getId());
  //    assertThat(newMaterial.getOriginalLength()).isEqualTo(material.getOriginalLength());
  //    assertThat(newMaterial.getOriginalWeight()).isEqualTo(material.getOriginalWeight());
  //    assertThat(newMaterial.getRemainingLength()).isEqualTo(material.getRemainingLength() - assignedLength);
  //    assertThat(newMaterial.getRemainingWeight()).isEqualTo(material.getRemainingWeight() - assignedWeight);
  //  }

  //  @Test
  //  void addRemainingMaterial_SavesNewMaterialInRepository() {
  //    // arrange
  //    Material material = new Material();
  //    material.setId(1);
  //    material.setOriginalLength(100.0);
  //    material.setOriginalWeight(10.0);
  //    material.setRemainingLength(50.0);
  //    material.setRemainingWeight(5.0);
  //    Double assignedLength = 20.0;
  //    Double assignedWeight = 2.0;
  //
  //    // act
  //    materialService.addRemainingMaterial(material, assignedLength, assignedWeight);
  //
  //    // assert
  //    verify(materialRepository, times(1)).save(any(Material.class));
  //  }

  //  @Test
  //  void buildMaterial_ShouldCreateNewMaterialWithCorrectProperties() throws IdNotFoundException {
  //    // Arrange
  //    Material originalMaterial = new Material();
  //    originalMaterial.setQuality("Test Quality");
  //    originalMaterial.setSize(10.0);
  //    originalMaterial.setHitNumber(5L);
  //    originalMaterial.setUnitPrice((long) 100.0);
  //    originalMaterial.setUnitWeight(0.5);
  //    originalMaterial.setUnitLength(2.0);
  //
  //    Double deltaLength = 5.0;
  //    Double deltaWeight = deltaLength * originalMaterial.getUnitWeight();
  //
  //    // Act
  //    Material newMaterial = materialService.buildMaterial(originalMaterial, deltaLength, deltaWeight);
  //
  //    // Assert
  //    assertThat(newMaterial).isNotNull();
  //    assertThat(newMaterial.getQuality()).isEqualTo(originalMaterial.getQuality());
  //    assertThat(newMaterial.getSize()).isEqualTo(originalMaterial.getSize());
  //    assertThat(newMaterial.getHitNumber()).isEqualTo(originalMaterial.getHitNumber());
  //    assertThat(newMaterial.getUnitPrice()).isEqualTo(originalMaterial.getUnitPrice());
  //    assertThat(newMaterial.getUnitWeight()).isEqualTo(originalMaterial.getUnitWeight());
  //    assertThat(newMaterial.getUnitLength()).isEqualTo(originalMaterial.getUnitLength());
  //    assertThat(newMaterial.getOriginalLength()).isEqualTo(deltaLength);
  //    assertThat(newMaterial.getOriginalWeight()).isEqualTo(deltaWeight);
  //    assertThat(newMaterial.getRemainingLength()).isEqualTo(deltaLength);
  //    assertThat(newMaterial.getRemainingWeight()).isEqualTo(deltaWeight);
  //    assertThat(newMaterial.getWarehouse().getId()).isEqualTo(2);
  //    assertThat(newMaterial.getUpdatedAt()).isEqualTo(LocalDate.now());
  //  }

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
