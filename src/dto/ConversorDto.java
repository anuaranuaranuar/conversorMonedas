package dto;

public record ConversorDto(
        String result,
        String base_code,
        String target_code,
        String conversion_rate,
        String conversion_result ) {

}
