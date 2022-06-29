package rs.ac.bg.fon.nprog.commonlibrary.validation;

import domain.Repair;
import domain.RepairItem;
import domain.validation.RepairItemValidator;
import domain.validation.RepairValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Dragon
 */
public class Validator {

    private final List<String> validationErrors;

    private static final String ALPHABETS_REGEX = "[a-zA-Z]+";

    private Validator() {
        validationErrors = new ArrayList();
    }

    public static Validator startValidation() {
        return new Validator();
    }

    public Validator validateNotNullOrEmpty(String value, String errorMessage) {
        if (value == null || value.trim().isEmpty()) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateNotNull(Object value, String errorMessage) {
        if (value == null) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateValueIsNonNegativeNumber(String value, String errorMessage) {
        if (value == null) {
            this.validationErrors.add(errorMessage);
        } else {
            try {
                BigDecimal number = new BigDecimal(value);
                if (number.compareTo(new BigDecimal(0)) == -1) {
                    this.validationErrors.add(errorMessage);
                }
            } catch (NumberFormatException e) {
                this.validationErrors.add(errorMessage);
            }
        }
        return this;
    }

    public Validator validateValueIsNonNegativeInteger(String value, String errorMessage) {
        if (value == null || value.isEmpty()) {
            this.validationErrors.add(errorMessage);
        } else {
            try {
                int number = Integer.parseInt(value);
                if (number < 0) {
                    this.validationErrors.add(errorMessage);
                }
            } catch (NumberFormatException e) {
                this.validationErrors.add(errorMessage);
            }
        }
        return this;
    }

    public Validator validateValueIsDate(String value, String pattern, String errorMessage) {
        try {
            if (value != null) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
                LocalDate.parse(value, dtf);
            } else {
                this.validationErrors.add(errorMessage);
            }
        } catch (DateTimeParseException ex) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateIsStartDateBeforeEndDate(String startDate, String endDate, String pattern, String errorMessage) {
        try {
            if (startDate != null && endDate != null) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);

                LocalDate sd = LocalDate.parse(startDate, dtf);
                LocalDate ed = LocalDate.parse(endDate, dtf);

                if (!sd.isBefore(ed)) {
                    this.validationErrors.add(errorMessage);
                }

            } else {
                this.validationErrors.add(errorMessage);
            }
        } catch (DateTimeParseException ex) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateIsStartDateBeforeEndDate(LocalDate startDate, LocalDate endDate, String errorMessage) {
        if (startDate == null || endDate == null || !startDate.isBefore(endDate)) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateValueIsAllAlphabets(String value, String errorMessage) {
        if (value == null || value.isEmpty() || !value.matches(ALPHABETS_REGEX)) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateListIsNotEmpty(List list, String errorMessage) {
        if (list == null || list.isEmpty()) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateNumberIsNonNegative(Number number, String errorMessage) {
        if (number == null || number.doubleValue() < 0) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateRepairTotalRevenue(Repair repair, String errorMessage) {
        RepairValidator repairValidator = new RepairValidator();

        try {
            repairValidator.validateTotalRevenue(repair);
        } catch (ValidationException ex) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateRepairTotalExpense(Repair repair, String errorMessage) {
        RepairValidator repairValidator = new RepairValidator();

        try {
            repairValidator.validateTotalExpense(repair);
        } catch (ValidationException ex) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateRepairItemEmployeeExpense(RepairItem repairItem, String errorMessage) {
        RepairItemValidator repairItemValidator = new RepairItemValidator();

        try {
            repairItemValidator.validateEmployeeExpense(repairItem);
        } catch (ValidationException ex) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateValueIsValidPortNumber(String value, String errorMessage) {
        if (value == null || value.isEmpty()) {
            this.validationErrors.add(errorMessage);
        } else {
            try {
                int number = Integer.parseInt(value);
                if (number < 1024 || number > 65535) {
                    this.validationErrors.add(errorMessage);
                }
            } catch (NumberFormatException e) {
                this.validationErrors.add(errorMessage);
            }
        }
        return this;
    }

    public Validator throwIfInvalideParameterInstance(Object value, String errorMessage, Class<?> cls) throws ValidationException {
        if (!cls.isInstance(value)) {
            throw new ValidationException(errorMessage);
        }
        return this;
    }

    public void throwIfInvalide() throws ValidationException {
        if (!validationErrors.isEmpty()) {
            throw new ValidationException(this.validationErrors.stream().collect(Collectors.joining("\n")));
        }
    }
}
