package com.hyunki.pointapi.exception.validator;

import com.hyunki.pointapi.domain.dto.PointRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

@Component
public class CreatePointValidator {

    public void validate(PointRequestDto pointRequestDto, Errors errors) {
        if(!StringUtils.hasText(pointRequestDto.getUsername())) {
            errors.rejectValue("username", "USERNAME_FIELD_NOT_BLANK", "username 필드는 빈 값일 수 없습니다.");
        }

        if(pointRequestDto.getPointAmt() <= 0) {
            errors.rejectValue("pointAmt", "POINT_AMT_FIELD_NOT_BELOW_ZERO", "pointAmt 필드는 0 포인트 이하일 수 없습니다.");
        }
    }
}
