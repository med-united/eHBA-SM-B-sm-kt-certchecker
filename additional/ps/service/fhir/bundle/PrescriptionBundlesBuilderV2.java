package health.ere.ps.service.fhir.bundle;

import health.ere.ps.model.muster16.MedicationString;
import health.ere.ps.model.muster16.Muster16PrescriptionForm;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.r4.model.Bundle;

import java.util.*;

public class PrescriptionBundlesBuilderV2 implements IBundlesBuilder {
    private static final String NULL_VALUE_PLACE_HOLDER = "";
    private static final String NULL_DATE_VALUE_PLACE_HOLDER = "9999-12-28";
    private static final String DEFAULT_QUALIFICATION_TEXT_FOR_DOCTOR = "Arzt";

    protected static final String $PRESCRIPTION_ID = "$PRESCRIPTION_ID";
    protected static final String $BUNDLE_ID = "$BUNDLE_ID";
    protected static final String $LAST_UPDATED = "$LAST_UPDATED";
    protected static final String $TIMESTAMP = "$TIMESTAMP";
    protected static final String $COMPOSITION_ID = "$COMPOSITION_ID";
    protected static final String $COMPOSITION_DATE = "$COMPOSITION_DATE";
    protected static final String $DEVICE_ID = "$DEVICE_ID";
    protected static final String $MEDICATION_REQUEST_ID = "$MEDICATION_REQUEST_ID";
    protected static final String $STATUS_CO_PAYMENT = "$STATUS_CO_PAYMENT";
    protected static final String $AUTHORED_ON = "$AUTHORED_ON";
    protected static final String $MEDICATION_ID = "$MEDICATION_ID";
    protected static final String $DOSAGE_QUANTITY = "$DOSAGE_QUANTITY";
    protected static final String $PZN = "$PZN";
    protected static final String $MEDICATION_NAME = "$MEDICATION_NAME";
    protected static final String $MEDICATION_FORM = "$MEDICATION_FORM";
    protected static final String $MEDICATION_SIZE = "$MEDICATION_SIZE";
    protected static final String $DOSAGE_TEXT = "$DOSAGE_TEXT";
    protected static final String $KVID_10 = "$KVID_10";
    protected static final String $PATIENT_ID = "$PATIENT_ID";
    protected static final String $PATIENT_NAME_PREFIX = "$PATIENT_NAME_PREFIX";
    protected static final String $PATIENT_NAME_FIRST = "$PATIENT_NAME_FIRST";
    protected static final String $PATIENT_NAME_FAMILY = "$PATIENT_NAME_FAMILY";
    protected static final String $PATIENT_BIRTH_DATE = "$PATIENT_BIRTH_DATE";
    protected static final String $PATIENT_ADDRESS_STREET_NAME = "$PATIENT_ADDRESS_STREET_NAME";
    protected static final String $PATIENT_ADDRESS_STREET_NUMBER = "$PATIENT_ADDRESS_STREET_NUMBER";
    protected static final String $PATIENT_ADDRESS_POSTAL_CODE = "$PATIENT_ADDRESS_POSTAL_CODE";
    protected static final String $PATIENT_ADDRESS_CITY = "$PATIENT_ADDRESS_CITY";
    protected static final String $PATIENT_STATUS = "$PATIENT_STATUS";
    protected static final String $PRACTITIONER_ID = "$PRACTITIONER_ID";
    protected static final String $PRACTITIONER_NAME_PREFIX = "$PRACTITIONER_NAME_PREFIX";
    protected static final String $PRACTITIONER_NAME_FIRST = "$PRACTITIONER_NAME_FIRST";
    protected static final String $PRACTITIONER_NAME_FAMILY = "$PRACTITIONER_NAME_FAMILY";
    protected static final String $PRACTITIONER_ADDRESS_STREET_NAME = "$PRACTITIONER_ADDRESS_STREET_NAME";
    protected static final String $PRACTITIONER_ADDRESS_STREET_NUMBER = "$PRACTITIONER_ADDRESS_STREET_NUMBER";
    protected static final String $PRACTITIONER_ADDRESS_POSTAL_CODE = "$PRACTITIONER_ADDRESS_POSTAL_CODE";
    protected static final String $PRACTITIONER_ADDRESS_CITY = "$PRACTITIONER_ADDRESS_CITY";
    protected static final String $PRACTITIONER_PHONE = "$PRACTITIONER_PHONE";
    protected static final String $PRACTITIONER_FAX = "$PRACTITIONER_FAX";
    protected static final String $PRACTITIONER_QUALIFICATIONS = "$PRACTITIONER_QUALIFICATIONS";
    protected static final String $PRACTITIONER_NUMBER = "$PRACTITIONER_NUMBER";
    protected static final String $PRACTITIONER_QUALIFICATION_TEXT = "$PRACTITIONER_QUALIFICATION_TEXT";
    protected static final String $ORGANIZATION_ID = "$ORGANIZATION_ID";
    protected static final String $CLINIC_ID = "$CLINIC_ID";
    protected static final String $ORGANIZATION_NAME = "$ORGANIZATION_NAME";
    protected static final String $ORGANIZATION_PHONE = "$ORGANIZATION_PHONE";
    protected static final String $ORGANIZATION_ADDRESS_STREET_NAME = "$ORGANIZATION_ADDRESS_STREET_NAME";
    protected static final String $ORGANIZATION_ADDRESS_STREET_NUMBER = "$ORGANIZATION_ADDRESS_STREET_NUMBER";
    protected static final String $ORGANIZATION_ADDRESS_POSTAL_CODE = "$ORGANIZATION_ADDRESS_POSTAL_CODE";
    protected static final String $ORGANIZATION_ADDRESS_CITY = "$ORGANIZATION_ADDRESS_CITY";
    protected static final String $ORGANIZATION_FAX = "$ORGANIZATION_FAX";
    protected static final String $COVERAGE_ID = "$COVERAGE_ID";
    protected static final String $COVERAGE_PERIOD_END = "$COVERAGE_PERIOD_END";
    protected static final String $INSURANCE_NAME = "$INSURANCE_NAME";
    protected static final String $INSURANCE_NUMBER = "$INSURANCE_NUMBER";
    protected static final String PREFIX_TEMPLATE =
            ",\n" +
            "\"prefix\": [\n" +
            "  \"$PREFIX\"\n" +
            "],\n" +
            "\"_prefix\": [\n" +
            "  {\n" +
            "    \"extension\": [\n" +
            "       {\n" +
            "         \"url\": \"http://hl7.org/fhir/StructureDefinition/iso21090-EN-qualifier\",\n" +
            "         \"valueCode\": \"AC\"\n" +
            "       }\n" +
            "     ]\n" +
            "   }\n" +
            "]";
    protected static final String $PREFIX = "$PREFIX";
    protected static final String DOSAGE_TEXT_TEMPLATE = "\"dosageInstruction\": [{\n" +
            "\t\t\t\t\t\t\"extension\": [{\n" +
            "\t\t\t\t\t\t\t\"url\": \"https://fhir.kbv.de/StructureDefinition/KBV_EX_ERP_DosageFlag\",\n" +
            "\t\t\t\t\t\t\t\"valueBoolean\": true\n" +
            "\t\t\t\t\t\t}],\n" +
            "\t\t\t\t\t\t\"text\": \"$DOSAGE\"\n" +
            "\t\t\t\t\t}],";
    protected static final String $DOSAGE = "$DOSAGE";

    protected final Muster16PrescriptionForm muster16PrescriptionForm;
    protected Map<String, String> templateKeyMapper;


    public PrescriptionBundlesBuilderV2(Muster16PrescriptionForm muster16PrescriptionForm) {
        this.muster16PrescriptionForm = muster16PrescriptionForm;
    }

    @Override
    public List<Bundle> createBundles() {
        List<Bundle> bundles = new ArrayList<>();

        muster16PrescriptionForm.getPrescriptionList().forEach(medicationString ->
                bundles.add(createBundleForMedication(medicationString)));

        return bundles;
    }

    @Override
    public Bundle createBundleForMedication(MedicationString medication) {
        templateKeyMapper = new HashMap<>();

        updateBundleResourceSection();
        updateCompositionSection();
        updateMedicationRequestSection();
        updateMedicationResourceSection(medication);
        updatePatientResourceSection();
        updatePractitionerResourceSection();
        updateOrganizationResourceSection();
        updateCoverageResourceSection();

        EreBundle bundle = new EreBundle(templateKeyMapper);

        bundle.setTimestampOnField($LAST_UPDATED, new Date());
        bundle.setTimestampOnField($TIMESTAMP, new Date());
        bundle.setTimestampOnField($COMPOSITION_DATE, new Date());

        return bundle;
    }

    protected void updateBundleResourceSection() {
        templateKeyMapper.put($BUNDLE_ID, UUID.randomUUID().toString());
        templateKeyMapper.put($PRESCRIPTION_ID, UUID.randomUUID().toString());
        templateKeyMapper.put($LAST_UPDATED, StringUtils.defaultString(null));
        templateKeyMapper.put($TIMESTAMP, StringUtils.defaultString(null));
    }

    protected void updateCompositionSection() {
        templateKeyMapper.put($COMPOSITION_ID, UUID.randomUUID().toString());
        templateKeyMapper.put($COMPOSITION_DATE, StringUtils.defaultString(null));

        //TODO: This is hardcoded but updated values may need to be sourced dynamically over time.
        templateKeyMapper.put($DEVICE_ID, "Y/410/2107/36/999");
    }

    protected void updateMedicationRequestSection() {
        templateKeyMapper.put($MEDICATION_REQUEST_ID, UUID.randomUUID().toString());

        templateKeyMapper.put($STATUS_CO_PAYMENT,
                getProtectedValue(muster16PrescriptionForm.getWithPayment() != null ?
                        (muster16PrescriptionForm.getWithPayment() ? "0" : "1") : null));

        templateKeyMapper.put($AUTHORED_ON,
                getProtectedDateValue(muster16PrescriptionForm.getPrescriptionDate()) + "T00:00:00.000Z");
    }

    protected void updateMedicationResourceSection(MedicationString medicationString) {
        templateKeyMapper.put($MEDICATION_ID, UUID.randomUUID().toString());
        templateKeyMapper.put($DOSAGE_QUANTITY, getProtectedValue(medicationString.getDosage()));
        templateKeyMapper.put($PZN, getProtectedValue(medicationString.getPzn()));
        templateKeyMapper.put($MEDICATION_FORM, getProtectedValue(medicationString.getForm()));
        templateKeyMapper.put($MEDICATION_NAME, getProtectedValue(medicationString.getName()));
        templateKeyMapper.put($MEDICATION_SIZE, getProtectedValue(medicationString.getSize()));

        if (StringUtils.isEmpty(medicationString.getInstructions())) {
            templateKeyMapper.put($DOSAGE_TEXT, "");
        } else {
            templateKeyMapper.put($DOSAGE_TEXT, DOSAGE_TEXT_TEMPLATE
                    .replace($DOSAGE, medicationString.getInstructions()));
        }
    }

    protected void updatePatientResourceSection() {
        templateKeyMapper.put($PATIENT_ID, UUID.randomUUID().toString());
        templateKeyMapper.put($KVID_10,
                getProtectedValue(muster16PrescriptionForm.getPatientInsuranceId()));

        if (!muster16PrescriptionForm.getPatientNamePrefix().isEmpty()) {
            String prefixes = String.join(" ", muster16PrescriptionForm.getPatientNamePrefix());
            templateKeyMapper.put($PATIENT_NAME_PREFIX, PREFIX_TEMPLATE.replace($PREFIX, prefixes));
        } else {
            templateKeyMapper.put($PATIENT_NAME_PREFIX, "");
        }

        templateKeyMapper.put($PATIENT_NAME_FIRST,
                getProtectedValue(muster16PrescriptionForm.getPatientFirstName()));

        templateKeyMapper.put($PATIENT_NAME_FAMILY,
                getProtectedValue(muster16PrescriptionForm.getPatientLastName()));

        templateKeyMapper.put($PATIENT_BIRTH_DATE,
                getProtectedDateValue(muster16PrescriptionForm.getPatientDateOfBirth()));

        templateKeyMapper.put($PATIENT_ADDRESS_STREET_NAME,
                getProtectedValue(muster16PrescriptionForm.getPatientStreetName()));

        templateKeyMapper.put($PATIENT_ADDRESS_STREET_NUMBER,
                getProtectedValue(muster16PrescriptionForm.getPatientStreetNumber()));

        templateKeyMapper.put($PATIENT_ADDRESS_POSTAL_CODE,
                getProtectedValue(muster16PrescriptionForm.getPatientZipCode()));

        templateKeyMapper.put($PATIENT_ADDRESS_CITY,
                getProtectedValue(muster16PrescriptionForm.getPatientCity()));
    }

    protected void updatePractitionerResourceSection() {
        templateKeyMapper.put($PRACTITIONER_ID, UUID.randomUUID().toString());

        if (!muster16PrescriptionForm.getPractitionerNamePrefix().isEmpty()) {
            templateKeyMapper.put($PRACTITIONER_NAME_PREFIX, PREFIX_TEMPLATE.replace($PREFIX,
                    muster16PrescriptionForm.getPractitionerNamePrefix()));
        } else {
            templateKeyMapper.put($PRACTITIONER_NAME_PREFIX, "");
        }

        templateKeyMapper.put($PRACTITIONER_NAME_FIRST,
                getProtectedValue(muster16PrescriptionForm.getPractitionerFirstName()));

        templateKeyMapper.put($PRACTITIONER_NAME_FAMILY,
                getProtectedValue(muster16PrescriptionForm.getPractitionerLastName()));

        templateKeyMapper.put($PRACTITIONER_ADDRESS_STREET_NAME,
                getProtectedValue(muster16PrescriptionForm.getPractitionerStreetName()));

        templateKeyMapper.put($PRACTITIONER_ADDRESS_STREET_NUMBER,
                getProtectedValue(muster16PrescriptionForm.getPractitionerStreetNumber()));

        templateKeyMapper.put($PRACTITIONER_ADDRESS_POSTAL_CODE,
                getProtectedValue(muster16PrescriptionForm.getPractitionerZipCode()));

        templateKeyMapper.put($PRACTITIONER_ADDRESS_CITY,
                getProtectedValue(muster16PrescriptionForm.getPractitionerCity()));

        templateKeyMapper.put($PRACTITIONER_PHONE,
                getProtectedValue(muster16PrescriptionForm.getPractitionerPhone()));

        templateKeyMapper.put($PRACTITIONER_FAX,
                getProtectedValue(muster16PrescriptionForm.getPractitionerFax()));

        templateKeyMapper.put($PRACTITIONER_QUALIFICATIONS, getProtectedValue(null));

        templateKeyMapper.put($PRACTITIONER_NUMBER,
                getProtectedValue(muster16PrescriptionForm.getPractitionerId()));

        templateKeyMapper.put($PRACTITIONER_QUALIFICATION_TEXT, DEFAULT_QUALIFICATION_TEXT_FOR_DOCTOR);
    }

    protected void updateOrganizationResourceSection() {
        templateKeyMapper.put($ORGANIZATION_ID, UUID.randomUUID().toString());

        templateKeyMapper.put($CLINIC_ID,
                getProtectedValue(muster16PrescriptionForm.getClinicId()));

        templateKeyMapper.put($ORGANIZATION_NAME, getProtectedValue(null));

        templateKeyMapper.put($ORGANIZATION_PHONE,
                getProtectedValue(muster16PrescriptionForm.getPractitionerPhone()));

        templateKeyMapper.put($ORGANIZATION_ADDRESS_STREET_NAME,
                getProtectedValue(muster16PrescriptionForm.getPractitionerStreetName()));

        templateKeyMapper.put($ORGANIZATION_ADDRESS_STREET_NUMBER,
                getProtectedValue(muster16PrescriptionForm.getPractitionerStreetNumber()));

        templateKeyMapper.put($ORGANIZATION_ADDRESS_POSTAL_CODE,
                getProtectedValue(muster16PrescriptionForm.getPractitionerZipCode()));

        templateKeyMapper.put($ORGANIZATION_ADDRESS_CITY,
                getProtectedValue(muster16PrescriptionForm.getPractitionerCity()));

        templateKeyMapper.put($ORGANIZATION_FAX,
                getProtectedValue(muster16PrescriptionForm.getPractitionerFax()));
    }

    protected void updateCoverageResourceSection() {
        templateKeyMapper.put($COVERAGE_ID, UUID.randomUUID().toString());

        templateKeyMapper.put($COVERAGE_PERIOD_END,
                getProtectedDateValue(muster16PrescriptionForm.getPrescriptionDate()));

        templateKeyMapper.put($INSURANCE_NAME,
                getProtectedValue(muster16PrescriptionForm.getInsuranceCompany()));

        templateKeyMapper.put($PATIENT_STATUS,
                getProtectedValue(muster16PrescriptionForm.getPatientStatus()));

        templateKeyMapper.put($INSURANCE_NUMBER,
                getProtectedValue(muster16PrescriptionForm.getInsuranceCompanyId()));
    }

    protected String getProtectedValue(String value) {
        return StringUtils.defaultString(value, NULL_VALUE_PLACE_HOLDER);
    }

    protected String getProtectedDateValue(String date) {
        return isKbvFormattedDate(date) ? date : NULL_DATE_VALUE_PLACE_HOLDER;
    }

    protected boolean isKbvFormattedDate(String date) {
        return StringUtils.isNotBlank(date) && date.matches("\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d");
    }

    public Map<String, String> getTemplateKeyMapper() {
        return templateKeyMapper;
    }
}
