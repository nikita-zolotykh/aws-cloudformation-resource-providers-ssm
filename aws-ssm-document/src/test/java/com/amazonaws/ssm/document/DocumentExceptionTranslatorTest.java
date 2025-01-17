package com.amazonaws.ssm.document;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.ssm.model.AutomationDefinitionNotFoundException;
import software.amazon.awssdk.services.ssm.model.DocumentAlreadyExistsException;
import software.amazon.awssdk.services.ssm.model.DocumentLimitExceededException;
import software.amazon.awssdk.services.ssm.model.DocumentVersionLimitExceededException;
import software.amazon.awssdk.services.ssm.model.InvalidDocumentContentException;
import software.amazon.awssdk.services.ssm.model.InvalidDocumentException;
import software.amazon.awssdk.services.ssm.model.InvalidDocumentVersionException;
import software.amazon.awssdk.services.ssm.model.MaxDocumentSizeExceededException;
import software.amazon.awssdk.services.ssm.model.SsmException;
import software.amazon.cloudformation.exceptions.CfnAlreadyExistsException;
import software.amazon.cloudformation.exceptions.CfnGeneralServiceException;
import software.amazon.cloudformation.exceptions.CfnInvalidRequestException;
import software.amazon.cloudformation.exceptions.CfnNotFoundException;
import software.amazon.cloudformation.exceptions.CfnServiceLimitExceededException;

@ExtendWith(MockitoExtension.class)
public class DocumentExceptionTranslatorTest {

    private static final String SAMPLE_DOCUMENT_NAME = "sampleDocument";
    private static final String SAMPLE_OPERATION_NAME = "sampleOperation";

    private final DocumentExceptionTranslator unitUnderTest = new DocumentExceptionTranslator();

    @Mock
    private SsmException ssmException;

    @Test
    public void testGetCfnException_verifyExceptionsReturned() {
        Assertions.assertTrue(unitUnderTest.getCfnException(InvalidDocumentException.builder().build(), SAMPLE_DOCUMENT_NAME, SAMPLE_OPERATION_NAME) instanceof CfnNotFoundException);

        Assertions.assertTrue(unitUnderTest.getCfnException(DocumentLimitExceededException.builder().build(), SAMPLE_DOCUMENT_NAME, SAMPLE_OPERATION_NAME) instanceof CfnServiceLimitExceededException);

        Assertions.assertTrue(unitUnderTest.getCfnException(DocumentVersionLimitExceededException.builder().build(), SAMPLE_DOCUMENT_NAME, SAMPLE_OPERATION_NAME) instanceof CfnServiceLimitExceededException);

        Assertions.assertTrue(unitUnderTest.getCfnException(DocumentAlreadyExistsException.builder().build(), SAMPLE_DOCUMENT_NAME, SAMPLE_OPERATION_NAME) instanceof CfnAlreadyExistsException);

        Assertions.assertTrue(unitUnderTest.getCfnException(MaxDocumentSizeExceededException.builder().build(), SAMPLE_DOCUMENT_NAME, SAMPLE_OPERATION_NAME) instanceof CfnInvalidRequestException);

        Assertions.assertTrue(unitUnderTest.getCfnException(InvalidDocumentContentException.builder().build(), SAMPLE_DOCUMENT_NAME, SAMPLE_OPERATION_NAME) instanceof CfnInvalidRequestException);

        Assertions.assertTrue(unitUnderTest.getCfnException(InvalidDocumentVersionException.builder().build(), SAMPLE_DOCUMENT_NAME, SAMPLE_OPERATION_NAME) instanceof CfnInvalidRequestException);

        Assertions.assertTrue(unitUnderTest.getCfnException(AutomationDefinitionNotFoundException.builder().build(), SAMPLE_DOCUMENT_NAME, SAMPLE_OPERATION_NAME) instanceof CfnInvalidRequestException);

        Assertions.assertTrue(unitUnderTest.getCfnException(ssmException, SAMPLE_DOCUMENT_NAME, SAMPLE_OPERATION_NAME) instanceof CfnGeneralServiceException);
    }
}
