package com.extensions.integrationtests.wrappers.email;

import com.extensions.integrationtests.dto.email.EmailDTOTest;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class EmailEmbeddedDTO implements Serializable {
    @JsonProperty("emailDTOList")
    private List<EmailDTOTest> emails;

    public EmailEmbeddedDTO() {
    }

    public List<EmailDTOTest> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailDTOTest> emails) {
        this.emails = emails;
    }
}
