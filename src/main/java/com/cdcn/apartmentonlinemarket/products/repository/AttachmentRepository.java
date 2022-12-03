package com.cdcn.apartmentonlinemarket.products.repository;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Attachments;

@Repository
public interface AttachmentRepository extends IJpaRepository<Attachments, UUID>{

}
