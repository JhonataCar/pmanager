package pmanager.infrastucture.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SaveProjectDataDTO {

    @NotNull(message = "Name cannot be empty")
    @Size(min = 1, max = 80)
    private final String name;

    @NotNull(message = "Description cannot be empty")
    private final String description;

    @NotNull(message = "initial Date cannot be empty")
    private final LocalDate initialDate;

    @NotNull(message = "Final Date cannot be empty")
    private final LocalDate finalData;
    private final String status;

    private final Set<String> memberIds;

    @AssertTrue(message = "Dates are not consistent")
    @SuppressWarnings("unused")
    private boolean isInitialDateBeforeFinalDate(){
        return initialDate.isBefore(finalData);
    }
}
