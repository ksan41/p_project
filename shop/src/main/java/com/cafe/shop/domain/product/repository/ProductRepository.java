package com.cafe.shop.domain.product.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cafe.shop.domain.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    public Optional<Product> findByProductIdAndManagerPhone(Long productId, String managerId);

    public List<Product> findByManagerPhone(String managerId);

    @Query("select p from Product p "
            + "where p.manager.phone = :managerId and " 
            + "p.productInfo.productName like concat('%', :keyword, '%')")
    public List<Product> searchProductsByKeyword(@Param("managerId") String managerId, @Param("keyword") String keyword, Pageable pageInfo);

}
