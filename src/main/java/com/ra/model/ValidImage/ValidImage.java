package com.ra.model.ValidImage;

import javax.validation.Payload;

public @interface ValidImage {
    String message() default "Invalid image file";
    String[] type();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
