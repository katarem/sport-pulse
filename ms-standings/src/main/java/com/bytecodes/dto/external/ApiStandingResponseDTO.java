package com.bytecodes.dto.external;

import java.util.List;

public record ApiStandingResponseDTO(
        List<ResponseItemDTO> response
) {
}
