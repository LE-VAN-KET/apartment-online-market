package com.cdcn.apartmentonlinemarket.products.services;
import java.util.List;
import java.util.UUID;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Attachments;

public interface AttachmentService {
	List<Attachments> getListAttachment(List<UUID> ids);

}
