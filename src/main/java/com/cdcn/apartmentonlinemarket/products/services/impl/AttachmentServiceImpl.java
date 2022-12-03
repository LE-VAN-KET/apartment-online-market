package com.cdcn.apartmentonlinemarket.products.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdcn.apartmentonlinemarket.products.domain.entity.Attachments;
import com.cdcn.apartmentonlinemarket.products.repository.AttachmentRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	
	@Autowired
	private AttachmentRepository attachmentRepository;

	@Override
	public List<Attachments> getListAttachment(List<UUID> ids) {
			List<Attachments> attachments = attachmentRepository.findAllById(ids);
		return attachments;
	}

}
