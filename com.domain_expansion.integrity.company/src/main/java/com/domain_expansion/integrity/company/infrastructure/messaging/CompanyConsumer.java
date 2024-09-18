package com.domain_expansion.integrity.company.infrastructure.messaging;

import com.domain_expansion.integrity.company.common.Serializer.EventSerializer;
import com.domain_expansion.integrity.company.common.exception.CompanyException;
import com.domain_expansion.integrity.company.common.message.ExceptionMessage;
import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.domain.repository.CompanyRepository;
import com.domain_expansion.integrity.company.events.HubDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompanyConsumer {

    private final CompanyRepository companyRepository;

    @Transactional
    @KafkaListener(topics = "hub-deleted-topic",groupId = "company-service-group")
    public void handleHubDeletedEvent(String message){
        HubDeleteEvent event = EventSerializer.deserialize(message, HubDeleteEvent.class);

        Company company = companyRepository.findByHubIdAndIsDeleteFalse(event.hubId()).orElseThrow(
                () -> new CompanyException(ExceptionMessage.NOT_FOUND_COMPANY_ID)
        );

        company.disconnectHub();

        log.info(company.getCompanyId());
    }
}
