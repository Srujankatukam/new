package com.nokia.edp.serialno.repository;

import com.nokia.edp.serialno.entity.EdpSerialNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for EDP_SERIALNO table operations
 */
@Repository
public interface EdpSerialNoRepository extends JpaRepository<EdpSerialNo, Long> {

    /**
     * Find a record by serial number
     */
    Optional<EdpSerialNo> findBySerialNum(String serialNum);

    /**
     * Check if a serial number exists
     */
    boolean existsBySerialNum(String serialNum);

    /**
     * Get all distinct serial numbers from the database
     */
    @Query("SELECT DISTINCT e.serialNum FROM EdpSerialNo e")
    List<String> findAllDistinctSerialNums();

    /**
     * Delete records by serial number
     */
    void deleteBySerialNum(String serialNum);
}
