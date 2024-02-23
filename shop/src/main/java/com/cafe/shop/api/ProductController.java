package com.cafe.shop.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.shop.domain.product.Product;
import com.cafe.shop.domain.product.dto.ProductInfoDto;
import com.cafe.shop.domain.product.dto.ProductManageDto;
import com.cafe.shop.domain.product.service.ProductManageService;
import com.cafe.shop.domain.product.service.ProductValidator;
import com.cafe.shop.util.response.MetaData;
import com.cafe.shop.util.response.ResponseMsg;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("product")
@RestController
public class ProductController {

    private final ProductManageService productManageService;
    private final ProductValidator productValidator;

    @PostMapping(value = "add")
    public ResponseMsg<Product> addProduct(@RequestBody ProductManageDto addData, HttpServletRequest request) {
        ResponseMsg<Product> msg = new ResponseMsg<>();

        String validationRes = productValidator.validateProductData(addData);
        if (!validationRes.isBlank()) {
            msg.setMeta(MetaData.PRODUCT_PARATETER_WRONG);
        } else {
            MetaData meta = productManageService.addProduct(addData, request);
            msg.setMeta(meta);
        }

        return msg;
    }

    @GetMapping(value = "get/{productId}")
    public ResponseMsg<ProductInfoDto> getProduct(@PathVariable("productId") Long productId,
            HttpServletRequest request) {
        ResponseMsg<ProductInfoDto> msg = new ResponseMsg<>();
        try {
            Product product = productManageService.getProduct(request, productId).orElseThrow(
                    () -> new Exception("Product : Database Not Found"));
            msg.setMeta(MetaData.SUCCESS);
            msg.setData(product.toDto());
        } catch (Exception e) {
            log.error("상품 조회 실패", e);
            msg.setMeta(MetaData.PRODUCT_NOT_FOUND);
        }
        return msg;
    }

    @GetMapping(value = "search")
    public ResponseMsg<List<ProductInfoDto>> searchProducts(
            @RequestParam(value = "keyword", required = false) String keyword, @RequestParam("page") int page,
            HttpServletRequest request) {
        ResponseMsg<List<ProductInfoDto>> msg = new ResponseMsg<>();
        List<ProductInfoDto> list = productManageService.searchProducts(keyword, page, request);
        msg.setMeta(MetaData.SUCCESS);
        msg.setData(list);
        return msg;
    }

    @PutMapping(value = "mod/{productId}")
    public ResponseMsg<Product> modifyProduct(@PathVariable("productId") Long productId,
            @RequestBody ProductManageDto productData,
            HttpServletRequest request) {
        ResponseMsg<Product> msg = new ResponseMsg<>();
        String validationRes = productValidator.validateProductData(productData);

        if (!validationRes.isBlank()) {
            msg.setMeta(MetaData.PRODUCT_PARATETER_WRONG);
        } else {
            MetaData meta = productManageService.modifyProduct(productId, productData, request);
            msg.setMeta(meta);
        }

        return msg;
    }

    @DeleteMapping(value = "remove/{productId}")
    public ResponseMsg<Product> deleteProduct(@PathVariable("productId") Long productId, HttpServletRequest request) {
        ResponseMsg<Product> msg = new ResponseMsg<>();
        MetaData meta = productManageService.deleteProduct(request, productId);
        msg.setMeta(meta);
        return msg;
    }
}
