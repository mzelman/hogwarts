package com.mz.hogwarts.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointUpdater {
    @Pattern(regexp = "\\d+", message = "Points must be numeral value between 1 and 200.")
    @Min(value = 1, message = "Points must be numeral value between 1 and 200.")
    @Max(value = 200, message = "Points must be numeral value between 1 and 200.")
    private String number;
}
