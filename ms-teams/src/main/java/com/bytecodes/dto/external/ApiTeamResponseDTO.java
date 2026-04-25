package com.bytecodes.dto.external;

import java.util.List;

public record ApiTeamResponseDTO(
        List<TeamWrapperDTO> response
) {
}
