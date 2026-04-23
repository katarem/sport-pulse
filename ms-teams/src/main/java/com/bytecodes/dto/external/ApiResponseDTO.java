package com.bytecodes.dto.external;

import java.util.List;

public record ApiResponseDTO(
        List<TeamWrapperDTO> response
) {
}
