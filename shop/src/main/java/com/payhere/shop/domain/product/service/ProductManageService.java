package com.payhere.shop.domain.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payhere.shop.domain.manager.Manager;
import com.payhere.shop.domain.manager.service.ManagerService;
import com.payhere.shop.domain.product.Product;
import com.payhere.shop.domain.product.dto.ProductInfoDto;
import com.payhere.shop.domain.product.dto.ProductManageDto;
import com.payhere.shop.domain.product.repository.ProductRepository;
import com.payhere.shop.util.BarcodeCreator;
import com.payhere.shop.util.exception.NotAllowedManagerException;
import com.payhere.shop.util.exception.ProductNotFountException;
import com.payhere.shop.util.response.MetaData;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductManageService {

    private final ProductRepository productRepository;
    private final ManagerService managerService;

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

    // 상품 조회(검색)
    public List<ProductInfoDto> searchProducts(String keyword, int page, HttpServletRequest request) {
        List<ProductInfoDto> list = new ArrayList<>();
        try {
            Manager foundManager = managerService.getManagerByHeaderToken(request);
            Pageable pageInfo = getPageInfo(page);
            if (!keyword.isBlank()) {
                if (!isChoStr(keyword)) {
                    list = productRepository.searchProductsByKeyword(foundManager.getPhone(), keyword, pageInfo).stream()
                            .map(e -> e.toDto())
                            .toList();
                } else {
    
                }
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
        return PageRequest.of(page, 10, Sort.by("productName").descending());
    }

    private final char[] CHOSUNG = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ',
            'ㅌ', 'ㅍ', 'ㅎ' };

    private boolean isChoStr(String keyword) {
        for (char c : keyword.toCharArray()) {
            if (!Pattern.matches("[ㄱ-ㅎ]", Character.toString(c))) {
                return false;
            }
        }
        return true;
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
