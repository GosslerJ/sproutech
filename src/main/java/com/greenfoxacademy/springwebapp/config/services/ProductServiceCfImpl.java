package com.greenfoxacademy.springwebapp.config.services;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.config.models.HProductCf;
import com.greenfoxacademy.springwebapp.config.models.HPackageCf;
import com.greenfoxacademy.springwebapp.config.models.ProductCfDTO;
import com.greenfoxacademy.springwebapp.config.models.ProductsCfDTO;
import com.greenfoxacademy.springwebapp.config.repositories.ProductRepositoryCf;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceCfImpl implements ProductServiceCf {

  private final ProductRepositoryCf productRepositoryCf;

  @Override
  public ProductsCfDTO findAll() {
    List<HProductCf> HProductCfs = productRepositoryCf.findAll();
    return convert(HProductCfs);
  }

  @Override
  public ProductCfDTO findById(Integer id) throws NoSuchElementException {
    return convert(findProductById(id));
  }

  private HProductCf findProductById(Integer id) throws IdNotFoundException {
    return productRepositoryCf.findById(id).orElseThrow(IdNotFoundException::new);
  }

  @Override
  public ProductCfDTO save(ProductCfDTO productCfDTO) {
    HProductCf product = HProductCf.builder()
            .code(productCfDTO.getCode())
            .productVersionFrom(productCfDTO.getProductVersionFrom())
            .productVersionTo(productCfDTO.getProductVersionTo())
            .build();
    productRepositoryCf.save(product);
    return convert(product);
  }

  @Override
  public ProductCfDTO updateProduct(Integer id, ProductCfDTO productCfDTO) throws NoSuchElementException {
    HProductCf HProductCf = findProductById(id);
    HProductCf.setCode(productCfDTO.getCode());
    HProductCf.setProductVersionFrom(productCfDTO.getProductVersionFrom());
    HProductCf.setProductVersionTo(productCfDTO.getProductVersionTo());
    productRepositoryCf.save(HProductCf);
    return convert(HProductCf);
  }

  @Override
  public void deleteProduct(Integer id) throws IdNotFoundException {
    try {
      productRepositoryCf.deleteById(id);
    } catch (RuntimeException e) {
      throw new IdNotFoundException();
    }
  }

  private ProductCfDTO convert(HProductCf HProductCf) {
    ProductCfDTO productCfDTO = ProductCfDTO.builder()
            .id(HProductCf.getId())
            .code(HProductCf.getCode())
            .productVersionFrom(HProductCf.getProductVersionFrom())
            .productVersionTo(HProductCf.getProductVersionTo())
            .build();

    if (HProductCf.getHPackageCfs() != null && !HProductCf.getHPackageCfs().isEmpty()) {
      Set<HPackageCf> HPackageCfs = HProductCf.getHPackageCfs(); // Csak az első csomagot másoljuk át (az igényeid szerint módosítható)
      productCfDTO.setHPackageCfs(HPackageCfs);
    }

    return productCfDTO;
  }

  private HProductCf convert(ProductCfDTO productCfDTO) {
    HProductCf product = HProductCf.builder()
            .code(productCfDTO.getCode())
            .productVersionFrom(productCfDTO.getProductVersionFrom())
            .productVersionTo(productCfDTO.getProductVersionTo())
            .build();
    return product;
  }

  private ProductsCfDTO convert(List<HProductCf> HProductCfs) {
    List<ProductCfDTO> productCfDTOS = HProductCfs.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    return new ProductsCfDTO(productCfDTOS);
  }

}
