package com.payhere.shop.domain.product.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.payhere.shop.domain.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    public Optional<Product> findByProductIdAndManagerPhone(Long productId, String managerId);

    @Query("select p from Product p "
            + "where p.manager.phone = :managerId and " 
            + "p.productName like concat('%', :keyword, '%')")
    List<Product> searchProductsByKeyword(@Param("managerId") String managerId, @Param("keyword") String keyword, Pageable pageInfo);

    // @Query("SELECT p FROM product p "
    //         + "WHERE p.managerId = :managerId AND " 
    //         + "FUNCTION('REGEXP', p.productName , :regex = true)")
    // Page<Product> searchProductsByCho(@Param("managerId") String managerId, @Param("regex") String regex, Pageable pageable);
}
