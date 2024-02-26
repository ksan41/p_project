package com.cafe.shop.domain.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.shop.domain.manager.Manager;
import com.cafe.shop.domain.manager.service.ManagerService;
import com.cafe.shop.domain.product.Product;
import com.cafe.shop.domain.product.dto.ProductInfoDto;
import com.cafe.shop.domain.product.dto.ProductManageDto;
import com.cafe.shop.domain.product.repository.ProductRepository;
import com.cafe.shop.util.BarcodeCreator;
import com.cafe.shop.util.RegexManager;
import com.cafe.shop.util.exception.NotAllowedManagerException;
import com.cafe.shop.util.exception.ProductNotFountException;
import com.cafe.shop.util.response.MetaData;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductManageService {

    private final ProductRepository productRepository;
    private final ManagerService managerService;
    
    private final int SEARCH_MAX_SIZE = 10;
    private final String PAGE_SORT_BY = "productName";

    public MetaData addProduct(ProductManageDto addData, HttpServletRequest request) {
        try {
            Manager foundManager = managerService.getManagerByHeaderToken(request);

            String barcode = BarcodeCreator.generateBarcode();
            Product newProduct = addData.toEntity(barcode, foundManager);
            productRepository.save(newProduct);
        } catch (NotAllowedManagerException e) {
            log.error("상품을 등록할 수 없습니다.", e);
            return MetaData.NOT_ALLOWED_MANAGER;
        }

        return MetaData.SUCCESS;
    }

    @Transactional
    public MetaData modifyProduct(Long productId, ProductManageDto productData, HttpServletRequest request) {
        try {
            Manager foundManager = managerService.getManagerByHeaderToken(request);
            Product foundProduct = productRepository.findByProductIdAndManagerPhone(productId, foundManager.getPhone())
                    .orElseThrow(() -> new ProductNotFountException());

            foundProduct.modyfyProductData(productData);
        } catch (NotAllowedManagerException e) {
            log.error(e.toString());
            return MetaData.NOT_ALLOWED_MANAGER;
        } catch (ProductNotFountException e) {
            log.error(e.toString());
            return MetaData.PRODUCT_NOT_FOUND;
        }

        return MetaData.SUCCESS;
    }

    public Optional<Product> getProduct(HttpServletRequest request, Long productId) {
        Optional<Product> foundProduct = Optional.empty();
        try {
            Manager foundManager = managerService.getManagerByHeaderToken(request);
            foundProduct = productRepository.findByProductIdAndManagerPhone(productId, foundManager.getPhone());
        } catch (NotAllowedManagerException e) {
            log.error("상품 정보를 불러올 수 없습니다.", e);
        }

        return foundProduct;
    }

    public List<ProductInfoDto> searchProducts(String keyword, int page, HttpServletRequest request) {
        List<ProductInfoDto> list = new ArrayList<>();
        try {
            Manager foundManager = managerService.getManagerByHeaderToken(request);
            Pageable pageInfo = getPageInfo(page);
            if (!keyword.isBlank()) {
                String regex = RegexManager.makeKeywordToRegex(keyword);
                List<Product> allList = productRepository.findByManagerPhone(foundManager.getPhone());
                list = allList.stream()
                        .filter(i -> RegexManager.isKeywordMatchesRegex(i.getProductName(), regex))
                        .skip(page * SEARCH_MAX_SIZE)
                        .limit(SEARCH_MAX_SIZE)
                        .sorted()
                        .map(i -> i.toDto())
                        .toList();
            } else {
                Page<Product> pages = productRepository.findAll(pageInfo);
                if (!pages.getContent().isEmpty()) {
                    list = pages.getContent().stream()
                            .map(e -> e.toDto())
                            .toList();
                }
            }
        } catch (NotAllowedManagerException e) {
            log.error("상품 정보를 불러올 수 없습니다.", e);
        } catch (ProductNotFountException e) {
            log.error("상품 정보를 불러올 수 없습니다.", e);
        }
        return list;
    }

    private Pageable getPageInfo(int page) {
        return PageRequest.of(page, SEARCH_MAX_SIZE, Sort.by(PAGE_SORT_BY).ascending());
    }

    public MetaData deleteProduct(HttpServletRequest request, Long productId) {
        try {
            Manager foundManager = managerService.getManagerByHeaderToken(request);
            Product foundProduct = productRepository.findByProductIdAndManagerPhone(productId, foundManager.getPhone())
                    .orElseThrow(() -> new ProductNotFountException());
            productRepository.delete(foundProduct);
        } catch (NotAllowedManagerException e) {
            log.error("상품 정보를 불러올 수 없습니다.", e);
            return MetaData.NOT_ALLOWED_MANAGER;
        } catch (ProductNotFountException e) {
            log.error("상품 정보를 불러올 수 없습니다.", e);
            return MetaData.PRODUCT_NOT_FOUND;
        }
        return MetaData.SUCCESS;
    }
}
