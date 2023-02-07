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

        if(createPointRequest.getPointAmt() < 10) {
            errors.rejectValue("pointAmt", "POINT_AMT_FIELD_NOT_BELOW_TEN", "pointAmt 필드는 10 포인트 미만일 수 없습니다.");
        }
    }
}
