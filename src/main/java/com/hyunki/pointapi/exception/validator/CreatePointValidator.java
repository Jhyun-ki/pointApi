package com.hyunki.pointapi.exception.validator;

import com.hyunki.pointapi.domain.dto.CreatePointRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

@Component
public class CreatePointValidator {

    public void validate(CreatePointRequest createPointRequest, Errors errors) {
        if(!StringUtils.hasText(createPointRequest.getUsername())) {
            errors.rejectValue("username", "USERNAME_FIELD_NOT_BLANK", "username 필드는 빈 값일 수 없습니다.");
        }

        if(createPointRequest.getPointAmt() <= 0) {
            errors.rejectValue("pointAmt", "POINT_AMT_FIELD_NOT_BELOW_ZERO", "pointAmt 필드는 0 포인트 이하일 수 없습니다.");
        }
    }
}
