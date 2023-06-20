package com.greenfoxacademy.springwebapp.config.services;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.config.models.Package;
import com.greenfoxacademy.springwebapp.config.models.Product;
import com.greenfoxacademy.springwebapp.config.models.ProductDTO;
import com.greenfoxacademy.springwebapp.config.models.ProductsDTO;
import com.greenfoxacademy.springwebapp.config.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
    List<Product> products = productRepository.findAll();
    return convert(products);
  }

  @Override
  public ProductDTO findById(Integer id) throws NoSuchElementException {
    return convert(findProductById(id));
  }

  private Product findProductById(Integer id) throws IdNotFoundException {
    return productRepository.findById(id).orElseThrow(IdNotFoundException::new);
  }

  @Override
  public ProductDTO save(ProductDTO productDTO) {
    Product product = Product.builder()
            .code(productDTO.getCode())
            .productVersionFrom(productDTO.getProductVersionFrom())
            .productVersionTo(productDTO.getProductVersionTo())
            .build();
    productRepository.save(product);
    return convert(product);
  }

  @Override
  public ProductDTO updateProduct(Integer id, ProductDTO productDTO) throws NoSuchElementException {
    Product product = findProductById(id);
    product.setCode(productDTO.getCode());
    product.setProductVersionFrom(productDTO.getProductVersionFrom());
    product.setProductVersionTo(productDTO.getProductVersionTo());
    productRepository.save(product);
    return convert(product);
  }

  @Override
  public void deleteProduct(Integer id) throws NoSuchElementException {
    try {
      productRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException();
    }
  }

  private ProductDTO convert(Product product) {
    ProductDTO productDTO = ProductDTO.builder()
            .id(product.getId())
            .code(product.getCode())
            .productVersionFrom(product.getProductVersionFrom())
            .productVersionTo(product.getProductVersionTo())
            .build();

    if (product.getPackages() != null && !product.getPackages().isEmpty()) {
      Set<Package> packages = product.getPackages(); // Csak az első csomagot másoljuk át (az igényeid szerint módosítható)
      productDTO.setPackages(packages);
    }

    return productDTO;
  }

  private Product convert(ProductDTO productDTO) {
    Product product = Product.builder()
            .code(productDTO.getCode())
            .productVersionFrom(productDTO.getProductVersionFrom())
            .productVersionTo(productDTO.getProductVersionTo())
            .build();
    return product;
  }

  private ProductsDTO convert(List<Product> products) {
    List<ProductDTO> productDTOs = products.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    return new ProductsDTO(productDTOs);
  }

}
