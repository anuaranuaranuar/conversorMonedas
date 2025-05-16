package dto;

import java.util.List;

public record CodigoDto(
        List<List<String>> supported_codes,
        String result) {
}
