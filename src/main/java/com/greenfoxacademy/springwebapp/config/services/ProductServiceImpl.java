package com.greenfoxacademy.springwebapp.config.services;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.config.models.HProduct;
import com.greenfoxacademy.springwebapp.config.models.HPackage;
import com.greenfoxacademy.springwebapp.config.models.ProductDTO;
import com.greenfoxacademy.springwebapp.config.models.ProductsDTO;
import com.greenfoxacademy.springwebapp.config.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public ProductsDTO findAll() {
    List<HProduct> HProducts = productRepository.findAll();
    return convert(HProducts);
  }

  @Override
  public ProductDTO findById(Integer id) throws NoSuchElementException {
    return convert(findProductById(id));
  }

  private HProduct findProductById(Integer id) throws IdNotFoundException {
    return productRepository.findById(id).orElseThrow(IdNotFoundException::new);
  }

  @Override
  public ProductDTO save(ProductDTO productDTO) {
    HProduct product = HProduct.builder()
            .code(productDTO.getCode())
            .productVersionFrom(productDTO.getProductVersionFrom())
            .productVersionTo(productDTO.getProductVersionTo())
            .build();
    productRepository.save(product);
    return convert(product);
  }

  @Override
  public ProductDTO updateProduct(Integer id, ProductDTO productDTO) throws NoSuchElementException {
    HProduct HProduct = findProductById(id);
    HProduct.setCode(productDTO.getCode());
    HProduct.setProductVersionFrom(productDTO.getProductVersionFrom());
    HProduct.setProductVersionTo(productDTO.getProductVersionTo());
    productRepository.save(HProduct);
    return convert(HProduct);
  }

  @Override
  public void deleteProduct(Integer id) throws IdNotFoundException {
    try {
      productRepository.deleteById(id);
    } catch (RuntimeException e) {
      throw new IdNotFoundException();
    }
  }

  private ProductDTO convert(HProduct HProduct) {
    ProductDTO productDTO = ProductDTO.builder()
            .id(HProduct.getId())
            .code(HProduct.getCode())
            .productVersionFrom(HProduct.getProductVersionFrom())
            .productVersionTo(HProduct.getProductVersionTo())
            .build();

    if (HProduct.getHPackages() != null && !HProduct.getHPackages().isEmpty()) {
      Set<HPackage> HPackages = HProduct.getHPackages();
      productDTO.setHPackages(HPackages);
    }

    return productDTO;
  }

  private HProduct convert(ProductDTO productDTO) {
    HProduct product = HProduct.builder()
            .code(productDTO.getCode())
            .productVersionFrom(productDTO.getProductVersionFrom())
            .productVersionTo(productDTO.getProductVersionTo())
            .build();
    return product;
  }

  private ProductsDTO convert(List<HProduct> HProducts) {
    List<ProductDTO> productDTOS = HProducts.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    return new ProductsDTO(productDTOS);
  }

}
